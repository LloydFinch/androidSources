package wyf.ytl;

import java.io.Serializable;

/* 
 * 该类代表科研人物，其不同于一般的将领对象，科研人物负责建造箭垛、战车之类的
 * 器械，不直接参与攻城
 */
public class Research implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 9056136905039230444L;
	String name;//科研人物,如鲁班\墨子等
	int researchProject;//科研项目,0表示战车,1表示箭垛
	int researchNumber;//科研数量
	int researchTimeSpan = 10;//研究成功一个科研项目的时间
	int progress = 0;//现在研究好几个了,最大不超过researchNumber

	public Research(){}
	
	public Research(String name,int researchProject,int researchNumber){
		this.name = name;
		this.researchProject = researchProject;
		this.researchNumber = researchNumber;
	}
	
	//方法:每次调用就完成一个科研项目,直至完成所有
	public boolean makeProgress(){
		this.progress+=1;
		if(this.progress == researchNumber){//如果科研项目已经全部完成
			return true;
		}
		return false;
	}
	
	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public int getResearchProject() {
		return researchProject;
	}


	public void setResearchProject(int researchProject) {
		this.researchProject = researchProject;
	}


	public int getResearchNumber() {
		return researchNumber;
	}


	public void setResearchNumber(int researchNumber) {
		this.researchNumber = researchNumber;
	}


	public int getProgress() {
		return progress;
	}


	public void setProgress(int progress) {
		this.progress = progress;
	}
}