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

	@Override//返回-1表示不能使用，返回0表示可以使用
	public int calculateResult() {
		if(hero.strength < strengthCost){
			return -1;
		}
		return 0;
	}

	@Override
	public void useSkill(int skillEarning) {
		hero.setStrength(hero.getStrength() - strengthCost);//减少体力
		int steps = hero.father.suiXinBu;//获取要走几步
		hero.startToGo(steps);//英雄开始走
		hero.father.setStatus(1);
	}
	
}