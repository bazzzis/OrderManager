/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Domain.Address;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Bazis
 */
public interface AddressDAO {
    
  
    public Address create(Address address) throws SQLException;

    
    public Address read(int key, boolean closeConnection) throws SQLException;

    
    public Address update(Address address)throws SQLException;

    
    public void block(Address address);
    
    
    
    public void unBlock(Address address);
    
    

    
    public List<Address> getAll()throws SQLException;
    
    public List<Address> findByCompanyName(String companyName)throws SQLException;
    
    public List<Address> findByAddress(String streetName, String Country, int postalCode)throws SQLException;
    public List<Address> findByAddress(String streetName, String Country,String City ,int postalCode)throws SQLException;
    public List<Address> findByAddress(String streetName, String Country,String City )throws SQLException;
    
}
