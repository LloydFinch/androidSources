package wyf.ytl;
import java.awt.Color;	//���������
import java.awt.Dimension;//���������
import java.awt.Graphics;//���������
import java.awt.Image;//���������
import javax.swing.ImageIcon;//���������
import javax.swing.JPanel;//���������
public class SplitPanel extends JPanel{
	Image bigImage;//ͼԪ��ͼƬ
	MapEditor father;//MapEditor������
	public SplitPanel(String path,MapEditor father){//������
		this.father=father;
		//����ͼԪ��ͼƬ
		ImageIcon ii=new ImageIcon(path);
		bigImage=ii.getImage();
		//��������СΪͼԪ��ͼƬ��С
		this.setPreferredSize(
			new Dimension(
				bigImage.getWidth(this),
				bigImage.getHeight(this)
			)
		);		
	} 
	public void paint(Graphics g){
		//������л���ͼԪ��ͼƬ
		g.drawImage(bigImage,0,0,Color.white,this);		
		int imageWidth=bigImage.getWidth(this);//ͼԪ��ͼƬ���
		int imageHeight=bigImage.getHeight(this);//ͼԪ��ͼƬ�߶�
		int xSpan=father.jSliderX.getValue();//ͼԪ���
		int ySpan=father.jSliderY.getValue();//ͼԪ�߶�
		//�Զ���������
		g.setColor(Color.green);
		int countS=imageWidth/xSpan+((imageWidth%xSpan==0)?0:1)+1;
		for(int i=0;i<countS;i++){//ѭ��
			if(xSpan*i<=imageWidth){
				g.drawLine(xSpan*i,0,xSpan*i,imageHeight);
			}			
		}
		//�Զ����ƺ���
		g.setColor(Color.green);
		int countH=imageHeight/ySpan+((imageHeight%ySpan==0)?0:1)+1;
		for(int i=0;i<countH;i++){//ѭ��
			if(ySpan*i<=imageHeight){
				g.drawLine(0,ySpan*i,imageWidth,ySpan*i);
			}			
		}
	}
}