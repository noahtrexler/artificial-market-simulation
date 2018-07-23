/*

The Portfolio class organizes multiple stock objects and retrieves information about them.

Authors: Noah Trexler and Bradley Bennett

*/
import java.io.*; //importing io functions, ArrayLists, and Scanner
import java.util.ArrayList;
import java.util.Scanner;
import java.text.DecimalFormat;

public class Portfolio {
   
   private ArrayList<Stock> portfolio = new ArrayList<Stock>(); //instance ArrayList of Stock objects
   DecimalFormat df = new DecimalFormat("###.##");
   
   public Portfolio(){} //empty constructor
   
   public void addStock(String symbol, String name){ //add a stock using a symbol and name
      symbol = symbol.toUpperCase(); //format symbol
      portfolio.add(new Stock(symbol, name)); //add new Stock object with given information
   }
   
   public void addStock(Stock nextStock){ //add a stock using an existing Stock object
      portfolio.add(nextStock);
   }
   
   public void removeStock(String symbol){ //remove a stock using the given symbol
      
      symbol = symbol.toUpperCase(); //format symbol
      
      for (Stock i : portfolio){ //for each Stock object in the portfolio ArrayList
         if ((i.getSymbol()).equals(symbol)){ //if the object's symbol equals the given symbol
            portfolio.remove(i); //remove the Stock object
            break; //break the loop
         }
      }
      
   }
   
   public void removeStock(Stock nextStock){ //remove a stock using an existing Stock object
      portfolio.remove(nextStock);
   }
   
   public int getLength(){ //returns length of the portfolio
      return portfolio.size();
   }
   
   public Stock getStock(int index){ //0 1 2 3
      return portfolio.get(index); // 0 1 2 3
   }
   
   public void displayPortfolio(){ //display portfolio ArrayList
      
      int counter = 0;
      
      for (Stock i : portfolio){ //for each Stock object in the portfolio
         System.out.println(Integer.toString(++counter) + ": " + i.getName() + " (" + i.getSymbol() + "): $" + Double.toString(i.getPrice())); //print name + symbol + price
      }
      
   }

   public void displayPortfolioWithShares(){ //display portfolio ArrayList with the amount of shares and their value
      
      int counter = 0;
      
      for (Stock i : portfolio){ //for each Stock object in the portfolio
         System.out.println(Integer.toString(++counter) + ": " + i.getName() + " (" + i.getSymbol() + "): $" + Double.toString(i.getPrice()) + " -> " + Double.toString(i.getShares()) + " shares = $" + df.format(i.getValueOfShares())); //print name + symbol + price + shares + value
      }
      
   }
   
   public void createPortfolio(){ //create a new portfolio
      
      boolean isRunning = true; //variable to determine status of while loop
      Scanner kboard = new Scanner(System.in); //new input
      
      while (isRunning){ //while isRunning == true
         
         System.out.println("\nPORTFOLIO OPTIONS:\n1. ADD STOCK\n2. REMOVE STOCK\n3. DISPLAY PORTFOLIO\n0. EXIT PORTFOLIO\n"); //display list of options
         int userChoice = kboard.nextInt(); //accept new input number
         kboard.nextLine();
         
         if (userChoice == 1){ //if user typed 1
            System.out.print("STOCK SYMBOL: ");
            String sym = kboard.nextLine(); //take new symbol
            System.out.print("STOCK NAME: ");
            String name = kboard.nextLine(); //take new name
            this.addStock(sym, name); //add new Stock
            System.out.println(sym.toUpperCase() + " ADDED.");
         }
         else if (userChoice == 2){ //if user typed 2
            this.displayPortfolio();
            System.out.print("STOCK SYMBOL: ");
            String sym = kboard.nextLine(); //take new symbol
            this.removeStock(sym); //remove Stock
            System.out.println(sym.toUpperCase() + " REMOVED.");
         }
         else if (userChoice == 3){ //if user typed 3
            this.displayPortfolio(); //display portfolio
         }
         else if (userChoice == 0){ //if user typed 0
            isRunning = false; //exit loop
         }
         else {
            System.out.println("COMMAND NOT RECOGNIZED!"); //default
         }
         
      }  
   }
}