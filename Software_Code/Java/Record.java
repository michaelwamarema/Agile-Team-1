/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agilejava;

/**
 *
 * @author jodielaurenson
 */
public class Record {
    String location;
    float price;
    int zip;
    
    public Record(String location, float price, int zip){
        this.location = location;
        this.price = price;
        this.zip = zip;
    }
    
    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }
    
    
    
    public void setData(String location, float price, int zip){
        this.location = location;
        this.price = price;
        this.zip = zip;
    }
    
    
}
