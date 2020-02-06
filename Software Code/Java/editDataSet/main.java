import java.io.*;
import java.util.*;
import java.lang.*;
public class main{
    
    String[] listTerm = {"DRG Definition - ", "Provider ID - ", "Provider Name - ", "Provider Address - ", "Provider City - ", "Provider State - ", "Provider Zipcode - ", "HRR Description - ", "Total Discharges - ", "Average Covered Charges - ", "Average Total Payments - ", "Average Medicare Payments - ", ""};
    String[][] list = {{"871 - SEPTICEMIA OR SEVERE SEPSIS W/O MV >96 HOURS W MCC", "670108", "BAYLOR SCOTT & WHITE MEDICAL CENTER - MARBLE FALLS", "810 W HIGHWAY 71", "MARBLE FALLS", "TX", "78654", "TX - Austin", "89", "34461.67", "13104.79", "7695.337", ""}, {"871 - SEPTICEMIA OR SEVERE SEPSIS W/O MV >96 HOURS W MCC", "670120", "THE HOSPITALS OF PROVIDENCE TRANSMOUNTAIN CAMPUS", "2000 TRANSMOUNTAIN RD", "EL PASO", "TX", "79911", "TX - El Paso", "22", "147342.18", "18504.95", "13261.09", ""}, {"470 - MAJOR JOINT REPLACEMENT OR REATTACHMENT OR LOWER EXTREMITY W/O MCC", "670116", "WISE HEALTH SYSTEM", "3200 NORTH TARRANT PARKWAY", "FORT WORTH", "TX", "76177", "TX - Fort Worth", "30", "94373.47", "13979.7", "11226.63", ""}, {"689 - KIDNEY & URINARY TRACT INFECTIONS W MCC", "670108", "BAYLOR SCOTT & WHITE MEDICAL CENTER - MARBLE FALLS", "810 W HIGHWAY 71", "MARBLE FALLS", "TX", "78654", "TX - Austin", "13", "21297.62", "8200", "3424.308", ""}, {"190 - CHRONIC OBSTRUCTIVE PULMONARY DISEASE W MCC", "670120", "THE HOSPITALS OF PROVIDENCE TRANSMOUNTAIN CAMPUS", "2000 TRANSMOUNTAIN RD", "EL PASO", "TX", "79911", "TX - El Paso", "12", "64578.42", "8526.75", "7649.33", ""}};
    
    public main(){
    
    }
    
    public String inputFromUser(int i){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter " + listTerm[i]);
        String input = scanner.nextLine();
        return input;
    }

    public void addToDataSet(){
        //ArrayList created to make an adjustable array
        ArrayList<ArrayList<String>> aList = new ArrayList<>(list.length + 1);
        //Creates the new record to be added to the list
        ArrayList<String> newLine = new ArrayList<>(list[0].length);
        for(int j = 0; j < list[0].length; j++){
            String enteredValue = inputFromUser(j);
            newLine.add(enteredValue);
        }
        //Fills the new list with information from the user and initial list
        for(int i = 0; i < list.length; i++){
            ArrayList<String> line = new ArrayList<>();
            for (int j = 0; j < list[i].length; j++){
                line.add(list[i][j]);
            }
            aList.add(line);
            if(i == list.length - 1){
                aList.add(newLine);
            }
        }
        //Prints the new list with added record
        for(ArrayList<String> b : aList){
            System.out.println(b);
        }
    }
    
    public void deleteToDataSet(){
        //ArrayList created to make an adjustable array
        ArrayList<ArrayList<String>> aList = new ArrayList<>(list.length);
        //Allows user to make a compound key to locate correct record
        String[] delQuery = new String[2];
        int[] IDIndex = new int[list.length];
        int locIndex = -1;
        boolean foundLoc = false;
        
        boolean[] foundID = new boolean[list.length]; 
        Arrays.fill(foundID, Boolean.FALSE); 
        //Populates the list
        for(int i = 0; i < list.length; i++){
            ArrayList<String> line = new ArrayList<>();
            for (int j = 0; j < list[i].length; j++){
                line.add(list[i][j]);
            }
            aList.add(line);
        }
        //Finds the compoud key the user wishes to delete
        for(int i = 0; i < 2; i++){
            delQuery[i] = inputFromUser(i);
        }
        //Searches through the DRG definition for the first part of the compound key
        for(int i = 0; i < list.length; i++){
            if(list[i][0].contains(delQuery[0].toUpperCase())){//Searches through the DRG Definition field
                foundID[i] = true;
                IDIndex[i] = i;
            }
        }
        //Searches through the Provider ID for the second part of the compound key
        for(int i = 0; i < list.length; i++){
            if(foundID[i] == true){//Checks to see if the first part of the compound key is there
                if(list[i][1].contains(delQuery[1].toUpperCase())){//Searches through the Provider ID field
                    foundLoc = true;
                    locIndex = i;
                }
            }
        }
        
        for(int i = 0; i < list.length; i++){
            ArrayList<String> nullLine = new ArrayList(list[i].length);//Creates a null line to replace the record
            for(int j = 0; j < list[i].length; j++){
                nullLine.add("");
            }
            if(foundID[i] == true && foundLoc == true){
                if(IDIndex[i] == locIndex){
                    aList.set(IDIndex[i], nullLine);//Sets the null line in the location determined by the compound key
                }
            }
        }
        //Prints the new list with deleted record
        for(ArrayList<String> b : aList){
            System.out.println(b);
        }
    }
    
    public void editToDataSet(){
        //ArrayList created to make an adjustable array
        ArrayList<ArrayList<String>> aList = new ArrayList<>(list.length);
        //Allows user to make a compound key to locate correct record
        String[] editQuery = new String[2];
        int[] IDIndex = new int[list.length];
        int locIndex = -1;
        boolean foundLoc = false;
        String[] editToMake = new String[list[0].length];
        
        for(int i = 0; i < list.length; i++){
            editToMake[i] = "0";
        }
        
        boolean[] editMade = new boolean[list[0].length];
        Arrays.fill(editMade, Boolean.FALSE); 
        
        boolean[] foundID = new boolean[list.length]; 
        Arrays.fill(foundID, Boolean.FALSE); 
  
        for(int i = 0; i < list.length; i++){
            ArrayList<String> line = new ArrayList<>();
            for (int j = 0; j < list[i].length; j++){
                line.add(list[i][j]);
            }
            aList.add(line);
        }
        //Creates compund key the user is searching for
        for(int i = 0; i < 2; i++){
            editQuery[i] = inputFromUser(i);
        }
        //Compares the first part of the compound key with the DRG definition to find the matching IDs
        for(int i = 0; i < list.length; i++){
            if(list[i][0].contains(editQuery[0].toUpperCase())){//Searches through the DRG Definition field.
                foundID[i] = true;
                IDIndex[i] = i;
            }
        }
        //If it finds an ID, it then searches through the ProviderIDs to locate the 
        for(int i = 0; i < list.length; i++){
            if(foundID[i] == true){
                if(list[i][1].contains(editQuery[1].toUpperCase())){
                    foundLoc = true;
                    locIndex = i;
                }
            }
        }
        //If a location is found, asks the user to enter in what fields they wish to edit
        if(locIndex != -1){
            for(int i = 0; i < list[0].length; i++){
                System.out.println("Enter in what fields you wish to edit.");
                System.out.println("Enter a 1 to make a edit to the field.");
                System.out.println("Enter a 0 to not make an edit");
            
                editToMake[i] = inputFromUser(i);
            }
        }else{//If a location is not found, the user is informed 
            System.out.println("Error. No record located with data entered.");
            //Since no record has been located, the program makes it so no edits can be made
            for(int i = 0; i < list.length; i++){
                editToMake[i] = "0";
            }
        }
       
        for(int i = 0; i < list.length; i++){
            ArrayList<String> editedLine = new ArrayList(list[i].length);//Edited line to be entered
            if(foundID[i] == true && foundLoc == true){
                for(int j = 0; j < list[i].length; j++){
                    if(editMade[j] != true){
                        if(editToMake[j].equals("0")){
                            editedLine.add(list[i][j]);//If the line is to be unedited, the line is set to what the values in the list
                        }else if(editToMake[j].equals("1")){
                            editedLine.add(inputFromUser(j));//If the line is to be edited, the line takes in information from the user
                            editMade[j] = true;
                        }else{
                            System.out.println("Error. Value entered is incorrect.");
                        }
                    }
                }
                if(IDIndex[i] == locIndex){//If the indexes match up, the edited line is entered into the list 
                    aList.set(IDIndex[i], editedLine);
                }
            }else{//If no values have been found, the program throws an error
                System.out.println("Error. No values entered have been found. Values are incorrect.");
            }
        }
        //Prints the new edited list
        for(ArrayList<String> b : aList){
            System.out.println(b);
        }
    }
}
