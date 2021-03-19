/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package table_meet;

/**
 *
 * @author hungh
 */
public class Country {
    private String countryName, countryCode;
    
    public Country(){}
    
    public Country(String countryName, String countryCode){
        super();
        this.countryName = countryName;
        this.countryCode = countryCode;
    }    
    
    public String getCountryName(){
        return countryName;
    }
    public void setCountryName(String Countryname){
        this.countryName = Countryname;
    }    
    public String getCountryCode(){
        return countryCode;
    }        
    public void setCountryCode(String Countrycode){
        this.countryName = Countrycode;
    }
    
}