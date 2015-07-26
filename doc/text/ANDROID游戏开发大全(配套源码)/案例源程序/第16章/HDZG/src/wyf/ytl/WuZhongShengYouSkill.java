package wyf.ytl;

import java.io.Serializable;

public class WuZhongShengYouSkill extends Skill implements Serializable{
	private static final long serialVersionUID = -3030418088566911402L;
	int moeny;//�������м��ܲ����Ľ�Ǯ
	int army;//�������м��ܲ����ľ���
	
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
		hero.setStrength(hero.getStrength() - strengthCost);//��������
		int realMoney = this.moeny+(int)(Math.random()*50);//��õĲ�ȷ������ĳ����Χ���ǻ�
		int realArmy = this.army+(int)(Math.random()*50);//��õĲ�ȷ������ĳ����Χ���ǻ�
		String alertMessage="���ڴ���Ͽ������ӵؽ���һ��������յ���"+realMoney+"���"+realArmy+"���ˡ�";
		hero.setTotalMoney(hero.getTotalMoney()+realMoney);
		hero.setArmyWithMe(hero.getArmyWithMe()+realArmy);
		PlainAlert pa = new PlainAlert(hero.father, alertMessage, GameView.dialogBack, GameView.dialogButton);
		hero.father.setCurrentGameAlert(pa);//���ù�ȥ���Զ�����
	}
}