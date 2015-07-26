package wyf;

import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

class MyCellRenderer extends JLabel implements ListCellRenderer 
{
     public MyCellRenderer() 
     {
         setOpaque(true);
     }

     public Component getListCellRendererComponent(JList list,
                                                   Object value,
                                                   int index,
                                                   boolean isSelected,
                                                   boolean cellHasFocus) 
     {

         Item item=(Item)value;
         this.setIcon(new ImageIcon(item.img));
         this.setText("¿í¶È: "+item.w+"¸ß¶È: "+item.h);
         if(isSelected)
         {
         	setBackground(Color.red);
         }
         else
         {
         	setBackground(Color.yellow);
         }
         this.setPreferredSize(new Dimension(180,66));
         
         return this;
     }
 }
