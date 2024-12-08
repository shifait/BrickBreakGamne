

package com.mycompany.game;


import java.awt.Color;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class MainClass extends JFrame {
         private Container c;
         private JButton bt1,bt2;
         private Font font;
         MainClass(){
             c=this.getContentPane();
             c.setLayout(null);
             c.setBackground(Color.GREEN);
             JLabel textLabel = new JLabel("BRICK BREAKER GAME!");
             textLabel.setBounds(110,40,300,50);
             font=textLabel.getFont();
             font=font.deriveFont(font.getSize()+13f);
             textLabel.setFont(font);
             getContentPane().setLayout(null);
             getContentPane().add(textLabel);
             
             bt1= new JButton("Start Game");
            // bt1.setBackground(Color.MAGENTA);
             bt1.setBounds(180,110,120,40);
             c.add(bt1);
             bt1.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
             bt1.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e)
                {
                    
                    JFrame f= new JFrame();// jframe creating
                    f.setTitle("Brick Breaker Game");// title
                    f.setSize(700,600);// jframe size
                    f.setLocationRelativeTo(null);// centre location
                    f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// 
                    f.setVisible(true);// visible
                    f.setResizable(false); // not resizable 
                    GamePlay gamePlay= new GamePlay();
                    f.add(gamePlay);
 
                }
                        
             });
             bt2= new JButton("Quit");
             bt2.setBounds(180,170,120,40);
             bt2.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
             c.add(bt2);
              bt2.addActionListener(new ActionListener(){
             public void actionPerformed(ActionEvent e)
             {
                //setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                int choice = JOptionPane.showConfirmDialog(null, "Are you sure you want to exit the game?", "Exit Game", JOptionPane.YES_NO_OPTION);
                if (choice == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
             }
             
             
             });
         }
    
    public static void main(String[] args) {
         MainClass frame=new MainClass();
         frame.setVisible(true);
         frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         frame.setBounds(300,100,500,400);
         frame.setTitle("Brick Breaker Game");
         
        
    }
 
}
