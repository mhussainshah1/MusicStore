package music.data;

import music.business.User;

import javax.naming.NamingException;
import java.sql.*;

public class UserDB {

    public static void insert(User user) {
        String query = """
                INSERT INTO User (FirstName, LastName, Email, CompanyName,
                Address1, Address2, City, State, Zip, Country,
                CreditCardType, CreditCardNumber, CreditCardExpirationDate)
                VALUES (?, ?, ?, ?,
                ?, ?, ?, ?, ?, ?,
                ?, ?, ?)
                """;
        try (ConnectionPool pool = ConnectionPool.getInstance();
             Connection connection = pool.getConnection();
             PreparedStatement ps = connection.prepareStatement(query);
             ResultSet rs = null;) {
            ps.setString(1, user.getFirstName());
            ps.setString(2, user.getLastName());
            ps.setString(3, user.getEmail());
            ps.setString(4, user.getCompanyName());
            ps.setString(5, user.getAddress1());
            ps.setString(6, user.getAddress2());
            ps.setString(7, user.getCity());
            ps.setString(8, user.getState());
            ps.setString(9, user.getZip());
            ps.setString(10, user.getCountry());
            ps.setString(11, user.getCreditCardType());
            ps.setString(12, user.getCreditCardNumber());
            ps.setString(13, user.getCreditCardExpirationDate());

            ps.executeUpdate();

            //Get the user ID from the last INSERT statement.
            String identityQuery = "SELECT @@IDENTITY AS IDENTITY";
            Statement identityStatement = connection.createStatement();
            ResultSet identityResultSet = identityStatement.executeQuery(identityQuery);
            identityResultSet.next();
            long userID = identityResultSet.getLong("IDENTITY");
            identityResultSet.close();
            identityStatement.close();

            // Set the user ID in the User object
            user.setUserId(userID);
        } catch (SQLException | NamingException e) {
            e.printStackTrace();
        }
    }

    public static void update(User user) {
        String query = """
                UPDATE User
                SET FirstName = ?,
                LastName = ?,
                CompanyName = ?,
                Address1 = ?,
                Address2 = ?,
                City = ?,
                State = ?,
                Zip = ?,
                Country = ?,
                CreditCardType = ?,
                CreditCardNumber = ?,
                CreditCardExpirationDate = ?
                WHERE Email = ?
                """;
        try (ConnectionPool pool = ConnectionPool.getInstance();
             Connection connection = pool.getConnection();
             PreparedStatement ps = connection.prepareStatement(query);
             ResultSet rs = null;) {
            ps.setString(1, user.getFirstName());
            ps.setString(2, user.getLastName());
            ps.setString(3, user.getCompanyName());
            ps.setString(4, user.getAddress1());
            ps.setString(5, user.getAddress2());
            ps.setString(6, user.getCity());
            ps.setString(7, user.getState());
            ps.setString(8, user.getZip());
            ps.setString(9, user.getCountry());
            ps.setString(10, user.getCreditCardType());
            ps.setString(11, user.getCreditCardNumber());
            ps.setString(12, user.getCreditCardExpirationDate());
            ps.setString(13, user.getEmail());

            ps.executeUpdate();
        } catch (SQLException | NamingException e) {
            e.printStackTrace();
        }
    }

    public static User selectUser(String email) {
        String query = """
                SELECT * FROM User
                WHERE Email = ?
                """;
        ResultSet rs = null;
        try (ConnectionPool pool = ConnectionPool.getInstance();
             Connection connection = pool.getConnection();
             PreparedStatement ps = connection.prepareStatement(query);) {
            ps.setString(1, email);
            rs = ps.executeQuery();
            User user = null;
            if (rs.next()) {
                user = new User();
                user.setUserId(rs.getLong("UserID"));
                user.setFirstName(rs.getString("FirstName"));
                user.setLastName(rs.getString("LastName"));
                user.setEmail(rs.getString("Email"));
                user.setCompanyName(rs.getString("CompanyName"));
                user.setAddress1(rs.getString("Address1"));
                user.setAddress2(rs.getString("Address2"));
                user.setCity(rs.getString("City"));
                user.setState(rs.getString("State"));
                user.setZip(rs.getString("Zip"));
                user.setCountry(rs.getString("Country"));
                user.setCreditCardType(rs.getString("CreditCardType"));
                user.setCreditCardNumber(rs.getString("CreditCardNumber"));
                user.setCreditCardExpirationDate(rs.getString("CreditCardExpirationDate"));
            }
            return user;
        } catch (SQLException | NamingException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean emailExists(String email) {
        String query = """
                SELECT Email FROM User
                WHERE Email = ?
                """;
        ResultSet rs = null;
        try (ConnectionPool pool = ConnectionPool.getInstance();
             Connection connection = pool.getConnection();
             PreparedStatement ps = connection.prepareStatement(query);) {
            ps.setString(1, email);
            rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException | NamingException e) {
            e.printStackTrace();
            return false;
        }
    }
}