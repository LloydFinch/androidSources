package wyf.ytl;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
public class ResultFrame extends JFrame{
	JTextArea jta = new JTextArea();//����һ���ı����ؼ�
	JScrollPane jsp = new JScrollPane(jta);//����һ���ɹ�������
	public ResultFrame(int[][] result){
		this.setTitle("���");	//���ñ���	
		jta.setText("int map[][] = \n {");//��ӵ�jta
		for(int i=0;i<result.length;i++){
			String temp = "";
			jta.setText(jta.getText()+"\n\t{");
			for(int j=0;j<result[0].length;j++){
				temp += result[i][j] + ",";
			}
			temp = temp.substring(0, temp.length()-1);
			jta.setText(jta.getText()+temp);
			jta.setText(jta.getText()+"}");
		}
		jta.setText(jta.getText()+"\n};");
		this.add(jsp);//��ӵ�������
		this.setBounds(100,50,400,500);//���ô��ڵĴ�С��λ��
		this.setVisible(true);//���ô��ڵĿɼ���
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);//�رհ�ť
	}
}