package wyf.ytl;

import java.io.Serializable;

public class WuZhongShengYouSkill extends Skill implements Serializable{
	private static final long serialVersionUID = -3030418088566911402L;
	int moeny;//无中生有技能产生的金钱
	int army;//无中生有技能产生的军队
	
	public WuZhongShengYouSkill(){}
	
	public WuZhongShengYouSkill(int id, String name, int basicEarning,
			int skillType, Hero hero) {
		super(id, name, basicEarning, skillType, hero);
		this.army = 450;
		this.moeny = 450;
		this.strengthCost = 59;
	}

	@Override
	public int calculateResult() {
		if(hero.strength < strengthCost){
			return -1;
		}
		return 0;
	}

	public void useSkill(int skillEarning) {
		hero.setStrength(hero.getStrength() - strengthCost);//减少体力
		int realMoney = this.moeny+(int)(Math.random()*50);//获得的不确定，在某个范围内徘徊
		int realArmy = this.army+(int)(Math.random()*50);//获得的不确定，在某个范围内徘徊
		String alertMessage="你在大街上口若悬河地讲了一番大道理，收到了"+realMoney+"金和"+realArmy+"个人。";
		hero.setTotalMoney(hero.getTotalMoney()+realMoney);
		hero.setArmyWithMe(hero.getArmyWithMe()+realArmy);
		PlainAlert pa = new PlainAlert(hero.father, alertMessage, GameView.dialogBack, GameView.dialogButton);
		hero.father.setCurrentGameAlert(pa);//设置过去会自动读的
	}
}