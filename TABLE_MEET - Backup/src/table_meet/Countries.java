/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package table_meet;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author hungh
 */
public class Countries{
    
    private ArrayList<Country> countries;
    
    public Countries(){}
    public Countries(ArrayList<Country> countries){
        super();
        this.countries = countries;
    }   
    
    public ArrayList<Country> insertCountry() throws SQLException{
        Connection cn = dba.DBConnection.table_meetConnection(); PreparedStatement ps; ResultSet rs;
        Country country;
        
        String query = "SELECT * FROM COUNTRY";        
        try{
            ps = cn.prepareStatement(query);
            rs = ps.executeQuery();
            while(rs.next()){
                country = new Country();
                country.setCountryName(rs.getString("COUNTRYNAME"));
                country.setCountryCode(rs.getString("COUNTRYCODE"));
                countries = new ArrayList<>();
                countries.add(country);
            }
        }catch(SQLException e){
            Logger.getLogger(SignInController.class.getName()).log(Level.SEVERE, null, e);
        }   
//        for(int i=0; i<countries.size();i++){
//            country = countries.get(i);
//            System.out.println(country.getCountryName());
//            System.out.println(country.getCountryCode());
//        }
        return countries;
    }        
}
