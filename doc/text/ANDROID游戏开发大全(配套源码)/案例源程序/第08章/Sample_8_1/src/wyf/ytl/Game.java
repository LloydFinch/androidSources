package wyf.ytl;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Stack;
import android.os.Handler;//���������
import android.os.Message;//���������
import android.widget.Button;//���������
import android.widget.TextView;//���������
public class Game {//�㷨��
	int algorithmId=0;//�㷨���� 0--�������
	int mapId = 0;//��ͼ���
	int[][] map = MapList.map[mapId];
	int[] source = MapList.source;//������
	int[] target = MapList.target[0];//Ŀ���
	GameView gameView;//gameView������
	Button goButton;//goButton������
	TextView BSTextView;//BSTextView������
	ArrayList<int[][]> searchProcess=new ArrayList<int[][]>();//��������
	Stack<int[][]> stack=new Stack<int[][]>();//�����������ջ
	HashMap<String,int[][]> hm=new HashMap<String,int[][]>();//���·����¼
	LinkedList<int[][]> queue=new LinkedList<int[][]>();//����������ö���
	//A*�����ȼ�����
	PriorityQueue<int[][]> astarQueue=new PriorityQueue<int[][]>(100,new AStarComparator(this));
	//��¼��ÿ��������·�� for Dijkstra
	HashMap<String,ArrayList<int[][]>> hmPath=new HashMap<String,ArrayList<int[][]>>();
	//��¼·������ for Dijkstra
	int[][] length=new int[MapList.map[mapId].length][MapList.map[mapId][0].length];
	int[][] visited=new int[MapList.map[0].length][MapList.map[0][0].length];//0 δȥ�� 1 ȥ��
	int[][] sequence={
		{0,1},{0,-1},
		{-1,0},{1,0},
		{-1,1},{-1,-1},
		{1,-1},{1,1}
	};
	boolean pathFlag=false;//true �ҵ���·��
	int timeSpan=10;//ʱ����
	private Handler myHandler = new Handler(){//��������UI�̵߳Ŀؼ�
        public void handleMessage(Message msg){
        	if(msg.what == 1){//�ı䰴ť״̬
        		goButton.setEnabled(true);
        	}
        	else if(msg.what == 2){//�ı䲽����TextView��ֵ
        		BSTextView.setText("ʹ�ò�����" + (Integer)msg.obj);
        	}
        }
	};
	public void clearState(){//�������״̬���б�
		pathFlag=false;	
		searchProcess.clear();
		stack.clear();
		queue.clear();
		astarQueue.clear();
		hm.clear();
		visited=new int[MapList.map[mapId].length][MapList.map[mapId][0].length];
		hmPath.clear();
		for(int i=0;i<length.length;i++){
			for(int j=0;j<length[0].length;j++){
				length[i][j]=9999;//��ʼ·������Ϊ�����붼�����ܵ���ô��	
			}
		}
	}
	public void runAlgorithm(){//�����㷨
		clearState();
		switch(algorithmId){
			case 0://��������㷨
				DFS();
				break;
			case 1://��������㷨
				BFS();
				break;
			case 2://������� A*�㷨
				BFSAStar();
				break;				
			case 3://Dijkstra�㷨
				Dijkstra();
				break;
			case 4:
				DijkstraAStar();//DijkstraA*�㷨
				break;				
		}		
	}
	

	public void DFS(){//��������㷨
		new Thread(){
			public void run(){
				boolean flag=true;
				int[][] start={//��ʼ״̬
					{source[0],source[1]},
					{source[0],source[1]}
				};
				stack.push(start);
				int count=0;//����������
				while(flag){
					int[][] currentEdge=stack.pop();//��ջ��ȡ����
					int[] tempTarget=currentEdge[1];//ȡ���˱ߵ�Ŀ�ĵ�
					//�ж�Ŀ�ĵ��Ƿ�ȥ������ȥ����ֱ�ӽ����´�ѭ��
					if(visited[tempTarget[1]][tempTarget[0]]==1){
						continue;
					}
					count++;
					visited[tempTarget[1]][tempTarget[0]]=1;//��ʶĿ�ĵ�Ϊ���ʹ�
					//����ʱĿ�ĵ��������������
					searchProcess.add(currentEdge);
					//��¼����ʱĿ�ĵ�ĸ��ڵ�
					hm.put(tempTarget[0]+":"+tempTarget[1],new int[][]{currentEdge[1],currentEdge[0]});
					gameView.postInvalidate();
					try{Thread.sleep(timeSpan);}catch(Exception e){e.printStackTrace();}
					//�ж��з��ҵ�Ŀ�ĵ�
					if(tempTarget[0]==target[0]&&tempTarget[1]==target[1]){
						break;
					}
					//�����п��ܵı���ջ
					int currCol=tempTarget[0];
					int currRow=tempTarget[1];
					for(int[] rc:sequence){
						int i=rc[1];
						int j=rc[0];
						if(i==0&&j==0){continue;}
						if(currRow+i>=0&&currRow+i<MapList.map[mapId].length&&currCol+j>=0&&currCol+j<MapList.map[mapId][0].length&&
						map[currRow+i][currCol+j]!=1){
							int[][] tempEdge={
								{tempTarget[0],tempTarget[1]},
								{currCol+j,currRow+i}
							};
							stack.push(tempEdge);
						}
					}
				}
				pathFlag=true;	
				gameView.postInvalidate();
				//���ð�ť�Ŀ�����
				Message msg1 = myHandler.obtainMessage(1);
				myHandler.sendMessage(msg1);
				//�ı�TextView����
				Message msg2 = myHandler.obtainMessage(2, count);
				myHandler.sendMessage(msg2);
			}
		}.start();		
	}
	
	public void BFS(){//��������㷨
		new Thread(){
			public void run(){
				int count=0;//����������
				boolean flag=true;
				int[][] start={//��ʼ״̬
					{source[0],source[1]},
					{source[0],source[1]}
				};
				queue.offer(start);
				while(flag){					
					int[][] currentEdge=queue.poll();//�Ӷ���ȡ����
					int[] tempTarget=currentEdge[1];//ȡ���˱ߵ�Ŀ�ĵ�
					//�ж�Ŀ�ĵ��Ƿ�ȥ������ȥ����ֱ�ӽ����´�ѭ��
					if(visited[tempTarget[1]][tempTarget[0]]==1){
						continue;
					}
					count++;
					visited[tempTarget[1]][tempTarget[0]]=1;//��ʶĿ�ĵ�Ϊ���ʹ�
					searchProcess.add(currentEdge);//����ʱĿ�ĵ��������������
					//��¼����ʱĿ�ĵ�ĸ��ڵ�
					hm.put(tempTarget[0]+":"+tempTarget[1],
							new int[][]{currentEdge[1],currentEdge[0]});
					gameView.postInvalidate();
					try{Thread.sleep(timeSpan);}catch(Exception e){e.printStackTrace();}
					//�ж��з��ҵ�Ŀ�ĵ�
					if(tempTarget[0]==target[0]&&tempTarget[1]==target[1]){
						break;
					}
					//�����п��ܵı������
					int currCol=tempTarget[0];
					int currRow=tempTarget[1];
					for(int[] rc:sequence){
						int i=rc[1];
						int j=rc[0];
						if(i==0&&j==0){continue;}
						if(currRow+i>=0&&currRow+i<MapList.map[mapId].length
								&&currCol+j>=0&&currCol+j<MapList.map[mapId][0].length&&
						map[currRow+i][currCol+j]!=1){
							int[][] tempEdge={
								{tempTarget[0],tempTarget[1]},
								{currCol+j,currRow+i}
							};
							queue.offer(tempEdge);
						}
					}
				}
				pathFlag=true;	
				gameView.postInvalidate();
				Message msg1 = myHandler.obtainMessage(1);
				myHandler.sendMessage(msg1);//���ð�ť�Ŀ�����
				Message msg2 = myHandler.obtainMessage(2, count);
				myHandler.sendMessage(msg2);//�ı�TextView����
			}
		}.start();				
	}
	
	public void Dijkstra(){//Dijkstra�㷨
		new Thread(){
			public void run(){
				int count=0;//����������
				boolean flag=true;//����ѭ������
				int[] start={source[0],source[1]};//��ʼ��col,row	
				visited[source[1]][source[0]]=1;
				for(int[] rowcol:sequence){	//����˵����п��Ե�����·��������				
					int trow=start[1]+rowcol[1];
					int tcol=start[0]+rowcol[0];
					if(trow<0||trow>18||tcol<0||tcol>18)continue;
					if(map[trow][tcol]!=0)continue;
					//��¼·������
					length[trow][tcol]=1;
					//����·��					
					String key=tcol+":"+trow;
					ArrayList<int[][]> al=new ArrayList<int[][]>();
					al.add(new int[][]{{start[0],start[1]},{tcol,trow}});
					hmPath.put(key,al);	
					//��ȥ���ĵ��¼			
					searchProcess.add(new int[][]{{start[0],start[1]},{tcol,trow}});
					count++;			
				}				
				gameView.postInvalidate();
				outer:while(flag){					
					//�ҵ���ǰ��չ��K Ҫ����չ��KΪ�ӿ�ʼ�㵽�˵�Ŀǰ·����̣��Ҵ˵�δ�����
					int[] k=new int[2];
					int minLen=9999;
					for(int i=0;i<visited.length;i++){
						for(int j=0;j<visited[0].length;j++){
							if(visited[i][j]==0){
								if(minLen>length[i][j]){
									minLen=length[i][j];
									k[0]=j;//col
									k[1]=i;//row
								}
							}
						}
					}
					visited[k[1]][k[0]]=1;//����ȥ���ĵ�					
					gameView.postInvalidate();//�ػ�
					int dk=length[k[1]][k[0]];//ȡ����ʼ�㵽K��·������
					ArrayList<int[][]> al=hmPath.get(k[0]+":"+k[1]);//ȡ����ʼ�㵽K��·��
					//ѭ����������K����ֱ�ӵ��ĵ㵽��ʼ���·������
					for(int[] rowcol:sequence){
						int trow=k[1]+rowcol[1];//������µ�Ҫ����ĵ������
						int tcol=k[0]+rowcol[0];
						//��Ҫ����ĵ㳬����ͼ�߽���ͼ�ϴ�λ��Ϊ�ϰ�������������˵�
						if(trow<0||trow>MapList.map[mapId].length-1||tcol<0||tcol>MapList.map[mapId][0].length-1)continue;
						if(map[trow][tcol]!=0)continue;
						int dj=length[trow][tcol];//ȡ����ʼ�㵽�˵��·������
						int dkPluskj=dk+1;//���㾭K�㵽�˵��·������
						//����K�㵽�˵��·�����ȱ�ԭ����С���޸ĵ��˵��·��
						if(dj>dkPluskj){
							String key=tcol+":"+trow;
							//��¡��ʼ�㵽K��·��
							ArrayList<int[][]> tempal=(ArrayList<int[][]>)al.clone();
							//��·���м���һ����K���˵�
							tempal.add(new int[][]{{k[0],k[1]},{tcol,trow}});
							//����·������Ϊ�ӿ�ʼ�㵽�˵��·��
							hmPath.put(key,tempal);
							//�޸ĵ��ӿ�ʼ�㵽�˵��·������							
							length[trow][tcol]=dkPluskj;
							//���˵��δ�����·�������򽫴˵���뿼����̼�¼
							if(dj==9999){//��ȥ���ĵ��¼	
								searchProcess.add(new int[][]{{k[0],k[1]},{tcol,trow}});
								count++;
							}
						}
						//���Ƿ��ҵ�Ŀ�ĵ�
						if(tcol==target[0]&&trow==target[1]){
							pathFlag=true;
							Message msg1 = myHandler.obtainMessage(1);
							myHandler.sendMessage(msg1);//���ð�ť�Ŀ�����
							Message msg2 = myHandler.obtainMessage(2, count);
							myHandler.sendMessage(msg2);//�ı�TextView����
							break outer;
						}
					}										
					try{Thread.sleep(timeSpan);}catch(Exception e){e.printStackTrace();}				
				}								
			}
		}.start();					
	}	

	public void BFSAStar(){//������� A*�㷨
		new Thread(){
			public void run(){
				boolean flag=true;
				int[][] start={//��ʼ״̬
					{source[0],source[1]},
					{source[0],source[1]}
				};
				astarQueue.offer(start);
				int count=0;
				while(flag){					
					int[][] currentEdge=astarQueue.poll();//�Ӷ���ȡ����
					int[] tempTarget=currentEdge[1];//ȡ���˱ߵ�Ŀ�ĵ�
					//�ж�Ŀ�ĵ��Ƿ�ȥ������ȥ����ֱ�ӽ����´�ѭ��
					if(visited[tempTarget[1]][tempTarget[0]]!=0){
						continue;
					}
					count++;
					//��ʶĿ�ĵ�Ϊ���ʹ�
					visited[tempTarget[1]][tempTarget[0]]=visited[currentEdge[0][1]][currentEdge[0][0]]+1;				
					searchProcess.add(currentEdge);//����ʱĿ�ĵ��������������
					//��¼����ʱĿ�ĵ�ĸ��ڵ�
					hm.put(tempTarget[0]+":"+tempTarget[1],new int[][]{currentEdge[1],currentEdge[0]});
					gameView.postInvalidate();
					try{Thread.sleep(timeSpan);}catch(Exception e){e.printStackTrace();}
					//�ж��з��ҵ�Ŀ�ĵ�
					if(tempTarget[0]==target[0]&&tempTarget[1]==target[1]){
						break;
					}
					int currCol=tempTarget[0];//�����п��ܵı������ȼ�����
					int currRow=tempTarget[1];
					for(int[] rc:sequence){
						int i=rc[1];
						int j=rc[0];
						if(i==0&&j==0){continue;}
						if(currRow+i>=0&&currRow+i<MapList.map[mapId].length&&currCol+j>=0
								&&currCol+j<MapList.map[mapId][0].length&&
						map[currRow+i][currCol+j]!=1){
							int[][] tempEdge={
								{tempTarget[0],tempTarget[1]},
								{currCol+j,currRow+i}
							};
							astarQueue.offer(tempEdge);
						}						
					}
				}
				pathFlag=true;	
				gameView.postInvalidate();
				Message msg1 = myHandler.obtainMessage(1);
				myHandler.sendMessage(msg1);//���ð�ť�Ŀ�����
				Message msg2 = myHandler.obtainMessage(2, count);
				myHandler.sendMessage(msg2);//�ı�TextView����
			}
		}.start();				
	}

	public void DijkstraAStar(){//Dijkstra A*�㷨
		new Thread(){
			public void run(){
				int count=0;//����������
				boolean flag=true;//����ѭ������
				int[] start={source[0],source[1]};//��ʼ��col,row	
				visited[source[1]][source[0]]=1;
				//����˵����п��Ե�����·��������
				for(int[] rowcol:sequence){					
					int trow=start[1]+rowcol[1];
					int tcol=start[0]+rowcol[0];
					if(trow<0||trow>MapList.map[mapId].length-1||tcol<0||tcol>MapList.map[mapId][0].length-1)continue;
					if(map[trow][tcol]!=0)continue;
					//��¼·������
					length[trow][tcol]=1;
					String key=tcol+":"+trow;//����·��
					ArrayList<int[][]> al=new ArrayList<int[][]>();
					al.add(new int[][]{{start[0],start[1]},{tcol,trow}});
					hmPath.put(key,al);	
					//��ȥ���ĵ��¼			
					searchProcess.add(new int[][]{{start[0],start[1]},{tcol,trow}});					
					count++;			
				}				
				gameView.postInvalidate();
				outer:while(flag){					
					int[] k=new int[2];
					int minLen=9999;
					boolean iniFlag=true;
					for(int i=0;i<visited.length;i++){
						for(int j=0;j<visited[0].length;j++){
							if(visited[i][j]==0){
								//����ͨDijkstra�㷨�����𲿷�=========begin=================================
								if(length[i][j]!=9999){
									if(iniFlag){//��һ���ҵ��Ŀ��ܵ�
										minLen=length[i][j]+
										(int)Math.sqrt((j-target[0])*(j-target[0])+(i-target[1])*(i-target[1]));
										k[0]=j;//col
										k[1]=i;//row
										iniFlag=!iniFlag;
									}
									else{
										int tempLen=length[i][j]+
										(int)Math.sqrt((j-target[0])*(j-target[0])+(i-target[1])*(i-target[1]));
										if(minLen>tempLen){
											minLen=tempLen;
											k[0]=j;//col
											k[1]=i;//row
										}
									}
								}
								//����ͨDijkstra�㷨�����𲿷�==========end==================================
							}
						}
					}
					//����ȥ���ĵ�
					visited[k[1]][k[0]]=1;					
					//�ػ�					
					gameView.postInvalidate();
					int dk=length[k[1]][k[0]];
					ArrayList<int[][]> al=hmPath.get(k[0]+":"+k[1]);
					for(int[] rowcol:sequence){
						int trow=k[1]+rowcol[1];
						int tcol=k[0]+rowcol[0];
						if(trow<0||trow>MapList.map[mapId].length-1||tcol<0||tcol>MapList.map[mapId][0].length-1)continue;
						if(map[trow][tcol]!=0)continue;
						int dj=length[trow][tcol];						
						int dkPluskj=dk+1;
						if(dj>dkPluskj){
							String key=tcol+":"+trow;
							ArrayList<int[][]> tempal=(ArrayList<int[][]>)al.clone();
							tempal.add(new int[][]{{k[0],k[1]},{tcol,trow}});
							hmPath.put(key,tempal);							
							length[trow][tcol]=dkPluskj;
							if(dj==9999){
								//��ȥ���ĵ��¼			
								searchProcess.add(new int[][]{{k[0],k[1]},{tcol,trow}});								
								count++;
							}
						}
						//���Ƿ��ҵ�Ŀ�ĵ�
						if(tcol==target[0]&&trow==target[1]){
							pathFlag=true;
							Message msg1 = myHandler.obtainMessage(1);
							myHandler.sendMessage(msg1);//���ð�ť�Ŀ�����
							Message msg2 = myHandler.obtainMessage(2, count);
							myHandler.sendMessage(msg2);//�ı�TextView����
							break outer;
						}
					}										
					try{Thread.sleep(timeSpan);}catch(Exception e){e.printStackTrace();}				
				}								
			}
		}.start();					
	}
}