/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MySQL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Bazis
 */
public class ConnectionFactory implements AutoCloseable {

    private static final Logger LOG = Logger.getLogger(ConnectionFactory.class.getName());

    private static final ConnectionFactory instance = new ConnectionFactory();
    private final String user = "root";
    private final String password = "fnb271";
    // private final String url = "";
    private final String url = "jdbc:mysql://localhost:3306/test";
    // private final String driver = "com.mysql.jdbc.Driver";

    static Connection connection = null;

    private Connection createConnection() {
        try {
            if (connection == null || connection.isClosed()) {

                connection = DriverManager.getConnection(url, user, password);
                LOG.log(Level.INFO, "Connected!" + url);

            }
        } catch (SQLException ex) {
            LOG.log(Level.SEVERE, " Unable to Connect to Database. ");
        }
        return connection;
    }

    public static Connection getConnection() {
        return instance.createConnection();
    }

    @Override
    public void close() throws Exception {
        try {
            connection.close();
        } catch (SQLException ex) {
            LOG.log(Level.SEVERE, null, ex);
        }

    }

}
