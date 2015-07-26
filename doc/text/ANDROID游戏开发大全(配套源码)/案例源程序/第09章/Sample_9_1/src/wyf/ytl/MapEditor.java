package wyf.ytl;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class MapEditor extends JFrame implements ActionListener,ChangeListener{
	private JMenu[] jMenu = {//菜单项
		new JMenu("文件"),
	};
	private JMenuItem[] jFileItem = {//子菜单项
		new JMenuItem("打开"),
	};
	private JMenuBar jMenuBar = new JMenuBar();//菜单栏
	JFileChooser jFileChooser = new JFileChooser();//文件选择窗口
	JSlider jSliderX = new JSlider(JSlider.HORIZONTAL,10,70,60);//创建两个JSlider控件
	JSlider jSliderY = new JSlider(JSlider.VERTICAL,10,70,60);
	JLabel jLabel_rows = new JLabel("地图行数:");//文本
	JTextField jTextField_rows = new JTextField("10");//输入框	
	JLabel jLabel_cols = new JLabel("地图列数:");//文本
	JTextField jTextField_cols = new JTextField("10");//输入框
	JButton jButton = new JButton("确定");//确定按钮
	JSpinner jSpinnerX = new JSpinner();//创建两个JSpinner控件
	JSpinner jSpinnerY = new JSpinner();
	JScrollPane jsp;//滚动窗口
	SplitPanel sp;
	public MapEditor(){//构造器
		for(JMenuItem item : jFileItem){//将子菜单添加到文件菜单下
			jMenu[0].add(item);
			item.addActionListener(this);
		}
		for(JMenu temp: jMenu){//将菜单项添加到菜单栏
			jMenuBar.add(temp);
		}
		this.setJMenuBar(jMenuBar);		
		//初始化垂直分割拖拉条
		jSliderY.setBounds(560,10,40,100);//设置位置和大小
		jSliderY.setMinorTickSpacing(2);
		jSliderY.setMajorTickSpacing(20);
		jSliderY.setPaintTicks(true);
		jSliderY.setPaintLabels(true);
		this.add(jSliderY);//添加到窗口中
		jSliderY.setValue(30);//设置初始值
		jSliderY.addChangeListener(this);//添加监听
		//初始化水平分割拖拉条
		jSliderX.setBounds(10,410,100,40);//设置位置和大小
		jSliderX.setMinorTickSpacing(2);
		jSliderX.setMajorTickSpacing(20);
		jSliderX.setPaintTicks(true);
		jSliderX.setPaintLabels(true);
		this.add(jSliderX);//添加到窗口中
		jSliderX.setValue(30);//设置初始值
		jSliderX.addChangeListener(this);//添加监听
		jSpinnerX.setBounds(120, 410, 50, 20);//设置位置和大小
		this.add(jSpinnerX);//添加到窗口中
		jSpinnerX.setValue(30);//设置初始值
		jSpinnerX.addChangeListener(this);//添加监听
		jSpinnerY.setBounds(560,120,40,20);//设置大小和位置
		this.add(jSpinnerY);//添加到窗口中
		jSpinnerY.setValue(30);//设置初始值
		jSpinnerY.addChangeListener(this);//添加监听
		//输入自定义地图行数的文本框
		jLabel_rows.setBounds(190,410,60,20);//设置大小和位置
		this.add(jLabel_rows);//添加到窗口中
		jTextField_rows.setBounds(245,410,60,20);//设置大小和位置
		this.add(jTextField_rows);//添加到窗口中
		//输入自定义地图列数的文本框
		jLabel_cols.setBounds(320,410,60,20);//设置大小和位置
		this.add(jLabel_cols);//添加到窗口中
		jTextField_cols.setBounds(375,410,60,20);//设置大小和位置
		this.add(jTextField_cols);//添加到窗口中
		//确定按钮
		jButton.setBounds(450,410,60,20);
		this.add(jButton);//添加按钮到窗口
		jButton.addActionListener(this);//为按钮添加监听
		this.setTitle("地图设计器 V0.1");//设置标题
		this.setLayout(null);//设置窗口的布局
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//关闭按钮
		this.setBounds(100, 100, 640, 500);//设置窗口的大小和位置
		this.setVisible(true);//设置窗口的可见性
	}
	public void actionPerformed(ActionEvent e) {//实现接口中的方法
		if(e.getSource() == jFileItem[0]){//点击打开菜单项
			jFileChooser.showOpenDialog(this);
			if(jFileChooser.getSelectedFile() != null){
				String path=jFileChooser.getSelectedFile().getAbsolutePath();		
				sp=new SplitPanel(path,this);		
				//将图片分割线面板摆放到滚动窗体中
				jsp=new JScrollPane(sp);
				jsp.setBounds(5,5,550,400);//设置SplitPanel的大小和位置
				this.add(jsp);//添加到窗口
				this.setVisible(true);	//设置可见性		
			}
		}
		else if(e.getSource() == jButton){//点击的是确定按钮
			if(sp != null){
				this.dispose();
				new MapEditorMinor(//创建一个MapEditorMinor窗口
					sp.bigImage,//图元的引用
					jSliderX.getValue(),
					jSliderY.getValue(),
					Integer.parseInt(jTextField_rows.getText()),//行
					Integer.parseInt(jTextField_cols.getText())//列
				);					
			}
		}
	}
	public void stateChanged(ChangeEvent e) {//实现接口中的方法
		if(sp != null){
			if(e.getSource() == jSliderX){	
				sp.repaint();
				jSpinnerX.setValue(jSliderX.getValue());//设置jSpinnerX的值
			}
			else if(e.getSource() == jSliderY){
				sp.repaint();
				jSpinnerY.setValue(jSliderY.getValue());//设置jSpinnerY的值
			}
			else if(e.getSource() == jSpinnerX){
				sp.repaint();
				jSliderX.setValue((Integer)jSpinnerX.getValue());//设置jSliderX的值
			}
			else if(e.getSource() == jSpinnerY){
				sp.repaint();
				jSliderY.setValue((Integer)jSpinnerY.getValue());//设置jSliderY的值
			}			
		}
		else{
			if(e.getSource() == jSliderX){	
				jSpinnerX.setValue(jSliderX.getValue());//设置jSpinnerX的值
			}
			else if(e.getSource() == jSliderY){
				jSpinnerY.setValue(jSliderY.getValue());//设置jSpinnerY的值
			}
			else if(e.getSource() == jSpinnerX){
				jSliderX.setValue((Integer)jSpinnerX.getValue());//设置jSliderX的值
			}
			else if(e.getSource() == jSpinnerY){
				jSliderY.setValue((Integer)jSpinnerY.getValue());//设置jSliderY的值
			}			
		}
	}
	public static void main(String[] args) {//主方法，整个程序的入口
		new MapEditor();
	}
}