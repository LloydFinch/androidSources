package wyf.ytl;
import java.util.*;
public class AStarComparator implements Comparator<int[][]>{	
	Game game;
	public AStarComparator(Game game){
		this.game=game;
	}
	public int compare(int[][] o1,int[][] o2){
		int[] t1=o1[1];
		int[] t2=o2[1];
		int[] target=game.target;
		//直线物理距离
		int a=(t1[0]-target[0])*(t1[0]-target[0])+(t1[1]-target[1])*(t1[1]-target[1]);
		int b=(t2[0]-target[0])*(t2[0]-target[0])+(t2[1]-target[1])*(t2[1]-target[1]);
		//门特卡罗距离
		//int a=game.visited[o2[0][1]][o2[0][0]]+Math.abs(t1[0]-target[0])+Math.abs(t1[1]-target[1]);
		//int b=game.visited[o2[0][1]][o2[0][0]]+Math.abs(t2[0]-target[0])+Math.abs(t2[1]-target[1]);	
		return a-b;
	}
	public boolean equals(Object obj){
		return false;
	}
}