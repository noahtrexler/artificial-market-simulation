/*

The Stock class is the foundation of the entire stock market simulation. The class creates Stock objects which store information about a certain stock on the NYSE.

Authors: Noah Trexler and Bradley Bennett

*/
import java.io.*; //importing io functions and net functions
import java.net.*;

public class Stock {
   
   private String symbol; //instance variables
   private String stockName;
   private double marketPrice;
   private double shares;
   
   public Stock(String sym, String name){ //constructor accepts symbol and name
      
      symbol = sym.toUpperCase(); //formatting symbol
      stockName = name;
      
      try { //use try catch in case of io error
         this.setPrice(); //use setPrice to find the stock's price
      }
      catch (IOException e){ //catch io error
         System.out.println("ERROR!"); //print error
      }
      
   }
   
   public Stock(String sym, String name, double numShares){ //constructor accepts number of shares
      
      symbol = sym.toUpperCase(); //formatting symbol
      stockName = name;
      shares = numShares;
      
      try { //use try catch in case of io error
         this.setPrice(); //use setPrice to find the stock's price
      }
      catch(IOException e){ //catch io error
         System.out.println("ERROR!"); //print error
      }
      
   }
   
   public void setPrice() throws IOException{ //setPrice() has no return and watches for io error
      
      URL stockURL = new URL("https://finance.yahoo.com/quote/" + symbol + "?p=" + symbol); //url combines the symbol and yahoo quote search
      URLConnection stockConnection = stockURL.openConnection(); //create new URLConnection
      
      InputStreamReader inStream = new InputStreamReader(stockConnection.getInputStream()); //create new InputStreamReader
      BufferedReader buff = new BufferedReader(inStream); //create new BufferedReader
      
      String price = "NULL"; //default string
      String line = null; //default html line
      
      while ((line = buff.readLine()) != null){ //while the BufferedReader does not read nothing
         
         if (line.contains("\"regularMarketPrice\":{\"raw\":")){ //if the line contains this specific string
            
            int targetIndex = line.indexOf("\"regularMarketPrice\":{\"raw\":"); //find index of specific currentPrice string from html data
            String currentPrice = line.substring(targetIndex, line.indexOf("}", targetIndex) - 1);
            
            price = currentPrice.substring(currentPrice.lastIndexOf("\"") + 1); //price is retrieved from formatting html data
            
         }
         
      }
      
      marketPrice = Double.valueOf(price.replaceAll(",", "").toString()); //format the double variable
      
   }
   
   public double getPrice(){ //returns marketPrice
      try { //updating price
         this.setPrice();
      }
      catch(IOException e){
         System.out.println("ERROR!");
      }
      return marketPrice;
   }
   
   public String getSymbol(){ //returns symbol
      return symbol;
   }
   
   public String getName(){ //returns name
      return stockName;
   }
   
   public double getShares(){ //returns number of shares
      return shares;
   }
   
   public void setShares(double numShares){ //assigns number of shares
      shares = numShares;
   }
   
   public double getValueOfShares(){ //returns shares * price
      return shares * this.getPrice();
   }
   
}