
package com.mycompany.game;

import java.awt.Color;
import static java.awt.Color.green;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import static javax.management.Query.value;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

public class GamePlay extends JPanel implements ActionListener, KeyListener{
    private int score=0;
    private int level=1;
    private int life =2;
    private boolean play=false;
    private int totalBrick=0;
    private Timer timer;
    private int delay = 8;
    private int ballposX=120;
    private int ballposY=350;
    private int ballXdir=-4;
    private int ballYdir=-4;
    private int playerX=350;
    private MapGenerator map;
    private int highScore=0;
    
    public GamePlay(){
       addKeyListener(this);
       setFocusable(true);
       setFocusTraversalKeysEnabled(true);
       timer = new Timer(delay,this);
       timer.start();
//       Random value=new Random();
//       int  row,col;
//       row= value.nextInt(8);
//       Random value1=new Random();
//       col=value1.nextInt(8);
       map= new MapGenerator(4,7);//(4 x 7) matrix
       totalBrick=map.map.length*map.map[0].length;
       highScore=getHighScore();
    }
    public void paint(Graphics g ){
      // black vanvas
      g.setColor(Color.black);
      g.fillRect(1,1 , 692, 592);
      
      //border
      g.setColor(Color.yellow);
      g.fillRect(0 ,0, 692, 3);// top border
      g.fillRect(0 ,3, 3, 592);//left border
      g.fillRect(684 ,3, 3, 592);// right border 684
      
      //Paddle 
      g.setColor(green);
      g.fillRect(playerX,550,150,10);
      Rectangle paddleRect = new Rectangle(playerX,550,150,10);
      //g.fillRect(playerX,550,100,8);
      //bricks draw
      //map.draw(Graphics2D g);
      map.draw((Graphics2D) g);
      
      //ball
      g.setColor(Color.red);
      g.fillOval(ballposX,ballposY,20, 20);
      
      //High Score
       g.setColor(Color.red);
      g.setFont(new Font("serif",Font.BOLD,20));
      g.drawString("High Score: "+highScore, 10, 25); 
      // Life 
       g.setColor(Color.red);
       g.setFont(new Font("serif",Font.BOLD,20));
       g.drawString("Life : "+life, 250, 30);
         
      // Level 
      g.setColor(Color.red);
       g.setFont(new Font("serif",Font.BOLD,20));
       g.drawString("Level : "+level, 400, 30);
       
      //score 
      g.setColor(Color.red); 
      g.setFont(new Font("serif",Font.BOLD,20));
      g.drawString("Score :"+score, 600, 30);
      
      //Game Over 
      if(ballposY>=570){
          life--;
          if(life<=0)//////////
          {   
              life=0;
              play=false;
              ballXdir=0;
              ballYdir=0;
              g.setColor(green);
              g.setFont(new Font("serif",Font.BOLD,30));
               g.drawString("Game Over!!!",250,260); 
              g.drawString("HighScore: "+highScore, 250, 300);
              g.drawString("You loss",270,340);
              g.drawString("Your Score : "+score,250,380);
              updateHighScore(score);
          }
          else
          {
              ballposX=120;
              ballposY=350;
              ballXdir=-4;
              ballYdir=-4;
          }
          
         
      }
      if(totalBrick<=0){
          level++;
          if(level>=3){
           play=false;
           level=2;
           ballXdir=0;
           ballYdir=0;
           g.setColor(green);
           g.setFont(new Font("serif",Font.BOLD,30));
           
           g.drawString("Game Over!!! You Win",250,300);
           g.drawString("Your Score : "+score,250,340);
            updateHighScore(score);
          }
          else{
               map= new MapGenerator(6,7);

           totalBrick=map.map.length*map.map[0].length;
           int ballposX=120;
           int ballposY=350;
           int ballXdir=-4;
           int ballYdir=-4;
           int playerX=350;
          }
          
      }
    }
    private void moveLeft(){
        play=true;
        playerX-=20;
    }
    private void moveRight(){
        play=true;
        playerX+=20;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if(play){
            if(ballposX<=0){
                ballXdir=-ballXdir;
            }
           
           if(ballposX>=670){
                ballXdir=-ballXdir;
            }
            if(ballposY<=0){
                ballYdir=-ballYdir;
            }
            Rectangle ballRect= new Rectangle(ballposX,ballposY,20,20);
            Rectangle paddleRect = new Rectangle(playerX,550,150,10);
           
            if(ballRect.intersects(paddleRect)){
                
                ballYdir=-ballYdir;
            }
           A:for(int i=0;i<map.map.length;i++)
            {
                for(int j=0;j<map.map[0].length;j++){
                    if(map.map[i][j]>0){
                        int width=map.brickWidth;
                        int height= map.brickHeight;
                        int brickXpos=80+j*width;
                        int brickYpos=50+i*height;
                       Rectangle brickRect= new Rectangle(brickXpos,brickYpos,width,height);
                        // Rectangle brickRact= new Rectangle(playerX,550,100,8);
                        if(ballRect.intersects(brickRect))
                        {
                            
                            map.setBrick(0, i, j);
                            totalBrick--;
                            score++;
                            if(ballposX+19<=brickXpos||ballposX+1>=brickXpos+width){// left or right bricks touch go oposite direction
                                ballXdir=-ballXdir;
                            }
                            else{
                                ballYdir=-ballYdir;
                            }
                            break A;  
                               
                        }
                    }
                }
            }
            ballposX+=ballXdir;
            ballposY+=ballYdir;
        }
        repaint();
       // throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void keyTyped(KeyEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode()==KeyEvent.VK_LEFT){//  Left <- button change 
            if(playerX<=0)
                playerX=0;
            else
                moveLeft();
        }
        if(e.getKeyCode()==KeyEvent.VK_RIGHT){//  Right -> button change
            if(playerX>=550)// initial sitution 600
                playerX=550; // 600 
            else
                 moveRight();
        }   
        repaint();
        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }


    @Override
    public void keyReleased(KeyEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
        public int getHighScore() {
        File highScoreFile = new File("high_score.txt");
        if (highScoreFile.exists()) {
            try {
                FileReader fileReader = new FileReader(highScoreFile);
                BufferedReader bufferedReader = new BufferedReader(fileReader);
                highScore = Integer.parseInt(bufferedReader.readLine());
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            highScore = 0;
        }
        return highScore;
    }

    // Method to update the high score and save it to a file
    public void updateHighScore(int score) {
        if (score > highScore) {
            highScore = score;
            try {
                FileWriter fileWriter = new FileWriter("high_score.txt");
                fileWriter.write(String.valueOf(highScore));
                fileWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
