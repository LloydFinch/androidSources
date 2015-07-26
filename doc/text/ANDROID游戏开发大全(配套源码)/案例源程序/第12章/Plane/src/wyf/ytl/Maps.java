package wyf.ytl;

import java.util.ArrayList;

public class Maps {
	public static int[][] pathA = {
		{480, 280, 80, -120},//·����ÿ�����X����
		{50, 110, 50, 110},//·����ÿ�����Y����
		{15, 15, 15, 15},//·���������Ĳ���
	};
	public static int[][] pathB = { 
		{480, 280, 80, -120},//·����ÿ�����X����
		{200, 260, 200, 260},//·����ÿ�����Y����
		{15, 15, 15, 15},//·���������Ĳ���
	};
	public static int[][] pathC = {
		{400, 360, 320, 280, 240, 200, 160, -120},//·����ÿ�����X����
		{0, 40, 74, 104, 130, 150, 164, 164},//·����ÿ�����Y����
		{4, 4, 4, 4, 4, 4, 25, 15},//·���������Ĳ���
	};
	public static int[][] pathD = {
		{-20, 80, 280, 480, 500},//·����ÿ�����X����
		{200, 260, 200, 260, 200},//·����ÿ�����Y����
		{15, 15, 15, 15, 15},//·���������Ĳ���
	};
	public static int[][] pathE = {
		{-20, 80, 280, 480, 500},//·����ÿ�����X����
		{50, 110, 50, 110, 50},//·����ÿ�����Y����
		{15, 15, 15, 15, 15},//·���������Ĳ���
	};
	public static int[][] pathF = {
		{400, 50},//·����ÿ�����X����
		{0, 330},//·����ÿ�����Y����
		{50, 15},//·���������Ĳ���
	};
	public static int[][] pathG = {
		{400, 50},//·����ÿ�����X����
		{320, -50},//·����ÿ�����Y����
		{50, 15},//·���������Ĳ���
	};
	public static int[][] pathH = {
		{480, -100},//·����ÿ�����X����
		{50, 50},//·����ÿ�����Y����
		{50, 15},//·���������Ĳ���
	};
	public static int[][] pathI = {
		{480, -100},//·����ÿ�����X����
		{230, 230},//·����ÿ�����Y����
		{50, 15},//·���������Ĳ���
	};
	public static int[][] pathJ = {
		{480, -100},//·����ÿ�����X����
		{140, 140},//·����ÿ�����Y����
		{60, 25},//·���������Ĳ���
	};
	public static int[][] pathK = {
		{310, 260, 350, 350, 10, 10, 350},//·����ÿ�����X����
		{130, 230, 20, 130, 0, 210, 230},//·����ÿ�����Y����
		{30, 20, 30, 20, 40, 25, 35},//·���������Ĳ���
	};
	public static ArrayList<Life> getFirstLife(){//Ϊ��һ�����Ѫ��
		ArrayList<Life> lifes = new ArrayList<Life>();
		lifes.add(new Life(0, 1, 0, pathJ, false, 190));
		lifes.add(new Life(0, 1, 0, pathJ, false, 570));
		return lifes;
	}
	
	public static ArrayList<ChangeBullet> getFirstBollet(){//Ϊ��һ����ӳ��˸ı�ǹ������
		ArrayList<ChangeBullet> lifes = new ArrayList<ChangeBullet>();
		lifes.add(new ChangeBullet(0, 1, 0, pathJ, false, 90)); 
		lifes.add(new ChangeBullet(0, 1, 0, pathJ, false, 290)); 
		return lifes;
	}
	//�����㡢Ŀ��㡢��ǰ���ϵĵڼ�����·�����顢״̬������ʱ�䡢���͡�����
	public static ArrayList<EnemyPlane> getFirst(){
		ArrayList<EnemyPlane> enemyPlanes = new ArrayList<EnemyPlane>();
		enemyPlanes.add(new EnemyPlane(0, 1, 0, pathA, false, 30, 1, 1));
		enemyPlanes.add(new EnemyPlane(0, 1, 0, pathA, false, 40, 1, 1));
		enemyPlanes.add(new EnemyPlane(0, 1, 0, pathA, false, 50, 1, 1));
		enemyPlanes.add(new EnemyPlane(0, 1, 0, pathA, false, 60, 1, 1));
		enemyPlanes.add(new EnemyPlane(0, 1, 0, pathB, false, 85, 1, 1));
		enemyPlanes.add(new EnemyPlane(0, 1, 0, pathB, false, 95, 1, 1));
		enemyPlanes.add(new EnemyPlane(0, 1, 0, pathB, false, 105, 1, 1));
		enemyPlanes.add(new EnemyPlane(0, 1, 0, pathB, false, 115, 1, 1));
		enemyPlanes.add(new EnemyPlane(0, 1, 0, pathC, false, 145, 1, 1));
		enemyPlanes.add(new EnemyPlane(0, 1, 0, pathC, false, 155, 1, 1));
		enemyPlanes.add(new EnemyPlane(0, 1, 0, pathC, false, 165, 1, 1));
		enemyPlanes.add(new EnemyPlane(0, 1, 0, pathC, false, 175, 1, 1));
		enemyPlanes.add(new EnemyPlane(0, 1, 0, pathH, false, 190, 1, 1));
		enemyPlanes.add(new EnemyPlane(0, 1, 0, pathH, false, 200, 1, 1));
		enemyPlanes.add(new EnemyPlane(0, 1, 0, pathH, false, 210, 1, 1));
		enemyPlanes.add(new EnemyPlane(0, 1, 0, pathI, false, 230, 1, 1));
		enemyPlanes.add(new EnemyPlane(0, 1, 0, pathI, false, 240, 1, 1));
		enemyPlanes.add(new EnemyPlane(0, 1, 0, pathI, false, 250, 1, 1));
		enemyPlanes.add(new EnemyPlane(0, 1, 0, pathJ, false, 260, 2, 2));
		enemyPlanes.add(new EnemyPlane(0, 1, 0, pathJ, false, 270, 2, 2));
		enemyPlanes.add(new EnemyPlane(0, 1, 0, pathH, false, 280, 2, 2));
		enemyPlanes.add(new EnemyPlane(0, 1, 0, pathG, false, 290, 1, 1));
		enemyPlanes.add(new EnemyPlane(0, 1, 0, pathG, false, 300, 1, 1));
		enemyPlanes.add(new EnemyPlane(0, 1, 0, pathG, false, 310, 1, 1));
		enemyPlanes.add(new EnemyPlane(0, 1, 0, pathE, false, 350, 4, 1));
		enemyPlanes.add(new EnemyPlane(0, 1, 0, pathE, false, 360, 4, 1));
		enemyPlanes.add(new EnemyPlane(0, 1, 0, pathE, false, 370, 4, 1));
		enemyPlanes.add(new EnemyPlane(0, 1, 0, pathD, false, 350, 4, 1));
		enemyPlanes.add(new EnemyPlane(0, 1, 0, pathD, false, 360, 4, 1));
		enemyPlanes.add(new EnemyPlane(0, 1, 0, pathD, false, 370, 4, 1));
		enemyPlanes.add(new EnemyPlane(0, 1, 0, pathC, false, 390, 1, 1));
		enemyPlanes.add(new EnemyPlane(0, 1, 0, pathF, false, 400, 1, 1));
		enemyPlanes.add(new EnemyPlane(0, 1, 0, pathC, false, 410, 1, 1));
		enemyPlanes.add(new EnemyPlane(0, 1, 0, pathJ, false, 440, 1, 1));
		enemyPlanes.add(new EnemyPlane(0, 1, 0, pathJ, false, 450, 1, 1));
		enemyPlanes.add(new EnemyPlane(0, 1, 0, pathJ, false, 460, 1, 1));
		enemyPlanes.add(new EnemyPlane(0, 1, 0, pathH, false, 490, 1, 1));
		enemyPlanes.add(new EnemyPlane(0, 1, 0, pathI, false, 490, 1, 1));
		enemyPlanes.add(new EnemyPlane(0, 1, 0, pathH, false, 500, 1, 1));
		enemyPlanes.add(new EnemyPlane(0, 1, 0, pathI, false, 500, 1, 1));
		enemyPlanes.add(new EnemyPlane(0, 1, 0, pathH, false, 510, 1, 1));
		enemyPlanes.add(new EnemyPlane(0, 1, 0, pathI, false, 510, 1, 1));
		enemyPlanes.add(new EnemyPlane(0, 1, 0, pathG, false, 540, 1, 1));
		enemyPlanes.add(new EnemyPlane(0, 1, 0, pathG, false, 550, 1, 1));
		enemyPlanes.add(new EnemyPlane(0, 1, 0, pathG, false, 560, 1, 1));
		enemyPlanes.add(new EnemyPlane(0, 1, 0, pathH, false, 580, 1, 1));
		enemyPlanes.add(new EnemyPlane(0, 1, 0, pathI, false, 580, 1, 1));
		enemyPlanes.add(new EnemyPlane(0, 1, 0, pathJ, false, 590, 2, 2));
		enemyPlanes.add(new EnemyPlane(0, 1, 0, pathH, false, 600, 1, 1));
		enemyPlanes.add(new EnemyPlane(0, 1, 0, pathI, false, 600, 1, 1));
		enemyPlanes.add(new EnemyPlane(0, 1, 0, pathK, false, 640, 3, 10));//�ؿ�
		return enemyPlanes;
	}
}