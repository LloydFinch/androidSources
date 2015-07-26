package bn.frt;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;

public class MapDesigner extends JFrame implements ActionListener{
	int col;
	int row;
	
	MapDesignPanel mdp;
	JScrollPane jsp;
	JButton jbGenerate=new JButton("生成地图");
	JRadioButton jrWu=new JRadioButton("黑色_无",null,true);
	JRadioButton jrZhuanQiang=new JRadioButton("白色_砖墙",null,false);
	JRadioButton jrDiZhuan=new JRadioButton("蓝色_地砖",null,false);
	JRadioButton jrMuBiao=new JRadioButton("绿色_目标",null,false);
	JRadioButton jrXiangZi=new JRadioButton("黄色_箱子",null,false);
	JRadioButton jrRen=new JRadioButton("红色_人",null,false);
	ButtonGroup bg=new ButtonGroup();
	
	JPanel jp=new JPanel();
	public MapDesigner(int col,int row)
	{
		this.col=col;
		this.row=row;
		this.setTitle("推箱子地图设计器");
		
		mdp=new MapDesignPanel(row,col,this);
		jsp=new JScrollPane(mdp);
		
		this.add(jsp);
		jp.add(jbGenerate);jp.add(jrWu);bg.add(jrWu);
		jp.add(jrZhuanQiang);bg.add(jrZhuanQiang);
		jp.add(jrDiZhuan);bg.add(jrDiZhuan);
		jp.add(jrMuBiao);bg.add(jrMuBiao);
		jp.add(jrXiangZi);bg.add(jrXiangZi);jp.add(jrRen);bg.add(jrRen);
		this.add(jp,BorderLayout.NORTH);
		jbGenerate.addActionListener(this);
		this.setBounds(10,10,800,600);
		this.setVisible(true);
		this.mdp.requestFocus(true);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		 if(e.getSource()==this.jbGenerate)
		    {//生成地图代码
		    	String s="public static final int[][] MAP=//0 空 1墙2地3目标4箱子5人\n{";
				for(int i=0;i<mdp.row;i++)
				{
					s=s+"\n\t{";
					for(int j=0;j<mdp.col;j++)
					{
						s=s+mdp.mapDate[i][j]+",";
					}
					s=s.substring(0,s.length()-1)+"},";
				}
				s=s.substring(0,s.length()-1)+"\n};";
				
				new Code(s,"迷宫地图代码");			
		    }
	}

}
