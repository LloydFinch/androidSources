package wyf;

import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.event.*;

public class MainFrame extends JFrame
{
	
	JTabbedPane jtb=new JTabbedPane();
	
	ItemDesignPanel idp=new ItemDesignPanel(this);
	
	LayerDesignPanel ldp=new LayerDesignPanel(this);
	
	public MainFrame()
	{
		this.setTitle("游戏设计系统-上层可遇层设计");
		
		this.add(jtb);
		
		jtb.add("Item设计",idp);
		
		jtb.add("Layer设计",ldp);
		
		
		jtb.addChangeListener(
		   new ChangeListener()
		   {
		   	  public void stateChanged(ChangeEvent e)
		   	  {
		   	  	ldp.flush();
		   	  }
		   }
		);
		
		this.setBounds(30,30,1000,800);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public static void main(String args[])
	{
		new MainFrame();
	}
}