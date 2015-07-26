package wyf.ytl;

import java.io.Serializable;

/* 
 * ��������������䲻ͬ��һ��Ľ�����󣬿������︺������⡢ս��֮���
 * ��е����ֱ�Ӳ��빥��
 */
public class Research implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 9056136905039230444L;
	String name;//��������,��³��\ī�ӵ�
	int researchProject;//������Ŀ,0��ʾս��,1��ʾ����
	int researchNumber;//��������
	int researchTimeSpan = 10;//�о��ɹ�һ��������Ŀ��ʱ��
	int progress = 0;//�����о��ü�����,��󲻳���researchNumber

	public Research(){}
	
	public Research(String name,int researchProject,int researchNumber){
		this.name = name;
		this.researchProject = researchProject;
		this.researchNumber = researchNumber;
	}
	
	//����:ÿ�ε��þ����һ��������Ŀ,ֱ���������
	public boolean makeProgress(){
		this.progress+=1;
		if(this.progress == researchNumber){//���������Ŀ�Ѿ�ȫ�����
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