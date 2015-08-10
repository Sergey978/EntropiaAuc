

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;


import java.util.ArrayList;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileSystemView;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Sergey
 */
public class EntropiaController {
    
    private static EntropiaController instance = null;
    private EntropiaModel model ;
    
    public String[] listNames ;
    
    private javax.swing.JTextField fieldBuyPrice = null;
    private javax.swing.JTextField fieldCount = null;
    private javax.swing.JTextField fieldMurkUp = null;
    private javax.swing.JTextField fieldPriceOne = null;
    private javax.swing.JTable jTable1 = null;
    private javax.swing.JComboBox jComboBox1 = null;
    private javax.swing.JComboBox jComboBox2 = null;
    
    private String defaultNameFile = "default.ent" ;
    private PaintGraph pg = null;
        
    public static EntropiaController getInstance() {
        if (instance==null) {
         instance = new EntropiaController();
        }
        return instance;
    }
    
    
    
    public EntropiaController(){
        model = new EntropiaModel();
        listNames = getItemsNames(model.items);
        
        defaultLoad(defaultNameFile);
        
        
        
    }
    // method to get a list of names
    public String[] getItemsNames(ArrayList items){
       Item item;
        String[] names = new String[items.size()];
        for (int i = 0; i < items.size(); i++){
            item = (Item)items.get(i);
            names[i] = item.getName();
        }
        return names;
        
    }
    
    public void choiceItem(String itemName){
        for (int i=0; i< model.items.size(); i++){
            if (model.items.get(i).getName().equals(itemName)){
                fieldBuyPrice.setText(Float.toString(model.items.get(i).getBuyPrice()));
                fieldPriceOne.setText(Float.toString(model.items.get(i).getPriceOne()));
                fieldCount.setText(Integer.toString(model.items.get(i).getCount()));
                fieldMurkUp.setText(Float.toString(model.items.get(i).getMurkUp()));
                pg.setKx(model.items.get(i).getKx());
                pg.setKy(model.items.get(i).getKy());
                pg.setOxn(model.items.get(i).getOxn());
                pg.setOyn(model.items.get(i).getOyn());
                
            }
        }
       
    }
    
    
   // metod processing pressed button "изменить"
    public void execute(){
        Item item = null;
        String regexp = "^\\d+(\\.\\d+)?";
        Float[][] tableResult ;// таблица с результатами вычислений
        
        for (int i = 0; i < model.items.size(); i++){
            if (model.items.get(i).getName().equals((String)jComboBox1.getSelectedItem())){
                item =  model.items.get(i);
            }            
        }
        //check the entered values and assigning variables
        if (fieldBuyPrice.getText().matches( regexp ) && fieldMurkUp.getText().matches( regexp)
                && fieldCount.getText().matches( regexp) && fieldPriceOne.getText().matches( regexp )){
            item.setBuyPrice(Float.parseFloat(fieldBuyPrice.getText()));
        
            item.setMurkUp(Float.parseFloat(fieldMurkUp.getText()));
       
            item.setCount(Integer.parseInt(fieldCount.getText()));
        
            item.setPriceOne(Float.parseFloat(fieldPriceOne.getText()));
        }
        else{
            JOptionPane.showMessageDialog(null, "Incorrect data !",
                                    "Error !", JOptionPane.ERROR_MESSAGE); 
        }
        
        
       
        
        
        tableResult =  new Float[item.getCount()][4];
        tableResult = model.calcTable(item);
        pg.setTable(tableResult);
        
        jTable1.setModel(new javax.swing.table.DefaultTableModel(tableResult, new String [] {
                "Количество", "Цена продажи", "Прибыль", "Налог", "MurkUp по ТТ"
            }));
        
        
    }
    // method of downloading from file
    public void load(File file){
        
        
        try{
            ObjectInputStream is = new ObjectInputStream(new FileInputStream(file));
                
            
                model.items = null;
           
                model.items = (ArrayList<Item>)is.readObject();
                
                listNames = getItemsNames(model.items);
                
            
            
        } catch(Exception ex){
            ex.printStackTrace();
             JOptionPane.showMessageDialog(null, "Can't load file !",
                                    "Error !", JOptionPane.ERROR_MESSAGE);
            
        }
        
        
    }
    
    //
    public void defaultLoad(String defaultName){
        // Specifies the path to the My Documents folder
        JFileChooser fr = new JFileChooser();  
        FileSystemView fw = fr.getFileSystemView();  
        File documents = fw.getDefaultDirectory();
        File file =  new File (documents, defaultName);
        
        load (file);
                       
    }
    
    
    
    //action when the window is closed
    public void windowClosing(){
        defaultSave(defaultNameFile);
        
    }
    
    
    
   // method of writing to a file
    public void save(File file){
        
        try{
            ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(file));
            
                os.writeObject(model.items);
                
            
            
        } catch (IOException ex){
           
        }
         
    }
    
    // method of default Save
    public void defaultSave(String defaultName){
        // Specifies the path to the My Documents folder
        JFileChooser fr = new JFileChooser();  
        FileSystemView fw = fr.getFileSystemView();  
        File documents = fw.getDefaultDirectory();
        File file =  new File (documents, defaultName);
        
        save( file);
        
    }
    
   // pressed button "Add Item"
    public void buttonAddPressed(String name, String priceOne, String buyPrice){
        
       
        float newPriceOne = 0;
        float newBuyPrice = 0;
        try {
            newPriceOne = Float.parseFloat(priceOne);
            newBuyPrice = Float.parseFloat(buyPrice);
        }
        catch (Exception ex){
            
            
        }
        model.items.add(new Item(name, newPriceOne, newBuyPrice));
        jComboBox1.addItem(name);
        getjComboBox2().addItem(name);
        listNames = getItemsNames(model.items); 
   
    }
    
    // pressed button "Delete Items
    public void buttonDeletePressed(String nameItem){
        for (int i = 0; i < model.items.size(); i++){
            if (model.items.get(i).getName().equals(nameItem)){
                 model.items.remove(i);
                  
            }   
            
        }
        listNames = getItemsNames(model.items);
        jComboBox1.removeItem(nameItem);
        getjComboBox2().removeItem(nameItem);
        
        
    }
    
    // presset button calc on average frame
    public float calcButtonPressed(String firstNumber, String firstPrice, String secondNumber, String secondPrice){
       
        float calc = 0;
        int firstNum = Integer.parseInt(firstNumber);
        int secondNum = Integer.parseInt(secondNumber);
        
        calc = (firstNum * Float.parseFloat(firstPrice) + secondNum * Float.parseFloat(secondPrice) ) / ( firstNum + secondNum);
        
        return calc;
       
    }
    
   //Изменение коэфицентов масштабирования по оси X и Y
    public void buttonXplusPressed(String name){
        pg.setKx(pg.getKx() + 50);
        for (int i=0; i < model.items.size(); i++){
            if (model.items.get(i).getName().equals(name)){
                model.items.get(i).setKx(pg.getKx());
               
            }
        }
        
    }
    
    public void buttonXminusPressed(String name){
        
        pg.setKx(pg.getKx() - 50);
        for (int i=0; i< model.items.size(); i++){
            if (model.items.get(i).getName().equals(name)){
                model.items.get(i).setKx(pg.getKx());
               
            }
        }
        
        
    }
    
   
     public void buttonYplusPressed( String name){
        pg.setKy(pg.getKy() + 50);
        for (int i=0; i< model.items.size(); i++){
            if (model.items.get(i).getName().equals(name)){
                model.items.get(i).setKy(pg.getKy());
               
            }
        }
    }
     
    public void buttonYminusPressed(String name){
         pg.setKy(pg.getKy() - 50);
         for (int i=0; i< model.items.size(); i++){
            if (model.items.get(i).getName().equals(name)){
                model.items.get(i).setKy(pg.getKy());
               
            }
        }
    }
    // Смещение графика вверх- вниз
    public void buttonUpPressed(String name){
        pg.setOyn(pg.getOyn() - 50);
        for (int i=0; i< model.items.size(); i++){
            if (model.items.get(i).getName().equals(name)){
                model.items.get(i).setOyn(pg.getOyn());
               
            }
        }
        
        
    }
    
    
    public void buttonDownPressed(String name){
        pg.setOyn(pg.getOyn() + 50);
        for (int i=0; i< model.items.size(); i++){
            if (model.items.get(i).getName().equals(name)){
                model.items.get(i).setOyn(pg.getOyn());
               
            }
        }
        
        
    }
    
    public void buttonLeftPressed(String name){
        pg.setOxn(pg.getOxn() - 50);
        for (int i=0; i< model.items.size(); i++){
            if (model.items.get(i).getName().equals(name)){
                model.items.get(i).setOxn(pg.getOxn());
               
            }
        }
        
    }
    public void buttonRightPressed(String name){
        pg.setOxn(pg.getOxn() + 50);
        for (int i=0; i< model.items.size(); i++){
            if (model.items.get(i).getName().equals(name)){
                model.items.get(i).setOxn(pg.getOxn());
               
            }
        }
        
    }
    
    
    
    
    
    
    
   // Поиск кликнутой точки
    public void mouseOnPoint (int x, int y){
       int numRow;
        
       numRow =   pg.getNumberLine(x, y);
       jTable1.setRowSelectionInterval(numRow , numRow); // показ выделенной точки в таблице
        
        jTable1.scrollRectToVisible(jTable1.getCellRect(numRow, 0, true)); //скролл таблицы к выделенной точке
    }
    
    public javax.swing.JTextField getFieldBuyPrice() {
        return fieldBuyPrice;
    }
    
    

    /**
     * @param fieldBuyPrice the fieldBuyPrice to set
     */
    public void setFieldBuyPrice(javax.swing.JTextField fieldBuyPrice) {
        this.fieldBuyPrice = fieldBuyPrice;
    }

    /**
     * @return the fieldCount
     */
    public javax.swing.JTextField getFieldCount() {
        return fieldCount;
    }

    /**
     * @param fieldCount the fieldCount to set
     */
    public void setFieldCount(javax.swing.JTextField fieldCount) {
        this.fieldCount = fieldCount;
    }

    /**
     * @return the fieldMurkUp
     */
    public javax.swing.JTextField getFieldMurkUp() {
        return fieldMurkUp;
    }

    /**
     * @param fieldMurkUp the fieldMurkUp to set
     */
    public void setFieldMurkUp(javax.swing.JTextField fieldMurkUp) {
        this.fieldMurkUp = fieldMurkUp;
    }

    /**
     * @return the fieldPriceOne
     */
    public javax.swing.JTextField getFieldPriceOne() {
        return fieldPriceOne;
    }

    /**
     * @param fieldPriceOne the fieldPriceOne to set
     */
    public void setFieldPriceOne(javax.swing.JTextField fieldPriceOne) {
        this.fieldPriceOne = fieldPriceOne;
    }

    /**
     * @return the jTable1
     */
    public javax.swing.JTable getjTable1() {
        return jTable1;
    }

    /**
     * @param jTable1 the jTable1 to set
     */
    public void setjTable1(javax.swing.JTable jTable1) {
        this.jTable1 = jTable1;
    }

    /**
     * @return the jComboBox1
     */
    public javax.swing.JComboBox getjComboBox1() {
        return jComboBox1;
    }

    /**
     * @param jComboBox1 the jComboBox1 to set
     */
    public void setjComboBox1(javax.swing.JComboBox jComboBox1) {
        this.jComboBox1 = jComboBox1;
    }

    /**
     * @param pg the pg to set
     */
    public void setPg(PaintGraph pg) {
        this.pg = pg;
    }

    /**
     * @return the jComboBox2
     */
    public javax.swing.JComboBox getjComboBox2() {
        return jComboBox2;
    }

    /**
     * @param jComboBox2 the jComboBox2 to set
     */
    public void setjComboBox2(javax.swing.JComboBox jComboBox2) {
        this.jComboBox2 = jComboBox2;
    }

   
    

   
   

    

    
    
}
