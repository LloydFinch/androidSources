package wyf.ytl;

public class HuiTouShiAnSkill extends Skill{
	public HuiTouShiAnSkill(int id, String name, int basicEarning,
			int skillType, Hero hero) {
		super(id, name, basicEarning, skillType, hero);
		this.strengthCost = 1;
	}
	@Override
	public int calculateResult() {
		if(hero.strength < strengthCost){
			return -1;
		}
		return 0;
	}
	@Override
	public void useSkill(int skillEarning) {
		hero.setStrength(hero.getStrength() - strengthCost);//��������
		hero.setAnimationDirection(3-hero.direction%4);//����Ӣ�۷���
		if(hero.hgt != null){//������жϳ���
			hero.direction=hero.hgt.checkIfTurn();
		}
	}
}