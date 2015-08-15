/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Domain;

/**
 *
 * @author bazziss
 */
public class State {
    int orderId;
    String action;
    String ok;
    String timeStamp;

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getOk() {
        return ok;
    }

    public void setOk(String ok) {
        this.ok = ok;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

   
    
}
