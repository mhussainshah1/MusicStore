package music.util;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import jakarta.servlet.http.HttpSessionAttributeListener;
import jakarta.servlet.http.HttpSessionBindingEvent;
import jakarta.servlet.http.HttpSessionEvent;
import jakarta.servlet.http.HttpSessionListener;

import java.time.LocalDate;
import java.util.ArrayList;

@WebListener
public class MusicStoreContextListener implements ServletContextListener, HttpSessionListener, HttpSessionAttributeListener {

    public MusicStoreContextListener() {
    }

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        /* This method is called when the servlet context is initialized(when the Web application is deployed). */
        ServletContext sc = sce.getServletContext();

        // get the absolute paths for swithing regular and secure connections
        String contextPath = sc.getContextPath();
        String absolutePath = "http://localhost:8080" + contextPath;
        String absolutePathSecure = "https://localhost:8443" + contextPath;
        sc.setAttribute("absolutePath", absolutePath);
        sc.setAttribute("absolutePathSecure", absolutePathSecure);

        // initialize the customer service email address that's used throughout the website
        String custServEmail = sc.getInitParameter("custServEmail");
        sc.setAttribute("custServEmail", custServEmail);

        // initialize the current year that's used in the copyright notice
        LocalDate currentDate = LocalDate.now();
        int currentYear = currentDate.getYear();
        sc.setAttribute("currentYear", currentYear);

        // initialize the array of years that's used for the credit card year
        ArrayList<String> creditCardYears = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            int year = currentYear + i;
            String yearString = Integer.toString(year);
            creditCardYears.add(yearString);
        }
        sc.setAttribute("creditCardYears", creditCardYears);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        /* This method is called when the servlet Context is undeployed or Application Server shuts down. */
    }

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        /* Session is created. */
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        /* Session is destroyed. */
    }

    @Override
    public void attributeAdded(HttpSessionBindingEvent sbe) {
        /* This method is called when an attribute is added to a session. */
    }

    @Override
    public void attributeRemoved(HttpSessionBindingEvent sbe) {
        /* This method is called when an attribute is removed from a session. */
    }

    @Override
    public void attributeReplaced(HttpSessionBindingEvent sbe) {
        /* This method is called when an attribute is replaced in a session. */
    }
}
