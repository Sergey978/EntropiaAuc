import java.io.BufferedReader;
import java.util.ArrayList;

/**
 *
 * @author Sergey
 */
public class EntropiaModel {
  
 public ArrayList<Item> items = new ArrayList<Item>();
 
 // calcute table
 public Float[][] calcTable(Item item){
     int salePrice;
     float profit;
     float fee;
     float murkUPtt;
     
     Float [][]table = new Float[item.getCount()][5];
     for(int i = 0; i < item.getCount(); i++  ){
         salePrice = (int)((i * item.getPriceOne()  ) + item.getMurkUp());
         murkUPtt = salePrice - item.getPriceOne()*i;
         // fee
         fee = (0.5f + murkUPtt * 99.5f/(1990 + murkUPtt));
         //profit
         profit = salePrice - (item.getPriceOne() * item.getBuyPrice() * i/100) - fee;
         table[i][0] = (float)i;
         table[i][1] = (float)salePrice;
         table[i][2] = profit;
         table[i][3] = fee;
         table[i][4] = salePrice / (i * item.getPriceOne()) * 100 ;
         
     }
     return table;
 }

 
 
 
 
    
    
 
    
    
    
    
    
    
    
   

    
    
}
