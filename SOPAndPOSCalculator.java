import java.util.Scanner;
/**
 * InnerSOPAndPOSCalculator: This program display Sum of Product 
 * and Product of Sum.
 * 
 * Author: Steve, Eric, Shammah 
 */
public class InnerSOPAndPOSCalculator {

    
}
public class SOPAndPOSCalculator {

    //defining symbols for SOP and POS
    public static final String X = "x";
    public static final String Y=  "y";
    public static final String Z = "z";
    public static final String A = "a";

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        int deg = 0;
        boolean validInput = false;

        // Prompt the user until a valid integer input is provided
        while (!validInput) {
            try {
                System.out.print("Please, enter your function degree (range is 1 to 4) : ");
                // Check if the next input is an integer
                while (!input.hasNextInt()) {
                    // Clear the input buffer
                    input.nextLine();
                    System.out.print("Invalid input. Please enter a degree (integer) that have range between 1 to 4: ");
                }
                // Read the integer input
                deg = input.nextInt();

                // Validate the degree range
                if (deg >= 1 && deg <= 4) {
                    validInput = true;
                } else {
                    System.out.println("Degree out of range. Try 1 to 4: ");
                }
            } catch (Exception e) {
                System.out.println("An error occurred: " + e.getMessage());
            }
        }

        //2-Dimensional array to model the table of inputs and functional values
        int[][] arr = new int[(int) Math.pow(2, deg)][deg+1];

        printTable(arr, deg);

        //user enters the functional values and sees the table as it updates
        
        System.out.print("Do want to change any of the boolean values on the last column to 1 ? Enter y (yes) or n (no): ");
        String choice = input.next();

        //check if the the user's input is valid for our condition
        while(!isValid(choice)){
            System.out.print("Entry is not valid, try y (yes) or n (no): ");
            choice = input.next();
        }

        while(true){

            if(choice.equalsIgnoreCase("y")){
                System.out.println("Which row do you want to enter the value 1 (one at a time): ");
                int pos = input.nextInt();

                while(pos > (int) Math.pow(2, deg) || pos < 1){
                    System.out.printf("Invalid row input, range is 1 to %d Please try again: ", (int) Math.pow(2, deg));
                    pos = input.nextInt();
                }

                arr[pos-1][deg] = 1;
                printTable(arr, deg);

                System.out.print("Do want to change any of the boolean values on the last column to 1 ? Enter y (yes) or n (no): ");
                choice = input.next();

                while(!isValid(choice)){
                    System.out.print("Entry is not valid, try y (yes) or n (no): ");
                    choice = input.next();
                }
                }else{
                    break;
                }
            
        }
        
        //testing SOP and POS finders
        findSOP(deg, arr);
        findPOS(deg, arr);
    }

    //a method that prints the table to show the user the function and values they are dealing with
    public static void printTable(int[][] arr, int deg){

        //filling in the first column of the table

        for(int a=0;a<arr.length/2;a++){
            arr[a][0] = 1;
        }

        //the conditions check the degree of the function to call an appropriate printing table method
        if(deg == 1){
            degOneTable();
        }else if(deg == 2){
            degTwoTable();
            setLastColumn(deg, arr);
        }else if(deg == 3){
            setLastColumn(deg, arr);
            setSecLastCol(deg, arr);
            degThreeTable(arr);
        }else if(deg == 4){
            setLastColumn(deg, arr);
            setSecLastCol(deg, arr);
            setThirdLastCol(deg, arr);
            degFourTable();
        }

        //drawing the table layout for the specified degree function

        for(int i = 0; i<arr.length; i++){
            for(int j = 0; j<arr[i].length;j++){
                System.out.print("| " + arr[i][j] + " ");
            }
            System.out.println("|");
        } 
    }

    //a method for a 1 degree table
    public static void degOneTable(){
        System.out.printf("%3s %3s", "x","F(x)");
        System.out.println();
    }

    //a method that displayes the header for a 2 degree table
    public static void degTwoTable(){
        System.out.printf("%3s %3s %3s", "x", "y", "F(x,y)");
        System.out.println();
    }

    //a method that displayes the header for a 3 degree table
    public static void degThreeTable(int[][] arr){        
        System.out.printf("%3s %3s %3s %3s", "x", "y", "z", "F(x,y,z)");
        System.out.println();
    }

    //a method that displayes the header for a 4 degree table
    public static void degFourTable(){
        System.out.printf("%3s %3s %3s %3s %3s", "x", "y", "z", "a", "F(x,y,z,a)");
        System.out.println();
    }

    //a method that sets the last column combination
    public static void setLastColumn(int deg, int[][] arr){
        for(int i=0; i<arr.length; i+=2){
            arr[i][deg-1] = 1;
        }
    }
    //a method that sets the second-from-last column combination
    public static void setSecLastCol(int deg, int[][] arr) {
        for(int i=0; i<arr.length; i+=4){
            arr[i][deg-2] = 1;
            arr[i+1][deg-2] = 1;
        }
    }
    //a method that sets the third-from-last column combination
    public static void setThirdLastCol(int deg, int[][] arr){
        for(int i = 0; i<arr.length; i+=8){
            arr[i][deg-3] = 1;
            arr[i+1][deg-3] = 1;
            arr[i+2][deg-3] = 1;
            arr[i+3][deg-3] = 1;
        }
    }

    public static void findSOP(int deg, int[][] arr) {
        // The string that will display the SOP
        StringBuilder somAnswer = new StringBuilder();
    
        for (int i = 0; i < arr.length; i++) {
            // Checking for output 1
            if (arr[i][deg] == 1) {
                // Initialize a term for the current row
                StringBuilder term = new StringBuilder();
    
                // Check each input variable and add it to the term if it's included
                for (int j = 0; j < deg; j++) {
                    if (arr[i][j] == 1) {
                        term.append(getVariable(j)).append(".");
                    } else {
                        term.append(getVariable(j) + "'").append(".");
                    }
                }
    
                // Remove the ending dot
                if (term.length() > 0) {
                    term.setLength(term.length() - 1);
                }
    
                // Append the term that is enclosed in parentheses to the answer.
                somAnswer.append("(").append(term).append(") + ");
            }
        }
    
        // Remove the ending " + "
        if (somAnswer.length() > 0) {
            somAnswer.setLength(somAnswer.length() - 3);
        }
    
        System.out.println("SOP is: " + somAnswer);
    }
    

    // Method to find POS for a given degree
    public static void findPOS(int deg, int[][] arr){ 
        // String to store the POS expression
        StringBuilder posAnswer = new StringBuilder();

        // Loop through the truth table
        for (int i = 0; i < arr.length; i++) {
            // Check if the functional value is 0
            if (arr[i][deg] == 0) {
                // Build the POS term based on all variables
                StringBuilder term = new StringBuilder();
                for (int j = 0; j < deg; j++) {
                    if (arr[i][j] == 0) {
                        term.append(getVariable(j)); // Append variable if input is 0
                    } else {
                        term.append(getVariable(j) + "'"); // Append complement of variable if input is 1
                    }
                    if (j < deg - 1) {
                        term.append(" + "); // Add '+' between variables
                    }
                }
                // Append the term wrapped in parentheses to the POS expression
                posAnswer.append("(").append(term).append(") . ");
            }
        }

        // Remove the last " . " from the POS expression if it's present
        if (posAnswer.length() >= 3) {
            posAnswer.setLength(posAnswer.length() - 3);
        }

        // Print the POS expression
        System.out.println("POS is: " + posAnswer);
    }

    // Method to get variable based on index
    public static String getVariable(int index) {
        switch (index) {
            case 0:
                return X;
            case 1:
                return Y;
            case 2:
                return Z;
            case 3:
                return A;
            default:
                return ""; // Handle the case for undefined index
        }
    }

    //a method that checks for the validity of the user input in filling the funvtional values
    //returns true if input is valid, false otherwise
    public static boolean isValid(String choice){
        if(choice.equalsIgnoreCase("n") || choice.equalsIgnoreCase("y")){
            return true;
        }else{
            return false;
        }
    }
}

