/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MySQL;

import Domain.OrderDetails;
import Domain.State;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author bazziss
 */
public class MysqlStateDao {
    
     Connection connection;
    PreparedStatement preparedStatement;
    ResultSet rs = null;
    
    public List<State> readByOrderId(int orderId) {
        
        
        List<State> state = new ArrayList<>();

        try {
            String sql = "SELECT * FROM test.state WHERE OrderId = " + orderId + " Order by id desc;";
            connection = ConnectionFactory.getConnection();
            preparedStatement = connection.prepareStatement(sql);

            rs = preparedStatement.executeQuery(sql);

            while (rs.next()) {
                State ol = new State();

                ol.setOrderId(rs.getInt(2));
                ol.setAction(rs.getString(3));
                ol.setOk(rs.getString(4));
                ol.setTimeStamp(rs.getTimestamp(5).toString());
               

                state.add(ol);

            }

        } catch (SQLException ex) {
            Logger.getLogger(MySqlOrderDetails.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            DbUtil.close(rs);
            DbUtil.close(preparedStatement);
            DbUtil.close(connection);

        }
        return state;
    }
    
    public void add(State state) {
        
        
        

        try {
            String sql = "INSERT INTO `test`.`state` (`orderId`, `action`, `ok/nok`) VALUES ("+state.getOrderId()+", '"+state.getAction()+"', '"+state.getOk()+"');";
            connection = ConnectionFactory.getConnection();
            preparedStatement = connection.prepareStatement(sql);

           preparedStatement.executeUpdate(sql);

           

        } catch (SQLException ex) {
            Logger.getLogger(MySqlOrderDetails.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            
            DbUtil.close(preparedStatement);
            DbUtil.close(connection);

        }
}
}