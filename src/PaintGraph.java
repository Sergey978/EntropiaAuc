import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.lang.*;

public class PaintGraph extends JPanel{
 private int ny , nx , oyn ,  oxn ,  ly , lx , sw ;//oxn, oyn отступы от края , ly, lx длинна осей
 private    float  kx , ky , hx;
 private boolean paintFunc; // флаг , разрешающий отрисовать график
 private Float table [][]; // table of data
 private int pointCord [][];// массив координат точек
 private int numberPointSelect = 0; //номер выделенной точки
 
 private float stepX; // шаг сетки по х
 private float stepY; // шаг сетки по у

 public PaintGraph()
 {
  ny = 10;// цена деления  по шкалам
  ky = (float)500; // коэф Масштабирования шкалы по у
  kx = (float)200; // коэф Масштабирования шкалы по x
  
  oyn = 50; // начальный отступ по y
  oxn = 50 ; //начальный отступ по х
  
  ly = 800; // длина оси у
  lx = 1000; // длина оси х
  // по умолчанию в начале на экран выводится график y=x
  sw = 1; // свич для переключения графика функции
  hx = (float)0.011;//шаг табуляции
  
  paintFunc = false; // 
  stepX = 5f;
  stepY = 0.1f;
 }

 public void paint(Graphics g)
 {
  super.paint(g);
  Graphics2D g2 = (Graphics2D)g;
  g2.setStroke(new BasicStroke(3));

  // Ось Y
  g2.drawLine(   oxn  , oyn , oxn, ly + oyn );
   // Ось Х
  g2.drawLine( oxn ,  ly + oyn  , lx + Math.abs(oxn) ,  ly + oyn  ); 
  //Ось Y 100%
  g.drawLine( (int)(oxn + kx) , 0 - Math.abs(oyn), (int)(oxn + kx) , ly + oyn);
  g2.setStroke(new BasicStroke(1));
  
// Надпись
 // g.drawString( "Y" , ( int ) ( lx * kx + oxn) - 10 , oyn + 10 );
 // g.drawString( "0" , ( int ) ( lx * kx + oxn ) - 10 , ( int) ( ly * ky+ oyn) + 10 );
  
 //Сетка
 // Вертикальные линии
  for (float  xLine = 0; xLine < 1000 ; xLine = xLine + stepX){
      int x = oxn + (int)( (xLine + 100)* kx  / 100 );
      
      g.drawLine(x , 0 - Math.abs(oyn), x , ly + oyn);
      
    //надпись по оси 0x
      g.drawString( Float.toString(xLine ).substring(0, 3) , x, ly + oyn + 15);
  }
  // горизонтальные линии
  for (int i = 0; true ; i++){
      
      
      int y =  oyn + ly -(int)(i * ky * stepY);
      g.drawLine(oxn, y, lx + Math.abs(oxn), y);
      
      //надпись по оси 0y
      g.drawString( Float.toString(i * stepY ).substring(0, 3) , (int)(oxn + kx) - 20 , y);
      
      if (oyn + ly -(int)(i * ky * stepY) < 0) break;
  }
  
//Деления
  //по оси X
  for ( int i = 0; lx + oxn > oxn + kx * i  ; i++)  {
   
      g.drawLine(  oxn + (int)(i * kx), ly + oyn - 3,
               oxn + (int)(i * kx) , ly + oyn + 3 );
  
  }
 // по оси Y
  
 for (int i = 0; ly + oyn > oyn + ky * i; i++){
     g.drawLine( oxn - 3 , oyn + ly -(int)(i * ky) , oxn + 3, oyn + ly -(int)(i * ky) );
 } 
  
 if (paintFunc){     funcGraph(g); }
  
 }

 // группа методов рисующих графики функций
 // Метод рисующий линию
 
 void funcGraph(Graphics g)
 {
       pointCord = new int[table.length][3];
       int x,y;// 
   
  for (int i = 1; i < table.length; i++){
      x = (int)(table[i][4] * kx/100 + oxn);
      y = (int)(ly + oyn - table[i][2] * ky);
      pointCord[i][0] = x;
      pointCord[i][1] = y;
      g.drawOval(x - 2 , y - 2 , 4 , 4);
      if (i == numberPointSelect){
          
          // draw selected point for green circle
             g.setColor(Color.green);
             g.fillOval(x - 3 , y - 3 , 6 , 6);
             g.setColor(Color.black);
      }
  
  }

 
 }
// найдет точку в таблице координат , если есть
 public int getNumberLine (int x, int y){
   
     int deltaX = 3;
     int deltaY = 3;
         
     int numLine = 0;
    if (paintFunc){
     for (int i = 0; i < table.length; i++){
        
         if (((x  - deltaX) < pointCord[i][0] & (x  + deltaX)> pointCord[i][0]) &
                 ((y - deltaY) < pointCord[i][1] & (y + deltaY)> pointCord[i][1])){
             numLine = i;
            break;
         }
     }
     
     }
     numberPointSelect = numLine;
     return numLine;
 }

 // группа getXXX(), setXXX() - методов
 public int getNx() {
  return nx;
 }
 public void setNx(int nx) {
  this.nx = nx;
 }
 public int getNy() {
  return ny;
 }
 public void setNy(int ny) {
  this.ny = ny;
 }
 public float getKy() {
  return ky;
 }
 public void setKy(float ky) {
  this.ky = ky;
 }
 public float getKx() {
  return kx;
 }
 public void setKx(float kx) {
        if (kx <= 0){
                 this.kx = 1;
                    }
                    else {
                             this.kx = kx;
                     }
 }
 
 public float getHx() {
  return hx;
 }
 public void setHx(float hx) {
  this.hx = hx;
 }
 public int getLx() {
  return lx;
 }
 public void setLx(int lx) {
  this.lx = lx;
 }
 public int getLy() {
  return ly;
 }
 public void setLy(int ly) {
  this.ly = ly;
 }
 public int getSw() {
  return sw;
 }
 public void setSw(int sw) {
  this.sw = sw;
 }
 public int getOyn() {
  return oyn;
 }
 public void setOyn(int oyn) {
  this.oyn = oyn;
 }
 public int getOxn() {
  return oxn;
 }
 public void setOxn(int oxn) {
  this.oxn = oxn;
 }

    /**
     * @return the table
     */
    public Float[][] getTable() {
        return table;
    }

    /**
     * @param table the table to set
     */
    public void setTable(Float[][] table) {
        this.table = table;
    }

    /**
     * @param paintFunc the paintFunc to set
     */
    public void setPaintFunc(boolean paintFunc) {
        this.paintFunc = paintFunc;
    }
}
