package MySQL;

import DAO.AddressDAO;
import Domain.Address;
import java.sql.Connection;
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
public class MySqlAddressDao implements AddressDAO {

    Connection connection;
    PreparedStatement preparedStatement;
    ResultSet rs = null;
    private static final Logger LOG = Logger.getLogger(MySqlAddressDao.class.getName());

    public MySqlAddressDao() {

    }

    @Override
    public Address create(Address address) {

        Address tmpAddress = null;

        try {

            String query = "INSERT INTO `test`.`address` (`CompanyName`, `StreetName`,"
                    + " `Country`, `City`, `ZIPCode`, `VAT number`) VALUES"
                    + " ('" + address.getCompanyName() + "', '" + address.getStreetName() + ""
                    + "', '" + address.getCountry() + "', '" + address.getCity() + "',"
                    + " '" + address.getZipCode() + "', '" + address.getVATnumber() + "');";

            connection = ConnectionFactory.getConnection();

            preparedStatement = connection.prepareStatement(query);

            preparedStatement.execute(query);

            query = "SELECT * FROM `test`.`address` ORDER BY address_id DESC LIMIT 0, 1";

            rs = preparedStatement.executeQuery(query);
            rs.next();

            tmpAddress = read(rs.getInt(1), true);

        } catch (SQLException ex) {
            Logger.getLogger(MySqlAddressDao.class.getName()).log(Level.SEVERE, "Can not establich connection from MYsqldaOADDRESS CLASS", ex);
        } finally {

            DbUtil.close(rs);
            DbUtil.close(preparedStatement);
            DbUtil.close(connection);
        }
        return tmpAddress;
    }

    @Override
    public Address read(int addressId, boolean closeConnection) {
        Address address = new Address();

        try {
            connection = ConnectionFactory.getConnection();

            String query = "SELECT * FROM test.address WHERE address_id = " + addressId + ";";
            
            preparedStatement = connection.prepareStatement(query);

            rs = preparedStatement.executeQuery(query);

            rs.next();

            address.setId(rs.getInt("address_id"));
            address.setCompanyName(rs.getString("CompanyName"));
            address.setStreetName(rs.getString("StreetName"));
            address.setCountry(rs.getString("Country"));
            address.setCity(rs.getString("City"));
            address.setZipCode(rs.getInt("ZIPCode"));
            address.setVATnumber(rs.getString("VAT number"));
            if (rs.getInt("block") == 1) {
                address.setBlock(true);

            } else {
                address.setBlock(false);
            }

        } catch (SQLException ex) {
            Logger.getLogger(MySqlAddressDao.class.getName()).log(Level.SEVERE, null, ex);
               JOptionPane.showMessageDialog(null, "There is no such relation ID, \nthe result is null! ", "Attention!", JOptionPane.WARNING_MESSAGE);
       
        
        } finally {

            if (closeConnection) {
                DbUtil.close(rs);
                DbUtil.close(preparedStatement);
                DbUtil.close(connection);
            }
        }
        return address;
    }

    @Override
    public Address update(Address address) {
        try {
            int block = 0;

            if (address.isBlock()) {
                LOG.log(WARNING, "The address " + address.toString() + " is blocked, unblock it first");
            } else {

                String query = "UPDATE `test`.`address` SET `CompanyName`='" + address.getCompanyName() + "',"
                        + " `StreetName`='" + address.getStreetName() + "', `Country`='Zimbabwe',"
                        + " `City`='" + address.getCity() + "', `ZIPCode`='" + address.getZipCode() + "',"
                        + " `VAT number`='" + address.getVATnumber() + "',`block`='" + block + "'"
                        + " WHERE `address_id`='" + address.getId() + "';";

                preparedStatement.executeUpdate(query);
            }

        } catch (SQLException ex) {

            LOG.log(SEVERE, (Supplier<String>) ex);

        } finally {
            DbUtil.close(rs);
            DbUtil.close(preparedStatement);
            DbUtil.close(connection);

        }
        return read(address.getId(), true);

    }

    @Override
    public void block(Address address) {

        if (!address.isBlock()) {
            address.setBlock(true);
            update(address);
        } else {
            LOG.log(WARNING, "The address " + address.toString() + " is already blocked");
        }
    }

    @Override
    public void unBlock(Address address) {

        if (address.isBlock()) {
            address.setBlock(false);
            update(address);
        } else {
            LOG.log(WARNING, "The address " + address.toString() + " is not blocked");
        }
    }

    @Override
    public List<Address> getAll() {
        List<Address> list = new ArrayList<>();

        try {

            connection = ConnectionFactory.getConnection();

            String sql = "SELECT * FROM test.address;";
            preparedStatement = connection.prepareStatement(sql);

            rs = preparedStatement.executeQuery(sql);

            while (rs.next()) {
                Address g = new Address();
                g.setId(rs.getInt("address_id"));
                g.setCompanyName(rs.getString("CompanyName"));
                g.setStreetName(rs.getString("StreetName"));
                g.setCountry(rs.getString("Country"));
                g.setCity(rs.getString("City"));
                g.setZipCode(rs.getInt("ZIPCode"));
                g.setVATnumber(rs.getString("VAT number"));
                if (rs.getInt("block") == 1) {
                    g.setBlock(true);

                } else {
                    g.setBlock(false);
                }
                list.add(g);
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
    public List<Address> findByCompanyName(String companyName) throws SQLException {
        List<Address> list = new ArrayList<>();
        companyName.trim();
        try {

            connection = ConnectionFactory.getConnection();

            String sql = "SELECT * FROM test.address WHERE CompanyName Like \"" + companyName + "%\";";

            preparedStatement = connection.prepareStatement(sql);

            rs = preparedStatement.executeQuery(sql);

            while (rs.next()) {

                list.add(read(rs.getInt(1), true));

            }
        } catch (SQLException ex) {
            Logger.getLogger(MySqlAddressDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            DbUtil.close(rs);
            DbUtil.close(preparedStatement);
            DbUtil.close(connection);

        }
        return list;
    }

    @Override
    public List<Address> findByAddress(String streetName, String Country, int postalCode) throws SQLException {
        List<Address> list = new ArrayList<>();
        streetName.trim();
        try {

            connection = ConnectionFactory.getConnection();

            String sql = "SELECT * FROM test.address WHERE StreetName Like \"" + streetName + "%\" AND Country = \"" + Country + "\" AND ZIPCode =\'" + postalCode + "\';";
            preparedStatement = connection.prepareStatement(sql);

            rs = preparedStatement.executeQuery(sql);

            while (rs.next()) {

                list.add(read(rs.getInt(1), true));

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
    public List<Address> findByAddress(String streetName, String Country, String City, int postalCode) throws SQLException {
        List<Address> list = new ArrayList<>();
        streetName.trim();
        try {

            connection = ConnectionFactory.getConnection();

            String sql = "SELECT * FROM test.address WHERE StreetName Like \"" + streetName + "%\" AND Country = \"" + Country + "\" AND City = \"" + City + "\" AND ZIPCode =\'" + postalCode + "\';";
            preparedStatement = connection.prepareStatement(sql);

            rs = preparedStatement.executeQuery(sql);

            while (rs.next()) {

                list.add(read(rs.getInt(1), true));

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
    public List<Address> findByAddress(String streetName, String Country, String City) throws SQLException {
        List<Address> list = new ArrayList<>();
        streetName.trim();
        try {
            connection = ConnectionFactory.getConnection();

            String sql = "SELECT * FROM test.address WHERE StreetName Like \"" + streetName + "%\" AND Country = \"" + Country + "\" AND City = \"" + City + "\";";
            preparedStatement = connection.prepareStatement(sql);

             ResultSet rsi = preparedStatement.executeQuery(sql);

            while (rsi.next()) {

                list.add(read(rsi.getInt(1), true));

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

}
