/*

The BuySell class contains the logic algorithm that determines the advice suggested to the user.

Authors: Noah Trexler and Bradley Bennett

*/
import java.util.Timer; //import Timer, TimerTask, ArrayList, Scanner, and Dating formats
import java.util.TimerTask;
import java.util.ArrayList;
import java.util.Scanner;
import java.text.SimpleDateFormat;  
import java.util.Date; 

public class BuySell {
   
   private Stock newStock; //instance variables
   private int updateTime;
   private Scanner kboard = new Scanner(System.in);
   private SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss"); //format of date/time (hour:minute:second)
   
   public ArrayList<Double> priceList = new ArrayList<Double>(); //new empty ArrayList of doubles, public so NextPrice can access
   
   public BuySell(Stock stock, int timeInterval){ //constructor accepts stock object and time in seconds
      newStock = stock; //assign variable
      updateTime = timeInterval * 1000; //convert to milliseconds
   }
   
   public BuySell(Stock stock){ //constructor accepts stock object
      newStock = stock; //assign variable
   }
   
   public class NextPrice extends TimerTask { //subclass
   
      private String returnString = "";
      
      public void run(){ //run() function
         
         Date date = new Date();
         
         System.out.println(formatter.format(date) + " - $" + newStock.getPrice()); //print Stock's price
         priceList.add(newStock.getPrice()); //add that price to the list
         
         if (priceList.size() == 4){ //once the list is 4 prices long
            
            if (priceList.get(0) > priceList.get(1) && priceList.get(1) > priceList.get(2) && priceList.get(2) < priceList.get(3)){
               returnString = "buy";
               System.out.println("BUY!"); //buy if price goes down, down, down, up
            }
            else if (priceList.get(0) < priceList.get(1) && priceList.get(1) < priceList.get(2) && priceList.get(2) > priceList.get(3)){
               returnString = "sell";
               System.out.println("SELL!"); //sell if price goes up, up, up, down
            }
            
            priceList.remove(0); //remove the first element, shift the ArrayList down one index
            
         }
      }
   }

   public void update(){ //update the list
      
      Timer timer = new Timer(); //create new Timer object
      timer.schedule(new NextPrice(), 0, updateTime); //schedule a new NextPrice() function that runs after every updateTime
      
   }
   
   public void updateWithInterrupt(){ //update until user interrupts loop
      
      while (true){ //infinite loop
         
         Date date = new Date(); //get current date
         
         String userInput = kboard.nextLine(); //get user input
         
         if (!userInput.equals("")){ //if user pressed anything but enter
            break; //break loop
         }
         else {
         
            System.out.println(formatter.format(date) + " - $" + newStock.getPrice()); //print Stock's price
            priceList.add(newStock.getPrice()); //add that price to the list
         
            if (priceList.size() == 4){ //once the list is 4 prices long
            
               if (priceList.get(0) > priceList.get(1) && priceList.get(1) > priceList.get(2) && priceList.get(2) < priceList.get(3)){
                  System.out.println("BUY!"); //buy if price goes down, down, down, up
               }
               else if (priceList.get(0) < priceList.get(1) && priceList.get(1) < priceList.get(2) && priceList.get(2) > priceList.get(3)){
                  System.out.println("SELL!"); //sell if price goes up, up, up, down
               }
            
               priceList.remove(0); //remove the first element, shift the ArrayList down one index
            
            }
         }
      }
      
   }

}
