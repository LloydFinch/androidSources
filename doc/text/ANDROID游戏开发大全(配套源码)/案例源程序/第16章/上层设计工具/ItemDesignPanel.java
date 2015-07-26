package wyf;

import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.event.*;

//地图元素设计面板

public class ItemDesignPanel extends JPanel implements ActionListener
{
	MainFrame father;	
	ItemViewPanel ivp=new ItemViewPanel(this);
	JFileChooser jfc=new JFileChooser(".\\res");	
	Image tempImage;
	String imagePath;
	int pCol;//元素的占位列
	int pRow;//元素的占位行
	
	HashMap<String,int[]> bktgMap=new HashMap<String,int[]>();//不可通过点行列列表
	HashMap<String,int[]> kyMap=new HashMap<String,int[]>();//可遇点行列列表
	
	JButton jbIn=new JButton("导入图片");
	JButton jbSave=new JButton("保存元素");
	JButton jbSetZW=new JButton("设置占位行列");
	JButton jbSetBKTG=new JButton("设置不可通过");
	JButton jbSaveList=new JButton("保存元素列表");
	JButton jbLoadList=new JButton("加载元素列表");
	JButton jbDelete=new JButton("删除元素");
	JButton jbSetKY=new JButton("设置可遇行列");
	
	JTextField jlLeiMing = new JTextField("森林");
	
	JList jl=new JList();
	JScrollPane jspL=new JScrollPane(jl);
	
	int status=0;//工作状态 0：什么都不干 1：导入图片  2：保存元素  3：设置占位行列 4:设置不可通过行列列表 5：保存元素列表  6:加载元素列表 7：删除元素 8:设置可遇
	
	ArrayList<Item> alItem=new ArrayList<Item>();
	
	public ItemDesignPanel(MainFrame father)
	{
		this.father=father;
		
		this.setLayout(null);
		ivp.setBounds(10,10,200,200);
		this.add(ivp);
		
		jbIn.setBounds(220,10,120,20);
		this.add(jbIn);
		jbIn.addActionListener(this);
		
		jbSave.setBounds(220,40,120,20);
		this.add(jbSave);
		jbSave.addActionListener(this);
		
		jbSetZW.setBounds(220,70,120,20);
		this.add(jbSetZW);
		jbSetZW.addActionListener(this);
		
		jbSetBKTG.setBounds(220,100,120,20);
		this.add(jbSetBKTG);
		jbSetBKTG.addActionListener(this);
		
		jbSaveList.setBounds(220,130,120,20);
		this.add(jbSaveList);
		jbSaveList.addActionListener(this);
		
		jbLoadList.setBounds(220,160,120,20);
		this.add(jbLoadList);
		jbLoadList.addActionListener(this);
		
		jbDelete.setBounds(220,190,120,20);
		this.add(jbDelete);
		jbDelete.addActionListener(this);
		
		jbSetKY.setBounds(220,220,120,20);
		this.add(jbSetKY);
		jbSetKY.addActionListener(this);
		
		jlLeiMing.setBounds(10,220,100,20);
		this.add(jlLeiMing);
		
		jspL.setBounds(350,10,140,200);
		this.add(jspL);		
		jl.setCellRenderer(new MyCellRenderer());
		jl.addListSelectionListener(
		   new ListSelectionListener()
		   {
		   	  public void valueChanged(ListSelectionEvent e)
		   	  {
		   	  	  Item item=(Item)jl.getSelectedValue();
		   	  	  if(item==null){return;}
		   	  	  tempImage=item.img;
				  imagePath=item.imgPath;
				  pCol=item.pCol;//元素的占位列
				  pRow=item.pRow;//元素的占位行
				  jlLeiMing.setText(item.leiMing);
					
				  bktgMap=new HashMap<String,int[]>();//不可通过点行列列表
				  for(int[] ia:item.notIn)
				  {
				  	String key=ia[0]+":"+ia[1];
				  	bktgMap.put(key,ia);
				  }
				  
				  kyMap=new HashMap<String,int[]>();//可遇点行列列表
				  for(int[] ia:item.keYu)
				  {
				  	String key=ia[0]+":"+ia[1];
				  	kyMap.put(key,ia);
				  }
				  ivp.repaint();		   	  	  
		   	  }
		   }
		);
			
	}
	
	public void flush()
	{
		jl.setListData(alItem.toArray());
	}
	
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource()==jbIn)
		{//"导入图片"
		   status=1;
		   int jg=jfc.showOpenDialog(this);
		   if(jg==JFileChooser.APPROVE_OPTION)
		   {
		   	  tempImage=(new ImageIcon(jfc.getSelectedFile().getAbsolutePath())).getImage();
		   	  imagePath="res\\"+jfc.getSelectedFile().getName();
		   	  ivp.repaint();
		   }
		   bktgMap=new HashMap<String,int[]>();
		   kyMap=new HashMap<String,int[]>();
		}
		else if(e.getSource()==jbSetZW)
		{//设置占位行列
			status=3;
		}
		else if(e.getSource()==jbSetBKTG)
		{//设置不可通过行列列表
			status=4;
		}
		else if(e.getSource()==jbSave)
		{//保存元素
			status=2;
			if(tempImage!=null)
			{
				Item item=new Item
				(
					tempImage,imagePath,
					tempImage.getWidth(this),
					tempImage.getHeight(this),
					pCol,
					pRow,
					bktgMap.values().toArray(new int[0][]),
					kyMap.values().toArray(new int[0][]),
					jlLeiMing.getText()
				);
				alItem.add(item);
				this.flush();	
			}
		}
		else if(e.getSource()==jbSaveList)
		{//保存元素列表
			status=5;
			try
			{
				FileOutputStream fout=new FileOutputStream("ItemList.wyf");
				ObjectOutputStream oout=new ObjectOutputStream(fout);
				oout.writeObject(alItem);
				oout.close();
				fout.close();
			}
			catch(Exception ea)
			{
				ea.printStackTrace();
			}
		}
		else if(e.getSource()==jbLoadList)
		{//加载元素列表
			status=6;
			try
			{
				FileInputStream fin=new FileInputStream("ItemList.wyf");
				ObjectInputStream oin=new ObjectInputStream(fin);
				alItem =(ArrayList<Item>)oin.readObject();
				oin.close();
				fin.close();
				this.flush();
			}
			catch(Exception ea)
			{
				ea.printStackTrace();
			}
		}
		else if(e.getSource()==jbDelete)
		{//删除元素
			status=7;

			if(jl.getSelectedIndex() != -1){
				alItem.remove(jl.getSelectedIndex());
				tempImage = null;
				imagePath = null;
				bktgMap.clear();//不可通过点行列列表
				kyMap.clear();
				this.flush();
				ivp.repaint();	
			}
		}
		else if(e.getSource()==jbSetKY)
		{//设置可遇矩阵
			status = 8;
		}
	}
	
	public static void main(String args[])
	{
		new MainFrame();
	}
}