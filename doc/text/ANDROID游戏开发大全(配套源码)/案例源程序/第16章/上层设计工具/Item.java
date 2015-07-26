package wyf;

import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;

public class Item implements Externalizable
{
	static final long serialVersionUID = 3243660019996329628L;
	
	Image img;//元素的图片
	String imgPath;
	int w;//元素的图片宽度
	int h;//元素的图片高度
	
	int col;//元素的地图列
	int row;//元素的地图行
	
	int pCol;//元素的占位列
	int pRow;//元素的占位行
	
	int[][] notIn;//元素的不可通过点行列列表
	int[][] keYu;//元素的可遇点行列列表
	
	
	String leiMing;//类名
	
	public Item()
	{
	}
	
	public Item(Image img,String imgPath,int w,int h,int pCol,int pRow,int[][] notIn,int [][] keYu,String leiMing)
	{
		this.img=img;
		this.imgPath=imgPath;
		this.w=w;
		this.h=h;
		this.pCol=pCol;
		this.pRow=pRow;
		this.notIn=notIn;
		this.keYu = keYu;
		this.leiMing = leiMing;
	}
	
	public void setPosition(int col,int row)
	{
		this.col=col;
		this.row=row;
	}
	
	public void draw(Graphics g,ImageObserver io)
	{		
		int span=ConstantUtil.itemSpan;
		
		int x=(col-pCol)*span;
		int y=row*span+(pRow+1)*span-h;
		
		g.drawImage(img,x,y,io);
	}
	
	
	public Item clone()
	{
		return new Item(img,imgPath,w,h,pCol,pRow,notIn,keYu,leiMing);
	}
	
	public void writeExternal(ObjectOutput out) throws IOException
	{
		out.writeObject(imgPath);
		out.writeInt(w);
		out.writeInt(h);
		out.writeInt(pCol);
		out.writeInt(pRow);
		out.writeObject(notIn);
		out.writeInt(col);
		out.writeInt(row);
		out.writeObject(keYu);
		out.writeUTF(leiMing);
		
	}
	
	public void readExternal(ObjectInput in)throws IOException,ClassNotFoundException
	{
		imgPath=(String)(in.readObject());
		img=new ImageIcon(imgPath).getImage();
		
		w=in.readInt();
		h=in.readInt();
		pCol=in.readInt();
		pRow=in.readInt();
		notIn=(int[][])(in.readObject());
		
		col=in.readInt();
		row=in.readInt();
		
		keYu=(int[][])(in.readObject());
		leiMing=in.readUTF();
	}
	
	
	public static void main(String args[])
	{
		new MainFrame();
	}
}