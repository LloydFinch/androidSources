package wyf;

import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

//地图层设计面板
public class LayerDesignPanel extends JPanel implements ActionListener
{
	MainFrame father;	
	Item[][] itemArray=new Item[40][60];
	
	LayerViewPanel lvp=new LayerViewPanel(this);
	JScrollPane jsp=new JScrollPane(lvp);
	
	
	JList jl=new JList();
	JScrollPane jspL=new JScrollPane(jl);
	JButton jbSaveLayer=new JButton("保存层");
	JButton jbLoadLayer=new JButton("加载层");
	JButton jbCreate=new JButton("生成地图文件");
	JButton jbLoadAll=new JButton("全部铺上当前选中");
	
	JTextField jtfCengMing=new JTextField("Layer");
	
	public LayerDesignPanel(MainFrame father)
	{
		this.father=father;
		
		this.setLayout(null);
		
		jsp.setBounds(10,10,800,600);
		this.add(jsp);
		
		jspL.setBounds(820,10,140,200);
		this.add(jspL);		
		jl.setCellRenderer(new MyCellRenderer());
		
		jbSaveLayer.setBounds(820,220,140,20);
		this.add(jbSaveLayer);
		jbSaveLayer.addActionListener(this);
		
		jbLoadLayer.setBounds(820,250,140,20);
		this.add(jbLoadLayer);
		jbLoadLayer.addActionListener(this);
		
		
		jbCreate.setBounds(820,280,140,20);
		this.add(jbCreate);
		jbCreate.addActionListener(this);
		
		jbLoadAll.setBounds(820,310,140,20);
		this.add(jbLoadAll);
		jbLoadAll.addActionListener(this);
		
		
		
		jtfCengMing.setBounds(820,340,140,20);
		this.add(jtfCengMing);
	}
	
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource()==jbSaveLayer)
		{
			try
			{
				FileOutputStream fout=new FileOutputStream(jtfCengMing.getText()+".wyf");
				ObjectOutputStream oout=new ObjectOutputStream(fout);
				oout.writeObject(itemArray);
				oout.close();
				fout.close();
			}
			catch(Exception ea)
			{
				ea.printStackTrace();
			}			
		}
		else if(e.getSource()==jbLoadLayer)
		{
			try
			{
				FileInputStream fin=new FileInputStream(jtfCengMing.getText()+".wyf");
				ObjectInputStream oin=new ObjectInputStream(fin);
				itemArray =(Item[][])oin.readObject();		

				oin.close();
				fin.close();
				this.flush();
			}
			catch(Exception ea)
			{
				ea.printStackTrace();
			}	
			lvp.repaint();		
		}
		else if(e.getSource()==jbLoadAll)
		{//全部铺上当前选中
				
				for(int row=0; row<40; row++)
				{
					for(int col=0; col<60; col++)
					{
						Item item=((Item)(jl.getSelectedValue())).clone();
						itemArray[row][col]=item;
						if(item!=null)
						{
							item.setPosition(col,row);
						}	
					
					}
				}
				

				lvp.repaint();
		}
		else if(e.getSource()==jbCreate)
		{//生成源代码
			
			try
			{			
				FileOutputStream fout = null;
				DataOutputStream dout = null;
				fout = new FileOutputStream("maps.so");
				dout = new DataOutputStream(fout);
				int totalBlocks=0;
				
				for(int i=0; i<40; i++)
				{
					for(int j=0; j<60; j++)
					{
						Item item = itemArray[i][j];
						if(item != null)
						{
							totalBlocks++;
						}
					}
				}
				System.out.println("totalBlocks="+totalBlocks);
				
				//写入不空块的数量
				dout.writeInt(totalBlocks);
			
				for(int i=0; i<40; i++)
				{
					for(int j=0; j<60; j++)
					{
						Item item = itemArray[i][j];
						if(item != null)
						{
							int w = item.w;//元素的图片宽度
							int h = item.h;//元素的图片高度
							int col = item.col;//元素的地图列
							int row = item.row;//元素的地图行
							int pCol = item.pCol;//元素的占位列
							int pRow = item.pRow;//元素的占位行
							String leiMing = item.leiMing;//类名
							
							int [][] notIn = item.notIn;//不可通过
							int [][] keYu = item.keYu;//可遇矩阵
							
							//计算图片下标
							int outBitmapInxex=0;
							if(leiMing.equals("Grass"))
							{
								outBitmapInxex=0;
							}
							else if(leiMing.equals("XiaoHua1"))
							{
								outBitmapInxex=1;
							}
							else if(leiMing.equals("MuZhuang"))
							{
								outBitmapInxex=2;
							}
							else if(leiMing.equals("XiaoHua2"))
							{
								outBitmapInxex=3;
							}
							else if(leiMing.equals("Road"))
							{
								outBitmapInxex=4;
							}
							else if(leiMing.equals("Jing"))
							{
								outBitmapInxex=5;
							}
							
							dout.writeByte(outBitmapInxex);//记录图片下标
							dout.writeByte(0);//记录可遇标志 0-不可遇 底层都不可遇
							dout.writeByte(w);//图片宽度
							dout.writeByte(h);//图片高度
							dout.writeByte(col);//总列数
							dout.writeByte(row);//总行数
							dout.writeByte(pCol);//占位列
							dout.writeByte(pRow);//占位行
							
							int bktgCount=notIn.length;//不可通过点的数量
							dout.writeByte(bktgCount);//写入不可通过点的数量
							
							for(int k=0; k<bktgCount; k++)
							{
								dout.writeByte(notIn[k][0]);
								dout.writeByte(notIn[k][1]);
							}							
						}
					}	
				}
	
				dout.close();
				fout.close();
			
			}
			catch(Exception ea)
			{
				ea.printStackTrace();
			}
		}
	}
	
	public void flush()
	{
		ArrayList<Item> alItem=father.idp.alItem;
		jl.setListData(alItem.toArray());
	}

	
	public static void main(String args[])
	{
		new MainFrame();
	}
}