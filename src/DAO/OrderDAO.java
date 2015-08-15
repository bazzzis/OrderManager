/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Domain.Order;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Bazis
 */

  /**  object  to handle the state of OrderDAO object */
public interface OrderDAO {
    
    
   /** creates record and it's object */
    public int create(Order order) throws SQLException;

   
    public Order read(int orderId, boolean closeConnection)throws SQLException;

   
    public Order update(Order order)throws SQLException;
    
  
    
    public List<Order> findByRelation(int relation,String companyName ,int relType, Date pickUpDate)throws SQLException;

    public List<Order> findByReference(String reference, int relType)throws SQLException;
    
    public void block(int orderId)throws SQLException;
    
    
    
    public void unBlock(int orderId)throws SQLException;

    
    public List<Order> getAll() throws SQLException;
}
    

