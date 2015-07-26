package wyf.ytl;
import java.io.DataInputStream;//���������
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
public class Sample_5_1Server{
	public static void main(String[] args){//������
		ServerSocket ss = null;//ServerSocket������
		Socket s = null;//Socket������
		DataInputStream din = null;
		DataOutputStream dout = null;
		try{
			ss = new ServerSocket(8888);//������8888�˿�
			System.out.println("�Ѽ�����8888�˿ڣ�");
		}
		catch(Exception e){
			e.printStackTrace();//��ӡ�쳣��Ϣ
		}
		while(true){
			try{
				s = ss.accept();//�ȴ��ͻ�������
				din = new DataInputStream(s.getInputStream());
				dout = new DataOutputStream(s.getOutputStream());//�õ����������
				String msg = din.readUTF();//��һ���ַ���
				System.out.println("ip: " + s.getInetAddress());//��ӡ�ͻ���IP
				System.out.println("msg: " + msg);//��ӡ�ͻ��˷�������Ϣ
				System.out.println("====================");
				dout.writeUTF("Hello Client!");//��ͻ��˷�����Ϣ
			}
			catch(Exception e){
				e.printStackTrace();//��ӡ�쳣��Ϣ
			}	
			finally{
				try{
					if(dout != null){
						dout.close();//�ر������
					}
					if(din != null){
						din.close();//�ر�������
					}
					if(s != null){
						s.close();//�ر�Socket����
					}
				}
				catch(Exception e){
					e.printStackTrace();//��ӡ�쳣��Ϣ
				}
			}
		}
	}
}