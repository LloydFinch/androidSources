package wyf.ytl;

import java.io.Serializable;
import java.util.ArrayList;

import android.content.res.Resources;

public class LayerList implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3249868473800473202L;
	ArrayList<Layer> layers = new ArrayList<Layer>();
	
	public LayerList(){}
	
	public LayerList(Resources resources){//构造器
		this.init(resources);
	}
	
	public void init(Resources resources){//初始化资源
		Layer l = new Layer(resources);
		layers.add(l);
		
		MeetableLayer ml = new MeetableLayer(resources);
		layers.add(ml);
	}
	
	public int[][] getTotalNotIn(){
		int[][] result = new int[40][60];
		for(Layer layer : layers){
			int[][] tempNotIn = layer.getNotIn();
			for(int i=0; i<tempNotIn.length; i++){
				for(int j=0; j<tempNotIn[i].length; j++){
					result[i][j] = result[i][j] | tempNotIn[i][j];
				}
			}
		}		
		return result;
	}
}
