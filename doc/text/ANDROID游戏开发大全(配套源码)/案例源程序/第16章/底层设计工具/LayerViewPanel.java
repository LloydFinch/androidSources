package wyf;

import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

//地图层绘制面板
public class LayerViewPanel extends JPanel implements MouseListener
{
	LayerDesignPanel father;
	
	public LayerViewPanel(LayerDesignPanel father)
	{
		this.father=father;
		this.setPreferredSize(new Dimension(1860,1240));
		
		this.addMouseListener(this);
	}
	
	
	public void paint(Graphics g)
	{
		//画背景
		g.setColor(Color.black);
		g.fillRect(0,0,1860,1240);
		
		int span=ConstantUtil.itemSpan;
		
		//绘制地图元素
		for(int i=0;i<40;i++)
		{
			for(int j=0;j<60;j++)
			{
				Item item=father.itemArray[i][j];
				if(item!=null)
				{
					item.draw(g,this);
				}				
			}
		}
		
		//画定位线-横线
		for(int i=0;i<40;i++)
		{
			g.setColor(Color.green);
			g.drawLine(0,i*span,1860,i*span);
		}
		
	    //画定位线-竖线线
		for(int i=0;i<60;i++)
		{
			g.setColor(Color.green);
			g.drawLine(i*span,0,i*span,1860);
		}
		
	}
	
	public void mousePressed(MouseEvent e){}
	
	public void mouseReleased(MouseEvent e){}
	
	public void mouseEntered(MouseEvent e){}
	
	public void mouseExited(MouseEvent e){}
	
	public void mouseClicked(MouseEvent e)
	{
		int mx=e.getX();
		int my=e.getY();
		int span=ConstantUtil.itemSpan;
		
		int col=(mx/span-1)+((mx%span==0)?0:1);
		int row=(my/span-1)+((my%span==0)?0:1);
		
		
		Item item=((Item)(father.jl.getSelectedValue())).clone();
		father.itemArray[row][col]=item;
		if(item!=null)
		{
			item.setPosition(col,row);
		}	
		
		this.repaint();
	}	
	
	public static void main(String args[])
	{
		new MainFrame();
	}
}