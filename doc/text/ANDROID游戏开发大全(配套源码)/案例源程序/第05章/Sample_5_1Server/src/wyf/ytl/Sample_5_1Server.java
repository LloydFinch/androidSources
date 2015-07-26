package wyf.ytl;
import java.io.DataInputStream;//引入相关类
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
public class Sample_5_1Server{
	public static void main(String[] args){//主方法
		ServerSocket ss = null;//ServerSocket的引用
		Socket s = null;//Socket的引用
		DataInputStream din = null;
		DataOutputStream dout = null;
		try{
			ss = new ServerSocket(8888);//监听到8888端口
			System.out.println("已监听到8888端口！");
		}
		catch(Exception e){
			e.printStackTrace();//打印异常信息
		}
		while(true){
			try{
				s = ss.accept();//等待客户端连接
				din = new DataInputStream(s.getInputStream());
				dout = new DataOutputStream(s.getOutputStream());//得到输入输出流
				String msg = din.readUTF();//读一个字符串
				System.out.println("ip: " + s.getInetAddress());//打印客户端IP
				System.out.println("msg: " + msg);//打印客户端发来的消息
				System.out.println("====================");
				dout.writeUTF("Hello Client!");//向客户端发送消息
			}
			catch(Exception e){
				e.printStackTrace();//打印异常信息
			}	
			finally{
				try{
					if(dout != null){
						dout.close();//关闭输出流
					}
					if(din != null){
						din.close();//关闭输入流
					}
					if(s != null){
						s.close();//关闭Socket连接
					}
				}
				catch(Exception e){
					e.printStackTrace();//打印异常信息
				}
			}
		}
	}
}