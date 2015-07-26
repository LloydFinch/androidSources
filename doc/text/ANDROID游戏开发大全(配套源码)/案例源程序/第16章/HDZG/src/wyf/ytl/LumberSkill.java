package wyf.ytl;
/*
 * ����Ϊ��ľ�����࣬�̳��˸���Skill�ĳ�Ա����
 * ��ʵ�����еĳ��󷽷����ֱ����ڼ��������ʩ�ż���
 */
import static wyf.ytl.ConstantUtil.*;

import java.io.Serializable;
public class LumberSkill extends Skill implements Serializable{
	private static final long serialVersionUID = -2689532190376953389L;
	public LumberSkill(){}
	public LumberSkill(int id, String name, int basicEarning, int skillType,
			Hero hero) {
		super(id, name, basicEarning, skillType, hero);
		this.strengthCost = 18;
	}
	@Override
	public int calculateResult() {
		if(hero.strength < strengthCost){//���Ӣ�۵���������ʹ�ñ��μ���
			return -1;
		}
		else{//���ù�ʽ����Ӣ������
			int result = GameFormula.getSkillEearning(proficiencyLevel, basicEarning);
			return result;
		}
	}
	@Override
	public void useSkill(int skillEarning) {
		hero.setTotalMoney(hero.getTotalMoney() + skillEarning);//Ӣ�۽�Ǯ����
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