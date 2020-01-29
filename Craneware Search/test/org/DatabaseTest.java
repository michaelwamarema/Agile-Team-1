/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org;

import java.sql.Connection;
import java.sql.ResultSet;
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

    
    
    
    /**@Test
    public void testRunSelectQuery(){
        Database db = new Database();
        Connection con = db.setUpConnection();
        ResultSet result = db.runSelect(con);
        int providerID = result.getInt("providerID");
        assertNotEquals("Query run successfully",providerID,10001);
    }**/
    
}
