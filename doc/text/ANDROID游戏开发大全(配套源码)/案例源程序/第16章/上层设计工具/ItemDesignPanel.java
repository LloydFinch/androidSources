package wyf;

import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.event.*;

//��ͼԪ��������

public class ItemDesignPanel extends JPanel implements ActionListener
{
	MainFrame father;	
	ItemViewPanel ivp=new ItemViewPanel(this);
	JFileChooser jfc=new JFileChooser(".\\res");	
	Image tempImage;
	String imagePath;
	int pCol;//Ԫ�ص�ռλ��
	int pRow;//Ԫ�ص�ռλ��
	
	HashMap<String,int[]> bktgMap=new HashMap<String,int[]>();//����ͨ���������б�
	HashMap<String,int[]> kyMap=new HashMap<String,int[]>();//�����������б�
	
	JButton jbIn=new JButton("����ͼƬ");
	JButton jbSave=new JButton("����Ԫ��");
	JButton jbSetZW=new JButton("����ռλ����");
	JButton jbSetBKTG=new JButton("���ò���ͨ��");
	JButton jbSaveList=new JButton("����Ԫ���б�");
	JButton jbLoadList=new JButton("����Ԫ���б�");
	JButton jbDelete=new JButton("ɾ��Ԫ��");
	JButton jbSetKY=new JButton("���ÿ�������");
	
	JTextField jlLeiMing = new JTextField("ɭ��");
	
	JList jl=new JList();
	JScrollPane jspL=new JScrollPane(jl);
	
	int status=0;//����״̬ 0��ʲô������ 1������ͼƬ  2������Ԫ��  3������ռλ���� 4:���ò���ͨ�������б� 5������Ԫ���б�  6:����Ԫ���б� 7��ɾ��Ԫ�� 8:���ÿ���
	
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
				  pCol=item.pCol;//Ԫ�ص�ռλ��
				  pRow=item.pRow;//Ԫ�ص�ռλ��
				  jlLeiMing.setText(item.leiMing);
					
				  bktgMap=new HashMap<String,int[]>();//����ͨ���������б�
				  for(int[] ia:item.notIn)
				  {
				  	String key=ia[0]+":"+ia[1];
				  	bktgMap.put(key,ia);
				  }
				  
				  kyMap=new HashMap<String,int[]>();//�����������б�
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
		{//"����ͼƬ"
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
		{//����ռλ����
			status=3;
		}
		else if(e.getSource()==jbSetBKTG)
		{//���ò���ͨ�������б�
			status=4;
		}
		else if(e.getSource()==jbSave)
		{//����Ԫ��
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
		{//����Ԫ���б�
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
		{//����Ԫ���б�
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
		{//ɾ��Ԫ��
			status=7;

			if(jl.getSelectedIndex() != -1){
				alItem.remove(jl.getSelectedIndex());
				tempImage = null;
				imagePath = null;
				bktgMap.clear();//����ͨ���������б�
				kyMap.clear();
				this.flush();
				ivp.repaint();	
			}
		}
		else if(e.getSource()==jbSetKY)
		{//���ÿ�������
			status = 8;
		}
	}
	
	public static void main(String args[])
	{
		new MainFrame();
	}
}