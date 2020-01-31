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
            while(result.next()){
                
                counter++;
                this.counter = counter;
            }
            
            int[] selectIds = new int[counter];
            
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
    
    
    
    
    public String[] parseCondition(ResultSet result){
        
        try {
            //get size of result set
            int counter = 0;
            while(result.next()){
                
                counter++;
            }
            
            
            String[] conditions = new String[counter];
            counter = 0;
            while(result.next()){
                conditions[counter] = result.getString("HRRDescription");
                System.out.println(result.getString("HRRDescription"));
                counter++;
            }
            
            
            System.out.println(conditions[2]);
            return conditions;
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
    public void sortByDistance(){
        Connection con = setUpConnection();
        ResultSet result = null;
        double[][] distances = null;
        try{
            Statement stmt = con.createStatement();
            result = stmt.executeQuery("SELECT * FROM lat_and_long"); //the query being executed, selects all results in florida
            
            int counter = 0;
            while(result.next()){
               distances[counter][0] = result.getInt("ZipCode");
               
               //calculateDistance(Location currentLocation,targetLocation)
                counter++;
            }

        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public double[][] quickSort(double[][] distances, int low, int high){
        
        if (low < high)
        {
            /* pi is partitioning index, arr[pi] is now
               at right place */
            int index = partition(distances, low, high);

            quickSort(distances, low, index - 1);  // Before pi
            quickSort(distances, index + 1, high); // After pi
        }
        return distances;
    }
    
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
    
    
    
    
    //--------------------------BEN----------------------\\
    
    
    //Method gets procedure name from user
    public String inputFromUser(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter Query: ");
        String input = scanner.nextLine();
        return input;
    }
    
    //Search for procedures or codes
    public void codeProcedureSearch(){
        String query = inputFromUser();
        boolean[] found = new boolean[list.length]; 
        Arrays.fill(found, Boolean.FALSE); 
        
        for(int i = 0; i < list.length; i++){
            if(list[i][0].contains(query.toUpperCase())){//Searches through the DRG Definition field.
                found[i] = true;
            }
        }
        for(int i = 0; i < list.length; i++){
            if(found[i] == true){
                for(int j = 0; j < list[i].length; j++){
                    System.out.print(list[i][j]);
                    System.out.println();
                }
            }
        }
    }
    //Search for locations
    public void locationSearch(){
        String query = inputFromUser();
        boolean[] found = new boolean[list.length]; 
        Arrays.fill(found, Boolean.FALSE);
        
        for(int i = 0; i < list.length; i++){
            if(list[i][7].toUpperCase().contains(query.toUpperCase())){//Searches through the HRR field.
                found[i] = true;
            }
        }
        for(int i = 0; i < list.length; i++){
            if(found[i] == true){
                for(int j = 0; j < list[i].length; j++){
                    System.out.print(list[i][j]);
                    System.out.println();
                }
            }
        }
    }
    
    
    public void locationTest(){
        //User Coords
        double userx = 56.462;
        double usery = -2.971;
        //Provider Coords
        double[] provx = {30.111, 31.899, 32.897, 30.111, 31.899};
        double[] provy = {-97.359,-106.408 , -97.313, -97.359, -106.408};
        Location userLocation = new Location();
        Location[] provLocation = new Location[list.length];
        
        //Temporary Values created and added to remove the nulls in provLocation.
        for(int i = 0; i < list.length; i++){
            Location tempVal = new Location();
            provLocation[i] = tempVal;
        }
        
        //Sets coords into the classes.
        userLocation.setLongitude(userx);
        userLocation.setLatitude(usery);
        for(int i = 0; i < list.length; i++){
            provLocation[i].setLongitude(provx[i]);
            provLocation[i].setLatitude(provy[i]);
        }
        
        double userLat = userLocation.getLatitude();
        double userLong = userLocation.getLongitude();
        double[] providerLat = new double[list.length];
        double[] providerLong = new double[list.length];
        
        for(int i = 0; i < list.length; i++){
            providerLat[i] = provLocation[i].getLatitude();
            providerLong[i] = provLocation[i].getLongitude();
        }
        
        distanceSort(userLat, userLong, providerLat, providerLong);
    }
    
    public void distanceSort(double currLat, double currLong, double[] providerLat, double[] providerLong){
        double[] distance = distanceCalc(currLat, currLong, providerLat, providerLong);
        
        //Adds distances to the list. *Will not work multiple times!*
        for (int i = 0; i < list.length; i++){
            list[i][12] = String.valueOf(distance[i]);
        }
        
        for(int i = 0; i < list.length; i++){
            int low = i;
            for(int j = 0; j < list[i].length; j++){
                for(int k = j; k < list.length; k++){
                    if(Double.parseDouble(list[j][12]) > Double.parseDouble(list[low][12])){// Changes to double are made to correctly compare the two values.
                        low = j;
                        swap(list[low], list[i]);
                    }
                }
            }
        }

        printMultiArray(list);
    }
    //Method finds distance between two points with longitude and latitude(As-Crows-Flys)
    //Reference:
    //https://www.movable-type.co.uk/scripts/latlong.html
    //Altered to use arrays
    public double[] distanceCalc(double currLat, double currLong, double[] providerLat, double[] providerLong){
        double[] distance = new double[list.length];
        double radius = 6371e3;
        double theta1 = Math.toRadians(currLat);
        double[] theta2 = new double[list.length];
        for (int i = 0; i < list.length; i++){
             theta2[i] = Math.toRadians(providerLat[i]);
        }
        double[] deltaTheta = new double[list.length];
        double[] deltaLambda = new double[list.length];
        double[] a = new double[list.length];
        double[] c = new double[list.length];
        
        for(int i = 0; i < list.length; i++){
            deltaTheta[i] = Math.toRadians(providerLat[i]-currLat);
            deltaLambda[i] = Math.toRadians(providerLong[i] - currLong);
        }
        
        for(int i = 0; i < list.length; i++){
            a[i] = (Math.sin(deltaTheta[i] / 2) * Math.sin(deltaTheta[i] / 2)) + Math.cos(theta1) * Math.cos(theta2[i]) * (Math.sin(deltaLambda[i] / 2) * Math.sin(deltaLambda[i] / 2)); 
        }
        
        for(int i = 0; i < list.length; i++){
            c[i] = 2 * Math.atan2(Math.sqrt(a[i]), Math.sqrt(1-a[i]));
        }
        
        for(int i = 0; i < list.length; i++){
            distance[i] = radius * c[i];
        }
        return distance;
    }
    //sorts the list based on price in an ascending order
    public void priceSort(){
        for(int i = 0; i < list.length; i++){
            int low = i;
            for(int j = 0; j < list[i].length; j++){
                for(int k = j; k < list.length; k++){
                    if (Double.parseDouble(list[j][11]) > Double.parseDouble(list[low][11])){
                        low = j;
                        swap(list[low], list[i]);
                    }
                }
            }
        }
        
        printMultiArray(list);
    }
    //Prints a 2-dimensional array
    public void printMultiArray(String[][] array){
        for(int i = 0; i < array.length; i++){
            for(int j = 0; j < array[i].length; j++){
                System.out.print(array[i][j]);
                System.out.println();
            }
        }
    }
    
    public void quickPrint(){
        printMultiArray(list);
    }
    //Swaps two lines in the list for the purpose of sorting
    public void swap(String[] x, String[] y){
        for(int i = 0; i < list[0].length; i++){
            String temp = x[i];
            x[i] = y[i];
            y[i] = temp;
        }
    }
    
    
    public int getCounter(){
        return counter;
        
    }
}


