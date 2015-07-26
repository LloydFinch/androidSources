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
	
	Image img;//Ԫ�ص�ͼƬ
	String imgPath;
	int w;//Ԫ�ص�ͼƬ���
	int h;//Ԫ�ص�ͼƬ�߶�
	
	int col;//Ԫ�صĵ�ͼ��
	int row;//Ԫ�صĵ�ͼ��
	
	int pCol;//Ԫ�ص�ռλ��
	int pRow;//Ԫ�ص�ռλ��
	
	int[][] notIn;//Ԫ�صĲ���ͨ���������б�
	int[][] keYu;//Ԫ�صĿ����������б�
	
	
	String leiMing;//����
	
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