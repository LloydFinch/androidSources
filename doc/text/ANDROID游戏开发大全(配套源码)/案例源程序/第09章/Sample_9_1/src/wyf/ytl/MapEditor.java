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
	private JMenu[] jMenu = {//�˵���
		new JMenu("�ļ�"),
	};
	private JMenuItem[] jFileItem = {//�Ӳ˵���
		new JMenuItem("��"),
	};
	private JMenuBar jMenuBar = new JMenuBar();//�˵���
	JFileChooser jFileChooser = new JFileChooser();//�ļ�ѡ�񴰿�
	JSlider jSliderX = new JSlider(JSlider.HORIZONTAL,10,70,60);//��������JSlider�ؼ�
	JSlider jSliderY = new JSlider(JSlider.VERTICAL,10,70,60);
	JLabel jLabel_rows = new JLabel("��ͼ����:");//�ı�
	JTextField jTextField_rows = new JTextField("10");//�����	
	JLabel jLabel_cols = new JLabel("��ͼ����:");//�ı�
	JTextField jTextField_cols = new JTextField("10");//�����
	JButton jButton = new JButton("ȷ��");//ȷ����ť
	JSpinner jSpinnerX = new JSpinner();//��������JSpinner�ؼ�
	JSpinner jSpinnerY = new JSpinner();
	JScrollPane jsp;//��������
	SplitPanel sp;
	public MapEditor(){//������
		for(JMenuItem item : jFileItem){//���Ӳ˵���ӵ��ļ��˵���
			jMenu[0].add(item);
			item.addActionListener(this);
		}
		for(JMenu temp: jMenu){//���˵�����ӵ��˵���
			jMenuBar.add(temp);
		}
		this.setJMenuBar(jMenuBar);		
		//��ʼ����ֱ�ָ�������
		jSliderY.setBounds(560,10,40,100);//����λ�úʹ�С
		jSliderY.setMinorTickSpacing(2);
		jSliderY.setMajorTickSpacing(20);
		jSliderY.setPaintTicks(true);
		jSliderY.setPaintLabels(true);
		this.add(jSliderY);//��ӵ�������
		jSliderY.setValue(30);//���ó�ʼֵ
		jSliderY.addChangeListener(this);//��Ӽ���
		//��ʼ��ˮƽ�ָ�������
		jSliderX.setBounds(10,410,100,40);//����λ�úʹ�С
		jSliderX.setMinorTickSpacing(2);
		jSliderX.setMajorTickSpacing(20);
		jSliderX.setPaintTicks(true);
		jSliderX.setPaintLabels(true);
		this.add(jSliderX);//��ӵ�������
		jSliderX.setValue(30);//���ó�ʼֵ
		jSliderX.addChangeListener(this);//��Ӽ���
		jSpinnerX.setBounds(120, 410, 50, 20);//����λ�úʹ�С
		this.add(jSpinnerX);//��ӵ�������
		jSpinnerX.setValue(30);//���ó�ʼֵ
		jSpinnerX.addChangeListener(this);//��Ӽ���
		jSpinnerY.setBounds(560,120,40,20);//���ô�С��λ��
		this.add(jSpinnerY);//��ӵ�������
		jSpinnerY.setValue(30);//���ó�ʼֵ
		jSpinnerY.addChangeListener(this);//��Ӽ���
		//�����Զ����ͼ�������ı���
		jLabel_rows.setBounds(190,410,60,20);//���ô�С��λ��
		this.add(jLabel_rows);//��ӵ�������
		jTextField_rows.setBounds(245,410,60,20);//���ô�С��λ��
		this.add(jTextField_rows);//��ӵ�������
		//�����Զ����ͼ�������ı���
		jLabel_cols.setBounds(320,410,60,20);//���ô�С��λ��
		this.add(jLabel_cols);//��ӵ�������
		jTextField_cols.setBounds(375,410,60,20);//���ô�С��λ��
		this.add(jTextField_cols);//��ӵ�������
		//ȷ����ť
		jButton.setBounds(450,410,60,20);
		this.add(jButton);//��Ӱ�ť������
		jButton.addActionListener(this);//Ϊ��ť��Ӽ���
		this.setTitle("��ͼ����� V0.1");//���ñ���
		this.setLayout(null);//���ô��ڵĲ���
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//�رհ�ť
		this.setBounds(100, 100, 640, 500);//���ô��ڵĴ�С��λ��
		this.setVisible(true);//���ô��ڵĿɼ���
	}
	public void actionPerformed(ActionEvent e) {//ʵ�ֽӿ��еķ���
		if(e.getSource() == jFileItem[0]){//����򿪲˵���
			jFileChooser.showOpenDialog(this);
			if(jFileChooser.getSelectedFile() != null){
				String path=jFileChooser.getSelectedFile().getAbsolutePath();		
				sp=new SplitPanel(path,this);		
				//��ͼƬ�ָ������ڷŵ�����������
				jsp=new JScrollPane(sp);
				jsp.setBounds(5,5,550,400);//����SplitPanel�Ĵ�С��λ��
				this.add(jsp);//��ӵ�����
				this.setVisible(true);	//���ÿɼ���		
			}
		}
		else if(e.getSource() == jButton){//�������ȷ����ť
			if(sp != null){
				this.dispose();
				new MapEditorMinor(//����һ��MapEditorMinor����
					sp.bigImage,//ͼԪ������
					jSliderX.getValue(),
					jSliderY.getValue(),
					Integer.parseInt(jTextField_rows.getText()),//��
					Integer.parseInt(jTextField_cols.getText())//��
				);					
			}
		}
	}
	public void stateChanged(ChangeEvent e) {//ʵ�ֽӿ��еķ���
		if(sp != null){
			if(e.getSource() == jSliderX){	
				sp.repaint();
				jSpinnerX.setValue(jSliderX.getValue());//����jSpinnerX��ֵ
			}
			else if(e.getSource() == jSliderY){
				sp.repaint();
				jSpinnerY.setValue(jSliderY.getValue());//����jSpinnerY��ֵ
			}
			else if(e.getSource() == jSpinnerX){
				sp.repaint();
				jSliderX.setValue((Integer)jSpinnerX.getValue());//����jSliderX��ֵ
			}
			else if(e.getSource() == jSpinnerY){
				sp.repaint();
				jSliderY.setValue((Integer)jSpinnerY.getValue());//����jSliderY��ֵ
			}			
		}
		else{
			if(e.getSource() == jSliderX){	
				jSpinnerX.setValue(jSliderX.getValue());//����jSpinnerX��ֵ
			}
			else if(e.getSource() == jSliderY){
				jSpinnerY.setValue(jSliderY.getValue());//����jSpinnerY��ֵ
			}
			else if(e.getSource() == jSpinnerX){
				jSliderX.setValue((Integer)jSpinnerX.getValue());//����jSliderX��ֵ
			}
			else if(e.getSource() == jSpinnerY){
				jSliderY.setValue((Integer)jSpinnerY.getValue());//����jSliderY��ֵ
			}			
		}
	}
	public static void main(String[] args) {//��������������������
		new MapEditor();
	}
}