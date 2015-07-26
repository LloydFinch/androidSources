package wyf.ytl;
import java.awt.Color;	//引入相关类
import java.awt.Dimension;//引入相关类
import java.awt.Graphics;//引入相关类
import java.awt.Image;//引入相关类
import javax.swing.ImageIcon;//引入相关类
import javax.swing.JPanel;//引入相关类
public class SplitPanel extends JPanel{
	Image bigImage;//图元总图片
	MapEditor father;//MapEditor的引用
	public SplitPanel(String path,MapEditor father){//构造器
		this.father=father;
		//加载图元总图片
		ImageIcon ii=new ImageIcon(path);
		bigImage=ii.getImage();
		//设置面版大小为图元总图片大小
		this.setPreferredSize(
			new Dimension(
				bigImage.getWidth(this),
				bigImage.getHeight(this)
			)
		);		
	} 
	public void paint(Graphics g){
		//在面板中绘制图元总图片
		g.drawImage(bigImage,0,0,Color.white,this);		
		int imageWidth=bigImage.getWidth(this);//图元总图片宽度
		int imageHeight=bigImage.getHeight(this);//图元总图片高度
		int xSpan=father.jSliderX.getValue();//图元宽度
		int ySpan=father.jSliderY.getValue();//图元高度
		//自动绘制竖线
		g.setColor(Color.green);
		int countS=imageWidth/xSpan+((imageWidth%xSpan==0)?0:1)+1;
		for(int i=0;i<countS;i++){//循环
			if(xSpan*i<=imageWidth){
				g.drawLine(xSpan*i,0,xSpan*i,imageHeight);
			}			
		}
		//自动绘制横线
		g.setColor(Color.green);
		int countH=imageHeight/ySpan+((imageHeight%ySpan==0)?0:1)+1;
		for(int i=0;i<countH;i++){//循环
			if(ySpan*i<=imageHeight){
				g.drawLine(0,ySpan*i,imageWidth,ySpan*i);
			}			
		}
	}
}