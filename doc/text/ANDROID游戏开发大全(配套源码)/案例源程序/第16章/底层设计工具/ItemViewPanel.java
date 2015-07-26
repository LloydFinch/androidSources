package wyf;

import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

//地图元素设计面板
public class ItemViewPanel extends JPanel implements MouseListener
{
	ItemDesignPanel father;
	Image iZw=(new ImageIcon("img/zw.png")).getImage();
	Image iBktg=(new ImageIcon("img/bktg.png")).getImage();
	Image iKy=(new ImageIcon("img/keyu.png")).getImage();
	
	public ItemViewPanel(ItemDesignPanel father)
	{
		this.father=father;
		this.addMouseListener(this);
	}
	
	public void paint(Graphics g)
	{
		//画背景
		g.setColor(Color.black);
		g.fillRect(0,0,200,200);
		
		//画元素图
		if(father.tempImage!=null) 
		{
			g.drawImage
			(
				father.tempImage,
				0,
				199-father.tempImage.getHeight(this),
				null,
				this
			);			
		}
		
		//画定位线
		g.setColor(Color.green);
		int span=ConstantUtil.itemSpan;
		
		//画横线
		for(int i=0;i<8;i++)
		{
			g.drawLine(0,199-31*i,200,199-31*i);
		}
		
		//画竖线
		for(int i=0;i<8;i++)
		{
			g.drawLine(31*i,0,31*i,200);
		}
		
		//画占位
		g.drawImage(iZw,father.pCol*span+5,199-(father.pRow*span+span-5),this);
		
		//画不可通过
		HashMap<String,int[]> bktgMap=father.bktgMap;
		for(int[] ia:bktgMap.values())
		{
			int col=ia[0];
			int row=ia[1];
			g.drawImage(iBktg,col*span+5,199-(row*span+span-5),this);
		}
		
		//画可遇
		HashMap<String,int[]> kyMap=father.kyMap;
		for(int[] ia:kyMap.values())
		{
			int col=ia[0];
			int row=ia[1];
			g.drawImage(iKy,col*span+5,199-(row*span+span-5),this);
		}
	}
	
	public void mousePressed(MouseEvent e){}
	
	public void mouseReleased(MouseEvent e){}
	
	public void mouseEntered(MouseEvent e){}
	
	public void mouseExited(MouseEvent e){}
	
	public void mouseClicked(MouseEvent e)
	{
		if(father.status==3)
		{//设置占位点
			int mx=e.getX();
			int my=199-e.getY();
			
			int span=ConstantUtil.itemSpan;
			
			father.pCol=(mx/span-1)+((mx%span==0)?0:1);
			father.pRow=(my/span-1)+((my%span==0)?0:1);
		}
		else if(father.status==4)
		{//设置不可通过行列列表
			int mx=e.getX();
			int my=199-e.getY();
			
			int span=ConstantUtil.itemSpan;
			
			int col=(mx/span-1)+((mx%span==0)?0:1);
			int row=(my/span-1)+((my%span==0)?0:1);	
			
			HashMap<String,int[]> bktgMap=father.bktgMap;
			String key=col+":"+row;
			if(bktgMap.containsKey(key))
			{
				bktgMap.remove(key);
			}
			else
			{
				bktgMap.put(key,new int[]{col,row});
			}	
		}
		
		this.repaint();
	}
	
	public static void main(String args[])
	{
		new MainFrame();
	}	
}