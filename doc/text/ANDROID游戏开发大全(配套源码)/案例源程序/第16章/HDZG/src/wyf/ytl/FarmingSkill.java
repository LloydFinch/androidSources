package wyf.ytl;

import static wyf.ytl.ConstantUtil.PROFICIENCY_INCREMENT;
import static wyf.ytl.ConstantUtil.PROFICIENCY_UPGRADE_SPAN;
import static wyf.ytl.ConstantUtil.SKILL_LEVEL_MAX;
import static wyf.ytl.ConstantUtil.STRENGTH_COST_DECREMENT;

import java.io.Serializable;

public class FarmingSkill extends Skill implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3127196633134119562L;

	public FarmingSkill(){}
	
	public FarmingSkill(int id, String name, int basicEarning, int skillType,
			Hero hero) {
		super(id, name, basicEarning, skillType, hero);
		this.strengthCost = 18;
	}
	@Override
	public int calculateResult() {
		if(hero.strength < strengthCost){//���Ӣ�۵���������ʹ�ñ��μ���
			return -1;
		}
		else{//����Ӣ������
			int result = GameFormula.getSkillEearning(proficiencyLevel, basicEarning);
			return result;
		}
	}

	@Override
	public void useSkill(int skillEarning) {
		hero.setFood(hero.getFood() + skillEarning);//Ӣ����������
		hero.setStrength(hero.getStrength() - strengthCost);//Ӣ����������
		tempProficiency += PROFICIENCY_INCREMENT;//Ӣ����ʱ����������
		if(tempProficiency == proficiencyToUpgrade && proficiencyLevel<SKILL_LEVEL_MAX){//����������������
			proficiencyLevel += 1;//�����ȵȼ���1
			tempProficiency = 0;//��ʱ����������
			strengthCost -= STRENGTH_COST_DECREMENT;//���ĵ�����ֵ����
			proficiencyToUpgrade+=PROFICIENCY_UPGRADE_SPAN;//��һ��������������
		}
	}
	
}