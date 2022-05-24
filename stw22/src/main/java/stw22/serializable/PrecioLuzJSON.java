/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stw22.serializable;

/**
 *
 * @author rober
 */
public class PrecioLuzJSON {
    private String date;
    private String hour;
    private boolean is_cheap;
    private boolean is_under_avg;
    private String market;
    private double price;
    private String units;

    public String getDate() {
        return date;
    }

    public String getHour() {
        return hour;
    }

    public boolean isIs_cheap() {
        return is_cheap;
    }

    public boolean isIs_under_avg() {
        return is_under_avg;
    }

    public String getMarket() {
        return market;
    }

    public double getPrice() {
        return price;
    }

    public String getUnits() {
        return units;
    }
    
    
    
}
