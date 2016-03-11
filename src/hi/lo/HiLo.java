/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hi.lo;

import java.util.*;
import java.io.*;
/**
 * @author hetru2942
 * This program is a game called "Hi-Lo". The program will generate a random number
 * The user will wager a number of points into the "pot".
 * The user will guess "high", "low", or call that the number is 7.
 * If the user guesses that the number "high" or "low", and wins then the user will 
 * receive back 2x the amount the user wagered.
 * If the user calls 7 and wins, then the user will receive back 10x the amount 
 * the user wagered.
 * If the user incorrectly guesses, then the user will lose the amount of points wagered.
 */
public class HiLo {

    public static int pointGame = 1000;
    /**
     * @param args the command line arguments
     */
    public static File dataFile = new File("ScoreHistory.dat");
    public static FileWriter out;
    public static BufferedWriter writeFile;
    
    public static void main(String[] args) {
        if (dataFile.exists()){
            dataFile.delete();
        }
        
        mainMenu(); //calls the main menu method
    }
    
    public static void mainMenu(){ 
        /*prints out the rules of the game, and then launches the pointTracker method
        This is stored in this method, seperate from the main (String[] args) to 
        make it easier to call when returning to the start of the game
        */
        
        //explains the rules of the game
        System.out.println("High Low Game \n\nRULES\nNumbers 1 through 6 are low" 
                + "\nNumbers 8 through 13 are high\nNumber 7 is neither high or low"
                + "\nIf you guess High or Low and win, you will receive 2x the amount"
                + " of points you wagered\nIf you guessed 7, and win, you will receive "
                + "10x the amount of points wagered.");
        
        pointTracker();
    }
    
    public static void pointTracker(){
        /*
        This method informs the user the amount of points they have to spend, 
        calls the scoreHistory method to save the amount of points they have and 
        then calls the input risk method
        */
        System.out.println("\nYou have "+pointGame+" points");
        scoreHistory(pointGame);
        
        inputRisk();
    }
    
    public static void inputRisk(){
        try
        {
            //Ask the user how many points they would like to wager
            Scanner inputRisk = new Scanner(System.in);
            System.out.print("Enter points to risk: ");
            int riskPoints = inputRisk.nextInt();
            
            if(riskPoints<=0){ 
                /*if the user wagers 0 or less points, the program will ask the 
                user how many points they would like to spend and inform them that 
                there previous input was not enough
                */
                System.out.println("\nSorry, but you have to pay to play.");
                inputRisk();
            } else if(riskPoints>pointGame){
                /*
                If the user wagers more points than the amount the user has, the 
                programn will inform the user that the user has insufficient amount 
                of points and goes back to asking the user how many points the user 
                would like to wager
                */
                System.out.println("\nInsufficient amount of points.");
                inputRisk();
            }else{
                /*
                If the user did not wager too many points, or too little, the 
                program will continue by calling the bet method
                */
                bet(riskPoints);
            }
        }
        catch(InputMismatchException e){
            /*
            If the user uses non-interger values, then the program will inform 
            the user that it is an invalid input and will start the game back from
            the beginning again.
            */
            System.out.println("\n\n\nYou can't bet that!");
            mainMenu();
        }
    }
    
    public static void bet(int riskPoints){
        try
        {
            //This method will ask for the user's guess
            System.out.print("\nPredict (0=Low, 1=High, 7=7): ");

            Scanner betInput = new Scanner(System.in);
            int inputBet = betInput.nextInt();

            /*
            This if statement will take the user's guess and pass it through, 
            along with the int value of the amount of points the user wagered, 
            into the compare method
            */
            if (inputBet == 0){
                //If the user inputs 0, then they are guessing "low"
                compare(riskPoints, inputBet);
            }
            else if (inputBet == 1){
                //If the user inputs 1, then they are guess "high"
                compare(riskPoints, inputBet);
            }
            else if (inputBet == 7){
                //If the user inputs 7, then they are guessing the number will be 7
                compare(riskPoints, inputBet);
            }
            else{
                /*
                If the user enters a value that is not 0, 1, or 7, then the 
                program will inform the user that the input is invalid and will 
                then run this method again, asking for the user's guess
                */
                System.out.println("\n\n*************"
                        + "\nInvalid Guess\n*************");
                bet(riskPoints);
            }
        }
        catch(InputMismatchException e){
            /*
            If the user enters a non numeric value, then the program will inform 
            the user that the input is invalid and the program will run the game 
            from the start
            */
            System.out.println("\n\n*************"
                        + "\nInvalid Guess\n*************");
            mainMenu();
        }
    }
    
    public static void compare(int riskPoints, int inputBet){
        /*
        Compares the player's guess to the answer, if the guess and answer
        match, the player wins and gains points
            -If the player had guessed 7 and won, he would earn 10x the amount he bet, 
        otherwise he wins 2x the amount he bet
        If not, the player loses the amount of points he bet.
        If the player's points reach 0, it's game over and the program ends.
        If the player still has points to bet, they may play the game again.
        */
        if(inputBet == result()) 
        //if the user's guess matches with the number generated
        {
            if(inputBet == 1 || inputBet == 0) 
            /*
            if the user guess high or low, the user will get the regular prize 
            (2x the amount the user bet) and the option to play again
            */
            {
                pointAdd(riskPoints); //calls method to add the prize
                
                //informs the user that the user has won and gives option to play again
                System.out.println("You win.\nPlay again? (Y/N)"); 
                
                Scanner again = new Scanner(System.in);
                String playAgain = again.nextLine();
                
                if(playAgain.equalsIgnoreCase("Y") || playAgain.equalsIgnoreCase("yes") 
                        || playAgain.equalsIgnoreCase("1"))
                {
                    /*
                    if the user types in "yes", "y", or "1" then the program 
                    will launch the game once again
                    */
                    pointTracker(); 
                }
                else if (playAgain.equalsIgnoreCase("n") || 
                        playAgain.equalsIgnoreCase("no") || playAgain.equalsIgnoreCase("0"))
                {
                    /*
                    If the user types in "no" , "n", or "0", then the program will 
                    inform the user what his inal amount of points the user had
                    and save the score onto the scoreHistory datafile
                    */
                    System.out.println("You have "+pointGame+" points. Good job.");
                    scoreHistory(pointGame);
                    
                }
                else
                {
                    /*
                    If the user enters an input that does not match the above 
                    available inputs, then the program will end
                    */
                    System.out.println("I'm not sure what you meant by that, but "
                            + "I'll just guess you're done. \n You have "+pointGame+" points. Good job.");
                    scoreHistory(pointGame);
                }
            }
            else
            {
                /*
                if the user had guess 7, then the user will receive 10x the 
                amount the user had wagered
                */
                jackpot(riskPoints);   
                
                /*
                Tells the user that they have won with a jackpot and ask if 
                they would like to play again
                */
                System.out.println("Jack Pot! You win.\nPlay again? (Y/N)"); 
                
                Scanner again = new Scanner(System.in);
                String playAgain = again.nextLine();
                
                if(playAgain.equalsIgnoreCase("Y") || playAgain.equalsIgnoreCase("Yes") 
                        || playAgain.equalsIgnoreCase("1"))
                {
                    /*
                    if the user types in "yes", "y", or "1" then the program 
                    will launch the game once again
                    */
                    pointTracker();
                }
                else if (playAgain.equalsIgnoreCase("n") || playAgain.equalsIgnoreCase("no") 
                        || playAgain.equalsIgnoreCase("0"))
                {
                    /*
                    If the user types in "no" , "n", or "0", then the program will 
                    inform the user what his inal amount of points the user had
                    and save the score onto the scoreHistory datafile
                    */
                    System.out.println("You have "+pointGame+" points. Good job.");
                    scoreHistory(pointGame);
                }
                else
                {
                    /*
                    If the user enters an input that does not match the above 
                    available inputs, then the program will end
                    */
                    System.out.println("I'm not sure what you meant by that, but "
                            + "I'll just guess you're done.");
                    scoreHistory(pointGame);
                }
            }
        }
        else
            /*If the user's guess does not match the program's generated number, 
            then the following will occur
            */
        {
            //The user will lose the amount that the user had wagered
            pointLoss(riskPoints);
            
            //The program will inform the user that they have lose
            System.out.println("You lose.");
                
            if(pointGame == 0){
                /*
                \
                    if the user has reached 0 points, then the user has lost the entire 
                game, and message indicating this appear and the program will end
                */
                System.out.println("\n\n"
                    + "  ________                        ________                     \n" +
                    " /  _____/_____    _____   ____   \\_____  \\___  __ ___________ \n" +
                    "/   \\  ___\\__  \\  /     \\_/ __ \\   /   |   \\  \\/ // __ \\_  __ \\\n" +
                    "\\    \\_\\  \\/ __ \\|  Y Y  \\  ___/  /    |    \\   /\\  ___/|  | \\/\n" +
                    " \\______  (____  /__|_|  /\\___  > \\_______  /\\_/  \\___  >__|   \n" +
                    "        \\/     \\/      \\/     \\/          \\/          \\/       ");
            }
            else{
                /*
                If the user still has points that they could spend, then the 
                program will ask if the user would like to play again
                */
                System.out.println("Play again? (Y/N)");

                Scanner again = new Scanner(System.in);
                String playAgain = again.nextLine();

                    if(playAgain.equalsIgnoreCase("Y") 
                            || playAgain.equalsIgnoreCase("Yes") 
                            || playAgain.equalsIgnoreCase("1"))
                    {
                        /*
                        If the user inputs "y", "yes" or "1", then the program 
                        will start the game again
                        */
                        pointTracker();
                    }
                    else if (playAgain.equalsIgnoreCase("n") 
                            || playAgain.equalsIgnoreCase("no") 
                            || playAgain.equalsIgnoreCase("0"))
                    {
                        /*
                        If the user inputs "n", "no", or "0", the the program 
                        will inform the user of how many points the user has, and 
                        saves the value of the points into a datafile for tracking 
                        the user's score history in their playthrough
                        */
                        System.out.println("You have "+pointGame+" points. Good job.");
                        scoreHistory(pointGame);
                    }
                    else
                    {
                        /*
                        If the user enters an input that doesn't match with 
                        the above options, then the program will end
                        */
                        System.out.println("I'm not sure what you meant by that, "
                                + "but I'll just guess you're done. You have "
                                +pointGame+" points. Good job.");
                        scoreHistory(pointGame);
                    }
            }    
        }
    }
        
    public static int result(){ 
        /*takes the random number, and determines where the number is a "high" 
        number, "low" number, or if it is 7, and returns that value
        */
        int resultNum;
        int randy = randomNumber();
        
        System.out.println("The number is " +randy);
        
        if(randy <= 6) //returns 0 to indicate a "low" number
        {
            resultNum = 0; 
            
        }
        else if(randy == 7) //returns 7 because that's what the number generated was
        {
            resultNum = 7;
        }
        else
        {
            resultNum = 1; //returns 0 to indicate a "high" number
        }
        
        return resultNum;
    }    
    
    public static int randomNumber(){ 
    //generate a random number between 1 to 13 and returns that int value when called
        int randomNum = (int)Math.ceil(1 + Math.random()*12);
        return randomNum;
    }
    
    public static void pointAdd(int riskPoints){ 
    //this method is for when the user wins, and earns double what they risked
        pointGame = pointGame + riskPoints;
    }
    
    public static void pointLoss(int riskPoints){ 
    //this method is for when the user loses the bet, and loses the amount of points he risked       
        pointGame = pointGame - riskPoints;
    }
    
    public static void jackpot(int riskPoints){ 
    //this method is for when the user hits the jackpot and earns 10x the amount he bet       
        pointGame = pointGame + 9*riskPoints;
    }
    
    public static void scoreHistory(int pointGame){ 
        /*Saves the player's score for his current playthrough (every single 
        time they play the game during the time the program is running) into a 
        datafile
        */
        try{
            out = new FileWriter(dataFile, true);
            writeFile = new BufferedWriter(out);
    
            for (int i = 0; i < 1; i++){
                writeFile.write(String.valueOf(pointGame));
                writeFile.newLine();
            }
            
            writeFile.close();
            out.close();
        } 
        catch(IOException e){
            //Display error message if any problems with 
            System.out.println("Problem saving score.");
            System.err.println("IOException: " +e.getMessage());
        }
    }    
}