package music.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import music.business.Download;
import music.business.Product;
import music.business.User;
import music.data.DownloadDB;
import music.data.ProductDB;
import music.data.UserDB;
import music.util.CookieUtil;

import java.io.IOException;

@WebServlet(name = "CatalogController", value = "/catalog/product/*")
public class CatalogController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String requestURI = request.getRequestURI();
        String url;
        if (requestURI.endsWith("/listen")) {
            url = listen(request, response);
        } else {
            url = showProduct(request, response);
        }
        getServletContext().getRequestDispatcher(url).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String requestURI = request.getRequestURI();
        String url = "/catalog";
        if (requestURI.endsWith("/register")) {
            url = registerUser(request, response);
        }
        getServletContext().getRequestDispatcher(url).forward(request, response);
    }

    private String showProduct(HttpServletRequest request, HttpServletResponse response) {
        String productCode = request.getPathInfo();
        if (productCode != null) {
            productCode = productCode.substring(1);
            Product product = ProductDB.selectProduct(productCode);
            HttpSession session = request.getSession();
            session.setAttribute("product", product);
        }
        return "/catalog/" + productCode + "/index.jsp";
    }

    private String listen(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null) {
            Cookie[] cookies = request.getCookies();
            String emailAddress = CookieUtil.getCookieValue(cookies, "emailCookie");
            if (emailAddress == null || emailAddress.isBlank()) {
                return "/catalog/register.jsp";
            } else {
                user = UserDB.selectUser(emailAddress);
                if (user == null) {
                    return "/catalog/register.jsp";
                }
                session.setAttribute("user", user);
            }
        }
        Product product = (Product) session.getAttribute("product");
        Download download = new Download();
        download.setUser(user);
        download.setProductCode(product.getCode());
        DownloadDB.insert(download);
        return "/catalog/" + product.getCode() + "/sound.jsp";
    }

    private String registerUser(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String email = request.getParameter("email");

        User user;
        if(UserDB.emailExists(email)){
            user = UserDB.selectUser(email);
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setEmail(email);
            UserDB.update(user);
        } else {
            user=new User();
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setEmail(email);
            UserDB.insert(user);
        }
        session.setAttribute("user",user);

        Cookie emailCookie = new Cookie("emailCookie", email);
        emailCookie.setMaxAge(60*60*24*365*2);
        emailCookie.setPath("/");
        response.addCookie(emailCookie);

        Product product = (Product) session.getAttribute("product");
        String url = "/catalog/" + product.getCode()+"/sound.jsp";
        return url;
    }
}
