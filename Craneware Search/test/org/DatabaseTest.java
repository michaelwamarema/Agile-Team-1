/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org;
import java.sql.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author jodielaurenson
 */
public class DatabaseTest {
    
    public DatabaseTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of setUpConnection method, of class Database.
     */
    @Test
    public void testSetUpConnection() {
        System.out.println("setUpConnection");
        Database db = new Database();
        Connection result = db.setUpConnection();
        assertNotEquals("Connection successful",result,null);
        
        
    }

    
    
    
    @Test
    public void testRunSelectQuery(){
        try{
            int providerID = -1;
            Database db = new Database();
            Connection con = db.setUpConnection();
            ResultSet result = db.runSelect(con);
            while(result.next()){
                providerID = result.getInt("providerID");
            }
            System.out.println("Provider ID: "+providerID);
            assertEquals("Query run successfully",providerID,10001); //check if the correct result is returned
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Test
    public void testSearchCity(){
        try{
            int providerCity[] = new int[3]; //create an array that will store the results
            Database db = new Database();
            Connection con = db.setUpConnection();
            ResultSet result = db.runSearchCityP("MONTGOMERY", con); //call procedure method with city "montgomery"
            
            int counter = 0;
            while(result.next()){
                    providerCity[counter] = result.getInt("providerID"); //add providerID to a position in the array
                    counter++;
            }
            assertEquals("Query run successfully - result 1 correct", providerCity[0],10023);
            assertEquals("Query run successfully - result 2 correct", providerCity[1],10024);
            assertEquals("Query run successfully - result 3 correct", providerCity[2],10149);
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    /**
     * Tests that the search address query successfully runs
     */
    @Test
    public void testSearchAddress(){
        try{
            int providerID[] = new int[98]; //create an array that will store the results
            Database db = new Database();
            Connection con = db.setUpConnection();
            ResultSet result = db.runSearchCityP("DATES DRIVE", con); //call procedure method with city "montgomery"
            
            int counter = 0;
            while(result.next()){
                    providerID[counter] = result.getInt("providerID"); //add providerID to a position in the array
                    counter++;
            }
            assertNotEquals("Query run successfully", providerID[0],190025);
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    @Test
    public void testSearchProcedure(){
        try{
            String providerCity[] = new String[934]; //create an array that will store the results
            String condition[] = new String[934];
            Database db = new Database();
            Connection con = db.setUpConnection();
            ResultSet result = db.runSearchConditionP("DYSEQUILIBRIUM", con); //call procedure method with city "montgomery"
            
            int counter = 0;
            while(result.next()){
                
                    condition[counter] = result.getString("DRG_Definition");
                    providerCity[counter] = result.getString("providerCity"); //add providerID to a position in the array
                    counter++;
            }
            
            assertEquals("Query run successfully - city 933 correct", "MILWAUKEE",providerCity[933]);
            
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
