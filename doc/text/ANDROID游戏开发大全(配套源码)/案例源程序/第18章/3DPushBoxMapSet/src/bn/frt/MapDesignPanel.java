package bn.frt;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JPanel;

public class MapDesignPanel extends JPanel implements MouseListener,MouseMotionListener{
	int row;
	int col;
	int span=32;
	MapDesigner md;
	
	int[][] mapDate;
	int [][] diamondMap;
	
	public MapDesignPanel(int row,int col,MapDesigner md)
	{
		this.row=row;
		this.col=col;
		this.md=md;
		
		this.setPreferredSize(new Dimension(span*col,span*row));
		mapDate=new int[row][col];
		for(int i=0;i<row;i++)
		{
			for(int j=0;j<col;j++)
			{
				mapDate[i][j]=0;
			}
		}
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
	}
	public void paint(Graphics g)
	{
		g.setColor(Color.BLACK);
		g.fillRect(0,0,span*col,span*row);
		
		for(int i=0;i<mapDate.length;i++)
		{
			for(int j=0;j<mapDate[0].length;j++)
			{
				if(mapDate[i][j]==0)
				{//绘制白色格子
					g.setColor(Color.black);
					g.fillRect(j*span,i*span,span,span);
				}else if(mapDate[i][j]==1)
				{
					g.setColor(Color.white);
					g.fillRect(j*span,i*span,span,span);
				}else if(mapDate[i][j]==2)
				{
					g.setColor(Color.blue);
					g.fillRect(j*span,i*span,span,span);
				}else if(mapDate[i][j]==3)
				{
					g.setColor(Color.green);
					g.fillRect(j*span,i*span,span,span);
				}else if(mapDate[i][j]==4)
				{
					g.setColor(Color.yellow);
					g.fillRect(j*span,i*span,span,span);
				}else if(mapDate[i][j]==5)
				{
					g.setColor(Color.red);
					g.fillRect(j*span,i*span,span,span);
				}
			}
		}
		g.setColor(Color.green);		
		for(int i=0;i<row+1;i++)
		{
			g.drawLine(0,span*i,span*col,span*i);
		}
		
		for(int j=0;j<col+1;j++)
		{
			g.drawLine(span*j,0,span*j,span*row);
		}
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		mouseClicked(arg0);
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		
		if(md.jrWu.isSelected())
		{
			int x=e.getX();
			int y=e.getY();
			
			int rowC=y/span;
			int colC=x/span;
			
			if(rowC>=row||colC>=col)
			{
				return;
			}
			
			mapDate[rowC][colC]=0;
		}
		else if(md.jrZhuanQiang.isSelected())
		{
			int x=e.getX();
			int y=e.getY();
			
			int rowC=y/span;
			int colC=x/span;
			
			if(rowC>=row||colC>=col)
			{
				return;
			}
			
			mapDate[rowC][colC]=1;
		}else if(md.jrDiZhuan.isSelected())
		{
			int x=e.getX();
			int y=e.getY();
			
			int rowC=y/span;
			int colC=x/span;
			
			if(rowC>=row||colC>=col)
			{
				return;
			}
			
			mapDate[rowC][colC]=2;
		}else if(md.jrMuBiao.isSelected())
		{
			int x=e.getX();
			int y=e.getY();
			
			int rowC=y/span;
			int colC=x/span;
			
			if(rowC>=row||colC>=col)
			{
				return;
			}
			
			mapDate[rowC][colC]=3;
		}else if(md.jrXiangZi.isSelected())
		{
			int x=e.getX();
			int y=e.getY();
			
			int rowC=y/span;
			int colC=x/span;
			
			if(rowC>=row||colC>=col)
			{
				return;
			}
			
			mapDate[rowC][colC]=4;
		}else if(md.jrRen.isSelected())
		{
			int x=e.getX();
			int y=e.getY();
			
			int rowC=y/span;
			int colC=x/span;
			
			if(rowC>=row||colC>=col)
			{
				return;
			}
			
			mapDate[rowC][colC]=5;
		}
		this.repaint();
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		
	}
	
}
