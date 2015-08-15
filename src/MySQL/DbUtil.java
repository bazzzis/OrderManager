package MySQL;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
 
public class DbUtil {
    private static final Logger LOG = Logger.getLogger(DbUtil.class.getName());
    
 
    public static void close(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
                LOG.log(Level.INFO, "Connection closed");
            } catch (SQLException e) {
                /*Ignore*/
            }
        }
    }
 
    public static void close(Statement statement) {
        if (statement != null) {
            try {
                statement.close();
                  LOG.log(Level.INFO, "statement closed");
            } catch (SQLException e) {
                /*Ignore*/
            }
        }
    }
 
    public static void close(ResultSet resultSet) {
        if (resultSet != null) {
            try {
                resultSet.close();
                LOG.log(Level.INFO, "resultSet closed");
            } catch (SQLException e) {
                /*Ignore*/
            }
        }
    }
}
