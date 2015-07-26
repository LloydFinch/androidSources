package wyf.ytl;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
public class MapEditorMinor extends JFrame 
		implements MouseListener, ActionListener{
	Image imageZ;//图元总图片
	Image imageTemp;//临时图片引用
	int xSpan;//图元宽度
	int ySpan;//图元高度
	int imageWidth;//图元总图片宽度
	int imageHeight;//图元总图片高度
	int countCols;//图元列表列数
	int countRows;//图元列表行数
	int rows;//地图行数
	int cols;//地图列数
	Icon tempii;//ICON临时引用
	JPanel jps;//上侧显示自定义地图的面板
	JPanel jpx;//下侧显示图元列表的面板
	JScrollPane jsps;//上侧的滚动窗体
	JScrollPane jspx;//下侧的滚动窗体
	JSplitPane jspz;//总分割窗体
	JLabel jpas[][];//上侧地图块数组
	JLabel jpax[][];//下侧图元块数组
	int result[][];
	int tempNumber;
	private JMenu[] jMenu = {//菜单项数组
		new JMenu("文件"),
	};
	private JMenuItem[] jFileItem = {//文件菜单中的子菜单
		new JMenuItem("生成"),	
	};
	private JMenuBar jMenuBar = new JMenuBar();//菜单栏
	public MapEditorMinor(Image imageZ,int xSpan,int ySpan,int rows,int cols){//构造器
		this.imageZ=imageZ;
		this.xSpan=xSpan;
		this.ySpan=ySpan;
		this.cols=cols;//列
		this.rows=rows;//行
		for(JMenuItem item : jFileItem){//将子菜单添加到文件菜单下
			jMenu[0].add(item);
			item.addActionListener(this);//添加监听
		}
		for(JMenu temp: jMenu){
			jMenuBar.add(temp);//将菜单添加到菜单栏
		}
		this.setJMenuBar(jMenuBar);//设置窗口的菜单栏
		imageWidth=imageZ.getWidth(this);//得到图片的宽
		imageHeight=imageZ.getHeight(this);//得到图片的高
		countCols=imageWidth/xSpan+((imageWidth%xSpan==0)?0:1);
		countRows=imageHeight/ySpan+((imageHeight%ySpan==0)?0:1);
		//初始化上边界面
		jps=new JPanel();
		jps.setPreferredSize(new Dimension((xSpan+2)*cols,(ySpan+2)*rows));
		jps.setLayout(null);//设置布局为null
		jpas=new JLabel[rows][cols];//创建JLabel数组
		result = new int[rows][cols];//创建int型数组
		for(int i=0;i<rows;i++){
			for(int j=0;j<cols;j++){
				jpas[i][j]=new JLabel();//创建一个JLabel对象
				jpas[i][j].setBackground(Color.RED);//设置JLabel的背景色
				jpas[i][j].setOpaque(true);
				jpas[i][j].setBounds(j*(xSpan+2),i*(ySpan+2),xSpan,ySpan);//设置大小和位置
				jpas[i][j].addMouseListener(this);//为刚创建的JLabel添加监听
				jps.add(jpas[i][j]);//添加到jps中
			}
		}
		jsps=new JScrollPane(jps);		
		//初始化下边界面
		jpx=new JPanel();
		jpx.setPreferredSize(new Dimension((xSpan+2)*countCols,(ySpan+2)*countRows));
		jpx.setLayout(null);
		jpax=new JLabel[countRows][countCols];
		for(int i=0;i<countRows;i++){
			for(int j=0;j<countCols;j++){
				jpax[i][j]=new JLabel();//创建JLabel对象
				jpax[i][j].setBackground(Color.BLUE);//设置背景色
				jpax[i][j].setBounds(j*(xSpan+2),i*(ySpan+2),xSpan,ySpan);//设置位置和大小
				jpax[i][j].addMouseListener(this);//添加监听
				jpx.add(jpax[i][j]);//添加到jpx中
			}
		}
		jspx=new JScrollPane(jpx);		
		//总界面
		jspz=new JSplitPane(JSplitPane.VERTICAL_SPLIT,jsps,jspx);//创建JSplitPane控件
		jspz.setDividerLocation(250);//设置分割位置
		jspz.setDividerSize(4);//设置分割线的宽度
		this.add(jspz);//添加到窗口
		//窗体
		this.setTitle("地图设计器 V0.1");
		this.setBounds(10,10,640,500);//设置窗口的大小和位置
		this.setVisible(true);//设置窗口的可见性
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//关闭按钮
		//初始化图片
		for(int i=0;i<countRows;i++){
			for(int j=0;j<countCols;j++){
				imageTemp=this.createImage(xSpan,ySpan);//创建图片
				Graphics g=imageTemp.getGraphics();//得到图片的画笔
				g.drawImage(imageZ,0,0,xSpan,ySpan,j*xSpan,i*ySpan,(j+1)*xSpan,(i+1)*ySpan,this);//绘制图片
				ImageIcon ii=new ImageIcon(imageTemp);//通过imageTemp创建ImageIcon
				jpax[i][j].setIcon(ii);
			}
		}		
	}
	public void mouseClicked(MouseEvent e){//鼠标单击方法
		Object o=e.getSource();
		if(o instanceof JLabel){//如果点击的是JLabel			
			boolean iss=false;
			//判断是否为地图元素
			for(int i=0;i<rows;i++){
				for(int j=0;j<cols;j++){
					if(jpas[i][j]==o){
						iss=true;//将iss设成true
					}
				}
			}
			if(iss){//点击上面地图元素
				if(tempii!=null){
					((JLabel)o).setIcon(tempii);
					tempii=((JLabel)o).getIcon();
					for(int i=0;i<rows;i++){
						for(int j=0;j<cols;j++){//循环
							if(jpas[i][j]==o){
								result[i][j] = tempNumber;
							}
						}
					}
				}	
			}
			else{//点击下面图元元素
				tempii=((JLabel)o).getIcon();
				for(int i=0;i<countRows;i++){
					for(int j=0;j<countCols;j++){
						if(jpax[i][j]==o){
							tempNumber = i*countCols + j + 1;//得到点击的格数
						}
					}
				}
			}			
		}
	}
	public void actionPerformed(ActionEvent e){
		if(e.getSource() == jFileItem[0]){//点击打开菜单项
			new ResultFrame(result);
		}
	}
	public void mousePressed(MouseEvent e){//接口中的方法
	}
	public void mouseReleased(MouseEvent e){//接口中的方法
	}
	public void mouseEntered(MouseEvent e){//接口中的方法
	}
	public void mouseExited(MouseEvent e){//接口中的方法
	}	
}