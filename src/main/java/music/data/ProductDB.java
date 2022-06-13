package music.data;

import music.business.Product;

import javax.naming.NamingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDB {
    public static Product selectProduct(String productCode) {
        String query = """
                SELECT *
                FROM product
                WHERE ProductCode = ?
                """;
        ResultSet rs;
        try (ConnectionPool pool = ConnectionPool.getInstance();
             Connection connection = pool.getConnection();
             PreparedStatement ps = connection.prepareStatement(query);) {
            ps.setString(1, productCode);
            rs = ps.executeQuery();
            if (rs.next()) {
                return new Product(rs.getLong("ProductID"),
                        rs.getString("ProductCode"),
                        rs.getString("ProductDescription"),
                        rs.getDouble("ProductPrice"));
            } else {
                return null;
            }
        } catch (SQLException | NamingException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Product selectProduct(long productID) {
        String query = """
                SELECT *
                FROM product
                WHERE ProductID = ?
                """;
        ResultSet rs;
        try (ConnectionPool pool = ConnectionPool.getInstance();
             Connection connection = pool.getConnection();
             PreparedStatement ps = connection.prepareStatement(query);) {
            ps.setLong(1, productID);
            rs = ps.executeQuery();
            if (rs.next()) {
                return new Product(rs.getLong("ProductID"),
                        rs.getString("ProductCode"),
                        rs.getString("ProductDescription"),
                        rs.getDouble("ProductPrice"));
            } else {
                return null;
            }
        } catch (SQLException | NamingException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static List<Product> selectProducts() {
        String query = """
                SELECT *
                FROM product         
                """;
        try (ConnectionPool pool = ConnectionPool.getInstance();
             Connection connection = pool.getConnection();
             PreparedStatement ps = connection.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {
            List<Product> products = new ArrayList<>();
            if (rs.next()) {
                Product product = new Product();
                product.setCode(rs.getString("ProductCode"));
                product.setDescription(rs.getString("ProductDescription"));
                product.setPrice(rs.getDouble("ProductPrice"));
                products.add(product);
                return products;
            } else {
                return null;
            }
        } catch (SQLException | NamingException e) {
            e.printStackTrace();
            return null;
        }
    }
}
