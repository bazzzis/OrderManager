/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MySQL;

import DAO.AddressDAO;
import DAO.OrderDAO;
import Domain.Address;
import Domain.Order;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import java.util.logging.Level;
import static java.util.logging.Level.SEVERE;
import static java.util.logging.Level.WARNING;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Bazis
 */
public class MySqlOrderDao implements OrderDAO {

    private static final Logger LOG = Logger.getLogger(MySqlOrderDao.class.getName());

    Address address = null;
    AddressDAO addressDao = new MySqlAddressDao();
    Connection connection;
    PreparedStatement preparedStatement;
    ResultSet rs;

    @Override
    public int create(Order order) {

        int lastid = 0;

        String sql = "INSERT INTO `test`.`orders` "
                + "(`pickup_relation`, `pickup_CompanyName`, `pickup_StreetName`"
                + ", `pickup_City`, `pickup_ZIPCode`, `pickup_Country`, `pickup_reference`,"
                + " `delivery_relation`, `delivery_CompanyName`, `delivery_StreetName`,"
                + " `delivery_City`, `delivery_ZIPCode`, `delivery_Country`,"
                + " `delivery_reference`, `invoice_relation`, `invoice_reference`,"
                + " `pickup_date`, `pickup_time`, `delivery_date`, `delivery_time`,"
                + " `delivery_conditions`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
        try {
            connection = ConnectionFactory.getConnection();
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, order.getPickUpRelation());
            preparedStatement.setString(2, order.getPickUpCompanyName());
            preparedStatement.setString(3, order.getPickUpStreetName());
            preparedStatement.setString(4, order.getPickUpCity());
            preparedStatement.setInt(5, order.getPickUpZipCode());
            preparedStatement.setString(6, order.getPickUpCountry());
            preparedStatement.setString(7, order.getPickupReference());
            preparedStatement.setInt(8, order.getDeliveryRelation());
            preparedStatement.setString(9, order.getDeliveryCompanyName());
            preparedStatement.setString(10, order.getDeliveryStreetName());
            preparedStatement.setString(11, order.getDeliveryCity());
            preparedStatement.setInt(12, order.getDeliveryZipCode());
            preparedStatement.setString(13, order.getDeliveryCountry());
            preparedStatement.setString(14, order.getDeliveryReference());
            preparedStatement.setInt(15, order.getInvoiceRelation());
            preparedStatement.setString(16, order.getInvoiceReference());
            preparedStatement.setDate(17, order.getPickUpDate());
            preparedStatement.setTime(18, order.getPickUpTime());
            preparedStatement.setDate(19, order.getDeliveryDate());
            preparedStatement.setTime(20, order.getDeliveryTime());
            preparedStatement.setString(21, order.getDeliveryConditions());
            preparedStatement.executeUpdate();
            connection.commit();

            ResultSet rs = preparedStatement.executeQuery("select last_insert_id() as last_id from test.orders");

            while (rs.next()) {
                lastid = Integer.parseInt(rs.getString("last_id"));

            }

        } catch (SQLException e) {
            LOG.log(Level.SEVERE, null, e);
        } finally {
            DbUtil.close(rs);
            DbUtil.close(preparedStatement);
            DbUtil.close(connection);

        }

        return lastid;
    }

    @Override
    public Order read(int orderId, boolean closeConnection) {
        Order order = new Order();

        String sql = "SELECT * FROM test.orders WHERE order_id = " + orderId + ";";
        try {
            connection = ConnectionFactory.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            rs = preparedStatement.executeQuery(sql);

            rs.next();

            order.setOrderId(rs.getInt("order_id"));

            if (rs.getInt("pickup_relation") != 0) {
                address = addressDao.read(rs.getInt("pickup_relation"), false);
                order.setPickUpRelation(address.getId());
                order.setPickUpCompanyName(address.getCompanyName());
                order.setPickUpStreetName(address.getStreetName());
                order.setPickUpCity(address.getCity());
                order.setPickUpZipCode(address.getZipCode());
                order.setPickUpCountry(address.getCountry());

            } else {
                order.setPickUpRelation(0);
                order.setPickUpCompanyName(rs.getString("pickup_CompanyName"));
                order.setPickUpStreetName(rs.getString("pickup_StreetName"));
                order.setPickUpCity(rs.getString("pickup_City"));
                order.setPickUpZipCode(rs.getInt("pickup_ZIPCode"));
                order.setPickUpCountry(rs.getString("pickup_Country"));

            }
            order.setPickupReference(rs.getString("pickup_reference"));
            if (rs.getInt("delivery_relation") != 0) {
                address = addressDao.read(rs.getInt("delivery_relation"), false);
                order.setDeliveryRelation(address.getId());
                order.setDeliveryCompanyName(address.getCompanyName());
                order.setDeliveryStreetName(address.getStreetName());
                order.setDeliveryCity(address.getCity());
                order.setDeliveryZipCode(address.getZipCode());
                order.setDeliveryCountry(address.getCountry());

            } else {

                order.setDeliveryRelation(0);
                order.setDeliveryCompanyName(rs.getString("delivery_CompanyName"));
                order.setDeliveryStreetName(rs.getString("delivery_StreetName"));
                order.setDeliveryCity(rs.getString("delivery_City"));
                order.setDeliveryZipCode(rs.getInt("delivery_ZIPCode"));
                order.setDeliveryCountry(rs.getString("delivery_Country"));
            }
            order.setDeliveryReference(rs.getString("delivery_reference"));

            if (rs.getInt("invoice_relation") != 0) {

                address = addressDao.read(rs.getInt("invoice_relation"), false);

                order.setInvoiceRelation(address.getId());
                order.setInvoiceCompanyName(address.getCompanyName());
                order.setInvoiceStreetName(address.getStreetName());
                order.setInvoiceCity(address.getCity());
                order.setInvoiceZipCode(address.getZipCode());
                order.setInvoiceCountry(address.getCountry());
                order.setInvoiceVatNumber(address.getVATnumber());
            } else {
                System.out.println("Invoice address must have a relation!");
            }
            order.setInvoiceReference(rs.getString("invoice_reference"));
            order.setPickUpDate(rs.getDate("pickup_date"));

            order.setPickUpTime(rs.getTime("pickup_time"));
            order.setDeliveryDate(rs.getDate("delivery_date"));
            order.setDeliveryTime(rs.getTime("delivery_time"));
            order.setDeliveryConditions(rs.getString("delivery_conditions"));

            if (rs.getInt("block") == 1) {
                order.setBlock(true);

            } else {
                order.setBlock(false);
            }
            System.out.println("read order ID =" + order.getOrderId());

        } catch (SQLException ex) {
            LOG.log(Level.SEVERE, null, ex);
        } finally {
            if (closeConnection) {
                // DbUtil.close(rs);
                DbUtil.close(preparedStatement);
                DbUtil.close(connection);
            }
        }

        return order;
    }

    @Override
    public Order update(Order order) {
        try {
            int block = 0;

            if (order.isBlock()) {
                LOG.log(WARNING, "The order " + order.toString() + " is blocked, unblock it first");
                JOptionPane.showMessageDialog(null, "The order " + order.toString() + " is blocked, unblock it first", "Error!", JOptionPane.ERROR_MESSAGE);
            } else {

                String query = "UPDATE `test`.`orders` SET `pickup_relation`='" + order.getPickUpRelation() + "',"
                        + " `pickup_CompanyName`='" + order.getPickUpCompanyName() + "',"
                        + " `pickup_StreetName`='" + order.getPickUpStreetName() + "', "
                        + "`pickup_City`='" + order.getPickUpCity() + "', `pickup_ZIPCode`='" + order.getPickUpZipCode() + "',"
                        + " `pickup_Country`='" + order.getPickUpCountry() + "', `pickup_reference`='" + order.getPickupReference() + "',"
                        + " `delivery_relation`='" + order.getDeliveryRelation() + "', `delivery_CompanyName`='" + order.getDeliveryCompanyName() + "', "
                        + "`delivery_StreetName`='" + order.getDeliveryStreetName() + "', `delivery_City`='" + order.getDeliveryCity() + "', "
                        + "`delivery_ZIPCode`='" + order.getDeliveryZipCode() + "', `delivery_Country`='" + order.getDeliveryCountry() + "',"
                        + " `delivery_reference`='" + order.getDeliveryReference() + "', `invoice_reference`='" + order.getInvoiceReference() + "',"
                        + " `pickup_date`='" + order.getPickUpDate() + "', `pickup_time`='" + order.getPickUpTime() + "',"
                        + " `delivery_date`='" + order.getDeliveryDate() + "', `delivery_time`='" + order.getDeliveryTime() + "',"
                        + " `delivery_conditions`='" + order.getDeliveryConditions() + "', `block`='" + block + "' WHERE `order_id`='" + order.getOrderId() + "';";

                preparedStatement.executeUpdate(query);
            }

        } catch (SQLException ex) {

            LOG.log(SEVERE, (Supplier<String>) ex);

        } finally {
            DbUtil.close(rs);
            DbUtil.close(preparedStatement);
            DbUtil.close(connection);

        }
        return read(order.getOrderId(), true);
    }

    @Override
    public void block(int orderId) {
        try {
            String query = "UPDATE `test`.`orders` SET `block`='1' WHERE `order_id`=" + orderId + ";";
            connection = ConnectionFactory.getConnection();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.executeUpdate(query);
        } catch (SQLException ex) {
            Logger.getLogger(MySqlOrderDao.class.getName()).log(Level.SEVERE, null, ex);
        }finally {
            DbUtil.close(rs);
            DbUtil.close(preparedStatement);
            DbUtil.close(connection);

        }
    }

    @Override
    public void unBlock(int orderId) {
        try {
            String query = "UPDATE `test`.`orders` SET `block`='0' WHERE `order_id`=" + orderId + ";";
            connection = ConnectionFactory.getConnection();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.executeUpdate(query);
        } catch (SQLException ex) {
            Logger.getLogger(MySqlOrderDao.class.getName()).log(Level.SEVERE, null, ex);
        }finally {
            DbUtil.close(rs);
            DbUtil.close(preparedStatement);
            DbUtil.close(connection);

        }
    }

    @Override
    public List<Order> getAll() {
        List<Order> list = new ArrayList<>();

        try {

            connection = ConnectionFactory.getConnection();

            String sql = "SELECT * FROM test.orders Order by order_id desc;";

            preparedStatement = connection.prepareStatement(sql);

            rs = preparedStatement.executeQuery(sql);

            list = getOrderList(rs);

        } catch (SQLException ex) {
            LOG.log(Level.SEVERE, null, ex);
        } finally {
            DbUtil.close(rs);
            DbUtil.close(preparedStatement);
            DbUtil.close(connection);

        }
        return list;
    }

    @Override
    public List<Order> findByRelation(int relation, String companyName, int relType, Date pickUpDate) {
        List<Order> list = new ArrayList<>();

        try {
            if (relation == 0) {

                connection = ConnectionFactory.getConnection();

                switch (relType) {

                    case 1:
                        String sql = "SELECT * FROM test.orders WHERE pickup_CompanyName Like \"" + companyName + "%\" AND pickup_date >= '" + pickUpDate + "';";

                        preparedStatement = connection.prepareStatement(sql);
                        System.out.println(preparedStatement);

                        rs = preparedStatement.executeQuery(sql);

                        while (rs.next()) {
                            LOG.log(Level.INFO, null, "next from case 1");
                            list.add(read(rs.getInt(1), true));

                        }
                        break;
                    case 2:
                        sql = "SELECT * FROM test.orders WHERE delivery_CompanyName Like \"" + companyName + "%\" AND pickup_date >= '" + pickUpDate + "';";

                        preparedStatement = connection.prepareStatement(sql);

                        rs = preparedStatement.executeQuery(sql);

                        while (rs.next()) {

                            list.add(read(rs.getInt(1), true));

                        }
                        break;
                    case 3:
                        sql = "SELECT * FROM test.orders WHERE invoice_CompanyName = Like \"" + companyName + "%\" AND pickup_date >= '" + pickUpDate + "';";

                        preparedStatement = connection.prepareStatement(sql);

                        rs = preparedStatement.executeQuery(sql);

                        while (rs.next()) {

                            list.add(read(rs.getInt(1), true));

                        }
                        break;
                    default:
                        LOG.log(Level.SEVERE, null, "No such type of relation to lookup for");

                }
            } else {

                connection = ConnectionFactory.getConnection();

                switch (relType) {

                    case 1:
                        String sql = "SELECT * FROM test.orders WHERE pickup_relation = " + relation + " AND pickup_date >= '" + pickUpDate + "';";

                        preparedStatement = connection.prepareStatement(sql);
                        System.out.println(preparedStatement);

                        rs = preparedStatement.executeQuery(sql);

                        while (rs.next()) {
                            LOG.log(Level.INFO, null, "next from case 1");
                            list.add(read(rs.getInt(1), true));

                        }
                        break;
                    case 2:
                        sql = "SELECT * FROM test.orders WHERE delivery_relation = " + relation + " AND pickup_date >= '" + pickUpDate + "';";

                        preparedStatement = connection.prepareStatement(sql);

                        rs = preparedStatement.executeQuery(sql);

                        while (rs.next()) {

                            list.add(read(rs.getInt(1), true));

                        }
                        break;
                    case 3:
                        sql = "SELECT * FROM test.orders WHERE invoice_relation = " + relation + " AND pickup_date >= '" + pickUpDate + "';";

                        preparedStatement = connection.prepareStatement(sql);

                        rs = preparedStatement.executeQuery(sql);

                        while (rs.next()) {

                            list.add(read(rs.getInt(1), true));

                        }
                        break;
                    default:
                        LOG.log(Level.SEVERE, null, "No such type of relation to lookup for");

                }
            }
        } catch (SQLException ex) {
            LOG.log(Level.SEVERE, null, ex);
        } finally {
            DbUtil.close(rs);
            DbUtil.close(preparedStatement);
            DbUtil.close(connection);

        }
        return list;
    }

    @Override
    public List<Order> findByReference(String reference, int relType) {

        List<Order> list = new ArrayList<>();
        try {

            connection = ConnectionFactory.getConnection();
            String sql;
            switch (relType) {

                case 1:
                    sql = "SELECT * FROM test.orders WHERE pickup_reference Like \"" + reference + "%\" Order by order_id desc;";

                    break;
                case 2:
                    sql = "SELECT * FROM test.orders WHERE delivery_reference Like \"" + reference + "%\" Order by order_id desc;";

                    break;
                case 3:
                    sql = "SELECT * FROM test.orders WHERE invoice_reference Like \"" + reference + "%\" Order by order_id desc;";

                    break;

                default:
                    sql = "SELECT * FROM test.orders Order by order_id desc;";

            }
            if (sql != null) {
                preparedStatement = connection.prepareStatement(sql);

                rs = preparedStatement.executeQuery(sql);

                list = getOrderList(rs);

            }
        } catch (SQLException ex) {
            LOG.log(Level.SEVERE, null, ex);
        } finally {
            DbUtil.close(rs);
            DbUtil.close(preparedStatement);
            DbUtil.close(connection);

        }

        return list;

    }

    public List<Order> getOrdersById(int id) {
        List<Order> list = new ArrayList<>();
        Order order = read(id, true);
        if (order.getOrderId() != 0) {
            list.add(order);
        }
        return list;
    }

    private List<Order> getOrderList(ResultSet rs) throws SQLException {
        List<Integer> ids = new ArrayList<>();
        List<Order> list = new ArrayList<>();
        while (rs.next()) {
            ids.add(rs.getInt(1));
        }
        for (Object id : ids) {

            Order order = read(Integer.parseInt(id.toString()), false);
            list.add(order);

        }
        return list;

    }

}
