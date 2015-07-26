package wyf.ytl;

import java.io.Serializable;

public class SuiXinBuSkill extends Skill implements Serializable{
	private static final long serialVersionUID = 7371558636600770105L;
	public SuiXinBuSkill(){}
	public SuiXinBuSkill(int id, String name, int basicEarning, int skillType,
			Hero hero) {
		super(id, name, basicEarning, skillType, hero);
		strengthCost = 60;
	}

	@Override//����-1��ʾ����ʹ�ã�����0��ʾ����ʹ��
	public int calculateResult() {
		if(hero.strength < strengthCost){
			return -1;
		}
		return 0;
	}

	@Override
	public void useSkill(int skillEarning) {
		hero.setStrength(hero.getStrength() - strengthCost);//��������
		int steps = hero.father.suiXinBu;//��ȡҪ�߼���
		hero.startToGo(steps);//Ӣ�ۿ�ʼ��
		hero.father.setStatus(1);
	}
	
}