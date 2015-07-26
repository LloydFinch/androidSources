package wyf.ytl;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
public class ResultFrame extends JFrame{
	JTextArea jta = new JTextArea();//创建一个文本区控件
	JScrollPane jsp = new JScrollPane(jta);//创建一个可滚动窗口
	public ResultFrame(int[][] result){
		this.setTitle("结果");	//设置标题	
		jta.setText("int map[][] = \n {");//添加到jta
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
		this.add(jsp);//添加到窗口中
		this.setBounds(100,50,400,500);//设置窗口的大小和位置
		this.setVisible(true);//设置窗口的可见性
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);//关闭按钮
	}
}