/*

The Simulate class contains the actual stock market simulation and utilizes each other class except for Main.

Authors: Noah Trexler and Bradley Bennett

*/
import java.util.Scanner; //imports Scanner, formatting, timers, and ArrayList
import java.text.DecimalFormat;
import java.util.Timer;
import java.util.TimerTask;
import java.util.ArrayList;

public class Simulate {
   
   private Portfolio portfolio; //instance variables
   private double liquidMoney;
   
   public Stock nextStock; //public because it needs to be accessed by NextPrice
   DecimalFormat df = new DecimalFormat("###.##");
   
   Scanner kboard = new Scanner(System.in); //user input
   
   public Simulate(Portfolio paramPortfolio, double money){ //constructor accepts portfolio object and money
      portfolio = paramPortfolio;
      liquidMoney = money;
   }
   
   public Simulate(Portfolio paramPortfolio){ //constructor accepts just portfolio
      portfolio = paramPortfolio;
   }
   
   public void investWithLimit(){ //invest with limited money
      
      boolean isRunning = true;
      
      while (isRunning && liquidMoney > 0){ //while the user has not exited and the user has money left
      
         portfolio.displayPortfolioWithShares(); //show the portfolio
         System.out.println("0: BEGIN SIMULATION");
         
         System.out.println("\nINVEST MONEY INTO A STOCK -- ($" + df.format(liquidMoney) + ")"); //print instructions
         System.out.println("SELECT NUMBER");
         
         try {
            
            int userChoice = kboard.nextInt(); //take user input
            
            if (userChoice == 0){ //if 0
               isRunning = false; //end loop
               break;
            }
            
            Stock nextStock = portfolio.getStock(userChoice - 1); //get stock user chose
            System.out.println("ENTER MONEY FROM FUND OF $" + df.format(liquidMoney) + ": " + (nextStock.getName())); //print instructions
            
            double deposit = kboard.nextDouble(); //get deposit
            
            if (deposit <= liquidMoney){ //deposit must be less than fund
            
               double numShares = deposit/nextStock.getPrice(); //number of shares is the deposit divided by the stock's price
               liquidMoney -= (numShares * nextStock.getPrice()); //subtract deposit from fund
               nextStock.setShares(numShares); //set shares of stock
               System.out.println("YOU PURCHASED " + df.format(numShares) + " SHARES OF " + nextStock.getSymbol()); //print result
               
            }
            else {
               System.out.println("INVALID INPUT!");
            }
            
         }
         catch (Exception e){ //catch error
            System.err.println("INVALID INPUT! " + e.getMessage());
            kboard.next();
         }
         
      }
      
      System.out.println(""); //new line
      this.calculateReturns(); //do sim
      
   }
   
   public void investNoLimit(){ //invest without money limit
      
      boolean isRunning = true;
      
      while (isRunning){ //while user has not quit
      
         portfolio.displayPortfolioWithShares(); //display portfolio
         System.out.println("0: BEGIN SIMULATION"); //print instructions
         
         System.out.println("\nINVEST MONEY INTO A STOCK");
         System.out.println("SELECT A NUMBER");
         
         try {
            
            int userChoice = kboard.nextInt(); //get user choice
            
            if (userChoice == 0){ //if 0
               isRunning = false; //quit
               break;
            }
            
            Stock nextStock = portfolio.getStock(userChoice - 1); //get user stock
            System.out.println("ENTER MONEY INTO: " + (nextStock.getName()));
            
            double deposit = kboard.nextDouble(); //get deposit
            
            double numShares = deposit/nextStock.getPrice(); //get number of shares
            nextStock.setShares(numShares); //set number of shares
            System.out.println("YOU PURCHASED " + df.format(numShares) + " SHARES OF " + nextStock.getSymbol()); //print result
               
         }
         catch (Exception e){ //catch error
            System.err.println("INVALID INPUT! " + e.getMessage());
            kboard.next();
         }
         
      }
      
      this.calculateReturns(); //do sim
      
   }
   
   public void calculateReturns(){ //actual sim
      
      ArrayList<Stock> stocksToSim = new ArrayList<Stock>();
      
      portfolio.displayPortfolioWithShares(); //display portfolio
      System.out.println("100. BEGIN");
      System.out.println("0. BACK");
      
      System.out.println("SELECT STOCKS TO SIMULATE"); //print instructions
      
      while (true){
         try {
            
            int userChoice = kboard.nextInt(); //get user choice
            kboard.nextLine();
            
            if (userChoice == 0){ //if 0, begin
               break;
            }
            else if (userChoice == 100){
            
               while (true){
                  
                  if (stocksToSim.size() == 0){
                     System.out.println("NO STOCKS SELECTED!"); //user must add stocks to simulate
                     break;
                  }
                  
                  try {
                  
                     System.out.println("ENTER TIME INTERVAL FOR SIMULATION UPDATES (MINUTES)");
                  
                     int timeInterval = kboard.nextInt() * 60; //get update interval, convert to seconds
                     kboard.nextLine();
                  
                     System.out.print("PLEASE GIVE TIME TO RETRIEVE INITIAL DATA"); //tell user to wait
                     for (int i = 1; i <= 3; i++){ //simple loop to print "..."
                        System.out.print(".");
                        try {
                           Thread.sleep(1000);
                        }
                        catch (Exception e){
                           break;
                        }
                     }
                     System.out.println("");
                  
                     Timer timer = new Timer(); //initializing Timer
                  
                     for (Stock i : stocksToSim){ //schedule a new TimerTask for each stock the user chose
                        timer.schedule(new NextPrice(i, liquidMoney), 0, timeInterval * 1000); //schedule new TimerTask with NextPrice, initial delay = 0, time interval = timeInterval converted to milliseconds
                     }
                  
                     kboard.nextLine();
                  
                  }
                  catch (Exception e){
                     System.out.println("COMMAND NOT RECOGNIZED!");
                  }
               }
               
            }
            else {
               Stock nextStock = portfolio.getStock(userChoice - 1); //set stock
               stocksToSim.add(nextStock);
               System.out.println("WILL SIMULATE: " + nextStock.getSymbol()); //display which stocks to simulate
            }
            
         }
         catch (Exception e){
            System.out.println("COMMAND NOT RECOGNIZED!");
         }
      }
      
      
      
   }

   public class NextPrice extends TimerTask { //subclass of TimerTask
   
      private String returnString = ""; //instance variables
      private double liquidSimMoney;
      private double numShares;
      private Stock simStock;
      
      private ArrayList<Double> priceList = new ArrayList<Double>();
      
      public NextPrice(Stock stock, double money){ //constructor accepts stock object and money
         simStock = stock;
         liquidSimMoney = money;
      }
      
      public void run(){ //run() function for TimerTask
         
         double numShares = simStock.getShares(); //set numShares
         returnString = "..."; //set returnString
      
         priceList.add(simStock.getPrice()); //add new price to the list
         
         if (priceList.size() == 4){ //once the list is 4 prices long
            
            if (priceList.get(0) > priceList.get(1) && priceList.get(1) > priceList.get(2) && priceList.get(2) < priceList.get(3)){ //down down down up
               
               returnString = "BUY";
               
               if (liquidSimMoney != 0.0){ //if the money is not 0
                  simStock.setShares(liquidSimMoney/simStock.getPrice()); //set amount of shares to simMoney/stock price, converts liquid money to shares
                  liquidSimMoney = 0.0; //get rid of money
               }
               
            }
            else if (priceList.get(0) < priceList.get(1) && priceList.get(1) < priceList.get(2) && priceList.get(2) > priceList.get(3)){ //up up up down
            
               returnString = "SELL";
               
               if (numShares != 0.0){ //if the amount of shares is not 0
                  liquidSimMoney = simStock.getValueOfShares(); //convert amount of shares to liquid money
                  simStock.setShares(0.0); //get rid of shares
               }
               
            }
            
            priceList.remove(0); //remove the first element, shift the ArrayList down one index
            
         }
         
         java.util.Date date = new java.util.Date(); //get current date/time
         System.out.println(date + "\n"); //print date and a new line 
            
         System.out.println("YOUR SHARES        SHARE VALUE       YOUR LIQUID MONEY       " + simStock.getName() + " [" + returnString + "]"); //return string says (.../BUY/SELL)
         System.out.println("-----------        -----------       -----------------");
         System.out.printf("%-17s $%-16s $%-16s\n", Double.toString(simStock.getShares()), df.format(simStock.getValueOfShares()), df.format(liquidSimMoney)); //format shares, their value, and liquid money into columns
         System.out.println("\n"); //print 2 new lines
         
      }
      
   }
   
}