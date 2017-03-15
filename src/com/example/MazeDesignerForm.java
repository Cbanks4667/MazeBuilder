/** Maze Designer
 *Created December 2nd 2016
 * 
 * @author Chris Banks
 */

package com.example;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import static java.lang.Integer.parseInt;
import java.lang.reflect.Field;
import java.util.HashSet;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**Maze Designer Form class
 * Creates and Edits Mazes
 * @author cbanks4667
 */
public class MazeDesignerForm extends JFrame
{
    private JButton b1, b2, b3, b4, b5, b6, b7, b8, b9,b10,
            btnSave, btnLoad, btnGenerate;
    private JTextField txtRows, txtColumns;
    private JLabel lblRows, lblColumns;
    private JPanel westPanel, northPanel, centerPanel;
    private Color color;
    private String btnText;
    private Color empty;
    private int Rows = 20;
    private int Columns = 20;
    private JFrame myFrame;
    WestButtonHandler westHandler = new WestButtonHandler();
    /**Maze Designer Constructor
     * loads the form
     */
    public MazeDesignerForm ()
    {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        b1 = new JButton("Empty Slot");
        b1.setAlignmentX(Component.RIGHT_ALIGNMENT);
        b1.addActionListener(westHandler);
        b2 = new JButton("Red Box");
        b2.setAlignmentX(Component.RIGHT_ALIGNMENT);
        b2.addActionListener(westHandler);
        b3 = new JButton("Blue Box");
        b3.setAlignmentX(Component.RIGHT_ALIGNMENT);
        b3.addActionListener(westHandler);
        b4 = new JButton("Green Box");
        b4.setAlignmentX(Component.RIGHT_ALIGNMENT);
        b4.addActionListener(westHandler);
        b5 = new JButton("Yellow Box");
        b5.setAlignmentX(Component.RIGHT_ALIGNMENT);
        b5.addActionListener(westHandler);
        b6 = new JButton("Pink Door");
        b6.setAlignmentX(Component.RIGHT_ALIGNMENT);
        b6.addActionListener(westHandler);
        b7 = new JButton("Cyan Door");
        b7.setAlignmentX(Component.RIGHT_ALIGNMENT);
        b7.addActionListener(westHandler);
        b8 = new JButton("Magenta Door");
        b8.setAlignmentX(Component.RIGHT_ALIGNMENT);
        b8.addActionListener(westHandler);
        b9 = new JButton("White Door");
        b9.setAlignmentX(Component.RIGHT_ALIGNMENT);
        b9.addActionListener(westHandler);
        b10 = new JButton("Wall");
        b10.setAlignmentX(Component.RIGHT_ALIGNMENT);
        b10.addActionListener(westHandler);
        
        btnSave = new JButton("Save");
        btnLoad = new JButton("Load");
        btnGenerate = new JButton("Generate");
        NorthButtonHandler bhandler = new NorthButtonHandler();
        btnGenerate.addActionListener(bhandler);
        btnSave.addActionListener(bhandler);
        btnLoad.addActionListener(bhandler);
        lblRows = new JLabel();
        lblColumns = new JLabel();
        txtRows = new JTextField("", 4);
        txtColumns = new JTextField("", 4);
       
        lblRows.setText("Rows");
        lblColumns.setText("Columns");
        
        txtRows.setText("5");
        txtColumns.setText("5");
        
        westPanel = new JPanel();
        westPanel.setLayout(new GridLayout(10,1));
        westPanel.add(b1);
        westPanel.add(b2);
        westPanel.add(b3);
        westPanel.add(b4);
        westPanel.add(b5);
        westPanel.add(b6);
        westPanel.add(b7);
        westPanel.add(b8);
        westPanel.add(b9);
        westPanel.add(b10);
        
        northPanel = new JPanel();
        northPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        northPanel.add(lblRows);
        northPanel.add(txtRows);
        northPanel.add(lblColumns);
        northPanel.add(txtColumns);
        northPanel.add(btnGenerate);
        northPanel.add(btnSave);
        northPanel.add(btnLoad);
        
        centerPanel = new JPanel();       
        this.setLayout(new BorderLayout());
        this.add(northPanel,BorderLayout.NORTH);
        //this.add(centerPanel, BorderLayout.CENTER);
        this.add(westPanel,BorderLayout.WEST);
       
        empty = b1.getBackground();
        color = empty;
        //this.pack();
        this.setSize(500,500);
        this.setVisible(true);
        myFrame = this;
    }
    /**Class that Allows user to change colors
     */
    private class WestButtonHandler implements ActionListener
    {
        /**
         * The Click event for the toolbox buttons in the west Panel
         * @param ae The action event performed
         */
        @Override
        public void actionPerformed(ActionEvent ae)
        {
            
           JButton btn = (JButton)ae.getSource();
            if (btn == b1)
            {
                color = empty;
            }
            else if(btn == b10)
            {
                color = Color.BLACK;
            }
            else
            {
             btnText =  btn.getText();
             btnText = btnText.substring(0,btnText.indexOf(' '));
             try {
                    Field field = Color.class.getField(btnText.toLowerCase());
                    color = (Color)field.get(null);
                } catch (Exception e) {
                    color = null; // Not defined
                }
            }        
            
             Component[] buttons = westPanel.getComponents();
            for (int i = 0; i < buttons.length; i++)
            {
                buttons[i].setBackground(empty);
            }
            btn.setBackground(color);
        }
        
    }
    /**Class  with ActionListener for the North Buttons
     * Describes the functionality of the north buttons
     * Determines actions for buttons
     */
    private class NorthButtonHandler implements ActionListener
    {
        /**The Click event for the buttons in the North Panel
         * Allows user to:
         * select rows/columns 
         * Generate a new Maze
         * Save current Maze
         * Load an existing Maze
         * @param ae The action event performed
         */
        @Override
        public void actionPerformed(ActionEvent ae)
        {
           
           JButton btn = (JButton)ae.getSource();
            if (btn == btnGenerate)
            {
                try {
              
                centerPanel.removeAll();
                CenterButtonHandler buttonHandler = new CenterButtonHandler();    
                Rows = parseInt(txtRows.getText());
                Columns = parseInt(txtColumns.getText());
                
                
                centerPanel.setLayout(new GridLayout(Rows,Columns));
                
                for (int r = 0; r < Rows; r++)
                {
                    for (int c = 0; c < Columns; c++)
                    {
                       JButton centerButton = new JButton();
                       centerButton.addActionListener(buttonHandler);
                       centerPanel.add(centerButton);
                      
                       
                    }
                }
                myFrame.add(centerPanel,BorderLayout.CENTER);
                myFrame.revalidate();
                myFrame.repaint();
                }
                catch(Exception ex)
                {
                    JOptionPane.showMessageDialog(null, 
                            "Error in Rows and Columns");
                }
               
            }
            else if(btn == btnSave)
            {
               try
               {
                   int result = doSave();
                   if (result == 1)
                   {
                     JOptionPane.showMessageDialog(null,
                             "File Saved Successful");
                   }
                   
               } catch (Exception ex)
               {
                   JOptionPane.showMessageDialog(null,"Error in file Save");
               }
            }
            else
            {
               try
               {
                  int result = doLoad();
                   if (result == 1)
                   {
                       JOptionPane.showMessageDialog(null, "Load Successful");
                   }
               } catch (Exception ex)
               {
                  JOptionPane.showMessageDialog(null, "Error on Load");
               }
            }
           
        }
        
    }
    /**
     * Class for handling the center tile Buttons
     */
    private class CenterButtonHandler implements ActionListener
    {
        /**Changes the color of the current button
         * 
         * @param ae the action event
         */
        @Override
        public void actionPerformed(ActionEvent ae)
        {
            
           JButton btn = (JButton)ae.getSource();
           btn.setBackground(color);
        }
        
    }
    /**Saves The Current Maze
     * 
     * @return An integer representing success/fail
     * @throws Exception on fail
     */
    private int doSave() throws Exception
    {
        Component[] buttons = centerPanel.getComponents();
        JFileChooser dlg = new JFileChooser();
        int a = dlg.showSaveDialog(this);
        if (a == JFileChooser.APPROVE_OPTION)
        {
           FileWriter writer = new FileWriter(dlg.getSelectedFile());
           writer.write(Rows+"\n");
           System.out.println(Rows+"\n");
           writer.write(Columns +"\n");
            System.out.println(Columns + "\n");
            for (int i = 0; i < buttons.length; i++)
            {
                
                color = buttons[i].getBackground();
                String clr = "Empty";
               if(color == Color.BLACK)
               {
                   clr = "black";
               }
               else if (color == Color.GREEN)
                {
                    clr = "green";
                }
               else if (color == Color.WHITE)
                {
                    clr = "white";
                }
               else if (color == Color.MAGENTA)
                {
                    clr = "magenta";
                }
               else if (color == Color.RED)
                {
                    clr = "red";
                }
               else if (color == Color.BLUE)
                {
                    clr = "blue";
                }
               else if (color == Color.YELLOW)
                {
                    clr = "yellow";
                }
               else if (color == Color.CYAN)
                {
                    clr = "cyan";
                }
               else if (color == Color.PINK)
                {
                    clr = "pink";
                }
               writer.write(clr);
               System.out.println(clr);
               writer.write("\n");
            }
            writer.close();
            return 1;
        }
        return 0;
    }
    /**Loads an existing Maze
     * 
     * @return an integer representing success/fail
     * @throws Exception on fail
     */
    private int doLoad() throws Exception
    {
        centerPanel.removeAll();
        int counter = 0;
        CenterButtonHandler buttonHandler = new CenterButtonHandler();
        JFileChooser dlg = new JFileChooser();
        int a = dlg.showOpenDialog(this);
        if (a == JFileChooser.APPROVE_OPTION)
        {
            FileReader fReader = new FileReader(dlg.getSelectedFile());
            BufferedReader bReader = new BufferedReader(fReader);
            String line = null;
            while((line = bReader.readLine()) != null)
            {
                counter++;
                if (counter == 1)
                {
                    Rows = parseInt(line);
                }
                else if (counter == 2)
                {
                    Columns = parseInt(line);
                }
                else
                {
                    
                    
                    centerPanel.setLayout(new GridLayout(Rows,Columns));

                           JButton centerButton = new JButton();
                           centerButton.addActionListener(buttonHandler);
                           try {
                                    Field field = Color.class
                                            .getField(line.toLowerCase());
                                    color = (Color)field.get(null);
                                } catch (Exception e) 
                                {
                                    color = empty; // Not defined
                                }
                                    
                           centerButton.setBackground(color);
                           centerPanel.add(centerButton);


                        
                   
                }
            }
             bReader.close();
             fReader.close();
            myFrame.add(centerPanel,BorderLayout.CENTER);
            myFrame.revalidate();
            myFrame.repaint();
            return 1;
        }
        return 0;
    }
}
