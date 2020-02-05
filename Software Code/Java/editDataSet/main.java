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
        ArrayList<ArrayList<String>> aList = new ArrayList<>(list.length + 1);
        ArrayList<String> newLine = new ArrayList<>(list[0].length);
        
        for(int j = 0; j < list[0].length; j++){
            String enteredValue = inputFromUser(j);
            newLine.add(enteredValue);
        }
        
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
        
        for(ArrayList<String> b : aList){
            System.out.println(b);
        }
    }
    
    public void deleteToDataSet(){
        ArrayList<ArrayList<String>> aList = new ArrayList<>(list.length);
        String[] delQuery = new String[2];
        int[] IDIndex = new int[list.length];
        int locIndex = -1;
        
        boolean[] foundID = new boolean[list.length]; 
        Arrays.fill(foundID, Boolean.FALSE); 
        
        boolean foundLoc = false;
        for(int i = 0; i < list.length; i++){
            ArrayList<String> line = new ArrayList<>();
            for (int j = 0; j < list[i].length; j++){
                line.add(list[i][j]);
            }
            aList.add(line);
        }
        
        for(int i = 0; i < 2; i++){
            delQuery[i] = inputFromUser(i);
        }

        for(int i = 0; i < list.length; i++){
            if(list[i][0].contains(delQuery[0].toUpperCase())){//Searches through the DRG Definition field.
                foundID[i] = true;
                IDIndex[i] = i;
            }
        }
        
        for(int i = 0; i < list.length; i++){
            if(foundID[i] == true){
                if(list[i][1].contains(delQuery[1].toUpperCase())){
                    foundLoc = true;
                    locIndex = i;
                }
            }
        }
        
        for(int i = 0; i < list.length; i++){
            ArrayList<String> nullLine = new ArrayList(list[i].length);
            for(int j = 0; j < list[i].length; j++){
                nullLine.add("");
            }
            if(foundID[i] == true && foundLoc == true){
                if(IDIndex[i] == locIndex){
                    aList.set(IDIndex[i], nullLine);
                }
            }
        }
        
        for(ArrayList<String> b : aList){
            System.out.println(b);
        }
    }
    
    public void editToDataSet(){
        ArrayList<ArrayList<String>> aList = new ArrayList<>(list.length);
        String[] editQuery = new String[2];
        int[] IDIndex = new int[list.length];
        int locIndex = -1;
        String[] editToMake = new String[list[0].length];
        
        for(int i = 0; i < list.length; i++){
            editToMake[i] = "0";
        }
        
        boolean[] foundID = new boolean[list.length]; 
        Arrays.fill(foundID, Boolean.FALSE); 
        
        boolean foundLoc = false;
        for(int i = 0; i < list.length; i++){
            ArrayList<String> line = new ArrayList<>();
            for (int j = 0; j < list[i].length; j++){
                line.add(list[i][j]);
            }
            aList.add(line);
        }
        
        for(int i = 0; i < 2; i++){
            editQuery[i] = inputFromUser(i);
        }
        
        for(int i = 0; i < list[0].length; i++){
            System.out.println("Enter in what fields you wish to edit.");
            System.out.println("Enter a 1 to make a edit to the field.");
            System.out.println("Enter a 0 to not make an edit");
            
            editToMake[i] = inputFromUser(i);
        }

        for(int i = 0; i < list.length; i++){
            if(list[i][0].contains(editQuery[0].toUpperCase())){//Searches through the DRG Definition field.
                foundID[i] = true;
                IDIndex[i] = i;
            }
        }
        
        for(int i = 0; i < list.length; i++){
            if(foundID[i] == true){
                if(list[i][1].contains(editQuery[1].toUpperCase())){
                    foundLoc = true;
                    locIndex = i;
                }
            }
        }
        
        for(int i = 0; i < list.length; i++){
            ArrayList<String> editedLine = new ArrayList(list[i].length);
            boolean editMade = false;
            
            if(foundID[i] == true && foundLoc == true){
                for(int j = 0; j < list[i].length; j++){
                    if(editToMake[j].equals("0")){
                        editedLine.add(list[i][j]);
                    }else if(editToMake[j].equals("1")){
                        editedLine.add(inputFromUser(j));
                        editMade = true;
                    }else{
                        System.out.println("Error. Value entered is incorrect.");
                    }
                }
                if(IDIndex[i] == locIndex && editMade == true){
                    aList.set(IDIndex[i], editedLine);
                }
            }
        }
        
        for(ArrayList<String> b : aList){
            System.out.println(b);
        }
    }
    
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
}
