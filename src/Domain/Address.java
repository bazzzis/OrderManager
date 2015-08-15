/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Domain;

/**
 *
 * @author Bazis
 */
public class Address {
    
    private String CompanyName,StreetName,Country,City,VATnumber;
    private int zipCode,id;
    private boolean block=false;
    
    

    public boolean isBlock() {
        return block;
    }

    public void setBlock(boolean block) {
        this.block = block;
    }

    public void setId(int id) {
        this.id = id;
    }
    
     public int getId() {
         return id;
    }

    public String getCompanyName() {
        return CompanyName;
    }

    public void setCompanyName(String CompanyName) {
        this.CompanyName = CompanyName;
    }

    public String getStreetName() {
        return StreetName;
    }

    public void setStreetName(String StreetName) {
        this.StreetName = StreetName;
    }

    public String getCountry() {
        return Country;
    }

    public void setCountry(String Country) {
        this.Country = Country;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String City) {
        this.City = City;
    }

    public String getVATnumber() {
        return VATnumber;
    }

    public void setVATnumber(String VATnumber) {
        this.VATnumber = VATnumber;
    }

    public int getZipCode() {
        return zipCode;
    }

    public void setZipCode(int zipCode) {
        this.zipCode = zipCode;
    }

    @Override
    public String toString() {
        return "Address{" + "CompanyName=" + CompanyName + ", StreetName=" + StreetName + ", Country=" + Country + ", City=" + City + ", VATnumber=" + VATnumber + ", zipCode=" + zipCode + ", id=" + id + ", block=" + block + '}';
    }

   
    
    
    
}
