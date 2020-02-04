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
import java.util.Arrays;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *This class creates a connection to the database
 * @author jodielaurenson
 */
public class Database {
    String[][] list = {{"871 - SEPTICEMIA OR SEVERE SEPSIS W/O MV >96 HOURS W MCC", "670108", "BAYLOR SCOTT & WHITE MEDICAL CENTER - MARBLE FALLS", "810 W HIGHWAY 71", "MARBLE FALLS", "TX", "78654", "TX - Austin", "89", "34461.67", "13104.79", "7695.337", ""}, {"871 - SEPTICEMIA OR SEVERE SEPSIS W/O MV >96 HOURS W MCC", "670120", "THE HOSPITALS OF PROVIDENCE TRANSMOUNTAIN CAMPUS", "2000 TRANSMOUNTAIN RD", "EL PASO", "TX", "79911", "TX - El Paso", "22", "147342.18", "18504.95", "13261.09", ""}, {"470 - MAJOR JOINT REPLACEMENT OR REATTACHMENT OR LOWER EXTREMITY W/O MCC", "670116", "WISE HEALTH SYSTEM", "3200 NORTH TARRANT PARKWAY", "FORT WORTH", "TX", "76177", "TX - Fort Worth", "30", "94373.47", "13979.7", "11226.63", ""}, {"689 - KIDNEY & URINARY TRACT INFECTIONS W MCC", "670108", "BAYLOR SCOTT & WHITE MEDICAL CENTER - MARBLE FALLS", "810 W HIGHWAY 71", "MARBLE FALLS", "TX", "78654", "TX - Austin", "13", "21297.62", "8200", "3424.308", ""}, {"190 - CHRONIC OBSTRUCTIVE PULMONARY DISEASE W MCC", "670120", "THE HOSPITALS OF PROVIDENCE TRANSMOUNTAIN CAMPUS", "2000 TRANSMOUNTAIN RD", "EL PASO", "TX", "79911", "TX - El Paso", "12", "64578.42", "8526.75", "7649.33", ""}};
    
    String test;
    int counter;
    int SearchCityP;
    String address;
    String procedure;
    int providerID;
    
    String[] addresses;
    String[] procedures;
    int[] providerIDs;
    
    
    
    
    
    
    
    /**
     * This function is called by the html page and it deals with inputs and calls a corresponding method
     * 
     * @param option this is a different number depending on what function needs to be called
     * @param input1 this is input 1 to be passed into a method
     * @param input2 this is input 2 to be passed into a method
     */
    public void callFunction(int option, String input1, String input2){
        if(option==-1){
            ResultSet result = getSelect(); //calls a simple get query
            int ids[] = parseID(result);
        }
        else if(option==1){
            ResultSet result = runSearchCityP(input1); //calls a query that searches for a city
            int ids[] = parseID(result);
        }
        else if(option==2){
            ResultSet result = runRestrictPriceP(Integer.parseInt(input1), Integer.parseInt(input2)); //calls a procedure that returns results in price range
            int ids[] = parseID(result);
        }
    }
    
    
    public int[] parseID(ResultSet result){
        
        try {
            //get size of result set
            int counter = 0;
            int size = getSizeOfResult(result);
            
            int[] selectIds = new int[size];
            
            while(result.next()){
                selectIds[counter] = result.getInt("providerID");
                counter++;
            }
            return selectIds;
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
            
    }
    
    
    public String[] parseAddress(ResultSet result){
        
        try {
            //get size of result set
            int counter = 0;
            int size = getSizeOfResult(result);
            
            String[] addresses = new String[size];
            
            while(result.next()){
                addresses[counter] = result.getString("providerStreetAddress");
                counter++;
            }
            return addresses;
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
            
    }
   
    
     public String[] parseProviderName(ResultSet result){
        
        try {
            //get size of result set
            int counter = 0;
            int size = getSizeOfResult(result);
            
            String[] provNames = new String[size];
            
            while(result.next()){
                provNames[counter] = result.getString("providerName");
                counter++;
            }
            return provNames;
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
            
    }
    
     public String[] parseCondition(ResultSet result){
        
        try {
            //get size of result set
            int counter = 0;
            int size = getSizeOfResult(result);
            
            String[] procedures = new String[size];
            
            while(result.next()){
                procedures[counter] = result.getString("HRRDescription");
                counter++;
            }
            return procedures;
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
            
    }
     
     
     public Double[] parseAvgMed(ResultSet result){
        
        try {
            //get size of result set
            int counter = 0;
            int size = getSizeOfResult(result);
            
            Double[] AvgMeds = new Double[size];
            
            while(result.next()){
                AvgMeds[counter] = result.getDouble("Average_Medicare_Payments");
                counter++;
            }
            return AvgMeds;
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
            
    }
    
    
    
    
    
    
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
    public ResultSet getSelect(){
        Connection con = setUpConnection();
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
    
    
    
    //-----------------FILTERS-----------------\\
    
    
    /**
     * This method uses the Haversine formula to calculate the distance between 
     * two points given the longitude and latitude. The equation was taken from
     * https://stackoverflow.com/questions/27928/calculate-distance-between-two-latitude-longitude-points-haversine-formula
     * 
     * @param currentLocation the location of the user stored in an object
     * @param targetLocation the location of the target, stored in an object
     * @return the distance between the two points in kilometres
     */
    public double calculateDistance(Location currentLocation, Location targetLocation){
        final double RADIUS_OF_EARTH = 6371;
        double latitudeDifference = Math.toRadians(currentLocation.getLatitude()-targetLocation.getLatitude()); //calculated difference between latitude in radians
        double longitudeDifference = Math.toRadians(currentLocation.getLongitude()-targetLocation.getLongitude());//calculated difference between longitude in radians
        
        //calculates the first part of Haversines formula
        double x = Math.sin(latitudeDifference/2)*Math.sin(latitudeDifference/2)+Math.cos(Math.toRadians(currentLocation.getLatitude()))*Math.cos(Math.toRadians(targetLocation.getLatitude()))*Math.sin(longitudeDifference/2)*Math.sin(longitudeDifference/2);
        double y = 2*Math.atan2(Math.sqrt(x), Math.sqrt(1-x));
        double distance = RADIUS_OF_EARTH * y;
        return distance;
        
    }
    
    /**
     * goes through all records and returns all within the distance range
     * 
     * @param currentLocation location of the user
     * @param range maximum distance in kilometres the user wants displayed
     * @param locations all records
     * @return 
     */
    public Location[] filterByLocation(Location currentLocation, int range, Location[] locations){
        Location locationsInRange[] = new Location[locations.length]; 
        int counter = 0;
        for(int l = 0;l<locations.length;l++){
            double currDistance = calculateDistance(currentLocation, locations[l]);
            if(currDistance<range){
                locationsInRange[counter]=locations[l];
                counter++;
            }
        }
        //creates a new array that is same as recordsInRange but without all the nulls
        Location returnLocations[] = new Location[counter]; 
        for(int i = 0; i < counter; i++){
            returnLocations[i] = locationsInRange[i];
        }
        return returnLocations;
    }
    
    /**public void searchLongAndLat(int zip, Location[] locations, Record[] records){
        for(int i = 0; i < locations.length; i++){
            if(locations[i].getZip()==zip){
                calculateDistance(locations[i], )
            }
        }
    }*/
    
    public Location getCurrentLocation(int zip, Location[] locations){
        int min = 0;
        int max = locations.length-1;
        int middle = -1;
        while(min<=max){
            middle = (int)Math.floor((min+max)/2);
            if(locations[middle].getZip()<zip){
                min = middle+1;
            }
            else if(locations[middle].getZip()>zip){
                max = middle-1;
            }
            else{
                return locations[middle];
            }
            
        }
        return null;
    }
    //##########################################################
    public int getSizeOfResult(ResultSet result){
        try {
            int size =0;
            if (result != null) 
            {


                    result.last();    // moves cursor to the last row
                    size = result.getRow(); // get row id 


            }
        result.first();
        return size;
        } catch (SQLException ex) {
                Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }
    
    /**
     * Takes in user zip and gets their longitude and latitude, gets the longitude and latitude of every hospital
     * and calls the function to calculate the distance and then sorts all distances
     * 
     * @param userZip the zip code of the user
     * @return a 2d array of provider ids and distances, sorted ascending
     */
    public double[][] sortByDistance(int userZip){
        Connection con = setUpConnection();
        CallableStatement stmt;
        Filter filter = new Filter();
        ResultSet result = null;
        
        double userLat=0;
        double userLong=0;
        
        try{
            stmt = con.prepareCall("{CALL search_latlong_zip(?)}"); //prepares the procedure
            stmt.setInt(1, userZip); //sets the parameter to the state variable
            
            ResultSet userResult = stmt.executeQuery(); //runs query and sets it to the result
            while(userResult.next()){
                userLat = userResult.getDouble("latitude");
                userLong = userResult.getDouble("longitude");
            }
            result = stmt.executeQuery("{CALL get_procedure_locations()}"); //the query being executed
            int size = getSizeOfResult(result);
            double[][] distances = new double[size-1][2];
            int counter = 0;
            double latitude = 0;
            double longitude = 0;
            result.first();
            while(result.next()){
                if(counter<3219){
                    distances[counter][0] = result.getInt("Provider_Zip_Code");
                    latitude = result.getDouble("Latitude");
                    longitude = result.getDouble("Longitude");
                    distances[counter][1] = filter.calculateDistance(userLat, userLong, latitude, longitude);
                }
               
               counter++;
            }
            //Arrays.copyOf(distances, distances.length-2);
            
            distances = quickSort(distances, 0, size-2);
            
            return distances;

        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    /**
     * sorts using a quicksort - chooses a partition index and recursively calls itself, creating a new index
     * 
     * @param distances a 2d array of ids and distances which is being sorted
     * @param low the smallest index of the partition
     * @param high the highest index of the partition
     * @return the sorted 2d array
     */
    public double[][] quickSort(double[][] distances, int low, int high){
        
        if (low < high)
        {
            
            int index = partition(distances, low, high); //set index using partition

            quickSort(distances, low, index - 1);  // Before index
            quickSort(distances, index + 1, high); // After index
        }
        return distances;
    }
    
    /**
     * 
     * @param distances
     * @param low
     * @param high
     * @return 
     */
    public int partition(double[][] distances, int low, int high) 
    { 
        double pivot = distances[high][1];  
        int i = (low-1); // index of smaller element 
        for (int j=low; j<high; j++) 
        { 
            // If current element is smaller than the pivot 
            if (distances[j][1] < pivot) 
            { 
                i++; 
  
                // swap arr[i] and arr[j] 
                double[] temp = distances[i]; 
                distances[i] = distances[j]; 
                distances[j] = temp; 
            } 
        } 
  
        // swap arr[i+1] and arr[high] (or pivot) 
        double[] temp = distances[i+1]; 
        distances[i+1] = distances[high]; 
        distances[high] = temp; 
  
        return i+1; 
    }
    
    //-------------STORED PROCEDURE METHODS-------------\\
    
    
    /**
     * This method runs a stored procedure to search a city and returns the results 
     * 
     * @param city The city the user wants to look for
     * @param con The connection to the db server
     * @return the results of the query
     */
    public ResultSet runSearchCityP(String city){
        try {
            Connection con = setUpConnection();
            CallableStatement stmt;
            
            stmt = con.prepareCall("{CALL search_city(?)}"); //prepares the procedure
            stmt.setString(1, city); //sets the parameter to the state variable
            
            ResultSet result = stmt.executeQuery(); //runs query and sets it to the result
            
            
            
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
    public ResultSet runSearchAddressP(String address){
        try {
            Connection con = setUpConnection();
            CallableStatement stmt;
            
            stmt = con.prepareCall("{CALL search_address(?)}"); //prepares the procedure
            stmt.setString(1, address); //sets the parameter to the state variable
            
            ResultSet result = stmt.executeQuery(); //runs query and sets it to the result
        
        
            return result;
            } catch (SQLException ex) {
                Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }  
    }
    
    
    /**
     * This method runs a stored procedure to search for a given state and returns the results 
     * 
     * @param state The state code the user wants to look for
     * @param con The connection to the db server
     * @return the results of the query
     */
    public ResultSet runSearchStateP(String state){
        try {
            Connection con = setUpConnection();
            CallableStatement stmt;
            
            stmt = con.prepareCall("{CALL search_state(?)}"); //prepares the procedure
            stmt.setString(1, state); //sets the parameter to the state variable
            
            ResultSet result = stmt.executeQuery(); //runs query and sets it to the result
        
        
            return result;
            } catch (SQLException ex) {
                Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }  
    }
    
    
    /**
     * This method runs a stored procedure to search for a given zip and returns the results 
     * 
     * @param zip The zip code the user wants to look for
     * @param con The connection to the db server
     * @return the results of the query
     */
    public ResultSet runSearchZipP(int zip){
        try {
            Connection con = setUpConnection();
            CallableStatement stmt;
            
            stmt = con.prepareCall("{CALL search_zip(?)}"); //prepares the procedure
            stmt.setInt(1, zip); //sets the parameter to the zip variable
            
            ResultSet result = stmt.executeQuery();//runs query and sets it to the result
        
        
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
            Connection con = setUpConnection();
            CallableStatement stmt;
            
            stmt = con.prepareCall("{CALL search_by_procedure(?)}"); //prepares the procedure
            stmt.setString(1, condition); //sets the parameter to the condition variable
            
            ResultSet result = stmt.executeQuery(); //runs query and sets it to the result
        
        
            return result;
            } catch (SQLException ex) {
                Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }  
    }
    
    
    /**
     * calls a stored procedure that returns procedures in a given price range
     * 
     * @param min the minimum value of the range
     * @param max the maximum value of the range
     * @param con the connection to the database server
     * @return the results of the query
     */
    public ResultSet runRestrictPriceP(double min, double max){
        try {
            Connection con = setUpConnection();
            CallableStatement stmt;
            
            stmt = con.prepareCall("{CALL restrict_price(?,?)}"); //prepares the procedure
            stmt.setDouble(1, min); //sets the first parameter to the minimum variable
            stmt.setDouble(2, max); //sets the second parameter to the maximum variable
            
            ResultSet result = stmt.executeQuery(); //runs query and sets it to the result
        
        
            return result;
            } catch (SQLException ex) {
                Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }  
    }
    
    
   public void setAddresses(String search){
       ResultSet result = runSearchAddressP(search);
       providerIDs =  parseID(result);
   }
   
   
   public String getAddresses(){
       //return addresses;
       //String[] addresses =  {"abc", "def"};
       return "test";
       //return addresses;
   }
   
   public void setProcedures(String search){
       ResultSet result = runSearchConditionP(search);
       providerIDs =  parseID(result);
   }
   
   public String getProcedures(){
       return "test";
       //return procedures;
   }
}


