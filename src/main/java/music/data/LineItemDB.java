package music.data;

import music.business.LineItem;
import music.business.Product;

import javax.naming.NamingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LineItemDB {

    //This method adds one lineItem to the LineItems table.
    public static long insert(long invoiceID, LineItem lineItem) {
        String query = """
                INSERT INTO LineItem(InvoiceID, ProductID, Quantity)
                VALUES (?, ?, ?)
                """;
        try (ConnectionPool pool = ConnectionPool.getInstance();
             Connection connection = pool.getConnection();
             PreparedStatement ps = connection.prepareStatement(query);
             ResultSet rs = null;) {

            ps.setLong(1, invoiceID);
            ps.setLong(2, lineItem.getProduct().getProductId());
            ps.setInt(3, lineItem.getQuantity());
            return ps.executeUpdate();
        } catch (SQLException | NamingException e) {
            e.printStackTrace();
            return 0;
        }
    }

    //This method returns null if a record isn't found.
    public static List<LineItem> selectLineItems(long invoiceID) {
        String query = """
                SELECT * FROM LineItem
                WHERE InvoiceID = ?
                """;
        ResultSet rs = null;
        try (ConnectionPool pool = ConnectionPool.getInstance();
             Connection connection = pool.getConnection();
             PreparedStatement ps = connection.prepareStatement(query);) {

            ps.setLong(1, invoiceID);
            rs = ps.executeQuery();
            List<LineItem> lineItems = new ArrayList<>();
            while (rs.next()) {
                LineItem lineItem = new LineItem();
                int productID = rs.getInt("ProductID");
                Product product = ProductDB.selectProduct(productID);
                lineItem.setProduct(product);
                lineItem.setQuantity(rs.getInt("Quantity"));
                lineItems.add(lineItem);
            }
            return lineItems;
        } catch (SQLException | NamingException e) {
            e.printStackTrace();
            return null;
        }
    }
}