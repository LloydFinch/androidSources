package wyf.ytl;

import java.util.ArrayList;
import java.util.Collections;

public class ConstantUtil {
	public static final int SCREEN_WIDTH = 320;//��Ļ���    
	public static final int SCREEN_HEIGHT = 480;//��Ļ���    
	 
	/**
	 * MenuView�������õ��ĳ���
	 */
	public static final int MENU_VIEW_SLEEP_SPAN = 200;//MenuView����ˢ֡�߳�˯��ʱ��
	public static final int MENU_VIEW_WORD_SPACE = 15;//MenuView����˵�֮������¼��
	public static final int MENU_VIEW_UP_SPACE = 110;//MenuView����˵����ϱ��صľ���
	public static final int MENU_VIEW_LEFT_SPACE = 110;//MenuView����˵�������صľ���
	public static final int PICTURECOUNT = 18;//���������и��� 
	public static final int PICTUREWIDTH = 50;
	public static final int PICTUREHEIGHT = 480;
	
	/**
	 * LoadingView�������õ��ĳ���
	 */
	public static final int LOADING_VIEW_WORD_SIZE = 12;//�����������С������С������ֵĴ�С
	public static final int LOADING_VIEW_START_X = 20;//�����������н�������x����
	public static final int LOADING_VIEW_START_Y = 230;//�����������н�������y����
	public static final int LOADING_VIEW_SLEEP_SPAN = 400;//����������ˢ֡�߳�˯��ʱ��
	
	/**
	 * GameView�������õ��ĳ���
	 */
	public static final int GAME_VIEW_SLEEP_SPAN = 100;//GameView����ˢ֡�߳�˯��ʱ��
	public static final int DIALOG_START_Y = 360;//��Ϸ�жԻ���Ļ���y���꣬x����Ϊ��
	//�Ի����е����ִ�С���Ϊ16����ô��ʼx����Ϊ24��1�еĺ�����Ϊ17
	//�Ի����е����ִ�С���Ϊ18����ô��ʼx����Ϊ24 ��1�еĺ�����Ϊ15
	public static final int DIALOG_WORD_SIZE = 16;//�Ի��������ֵĴ�С
	public static final int DIALOG_WORD_START_X = 24;//�Ի��������ֿ�ʼ��x����
	public static final int DIALOG_WORD_START_Y = 390;//�Ի������ֿ�ʼ��y����
	public static final int DIALOG_WORD_EACH_LINE = 17;//�Ի���ÿ�е����ָ���
	public static final int DIALOG_BTN_START_X = 50;//�Ի����а�ť�Ŀ�ʼx����
	public static final int DIALOG_BTN_START_Y = 440;//�Ի����а�ť�Ŀ�ʼy����
	public static final int DIALOG_BTN_WIDTH = 60;//�Ի����а�ť�Ŀ��
	public static final int DIALOG_BTN_HEIGHT = 30;//�Ի����а�ť�ĸ߶�
	public static final int DIALOG_BTN_SPAN = 160;//�Ի�����������ť��x�����ϵļ��
	public static final int DIALOG_BTN_WORD_LEFT = 12;//�Ի����а�ť�����־ఴť��ߵľ���
	public static final int DIALOG_BTN_WORD_UP = 6;//�Ի����а�ť�����־ఴť�ϱ��صľ���
	public static final int DICE_START_X = 2;//���ӿ�ʼ��x����
	public static final int DICE_START_Y = 452;//���ӿ�ʼ��y����
	public static final int DICE_SPAN = 28;//����֮��ļ��
	public static final int GAME_VIEW_MEMU_WORD_SPACE = 10;//GameView�����г��ֵ����˵�֮������¼��
	public static final int GAME_VIEW_MEMU_LEFT_SPACE = 100;//GameView�����г��ֵ����˵�����߾�ľ���
	public static final int GAME_VIEW_MEMU_UP_SPACE = 90;//GameView�����г��ֵ����˵����ϱ��صľ���	
	public static final int GAME_VIEW_SCREEN_ROWS= 14;//GameView�ܹ�������
	public static final int GAME_VIEW_SCREEN_COLS = 11;//GameView�ܹ�������
	public static final int MIN_FOOD = 2000;//�ǳ�С�ڸ�ֵ�򱨾�
	public static final int HERO_FACE_START_Y = 365;//Ӣ��ͷ����ʼ��y���꣬x����Ϊ�㡣Ӣ�۵�ͷ����Ա�����Ӷ�����������
	public static final int HERO_FACE_WIDTH = 60;//Ӣ��ͷ��Ŀ��
	public static final int HERO_FACE_HEIGHT = 60;//Ӣ��ͷ��ĸ߶�
	public static final int DASHBOARD_START_Y = 365;
	public static final int DICE_SIZE = 25;
	public static final int ROLL_SCREEN_SPACE_RIGHT =149;//Ӣ�۾���Ļ�ұ߽�149������ʱ��Ӧ�ù�����320-(31*5+16)
	public static final int ROLL_SCREEN_SPACE_DOWN = 216;//Ӣ�۾���Ļ�±߽�216������ʱ��Ӧ�ù�����480-(31*8+16)
	public static final int ROLL_SCREEN_SPACE_LEFT =140;//Ӣ�۾���Ļ�ұ߽�140������ʱ��Ӧ�ù�����31*4+16
	public static final int ROLL_SCREEN_SPACE_UP = 140;//Ӣ�۾���Ļ�±߽�140������ʱ��Ӧ�ù�����31*4+16
	public static final int MINI_MAP_SPAN = 4;//�����ͼ�Ŀ��С
	public static final int MINI_MAP_START_X = 80;//�����ͼ��ʼ��x����,
	public static final int MAP_BUTTON_START_X = 62;//�����ͼ���صĿ�ʼx����
	public static final int MAP_BUTTON_START_Y = 392;//�����ͼ���صĿ�ʼy����
	public static final int MAP_BUTTON_SIZE = 17;//�����ͼ���صĴ�С
	public static final int STRENGTH_COST_DECREMENT = 2;//ÿ�μ���������С����������ֵ
	/**
	 * Ӣ���������õ��ĳ���
	 */
	public static final int HERO_ANIMATION_SEGMENTS = 8;//Ӣ���ܹ��Ķ����θ���
	public static final int HERO_ANIMATION_FRAMES =2;//Ӣ��ÿ�������ε���֡��
	public static final int HERO_WIDTH = 31;//Ӣ��ͼƬ�Ŀ��
	public static final int HERO_HEIGHT = 62;//Ӣ��ͼƬ�ĸ߶�
	public static final int HERO_MOVING_SPAN = 4;//Ӣ����·�Ĳ���!!!!!!!!!ע�⣬���������ˣ���ô�޼���������ҲҪ����費��Ҫ�ã���������
	public static final int HERO_MOVING_SLEEP_SPAN = 50;//Ӣ���޼�ÿ��һС�����ߵ�ʱ��
	public static final int HERO_WAIT_SPAN = 1000;//Ӣ��û�����ߣ���·�߳̿�ת�ĵȴ�ʱ��
	public static final int HERO_ANIMATION_SLEEP_SPAN = 300;//Ӣ�۶����任���ߵ�ʱ��
	public static final int HERO_NO_ANIMATION_SLEEP_SPAN = 2000;//Ӣ��û�ж����任ʱ����֡�߳̿�ת��˯��ʱ��
	public static final int UP = 7;//Ӣ�۵��ƶ��������ϣ�Ҳ������Ӧ������
	public static final int DOWN = 4;//Ӣ�۵��ƶ��������£�Ҳ������Ӧ������
	public static final int LEFT = 5;//Ӣ�۵��ƶ���������Ҳ������Ӧ������
	public static final int RIGHT = 6;//Ӣ�۵��ƶ��������ң�Ҳ������Ӧ������
	
	
	//Ӣ�۵Ĺ���
	public static final String[] HERO_TITLE = new String[]
	{
		"����",
		"���",
		"����",
		"�Ӿ�",
		"�о�"
	};
	
	//�����ְλ
	public static final String[] GENERAL_TITLE = new String[]
	{
		"�ܱ�",
		"����",
		"��Զ����",
		"��������",
		"�������",
		"��������",
		"��������"
	};
	
	//���е���Ŀ
	public static final String[] RESEARCH_PROJECT = new String[]
	{
		"ս��",
		"����"  
	};
	
	//�з��ǳ��еĽ���
	public static final ArrayList<General> ENEMY_GENERAL = new ArrayList<General>();
	static{
		ENEMY_GENERAL.add(new General("����", 5, 94, 80, 69, 69, 78, 80, 8));
		ENEMY_GENERAL.add(new General("���", 4, 92, 68, 63, 72, 73, 90, 7));
		ENEMY_GENERAL.add(new General("�ﵥ", 5, 92, 70, 84, 52, 68, 82, 5));
		ENEMY_GENERAL.add(new General("��Ӥ", 3, 91, 69, 84, 52, 68, 70, 2));
		ENEMY_GENERAL.add(new General("����", 2, 92, 67, 63, 57, 67, 88, 1));
		ENEMY_GENERAL.add(new General("����", 1, 97, 20, 44, 54, 67, 95, 4));
		ENEMY_GENERAL.add(new General("����", 6, 97, 40, 84, 82, 88, 55, 3));
		ENEMY_GENERAL.add(new General("����", 5, 97, 70, 74, 78, 82, 95, 5));
		ENEMY_GENERAL.add(new General("����", 3, 97, 70, 54, 52, 58, 28, 2));
		ENEMY_GENERAL.add(new General("����", 5, 97, 50, 84, 52, 68, 63, 3));
		ENEMY_GENERAL.add(new General("����", 2, 97, 70, 84, 52, 68, 56, 4));
		ENEMY_GENERAL.add(new General("����", 4, 97, 60, 84, 52, 68, 95, 5));
		ENEMY_GENERAL.add(new General("��ʤ", 6, 97, 77, 94, 42, 48, 75, 1));
		ENEMY_GENERAL.add(new General("κ�޼�", 4, 97, 70, 84, 52, 68, 85, 1));
		ENEMY_GENERAL.add(new General("��Ъ", 2, 97, 64, 24, 32, 48, 87, 1));
		ENEMY_GENERAL.add(new General("����", 2, 97, 30, 54, 52, 38, 88, 2));
		ENEMY_GENERAL.add(new General("����", 3, 97, 30, 64, 56, 53, 89, 4));
		ENEMY_GENERAL.add(new General("����", 5, 97, 76, 84, 72, 63, 49, 5));
		ENEMY_GENERAL.add(new General("����", 4, 97, 74, 64, 52, 70, 59, 5));
		ENEMY_GENERAL.add(new General("������", 5, 97, 70, 84, 52, 68, 94, 6));
		ENEMY_GENERAL.add(new General("����Τ", 1, 97, 32, 24, 42, 28, 45, 8));
		ENEMY_GENERAL.add(new General("��˹", 1, 56, 52, 62, 59, 68, 68, 2));
	}
	
	//���еĳ�����Ϣ
	public static final ArrayList<CityInfo> CITY_INFO = new ArrayList<CityInfo>();
	static{
		Collections.shuffle(ENEMY_GENERAL);//����˳��
		CITY_INFO.add(new CityInfo("����", 0, 5000, 5000, 2, 22, 15, 80000, 5, 8, ENEMY_GENERAL.get(0),1));
		CITY_INFO.add(new CityInfo("�²�", 1, 6000, 6000, 3, 22, 15, 40000, 3, 4, ENEMY_GENERAL.get(1),2));
		CITY_INFO.add(new CityInfo("��ͥ", 1, 7000, 7000, 4, 21, 16, 50000, 6, 5, ENEMY_GENERAL.get(2),3));
		CITY_INFO.add(new CityInfo("�ٴ�", 2, 5000, 5000, 4, 20, 15, 50000, 7, 6, ENEMY_GENERAL.get(3),4));
		CITY_INFO.add(new CityInfo("�³�", 2, 6000, 6000, 5, 22, 15, 60000, 5, 7, ENEMY_GENERAL.get(4),5));
		CITY_INFO.add(new CityInfo("�Ž�", 2, 7000, 7000, 3, 24, 15, 40000, 6, 8, ENEMY_GENERAL.get(5),6));
		CITY_INFO.add(new CityInfo("��ƽ", 2, 8000, 4000, 4, 22, 15, 60000, 7, 4, ENEMY_GENERAL.get(6),7));
		CITY_INFO.add(new CityInfo("��¹", 3, 5000, 5000, 5, 21, 14, 50000, 14, 9, ENEMY_GENERAL.get(7),8));
		CITY_INFO.add(new CityInfo("����", 3, 6000, 7000, 4, 22, 11, 50000, 6, 4, ENEMY_GENERAL.get(8),9));
		CITY_INFO.add(new CityInfo("���", 3, 7000, 4000, 3, 22, 14, 40000, 12, 7, ENEMY_GENERAL.get(9),10));
		CITY_INFO.add(new CityInfo("��֣", 4, 5000, 5000, 1, 24, 18, 60000, 5, 7, ENEMY_GENERAL.get(10),11));
		CITY_INFO.add(new CityInfo("�е�", 4, 6000, 7000, 4, 22, 15, 70000, 5, 4, ENEMY_GENERAL.get(11),12));
		CITY_INFO.add(new CityInfo("����", 4, 8000, 4000, 3, 22, 15, 30000, 7, 8, ENEMY_GENERAL.get(12),13));
		CITY_INFO.add(new CityInfo("����", 4, 8000, 5000, 4, 22, 15, 30000, 3, 6, ENEMY_GENERAL.get(13),14));
		CITY_INFO.add(new CityInfo("��¹", 5, 5000, 7000, 3, 23, 13, 40000, 6, 5, ENEMY_GENERAL.get(14),15));
		CITY_INFO.add(new CityInfo("����", 5, 6000, 5000, 5, 22, 13, 40000, 8, 6, ENEMY_GENERAL.get(15),16));
		CITY_INFO.add(new CityInfo("�䰲", 5, 8000, 4000, 3, 25, 15, 70000, 9, 6, ENEMY_GENERAL.get(16),17));
		CITY_INFO.add(new CityInfo("����", 6, 5000, 5000, 2, 22, 15, 80000, 6, 4, ENEMY_GENERAL.get(17),18));
		CITY_INFO.add(new CityInfo("���", 6, 6000, 7000, 1, 22, 15, 20000, 5, 0, ENEMY_GENERAL.get(18),19));
		CITY_INFO.add(new CityInfo("Ұ��", 6, 6000, 6000, 2, 22, 15, 30000, 4, 4, ENEMY_GENERAL.get(19),20));
		CITY_INFO.add(new CityInfo("����", 7, 5000, 5000, 4, 23, 14, 40000, 5, 8, ENEMY_GENERAL.get(20),21));
		CITY_INFO.add(new CityInfo("����", 7, 6000, 6000, 5, 22, 15, 50000, 7, 12, ENEMY_GENERAL.get(21),22));
	}
	
	//���еĹ���
	public static final String[] COUNTRY_NAME = new String[]
	{
		"�ܹ�",
		"���",
		"����",
		"���",
		"����",
		"�Թ�",
		"κ��",
		"�ع�",
		"�Լ�"
	};
	
	/**
	 * �����������õ��ĳ���
	 */
	public static final int LUMBER = 0;//����ľ���ܣ���Ϊ����HashMap�ļ�
	public static final int FISHING = 1;//������漼�ܣ���Ϊ����HashMap�ļ�
	public static final int FARMING = 2;//����ũ�����ܣ���Ϊ����HashMap�ļ�
	public static final int MINING = 3;//����ɿ��ܣ���Ϊ����HashMap�ļ�
	public static final int SUI_XIN_BU = 4;//�������Ĳ�
	public static final int HUI_TOU_SHI_AN = 5;//�����ͷ�ǰ�
	public static final int WU_ZHONG_SHENG_YOU = 6;//������������
	public static final int SKILL_LEVEL_MAX = 3;	//�����ܹ����������ȼ�
	
	/**
	 * ��������
	 */
	public static final int TILE_SIZE = 31;//��ͼͼԪ�Ĵ�С
	public static final int MAP_ROWS = 40;//��ͼ�ж�����
	public static final int MAP_COLS = 60;//��ͼ�ж�����
	public static final int PROFICIENCY_INCREMENT = 5;//Ӣ��ÿʹ��һ�μ������ӵ�������
	public static final int PROFICIENCY_UPGRADE_SPAN = 50;//Ӣ��ÿ��һ������Ҫ�����������������ӣ���������һ��Ҫ100�����ڶ���Ҫ150
	public static final int HERO_STRENGTH_DRAW_X = 119;//Ӣ�۵�������ʾx����
	public static final int HERO_STRENGTH_DRAW_Y = 438;//Ӣ�۵�������ʾy����
	public static final int HERO_MONEY_DRAW_X = 215;//Ӣ�۵Ľ�Ǯ����ʾx����
	public static final int HERO_MONEY_DRAW_Y = 438;//Ӣ�۵Ľ�Ǯ��ʾy����
	public static final int HERO_ARMY_DRAW_X = 119;//Ӣ�۵ľ�����ʾx����
	public static final int HERO_ARMY_DRAW_Y = 458;//Ӣ�۵ľ�����ʾy����
	public static final int HERO_FOOD_DRAW_X = 215;//Ӣ�۵�������ʾx����
	public static final int HERO_FOOD_DRAW_Y = 458;//Ӣ�۵�������ʾy����
	public static final int DIGIT_SPAN = 10;//�������������صľ���
}
