/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.*;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *This class creates a connection to the database
 * @author jodielaurenson
 */
public class Database {
    
    /**
     * sets up the connection to mysql and runs a simple select query
     * @return 
     */
    public Connection setUpConnection(){
        Connection con;
        try {  
           Class.forName("com.mysql.jdbc.Driver");
        
           con = DriverManager.getConnection("jdbc:mysql://silva.computing.dundee.ac.uk:3306/19agileteam1db","19agileteam1","8760.at1.0678"); //connection details
           
           return con;
           
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    /**
     * ********Runs a simple search, not needed in final version
     * 
     * @param con
     * @return 
     */
    public ResultSet runSelect(Connection con){
        ResultSet result = null;
        try{
            Statement stmt = con.createStatement();
            result = stmt.executeQuery("SELECT * FROM provider WHERE providerZipCode = 36301"); //the query being executed, selects all results in florida

            
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
    
    /**
     * 
     * @param result 
     */
    public void getResult(ResultSet result){
        try {
            while(result.next()){
                int providerID = result.getInt("providerID");
                System.out.println("ID: "+providerID);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    //-------------STORED PROCEDURE METHODS-------------\\
    
    
    /**
     * This method runs a stored procedure to search a city and returns the results 
     * 
     * @param city The city the user wants to look for
     * @param con The connection to the db server
     * @return the results of the query
     */
    public ResultSet runSearchCityP(String city, Connection con){
        try {
            CallableStatement stmt;
            
            stmt = con.prepareCall("{CALL search_city(?)}");
            stmt.setString(1, city);
            
            ResultSet result = stmt.executeQuery();
        
        
            return result;
            } catch (SQLException ex) {
                Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }  
    }
    
    /**
     * This method runs a stored procedure to search an address and returns the results 
     * 
     * @param address The address the user wants to look for
     * @param con The connection to the db server
     * @return the results of the query
     */
    public ResultSet runSearchAddressP(String address, Connection con){
        try {
            CallableStatement stmt;
            
            stmt = con.prepareCall("{CALL search_address(?)}");
            stmt.setString(1, address);
            
            ResultSet result = stmt.executeQuery();
        
        
            return result;
            } catch (SQLException ex) {
                Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }  
    }
    
    /**
     * This method runs a stored procedure to search an address and returns the results 
     * 
     * @param address The address the user wants to look for
     * @param con The connection to the db server
     * @return the results of the query
     */
    public ResultSet runSearchConditionP(String condition){
        try {
            CallableStatement stmt;
            
            stmt = con.prepareCall("{CALL search_by_procedure(?)}");
            stmt.setString(1, condition);
            
            ResultSet result = stmt.executeQuery();
        
        
            return result;
            } catch (SQLException ex) {
                Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }  
    }
    
    public ResultSet runRestrictPriceP(double min, double max, Connection con){
        try {
            CallableStatement stmt;
            
            stmt = con.prepareCall("{CALL restrict_price(?,?)}");
            stmt.setDouble(1, min);
            stmt.setDouble(2, max);
            
            ResultSet result = stmt.executeQuery();
        
        
            return result;
            } catch (SQLException ex) {
                Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }  
    }
    
    
}


