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
	Image imageZ;//ͼԪ��ͼƬ
	Image imageTemp;//��ʱͼƬ����
	int xSpan;//ͼԪ���
	int ySpan;//ͼԪ�߶�
	int imageWidth;//ͼԪ��ͼƬ���
	int imageHeight;//ͼԪ��ͼƬ�߶�
	int countCols;//ͼԪ�б�����
	int countRows;//ͼԪ�б�����
	int rows;//��ͼ����
	int cols;//��ͼ����
	Icon tempii;//ICON��ʱ����
	JPanel jps;//�ϲ���ʾ�Զ����ͼ�����
	JPanel jpx;//�²���ʾͼԪ�б�����
	JScrollPane jsps;//�ϲ�Ĺ�������
	JScrollPane jspx;//�²�Ĺ�������
	JSplitPane jspz;//�ָܷ��
	JLabel jpas[][];//�ϲ��ͼ������
	JLabel jpax[][];//�²�ͼԪ������
	int result[][];
	int tempNumber;
	private JMenu[] jMenu = {//�˵�������
		new JMenu("�ļ�"),
	};
	private JMenuItem[] jFileItem = {//�ļ��˵��е��Ӳ˵�
		new JMenuItem("����"),	
	};
	private JMenuBar jMenuBar = new JMenuBar();//�˵���
	public MapEditorMinor(Image imageZ,int xSpan,int ySpan,int rows,int cols){//������
		this.imageZ=imageZ;
		this.xSpan=xSpan;
		this.ySpan=ySpan;
		this.cols=cols;//��
		this.rows=rows;//��
		for(JMenuItem item : jFileItem){//���Ӳ˵���ӵ��ļ��˵���
			jMenu[0].add(item);
			item.addActionListener(this);//��Ӽ���
		}
		for(JMenu temp: jMenu){
			jMenuBar.add(temp);//���˵���ӵ��˵���
		}
		this.setJMenuBar(jMenuBar);//���ô��ڵĲ˵���
		imageWidth=imageZ.getWidth(this);//�õ�ͼƬ�Ŀ�
		imageHeight=imageZ.getHeight(this);//�õ�ͼƬ�ĸ�
		countCols=imageWidth/xSpan+((imageWidth%xSpan==0)?0:1);
		countRows=imageHeight/ySpan+((imageHeight%ySpan==0)?0:1);
		//��ʼ���ϱ߽���
		jps=new JPanel();
		jps.setPreferredSize(new Dimension((xSpan+2)*cols,(ySpan+2)*rows));
		jps.setLayout(null);//���ò���Ϊnull
		jpas=new JLabel[rows][cols];//����JLabel����
		result = new int[rows][cols];//����int������
		for(int i=0;i<rows;i++){
			for(int j=0;j<cols;j++){
				jpas[i][j]=new JLabel();//����һ��JLabel����
				jpas[i][j].setBackground(Color.RED);//����JLabel�ı���ɫ
				jpas[i][j].setOpaque(true);
				jpas[i][j].setBounds(j*(xSpan+2),i*(ySpan+2),xSpan,ySpan);//���ô�С��λ��
				jpas[i][j].addMouseListener(this);//Ϊ�մ�����JLabel��Ӽ���
				jps.add(jpas[i][j]);//��ӵ�jps��
			}
		}
		jsps=new JScrollPane(jps);		
		//��ʼ���±߽���
		jpx=new JPanel();
		jpx.setPreferredSize(new Dimension((xSpan+2)*countCols,(ySpan+2)*countRows));
		jpx.setLayout(null);
		jpax=new JLabel[countRows][countCols];
		for(int i=0;i<countRows;i++){
			for(int j=0;j<countCols;j++){
				jpax[i][j]=new JLabel();//����JLabel����
				jpax[i][j].setBackground(Color.BLUE);//���ñ���ɫ
				jpax[i][j].setBounds(j*(xSpan+2),i*(ySpan+2),xSpan,ySpan);//����λ�úʹ�С
				jpax[i][j].addMouseListener(this);//��Ӽ���
				jpx.add(jpax[i][j]);//��ӵ�jpx��
			}
		}
		jspx=new JScrollPane(jpx);		
		//�ܽ���
		jspz=new JSplitPane(JSplitPane.VERTICAL_SPLIT,jsps,jspx);//����JSplitPane�ؼ�
		jspz.setDividerLocation(250);//���÷ָ�λ��
		jspz.setDividerSize(4);//���÷ָ��ߵĿ��
		this.add(jspz);//��ӵ�����
		//����
		this.setTitle("��ͼ����� V0.1");
		this.setBounds(10,10,640,500);//���ô��ڵĴ�С��λ��
		this.setVisible(true);//���ô��ڵĿɼ���
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//�رհ�ť
		//��ʼ��ͼƬ
		for(int i=0;i<countRows;i++){
			for(int j=0;j<countCols;j++){
				imageTemp=this.createImage(xSpan,ySpan);//����ͼƬ
				Graphics g=imageTemp.getGraphics();//�õ�ͼƬ�Ļ���
				g.drawImage(imageZ,0,0,xSpan,ySpan,j*xSpan,i*ySpan,(j+1)*xSpan,(i+1)*ySpan,this);//����ͼƬ
				ImageIcon ii=new ImageIcon(imageTemp);//ͨ��imageTemp����ImageIcon
				jpax[i][j].setIcon(ii);
			}
		}		
	}
	public void mouseClicked(MouseEvent e){//��굥������
		Object o=e.getSource();
		if(o instanceof JLabel){//����������JLabel			
			boolean iss=false;
			//�ж��Ƿ�Ϊ��ͼԪ��
			for(int i=0;i<rows;i++){
				for(int j=0;j<cols;j++){
					if(jpas[i][j]==o){
						iss=true;//��iss���true
					}
				}
			}
			if(iss){//��������ͼԪ��
				if(tempii!=null){
					((JLabel)o).setIcon(tempii);
					tempii=((JLabel)o).getIcon();
					for(int i=0;i<rows;i++){
						for(int j=0;j<cols;j++){//ѭ��
							if(jpas[i][j]==o){
								result[i][j] = tempNumber;
							}
						}
					}
				}	
			}
			else{//�������ͼԪԪ��
				tempii=((JLabel)o).getIcon();
				for(int i=0;i<countRows;i++){
					for(int j=0;j<countCols;j++){
						if(jpax[i][j]==o){
							tempNumber = i*countCols + j + 1;//�õ�����ĸ���
						}
					}
				}
			}			
		}
	}
	public void actionPerformed(ActionEvent e){
		if(e.getSource() == jFileItem[0]){//����򿪲˵���
			new ResultFrame(result);
		}
	}
	public void mousePressed(MouseEvent e){//�ӿ��еķ���
	}
	public void mouseReleased(MouseEvent e){//�ӿ��еķ���
	}
	public void mouseEntered(MouseEvent e){//�ӿ��еķ���
	}
	public void mouseExited(MouseEvent e){//�ӿ��еķ���
	}	
}