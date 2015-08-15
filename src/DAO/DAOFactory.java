/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * @author Bazis
 */
public interface DAOFactory {
    
    /** returns database connection */
    public Connection getConnection() throws SQLException;

    /** return an object  to handle the state of OrderDAO object */
    public OrderDAO getOrderDao(Connection connection);

    /** return an object  to handle the state of AddressDAO object */
    public AddressDAO getAddressDao(Connection connection);
    
}
