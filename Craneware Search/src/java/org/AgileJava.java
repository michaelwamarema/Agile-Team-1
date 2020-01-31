/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org;
import java.sql.*;
import java.util.Scanner; 
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author jodielaurenson
 */
public class AgileJava {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            
            boolean exit = false;
            while(!exit){
                Database database = new Database();
                Connection con = database.setUpConnection();
                System.out.println("1: search by city");
                System.out.println("2: search by address");
                System.out.println("3: search by state");
                System.out.println("4: search by zip");
                System.out.println("5: search by condition");
                System.out.println("6: filter by price range");
                System.out.print("Enter option: ");

                Scanner scan = new Scanner(System.in);
                int option = scan.nextInt();
                Scanner scanner = new Scanner(System.in);

                ResultSet result = null;

                if(option==1){

                    System.out.print("Enter city:");
                    String city = scanner.nextLine();
                    result = database.runSearchCityP(city);

                }
                else if(option==2){
                    System.out.print("Enter address:");
                    String address = scanner.nextLine();
                    result = database.runSearchAddressP(address);
                }
                else if(option==3){
                    System.out.print("Enter state:");
                    String state = scanner.nextLine();
                    result = database.runSearchStateP(state);
                }
                else if(option==4){
                    System.out.print("Enter zip:");
                    int zip = scanner.nextInt();
                    result = database.runSearchZipP(zip);
                }
                else if(option==5){
                    System.out.print("Enter condition:");
                    String condition = scanner.nextLine();
                    result = database.runSearchConditionP(condition);
                }
                else if(option==6){
                    System.out.print("Enter minimum value:");
                    double min = scanner.nextDouble();
                    Scanner scan2 = new Scanner(System.in);
                    System.out.print("Enter maximum value:");
                    double max = scan2.nextDouble();
                    result = database.runRestrictPriceP(min, max);
                }
                else if(option==-1){
                    exit=true;
                }
                if(result==null){
                    System.out.print("ERROR");
                }


                int size =0;
                if (result != null) 
                {
                  result.last();    // moves cursor to the last row
                  size = result.getRow(); // get row id 
                }
                result.first();





                int selectIds[] = new int[size];
                double cost[] = new double[size];
                String descriptions[] = new String[size];
                String name[] = new String[size];
                String address[] = new String[size];
                String city[] = new String[size];
                String state[] = new String[size];
                int zip[] = new int[size];
                int counter = 0;
                while(result.next()){

                    selectIds[counter] = result.getInt("providerID");

                    name[counter] = result.getString("providerName");
                    address[counter] = result.getString("providerStreetAddress");
                    //city[counter] = result.getString("providerCity");
                    //state[counter] = result.getString("providerState");
                    zip[counter] = result.getInt("providerZipCode");
                    descriptions[counter] = result.getString("DRG_Definition");
                    cost[counter] = result.getDouble("Average_Medicare_Payments");
                    counter++;
                }





                System.out.printf("%-20s %-75s %-30s %-10s %-10s\n","Provider ID", "description","address","zip","cost");
                for(int i = 0;i<size-1;i++){
                    //System.out.printf("%-30.30s  %-30.30s%n", address[i], selectIds[i]);
                    System.out.printf("%-20s %-75s %-30s %-10s %-10s\n",selectIds[i],descriptions[i],address[i],zip[i],cost[i]);
                }
                
            }
            } catch (SQLException ex) {
                Logger.getLogger(AgileJava.class.getName()).log(Level.SEVERE, null, ex);
            }



        
        
        
        
        
        
        
    }
    
    /**
     * This method uses the scanner class and gets user input
     * 
     * @param records an array of record class that holds all data
     */
    public void getPriceInput(Record records[]){
        Filter filter = new Filter();
        Scanner scan = new Scanner(System.in);
        System.out.println("Minimum price range: ");
        float min = scan.nextInt();
        System.out.println("Maximum price range: ");
        float max = scan.nextInt();
        filter.restrictToPrice(records, min, max); //calls a method that filters the records
    }
    
    
    
    
}
