/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Domain;

import java.sql.Date;
import java.sql.Time;

/**
 *
 * @author Bazis
 */
public class Order {

    private String pickUpCompanyName, pickUpStreetName, pickUpCountry, pickUpCity, pickupReference,
            deliveryCompanyName, deliveryStreetName, deliveryCountry, deliveryCity, deliveryReference,
            deliveryConditions, invoiceCompanyName, invoiceStreetName, invoiceCountry, invoiceCity,invoiceVatNumber,
            invoiceReference;
    private int orderId, pickUpRelation, pickUpZipCode, deliveryRelation, deliveryZipCode,
            invoiceRelation, invoiceZipCode;
    private Date pickUpDate, deliveryDate;
    private Time pickUpTime, deliveryTime;
    private boolean block = false;
    
    
    public String getInvoiceVatNumber() {
        return invoiceVatNumber;
    }

    public void setInvoiceVatNumber(String invoiceVatNumber) {
        this.invoiceVatNumber = invoiceVatNumber;
    }
    

    public String getInvoiceStreetName() {
        return invoiceStreetName;
    }

    public void setInvoiceStreetName(String invoiceStreetName) {
        this.invoiceStreetName = invoiceStreetName;
    }

    public String getInvoiceCompanyName() {
        return invoiceCompanyName;
    }

    public void setInvoiceCompanyName(String invoiceCompanyName) {
        this.invoiceCompanyName = invoiceCompanyName;
    }

    public String getInvoiceCountry() {
        return invoiceCountry;
    }

    public void setInvoiceCountry(String invoiceCountry) {
        this.invoiceCountry = invoiceCountry;
    }

    public String getInvoiceCity() {
        return invoiceCity;
    }

    public void setInvoiceCity(String invoiceCity) {
        this.invoiceCity = invoiceCity;
    }

    public int getInvoiceZipCode() {
        return invoiceZipCode;
    }

    public void setInvoiceZipCode(int invoiceZipCode) {
        this.invoiceZipCode = invoiceZipCode;
    }

    public String getPickUpCompanyName() {
        return pickUpCompanyName;
    }

    public void setPickUpCompanyName(String pickUpCompanyName) {
        this.pickUpCompanyName = pickUpCompanyName;
    }

    public String getPickUpStreetName() {
        return pickUpStreetName;
    }

    public void setPickUpStreetName(String pickUpStreetName) {
        this.pickUpStreetName = pickUpStreetName;
    }

    public String getPickUpCountry() {
        return pickUpCountry;
    }

    public void setPickUpCountry(String pickUpCountry) {
        this.pickUpCountry = pickUpCountry;
    }

    public String getPickUpCity() {
        return pickUpCity;
    }

    public void setPickUpCity(String pickUpCity) {
        this.pickUpCity = pickUpCity;
    }

    public String getPickupReference() {
        return pickupReference;
    }

    public void setPickupReference(String pickupReference) {
        this.pickupReference = pickupReference;
    }

    public String getDeliveryCompanyName() {
        return deliveryCompanyName;
    }

    public void setDeliveryCompanyName(String deliveryCompanyName) {
        this.deliveryCompanyName = deliveryCompanyName;
    }

    public String getDeliveryStreetName() {
        return deliveryStreetName;
    }

    public void setDeliveryStreetName(String deliveryStreetName) {
        this.deliveryStreetName = deliveryStreetName;
    }

    public String getDeliveryCountry() {
        return deliveryCountry;
    }

    public void setDeliveryCountry(String deliveryCountry) {
        this.deliveryCountry = deliveryCountry;
    }

    public String getDeliveryCity() {
        return deliveryCity;
    }

    public void setDeliveryCity(String deliveryCity) {
        this.deliveryCity = deliveryCity;
    }

    public String getDeliveryReference() {
        return deliveryReference;
    }

    public void setDeliveryReference(String deliveryReference) {
        this.deliveryReference = deliveryReference;
    }

    public String getDeliveryConditions() {
        return deliveryConditions;
    }

    public void setDeliveryConditions(String deliveryConditions) {
        this.deliveryConditions = deliveryConditions;
    }

    public String getInvoiceReference() {
        return invoiceReference;
    }

    public void setInvoiceReference(String invoiceRefrence) {
        this.invoiceReference = invoiceRefrence;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getPickUpRelation() {
        return pickUpRelation;
    }

    public void setPickUpRelation(int pickUpRelation) {
        this.pickUpRelation = pickUpRelation;
    }

    public int getPickUpZipCode() {
        return pickUpZipCode;
    }

    public void setPickUpZipCode(int pickUpZipCode) {
        this.pickUpZipCode = pickUpZipCode;
    }

    public int getDeliveryRelation() {
        return deliveryRelation;
    }

    public void setDeliveryRelation(int deliveryRelation) {
        this.deliveryRelation = deliveryRelation;
    }

    public int getDeliveryZipCode() {
        return deliveryZipCode;
    }

    public void setDeliveryZipCode(int deliveryZipCode) {
        this.deliveryZipCode = deliveryZipCode;
    }

    public int getInvoiceRelation() {
        return invoiceRelation;
    }

    public void setInvoiceRelation(int invoiceRelation) {
        this.invoiceRelation = invoiceRelation;
    }

    public Date getPickUpDate() {
        return pickUpDate;
    }

    public void setPickUpDate(Date pickUpDate) {
        this.pickUpDate = pickUpDate;
    }

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public Time getPickUpTime() {
        return pickUpTime;
    }

    public void setPickUpTime(Time pickUpTime) {
        this.pickUpTime = pickUpTime;
    }

    public Time getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(Time deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public boolean isBlock() {
        return block;
    }

    public void setBlock(boolean block) {
        this.block = block;
    }

    @Override
    public String toString() {
        return "Order{" + "pickUpCompanyName=" + pickUpCompanyName + ", pickUpStreetName=" + pickUpStreetName + ", pickUpCountry=" + pickUpCountry + ", pickUpCity=" + pickUpCity + ", pickupReference=" + pickupReference + ", deliveryCompanyName=" + deliveryCompanyName + ", deliveryStreetName=" + deliveryStreetName + ", deliveryCountry=" + deliveryCountry + ", deliveryCity=" + deliveryCity + ", deliveryReference=" + deliveryReference + ", deliveryConditions=" + deliveryConditions + ", invoiceCompanyName=" + invoiceCompanyName + ", invoiceStreetName=" + invoiceStreetName + ", invoiceCountry=" + invoiceCountry + ", invoiceCity=" + invoiceCity + ", invoiceVatNumber=" + invoiceVatNumber + ", invoiceReference=" + invoiceReference + ", orderId=" + orderId + ", pickUpRelation=" + pickUpRelation + ", pickUpZipCode=" + pickUpZipCode + ", deliveryRelation=" + deliveryRelation + ", deliveryZipCode=" + deliveryZipCode + ", invoiceRelation=" + invoiceRelation + ", invoiceZipCode=" + invoiceZipCode + ", pickUpDate=" + pickUpDate + ", deliveryDate=" + deliveryDate + ", pickUpTime=" + pickUpTime + ", deliveryTime=" + deliveryTime + ", block=" + block + '}';
    }

    

}
