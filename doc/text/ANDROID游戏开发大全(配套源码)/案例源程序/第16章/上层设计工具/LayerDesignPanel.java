package wyf;

import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

//��ͼ��������
public class LayerDesignPanel extends JPanel implements ActionListener
{
	MainFrame father;	
	Item[][] itemArray=new Item[40][60];
	
	LayerViewPanel lvp=new LayerViewPanel(this);
	JScrollPane jsp=new JScrollPane(lvp);
	
	
	JList jl=new JList();
	JScrollPane jspL=new JScrollPane(jl);
	JButton jbSaveLayer=new JButton("�����");
	JButton jbLoadLayer=new JButton("���ز�");
	JButton jbCreate=new JButton("���ɵ�ͼ�ļ�");
	
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
		else if(e.getSource()==jbCreate)
		{//����Դ����
			
			try
			{			
				FileOutputStream fout = null;
				DataOutputStream dout = null;
				fout = new FileOutputStream("mapsu.so");
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
				
				//д�벻�տ������
				dout.writeInt(totalBlocks);
			
				for(int i=0; i<40; i++)
				{
					for(int j=0; j<60; j++)
					{
						Item item = itemArray[i][j];
						if(item != null)
						{
							int w = item.w;//Ԫ�ص�ͼƬ���
							int h = item.h;//Ԫ�ص�ͼƬ�߶�
							int col = item.col;//Ԫ�صĵ�ͼ��
							int row = item.row;//Ԫ�صĵ�ͼ��
							int pCol = item.pCol;//Ԫ�ص�ռλ��
							int pRow = item.pRow;//Ԫ�ص�ռλ��
							String leiMing = item.leiMing;//����
							
							int [][] notIn = item.notIn;//����ͨ��
							int [][] keYu = item.keYu;//��������
							
							//����ͼƬ�±�
							int outBitmapInxex=0;
							if(leiMing.equals("Forest"))
							{
								outBitmapInxex=0;
							}
							else if(leiMing.equals("Tree"))
							{
								outBitmapInxex=1;
							}
							else if(leiMing.equals("MoZi"))
							{
								outBitmapInxex=2;
							}
							else if(leiMing.equals("YiZhan"))
							{
								outBitmapInxex=3;
							}
							else if(leiMing.equals("Fishing"))
							{
								outBitmapInxex=4;
							}
							else if(leiMing.equals("City"))
							{
								outBitmapInxex=5;
							}
							else if(leiMing.equals("Mine"))
							{
								outBitmapInxex=6;
							}
							else if(leiMing.equals("Rice"))
							{
								outBitmapInxex=7;
							}
							else if(leiMing.equals("TieJangPu"))
							{
								outBitmapInxex=8;
							}
							else if(leiMing.equals("Village"))
							{
								outBitmapInxex=9;
							}
							else if(leiMing.equals("LuBan"))
							{
								outBitmapInxex=10;
							}
							else if(leiMing.equals("WuGuan"))
							{
								outBitmapInxex=11;
							}
							else if(leiMing.equals("XueTang"))
							{
								outBitmapInxex=12;
							}
							else if(leiMing.equals("Wheat"))
							{
								outBitmapInxex=13;
							}
							else if(leiMing.equals("SunZi"))
							{
								outBitmapInxex=14;
							}
							else if(leiMing.equals("MaChang"))
							{
								outBitmapInxex=15;
							}
							else if(leiMing.equals("ZhanXianGuan"))
							{
								outBitmapInxex=16;
							}
							else if(leiMing.equals("Palace")){
								outBitmapInxex=17;
							}
							
							dout.writeByte(outBitmapInxex);//��¼ͼƬ�±�
							dout.writeByte(1);//��¼������־ 1-���� �ϲ㶼����
							dout.writeByte(w);//ͼƬ���
							dout.writeByte(h);//ͼƬ�߶�
							dout.writeByte(col);//������
							dout.writeByte(row);//������
							dout.writeByte(pCol);//ռλ��
							dout.writeByte(pRow);//ռλ��
							
							int bktgCount=notIn.length;//����ͨ���������
							dout.writeByte(bktgCount);//д�벻��ͨ���������
							
							for(int k=0; k<bktgCount; k++)
							{
								dout.writeByte(notIn[k][0]);
								dout.writeByte(notIn[k][1]);
							}		
							
							
							int keYuCount=keYu.length;//�����������
							dout.writeByte(keYuCount);//д������������				
							for(int k=0; k<keYuCount; k++)
							{
								dout.writeByte(keYu[k][0]);
								dout.writeByte(keYu[k][1]);
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