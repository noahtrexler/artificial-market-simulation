/*

The Main class contains the UI and the main code that is run, utilizing the other classes.

Authors: Noah Trexler and Bradley Bennett

*/
import java.util.Scanner; //importing Scanner

public class Main {

   public static void main(String[] args){ //main
      
      Scanner kboard = new Scanner(System.in); //initialize new Scanner
      Portfolio userPortfolio = new Portfolio(); //create new empty portfolio
      boolean isRunning = true; //declare isRunning to be true
      
      System.out.println("\nWELCOME!");
      System.out.println("v1.0");
      
      while (isRunning){ //while isRunning is true
         
         System.out.println("\n1. CREATE PORTFOLIO"); //print options
         System.out.println("2. RUN BUY/SELL ALGORITHM");
         System.out.println("3. RUN SIMULATION");
         System.out.println("4. INFO");
         System.out.println("0. QUIT");
         
         try {
         
            int userChoice = kboard.nextInt(); //accept user input
         
            if (userChoice == 1){ //if 1
               userPortfolio.createPortfolio(); //create portfolio
            }
            else if (userChoice == 2){ //if 2
            
               if (userPortfolio.getLength() != 0){ //if the portfolio is not empty
               
                  System.out.println("SELECT STOCK FROM PORTFOLIO:"); //print instruction
                  userPortfolio.displayPortfolio(); //display portfolio
                  try {
                     int userIndex = kboard.nextInt() - 1; //get stock index
                     if (userIndex > userPortfolio.getLength() - 1 || userIndex < 0){ //if the index is greater than the length of the portfolio or if the index is negative
                        System.out.println("OUT OF BOUNDS!");
                     }
                     else {
                     
                        System.out.println("SELECT (1. INFINITE UPDATE) OR (2. INTERMITTENT UPDATE)"); //print instruction
                        int updateInterval = kboard.nextInt(); //get user choice
                        if (updateInterval == 1){ //if 1
                           System.out.println("SELECT TIME INTERVAL (SECONDS):");
                           int timeInterval = kboard.nextInt(); //get seconds
                           System.out.println("YOU MUST EXIT PROGRAM TO END PRICE UPDATE!");
                           BuySell userBuySell = new BuySell(userPortfolio.getStock(userIndex), timeInterval); //create new BuySell algorithm
                           userBuySell.update(); //initialize algorithm
                           break;
                        }
                        else if (updateInterval == 2){ //if 2
                           System.out.println("PRESS ENTER TO RETRIEVE PRICE (PRESS OTHER KEY TO EXIT)");
                           BuySell userBuySell = new BuySell(userPortfolio.getStock(userIndex)); //create new BuySell algorithm
                           userBuySell.updateWithInterrupt(); //initialize algorithm with interrupt option
                        }
                        else {
                           System.out.println("COMMAND NOT RECOGNIZED!");
                        }
                     
                     }
                  }
                  catch (Exception e){
                     System.out.println("COMMAND NOT RECOGNIZED!");
                  }
               }
               else {
                  System.out.println("PORTFOLIO IS EMPTY!");
               }
            
            }
            else if (userChoice == 3){ //if 3
            
               if (userPortfolio.getLength() != 0){ //if portfolio is not empty
                  
                  System.out.println("SELECT (1. INVEST WITH STARTING FUND) OR (2. INVEST WITHOUT LIMIT) OR (3. RUN SIMULATION WITH PREVIOUS INVESTMENTS)"); //print instruction
                  try {
                     int simSetting = kboard.nextInt(); //get user input
                     if (simSetting == 1){ //if 1
                        System.out.println("SELECT INVESTMENT MONEY:");
                        double userMoney = kboard.nextDouble(); //get money
                        Simulate userSimulation = new Simulate(userPortfolio, userMoney); //initialize simulation
                        userSimulation.investWithLimit(); //invest with limit
                     }
                     else if (simSetting == 2){ //if 2
                        Simulate userSimulation = new Simulate(userPortfolio);
                        userSimulation.investNoLimit(); //invest with infinite money
                     }
                     else if (simSetting == 3){ //if 3
                        
                        boolean stockHasShares = false;
                        
                        for (int i = 0; i < userPortfolio.getLength(); i++){ //for each stock index in userPortfolio
                           if ((userPortfolio.getStock(i)).getShares() > 0){ //if the stock has more than 0 shares
                              stockHasShares = true; //stock has shares
                              break; //break for loop
                           }
                        }
                        if (stockHasShares){ //if stockHasShares == true
                           Simulate userSimulation = new Simulate(userPortfolio);
                           userSimulation.calculateReturns(); //skip investment step
                        }
                        else {
                           System.out.println("PORTFOLIO HAS NO MONEY INVESTED!");
                        }
                     }
                     else {
                        System.out.println("COMMAND NOT RECOGNIZED!");
                     }
                  }
                  catch (Exception e){
                     System.out.println("COMMAND NOT RECOGNIZED!");
                  }
               }
               else {
                  System.out.println("PORTFOLIO IS EMPTY!");
               }
            
            }
            else if (userChoice == 4){ //if 4
            
               System.out.println("\nThis is a program designed to allow the user to observe and detect profitable changes in the stock market.\n"); //print info
               System.out.println("Use the '1. CREATE PORTFOLIO' command to add stocks and their symbols to a list of stocks you wish to observe.");
               System.out.println("It is possible to create your own stock by entering a symbol that does not exist on the NYSE. The price will be random.\n");
               System.out.println("Use the '2. RUN BUY/SELL ALGORITHM' command to observe stock prices in real time. The algorithm will give invest-");
               System.out.println("ment advice based on the following logic: if a stock price increases 3 times in a row then decreases, 'SELL' will");
               System.out.println("be displayed; however, if a stock price decreases 3 times in a row then increases, 'BUY' will be displayed.\n");
               System.out.println("Use the '3. RUN SIMULATION' command to determine how much money you could earn in a stock using the BUY/SELL ALGORITHM.");
               System.out.println("We recommend using longer time intervals for more accurate investments.\n");
            
            }
            else if (userChoice == 0){ //if 0
               isRunning = false; //quit
            }
            else {
               System.out.println("COMMAND NOT RECOGNIZED!");
            }
            
         }
         catch (Exception e){ //catch error
            System.err.println("INVALID INPUT! " + e.getMessage());
            kboard.next();
         }
         
      }
      
   }

}