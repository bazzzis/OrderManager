/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MySQL;

import Domain.OrderDetails;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author bazziss
 */
public class MySqlOrderDetails {

    Connection connection;
    PreparedStatement preparedStatement;
    ResultSet rs = null;
    private static final Logger LOG = Logger.getLogger(MySqlAddressDao.class.getName());

    public void create(OrderDetails orderDetails) {

        try {
            String sql = "INSERT INTO `test`.`OrderDetails` (`PckagingType`, `Descritption`,"
                    + " `Weight`, `OrderId`) VALUES ('" + orderDetails.getPackagingType() + "','" + orderDetails.getDescription() + "',"
                    + "" + orderDetails.getWeight() + "," + orderDetails.getOrderID() + ");";

            connection = ConnectionFactory.getConnection();
            preparedStatement = connection.prepareStatement(sql);

            preparedStatement.executeUpdate(sql);

        } catch (SQLException ex) {
            Logger.getLogger(MySqlOrderDetails.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Data truncation: Out of range value ", "Error!", JOptionPane.ERROR_MESSAGE);

        } finally {

            DbUtil.close(preparedStatement);
            DbUtil.close(connection);

        }

    }

    public List<OrderDetails> readByProductId(int orderId) {
        List<OrderDetails> orderLines = new ArrayList<>();

        try {
            String sql = "SELECT * FROM test.OrderDetails WHERE OrderId = " + orderId + ";";
            connection = ConnectionFactory.getConnection();
            preparedStatement = connection.prepareStatement(sql);

            rs = preparedStatement.executeQuery(sql);

            while (rs.next()) {
                OrderDetails ol = new OrderDetails();

                ol.setId(rs.getInt(1));
                ol.setPackagingType(rs.getString(2));
                ol.setDescription(rs.getString(3));
                ol.setWeight(rs.getDouble(4));
                ol.setOrderID(orderId);

                orderLines.add(ol);

            }

        } catch (SQLException ex) {
            Logger.getLogger(MySqlOrderDetails.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            DbUtil.close(rs);
            DbUtil.close(preparedStatement);
            DbUtil.close(connection);

        }
        return orderLines;
    }

    public OrderDetails readById(int orderLineId) {
        OrderDetails ol = new OrderDetails();

        try {
            String sql = "SELECT * FROM test.OrderDetails WHERE Id = " + orderLineId + ";";
            connection = ConnectionFactory.getConnection();
            preparedStatement = connection.prepareStatement(sql);

            rs = preparedStatement.executeQuery(sql);

            rs.next();

            ol.setId(rs.getInt(1));
            ol.setPackagingType(rs.getString(2));
            ol.setDescription(rs.getString(3));
            ol.setWeight(rs.getDouble(4));
            ol.setOrderID(rs.getInt(5));

        } catch (SQLException ex) {
            Logger.getLogger(MySqlOrderDetails.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            DbUtil.close(rs);
            DbUtil.close(preparedStatement);
            DbUtil.close(connection);

        }
        return ol;
    }

    public void update(OrderDetails orderDetails) {

        try {
            String sql = "UPDATE `test`.`OrderDetails` SET `PckagingType`='" + orderDetails.getPackagingType() + "',"
                    + " `Descritption`='" + orderDetails.getDescription() + "', `Weight`=" + orderDetails.getWeight() + " WHERE id=" + orderDetails.getId() + ";";

            connection = ConnectionFactory.getConnection();
            preparedStatement = connection.prepareStatement(sql);

            preparedStatement.executeUpdate(sql);

        } catch (SQLException ex) {
            Logger.getLogger(MySqlOrderDetails.class.getName()).log(Level.SEVERE, null, ex);
            Logger.getLogger(MySqlOrderDetails.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Data truncation: Out of range value ", "Error!", JOptionPane.ERROR_MESSAGE);

        } finally {

            DbUtil.close(preparedStatement);
            DbUtil.close(connection);

        }

    }

    public void deleteByODetails(int orderDetailsId) {

        try {
            String sql = "DELETE FROM `test`.`OrderDetails` WHERE `id`= ?;";
            connection = ConnectionFactory.getConnection();
            connection.setAutoCommit(false);
             preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, orderDetailsId);
           
            preparedStatement.executeUpdate();
            connection.commit();

        } catch (SQLException ex) {
            Logger.getLogger(MySqlOrderDetails.class.getName()).log(Level.SEVERE, null, ex);
        } finally {

            DbUtil.close(preparedStatement);
            DbUtil.close(connection);

        }

    }

    public void deleteByOrder(int orderId) {

        try {
            String sql = "DELETE FROM `test`.`OrderDetails` WHERE OrderId=" + orderId + ";";

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
