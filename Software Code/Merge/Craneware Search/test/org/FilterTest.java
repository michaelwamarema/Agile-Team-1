/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org;

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
public class FilterTest {
    
    public FilterTest() {
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
     * Tests if records are added correctly
     */
    @Test
    public void testSingleRecord(){
        Filter filter = new Filter();
        Record records[] = new Record[1];
        records[0] = new Record("AL", 24057, 11111);
        boolean empty = filter.isEmpty(records);
        assertFalse("Array is not empty",empty);
    }
    
    
    /**
     * Tests if method filters with a single record
     */
    @Test
    public void testRetrictToPriceInRange(){
        Filter filter = new Filter();
        Record records[] = new Record[1];
        records[0] = new Record("AL", 24057, 11111);
        Record[] filteredRecords = filter.restrictToPrice(records, 22000.0, 28000.0);
        boolean empty = filter.isEmpty(filteredRecords);
        assertFalse("Array is not empty",empty);
    }
    
    /**
     * Tests if array is empty after filtering when nothing is in range
     */
    @Test
    public void testRetrictToPriceNotInRange(){
        Filter filter = new Filter();
        Record records[] = new Record[1];
        records[0] = new Record("AL", 20000, 11111);
        Record[] filteredRecords = filter.restrictToPrice(records, 22000.0, 28000.0);
        assertEquals("Array is empty",filteredRecords.length, 0);
    }

    /**
     * Test of restrictToPrice method, of class AgileJava.
     */
    @Test
    public void testRestrictToPrice() {
        Filter filter = new Filter();
        Record records[] = new Record[5];
        records[0] = new Record("AL", 24057,11111);
        records[1] = new Record("FL", 20365,22222);
        records[2] = new Record("KS", 25968,33333);
        records[3] = new Record("KY", 28776,44444);
        records[4] = new Record("FL", 25432,55555);
        
        Record[] filteredRecords = filter.restrictToPrice(records, 22000.0, 28000.0);
        System.out.println("length: "+filteredRecords.length);
        assertEquals("Restricted values is correct",filteredRecords[0],records[0]);
        assertEquals("Length is correct",filteredRecords.length,3);
    }
    
    
    /**
     * test to check that Location objects are being initialised correctly
     */
    @Test 
    public void testAddLocation(){
        Filter filter = new Filter();
        Location locations[] = new Location[3];
        locations[0] = new Location(48834,43.142,-85.049);
        locations[1] = new Location(55304,45.255,-93.287);
        locations[2] = new Location(55422,43.255,-93.340);
        assertEquals("Array is correct length",locations.length, 3);
    }
    
    /**
     * tests whether the function correctly calculates distance between two points
     */
    @Test 
    public void testCalculateDistance(){
        Filter filter = new Filter();
        Location locations[] = new Location[2];
        locations[0] = new Location(48834,43.142,-85.049);
        locations[1] = new Location(55304,45.255,-93.287);
        double distance = filter.calculateDistance(locations[0], locations[1]);
        distance = Math.round(distance);
        int distanceInt = (int)distance;
        assertEquals("Distance is correct",distanceInt, 697);
    }
    
    
    @Test
    public void testCalculateDistanceGivenZips(){
        Filter filter = new Filter();
        Location locations[] = new Location[5];
        locations[0] = new Location(48834,43.142,-85.049);
        locations[1] = new Location(55304,45.255,-93.287);
        locations[2] = new Location(55422,43.255,-93.340);
        locations[3] = new Location(29079,34.296,-80.113);
        locations[4] = new Location(29390,34.888,-81.969);
        Location results[] = filter.filterByLocation(locations[0],700,locations);
        boolean noResults;
        noResults = results.length>1;
        System.out.println("Number of locations in range: "+results.length);
        assertTrue("Correct results shown",noResults);
    }
    
    
    @Test
    public void testCalculateDistanceGivenProcedures(){
        Filter filter = new Filter();
        Location locations[] = new Location[5];
        Record records[] = new Record[3];
        records[0] = new Record("AL", 20000,55304);
        records[1] = new Record("FL", 30000,29079);
        records[2] = new Record("CA", 25000,48834);
        locations[0] = new Location(29079,34.296,-80.113);
        locations[1] = new Location(29390,34.888,-81.969);
        locations[2] = new Location(48834,43.142,-85.049);
        locations[3] = new Location(55304,45.255,-93.287);
        locations[4] = new Location(55422,43.255,-93.340);
        
        
        Location currentLocation = filter.getCurrentLocation(55422, locations);
        Location results[] = filter.filterByLocation(currentLocation,700,locations);
        boolean noResults;
        if(results.length>1){
            noResults = true;
        }
        else{
            noResults = false;
        }
        System.out.println("Number of locations in range: "+results.length);
        assertTrue("Correct results shown",noResults);
    }
}
