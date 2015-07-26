package bn.frt;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class ColAndRow extends JFrame implements ActionListener{
	/**
	 * �������ͼ�����������������
	 */

	JLabel jlRow=new JLabel("��ͼ����");
	JLabel jlCol=new JLabel("��ͼ����");
	JTextField jtfRow=new JTextField("10");
	JTextField jtfCol=new JTextField("10");
	JButton jbOk=new JButton("ȷ��");
	public ColAndRow()
	{
		this.setTitle("�����ӵ�ͼ�����");
		
		this.setLayout(null);
		jlRow.setBounds(10,5,60,20);
		this.add(jlRow);
		jtfRow.setBounds(70,5,100,20);
		this.add(jtfRow);
		
		jlCol.setBounds(10,30,60,20);
		this.add(jlCol);
		jtfCol.setBounds(70,30,100,20);
		this.add(jtfCol);
		
		jbOk.setBounds(180,5,60,20);
		this.add(jbOk);
		jbOk.addActionListener(this);
		
		this.setBounds(440,320,300,100);
		this.setVisible(true);		
	}
	public static void main(String[] args) {
		new ColAndRow();
	}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		int row=Integer.parseInt(jtfRow.getText().trim());
		int col=Integer.parseInt(jtfCol.getText().trim());
		
		new MapDesigner(row,col);
		this.dispose();
	}

}
