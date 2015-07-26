package bn.frt;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class Code extends JFrame{
	JTextArea jta=new JTextArea();
	JScrollPane jsp=new JScrollPane(jta);
	
	public Code(String s,String title)
	{
		this.setTitle(title);
		this.add(jsp);
		jta.setText(s);
		this.setBounds(100,100,400,300);
		this.setVisible(true);
	}

}
