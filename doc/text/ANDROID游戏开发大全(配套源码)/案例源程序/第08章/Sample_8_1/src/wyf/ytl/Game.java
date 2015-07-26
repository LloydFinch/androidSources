package wyf.ytl;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Stack;
import android.os.Handler;//引入相关类
import android.os.Message;//引入相关类
import android.widget.Button;//引入相关类
import android.widget.TextView;//引入相关类
public class Game {//算法类
	int algorithmId=0;//算法代号 0--深度优先
	int mapId = 0;//地图编号
	int[][] map = MapList.map[mapId];
	int[] source = MapList.source;//出发点
	int[] target = MapList.target[0];//目标点
	GameView gameView;//gameView的引用
	Button goButton;//goButton的引用
	TextView BSTextView;//BSTextView的引用
	ArrayList<int[][]> searchProcess=new ArrayList<int[][]>();//搜索过程
	Stack<int[][]> stack=new Stack<int[][]>();//深度优先所用栈
	HashMap<String,int[][]> hm=new HashMap<String,int[][]>();//结果路径记录
	LinkedList<int[][]> queue=new LinkedList<int[][]>();//广度优先所用队列
	//A*用优先级队列
	PriorityQueue<int[][]> astarQueue=new PriorityQueue<int[][]>(100,new AStarComparator(this));
	//记录到每个点的最短路径 for Dijkstra
	HashMap<String,ArrayList<int[][]>> hmPath=new HashMap<String,ArrayList<int[][]>>();
	//记录路径长度 for Dijkstra
	int[][] length=new int[MapList.map[mapId].length][MapList.map[mapId][0].length];
	int[][] visited=new int[MapList.map[0].length][MapList.map[0][0].length];//0 未去过 1 去过
	int[][] sequence={
		{0,1},{0,-1},
		{-1,0},{1,0},
		{-1,1},{-1,-1},
		{1,-1},{1,1}
	};
	boolean pathFlag=false;//true 找到了路径
	int timeSpan=10;//时间间隔
	private Handler myHandler = new Handler(){//用来更新UI线程的控件
        public void handleMessage(Message msg){
        	if(msg.what == 1){//改变按钮状态
        		goButton.setEnabled(true);
        	}
        	else if(msg.what == 2){//改变步数的TextView的值
        		BSTextView.setText("使用步数：" + (Integer)msg.obj);
        	}
        }
	};
	public void clearState(){//清空所有状态与列表
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
				length[i][j]=9999;//初始路径长度为最大距离都不可能的那么大	
			}
		}
	}
	public void runAlgorithm(){//运行算法
		clearState();
		switch(algorithmId){
			case 0://深度优先算法
				DFS();
				break;
			case 1://广度优先算法
				BFS();
				break;
			case 2://广度优先 A*算法
				BFSAStar();
				break;				
			case 3://Dijkstra算法
				Dijkstra();
				break;
			case 4:
				DijkstraAStar();//DijkstraA*算法
				break;				
		}		
	}
	

	public void DFS(){//深度优先算法
		new Thread(){
			public void run(){
				boolean flag=true;
				int[][] start={//开始状态
					{source[0],source[1]},
					{source[0],source[1]}
				};
				stack.push(start);
				int count=0;//步数计数器
				while(flag){
					int[][] currentEdge=stack.pop();//从栈顶取出边
					int[] tempTarget=currentEdge[1];//取出此边的目的点
					//判断目的点是否去过，若去过则直接进入下次循环
					if(visited[tempTarget[1]][tempTarget[0]]==1){
						continue;
					}
					count++;
					visited[tempTarget[1]][tempTarget[0]]=1;//标识目的点为访问过
					//将临时目的点加入搜索过程中
					searchProcess.add(currentEdge);
					//记录此临时目的点的父节点
					hm.put(tempTarget[0]+":"+tempTarget[1],new int[][]{currentEdge[1],currentEdge[0]});
					gameView.postInvalidate();
					try{Thread.sleep(timeSpan);}catch(Exception e){e.printStackTrace();}
					//判断有否找到目的点
					if(tempTarget[0]==target[0]&&tempTarget[1]==target[1]){
						break;
					}
					//将所有可能的边入栈
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
				//设置按钮的可用性
				Message msg1 = myHandler.obtainMessage(1);
				myHandler.sendMessage(msg1);
				//改变TextView文字
				Message msg2 = myHandler.obtainMessage(2, count);
				myHandler.sendMessage(msg2);
			}
		}.start();		
	}
	
	public void BFS(){//广度优先算法
		new Thread(){
			public void run(){
				int count=0;//步数计数器
				boolean flag=true;
				int[][] start={//开始状态
					{source[0],source[1]},
					{source[0],source[1]}
				};
				queue.offer(start);
				while(flag){					
					int[][] currentEdge=queue.poll();//从队首取出边
					int[] tempTarget=currentEdge[1];//取出此边的目的点
					//判断目的点是否去过，若去过则直接进入下次循环
					if(visited[tempTarget[1]][tempTarget[0]]==1){
						continue;
					}
					count++;
					visited[tempTarget[1]][tempTarget[0]]=1;//标识目的点为访问过
					searchProcess.add(currentEdge);//将临时目的点加入搜索过程中
					//记录此临时目的点的父节点
					hm.put(tempTarget[0]+":"+tempTarget[1],
							new int[][]{currentEdge[1],currentEdge[0]});
					gameView.postInvalidate();
					try{Thread.sleep(timeSpan);}catch(Exception e){e.printStackTrace();}
					//判断有否找到目的点
					if(tempTarget[0]==target[0]&&tempTarget[1]==target[1]){
						break;
					}
					//将所有可能的边入队列
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
				myHandler.sendMessage(msg1);//设置按钮的可用性
				Message msg2 = myHandler.obtainMessage(2, count);
				myHandler.sendMessage(msg2);//改变TextView文字
			}
		}.start();				
	}
	
	public void Dijkstra(){//Dijkstra算法
		new Thread(){
			public void run(){
				int count=0;//步数计数器
				boolean flag=true;//搜索循环控制
				int[] start={source[0],source[1]};//开始点col,row	
				visited[source[1]][source[0]]=1;
				for(int[] rowcol:sequence){	//计算此点所有可以到达点的路径及长度				
					int trow=start[1]+rowcol[1];
					int tcol=start[0]+rowcol[0];
					if(trow<0||trow>18||tcol<0||tcol>18)continue;
					if(map[trow][tcol]!=0)continue;
					//记录路径长度
					length[trow][tcol]=1;
					//计算路径					
					String key=tcol+":"+trow;
					ArrayList<int[][]> al=new ArrayList<int[][]>();
					al.add(new int[][]{{start[0],start[1]},{tcol,trow}});
					hmPath.put(key,al);	
					//将去过的点记录			
					searchProcess.add(new int[][]{{start[0],start[1]},{tcol,trow}});
					count++;			
				}				
				gameView.postInvalidate();
				outer:while(flag){					
					//找到当前扩展点K 要求扩展点K为从开始点到此点目前路径最短，且此点未考察过
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
					visited[k[1]][k[0]]=1;//设置去过的点					
					gameView.postInvalidate();//重绘
					int dk=length[k[1]][k[0]];//取出开始点到K的路径长度
					ArrayList<int[][]> al=hmPath.get(k[0]+":"+k[1]);//取出开始点到K的路径
					//循环计算所有K点能直接到的点到开始点的路径长度
					for(int[] rowcol:sequence){
						int trow=k[1]+rowcol[1];//计算出新的要计算的点的坐标
						int tcol=k[0]+rowcol[0];
						//若要计算的点超出地图边界或地图上此位置为障碍物则舍弃考察此点
						if(trow<0||trow>MapList.map[mapId].length-1||tcol<0||tcol>MapList.map[mapId][0].length-1)continue;
						if(map[trow][tcol]!=0)continue;
						int dj=length[trow][tcol];//取出开始点到此点的路径长度
						int dkPluskj=dk+1;//计算经K点到此点的路径长度
						//若经K点到此点的路径长度比原来的小则修改到此点的路径
						if(dj>dkPluskj){
							String key=tcol+":"+trow;
							//克隆开始点到K的路径
							ArrayList<int[][]> tempal=(ArrayList<int[][]>)al.clone();
							//将路径中加上一步从K到此点
							tempal.add(new int[][]{{k[0],k[1]},{tcol,trow}});
							//将此路径设置为从开始点到此点的路径
							hmPath.put(key,tempal);
							//修改到从开始点到此点的路径长度							
							length[trow][tcol]=dkPluskj;
							//若此点从未计算过路径长度则将此点加入考察过程记录
							if(dj==9999){//将去过的点记录	
								searchProcess.add(new int[][]{{k[0],k[1]},{tcol,trow}});
								count++;
							}
						}
						//看是否找到目的点
						if(tcol==target[0]&&trow==target[1]){
							pathFlag=true;
							Message msg1 = myHandler.obtainMessage(1);
							myHandler.sendMessage(msg1);//设置按钮的可用性
							Message msg2 = myHandler.obtainMessage(2, count);
							myHandler.sendMessage(msg2);//改变TextView文字
							break outer;
						}
					}										
					try{Thread.sleep(timeSpan);}catch(Exception e){e.printStackTrace();}				
				}								
			}
		}.start();					
	}	

	public void BFSAStar(){//广度优先 A*算法
		new Thread(){
			public void run(){
				boolean flag=true;
				int[][] start={//开始状态
					{source[0],source[1]},
					{source[0],source[1]}
				};
				astarQueue.offer(start);
				int count=0;
				while(flag){					
					int[][] currentEdge=astarQueue.poll();//从队首取出边
					int[] tempTarget=currentEdge[1];//取出此边的目的点
					//判断目的点是否去过，若去过则直接进入下次循环
					if(visited[tempTarget[1]][tempTarget[0]]!=0){
						continue;
					}
					count++;
					//标识目的点为访问过
					visited[tempTarget[1]][tempTarget[0]]=visited[currentEdge[0][1]][currentEdge[0][0]]+1;				
					searchProcess.add(currentEdge);//将临时目的点加入搜索过程中
					//记录此临时目的点的父节点
					hm.put(tempTarget[0]+":"+tempTarget[1],new int[][]{currentEdge[1],currentEdge[0]});
					gameView.postInvalidate();
					try{Thread.sleep(timeSpan);}catch(Exception e){e.printStackTrace();}
					//判断有否找到目的点
					if(tempTarget[0]==target[0]&&tempTarget[1]==target[1]){
						break;
					}
					int currCol=tempTarget[0];//将所有可能的边入优先级队列
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
				myHandler.sendMessage(msg1);//设置按钮的可用性
				Message msg2 = myHandler.obtainMessage(2, count);
				myHandler.sendMessage(msg2);//改变TextView文字
			}
		}.start();				
	}

	public void DijkstraAStar(){//Dijkstra A*算法
		new Thread(){
			public void run(){
				int count=0;//步数计数器
				boolean flag=true;//搜索循环控制
				int[] start={source[0],source[1]};//开始点col,row	
				visited[source[1]][source[0]]=1;
				//计算此点所有可以到达点的路径及长度
				for(int[] rowcol:sequence){					
					int trow=start[1]+rowcol[1];
					int tcol=start[0]+rowcol[0];
					if(trow<0||trow>MapList.map[mapId].length-1||tcol<0||tcol>MapList.map[mapId][0].length-1)continue;
					if(map[trow][tcol]!=0)continue;
					//记录路径长度
					length[trow][tcol]=1;
					String key=tcol+":"+trow;//计算路径
					ArrayList<int[][]> al=new ArrayList<int[][]>();
					al.add(new int[][]{{start[0],start[1]},{tcol,trow}});
					hmPath.put(key,al);	
					//将去过的点记录			
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
								//与普通Dijkstra算法的区别部分=========begin=================================
								if(length[i][j]!=9999){
									if(iniFlag){//第一个找到的可能点
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
								//与普通Dijkstra算法的区别部分==========end==================================
							}
						}
					}
					//设置去过的点
					visited[k[1]][k[0]]=1;					
					//重绘					
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
								//将去过的点记录			
								searchProcess.add(new int[][]{{k[0],k[1]},{tcol,trow}});								
								count++;
							}
						}
						//看是否找到目的点
						if(tcol==target[0]&&trow==target[1]){
							pathFlag=true;
							Message msg1 = myHandler.obtainMessage(1);
							myHandler.sendMessage(msg1);//设置按钮的可用性
							Message msg2 = myHandler.obtainMessage(2, count);
							myHandler.sendMessage(msg2);//改变TextView文字
							break outer;
						}
					}										
					try{Thread.sleep(timeSpan);}catch(Exception e){e.printStackTrace();}				
				}								
			}
		}.start();					
	}
}