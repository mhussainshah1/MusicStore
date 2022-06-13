package music.data;

import music.business.Download;

import javax.naming.NamingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DownloadDB {

    public static long insert(Download download) {

        String query = """
                    INSERT INTO Download(UserID, DownloadDate, ProductCode)
                    VALUES (?, NOW(), ?)
                """;

        ResultSet rs = null;
        try (ConnectionPool pool = ConnectionPool.getInstance();
             Connection connection = pool.getConnection();
             PreparedStatement ps = connection.prepareStatement(query);) {

            ps.setLong(1, download.getUser().getUserId());
            ps.setString(2, download.getProductCode());
            return ps.executeUpdate();
        } catch (SQLException | NamingException e) {
            e.printStackTrace();
            return 0;
        }
    }
}