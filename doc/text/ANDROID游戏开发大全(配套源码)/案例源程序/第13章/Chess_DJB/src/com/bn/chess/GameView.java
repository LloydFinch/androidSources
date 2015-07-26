package com.bn.chess;

import static com.bn.chess.Chess_LoadUtil.DST;
import static com.bn.chess.LoadUtil.IsMate;
import static com.bn.chess.LoadUtil.LegalMove;
import static com.bn.chess.LoadUtil.MakeMove;
import static com.bn.chess.LoadUtil.SearchMain;
import static com.bn.chess.LoadUtil.mvResult;
import static com.bn.chess.ViewConstant.*;

import java.util.Stack;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;
public class GameView extends SurfaceView implements SurfaceHolder.Callback{
	Chess_DJB_Activity father;
	Bitmap[][] chessBitmap;//��������ͼƬ
	Bitmap chessZouQiflag;//��־����
	Bitmap paotai;//��̨
	Bitmap paotai2;//��̨���ص�ʱ��
	Bitmap paotai1;
	Bitmap chuhe;//����ͼƬ
	Paint paint;//����
	Bitmap chessQipan2;//���̱߿�
	Bitmap huiqi;//����ͼƬ
	Bitmap chonWan;//��������
	Bitmap[] iscore=new Bitmap[10];//����ͼ
	Bitmap dunhao;//�ٺ�
	Bitmap beijint;//����ͼ
	Bitmap minueBeijint;//�˵�����ͼ
	Bitmap ifPlayChess;//��ʶ���巽�ı���
	Bitmap isPlaySound;//��������
	Bitmap noPlaySound;//�ر�����
	Bitmap start;//��ʼ
	Bitmap suspend;//��ͣ
	Bitmap nandutiaoZ;//�Ѷ�
	Bitmap nonandutiaoZ;//�ѶȲ�����
	Bitmap guanggao1[]=new Bitmap[2];//�����1
	Bitmap guanggao2;//�����2
	
	Bitmap winJiemian;//Ӯ����
	Bitmap loseJiemian;//�����
	Bitmap fugaiTu;//����ͼ
	Bitmap queDinButton;//ȷ����ť
	Bitmap beijint3;//�϶�������
	Bitmap beijint4;//���ÿ򱳾�
	int[][] color=new int[20][3];
	int length;//�Ѷ���
	int huiqibushu=0;
	float guanggao2X=0;//
	boolean playChessflag;//���巽��־λ��falseΪ�ڷ�����
	SurfaceHolder holder;//����
	Canvas canvas;
	int ucpcSquares[]=new int[256];
	Stack<StackplayChess> stack=new Stack<StackplayChess>();
	float xMove;
	float yMove;//�ƶ�����
	boolean cMfleg=true;//�����Ƿ���Ч
	boolean isFlage;//�Ƿ�Ϊ��һ������
	boolean xzflag = false;//�Ƿ�Ϊѡ�У�ѡ������Ҫ�ƶ��Ļ��ͻ����ƶ�
	boolean threadFlag=true;//�߳��Ƿ�����
	int xzgz = 0;//ѡ�еĳ�ʼ����
	boolean flag;//�Ƿ�Ϊ�ƶ���־
	boolean huiqiFlag=false;//�����Ϊ�����־
	boolean chonwanFlag=false;//�����Ϊ�����־
	boolean isnoNanDu=true;//�Ѷ��Ƿ��ѡ
	boolean dianjiNanDu;//�����Ϊ�Ѷ�
	boolean dianjiXinJu;//�����Ϊ�¾�
	boolean dianjiKaiShi;//�����Ϊ��ʼ
	boolean dianjishengyin;//���Ϊ��������
	boolean dianjiJDT;//�����Ϊ�϶�
	boolean dianjiQueDing;//�����Ϊȷ����ť
	boolean nanduBXZ;
	int bzcol;
	int bzrow;
	public GameView(Context context) {
		super(context);
		this.father=(Chess_DJB_Activity)context;	
		this.getHolder().addCallback(this);//�����������ڻص��ӿڵ�ʵ����
		paint = new Paint();//��������
		paint.setAntiAlias(true);//�򿪿����
		isnoStart=false;
		length=nanduXS*4;
		initColer();//��ʼ���Ѷȹ�������ɫ����
		LoadUtil.Startup();//��ʼ������
		initArrays();//��ʼ������
		initBitmap(); ///��ʼ��ͼƬ
		LoadUtil.sdPlayer=0;//���巽Ϊ
		endTime=zTime;//��ʱ��		
	}
	public void initArrays()
	{
		for(int i=0;i<256;i++)
		{
			ucpcSquares[i]=LoadUtil.ucpcSquares[i];
		}
	}
	@Override
	public void onDraw(Canvas canvas)
	{		
		canvas.drawColor(Color.argb(255, 0, 0, 0));
		canvas.drawBitmap(beijint,sXtart,sYtart, null);

		if(isnoStart)//�����ʼ��
		{
			onDrawWindowindow(canvas,ViewConstant.sXtart,ViewConstant.sYtart);
			if(flag)
			{
				float left=xMove>sXtart+5*xSpan?windowXstartLeft:windowXstartRight;
				float top=windowYstart;			
				float right=left+windowWidth;
				float bottom=top+windowHeight;
				canvas.save();
				canvas.clipRect(new RectF(left,top,right,bottom));
				onDrawWindowindow(canvas,sXtartCk,sYtartCk);//С����
				canvas.restore();
				canvas.drawBitmap(beijint4,left-6,top-6, null);
			}

		}else
		{
			canvas.drawBitmap(guanggao1[(int) ((Math.abs(guanggao2X/40)%2))],sXtart,sYtart, null);
		}
		
		
		onDrawWindowMenu(canvas,ViewConstant.sXtart,ViewConstant.sYtart);
		if(yingJMflag)//�����Ӯ��
		{
			canvas.drawBitmap(fugaiTu,sXtart,sYtart, null);//������ͼ
			canvas.drawBitmap(winJiemian,sXtart+2.5f*xSpan,sYtart+5f*ySpan, null);//Ӯ������ͼ
			if(dianjiQueDing)
			{
				canvas.drawBitmap(scaleToFit(queDinButton,1.2f),sXtart+3.7f*xSpan,sYtart+8.3f*ySpan, null);//ȷ����ť
			}else
			canvas.drawBitmap(queDinButton,sXtart+3.9f*xSpan,sYtart+8.5f*ySpan, null);//ȷ����ť
		}
		else if(shuJMflag)//����
		{
			canvas.drawBitmap(fugaiTu,sXtart,sYtart, null);//������ͼ
			canvas.drawBitmap(loseJiemian,sXtart+2.5f*xSpan,sYtart+5f*ySpan, null);//Ӯ������ͼ
			if(dianjiQueDing){
				canvas.drawBitmap(scaleToFit(queDinButton,1.2f),sXtart+3.7f*xSpan,sYtart+8.3f*ySpan, null);//ȷ����ť
			}else			
			canvas.drawBitmap(queDinButton,sXtart+3.9f*xSpan,sYtart+8.5f*ySpan, null);//ȷ����ť
		}
	}

	public void onDrawWindowindow(Canvas canvas,float sXtart,float sYtart)
	{
		canvas.drawBitmap(chessQipan2,sXtart,sYtart, null);
		
		//���ƺ�ɫ������
		paint.setColor(Color.RED);//���û�����ɫ
		
		paint.setColor(Color.RED);//���û�����ɫ
		paint.setStrokeWidth(3);//�����ߵĴ�ϸ
		
		for(int i=0;i<10;i++)//������
		{
			canvas.drawLine(xSpan+sXtart,ySpan+ySpan*i+sYtart, sXtart+xSpan*9, sYtart+ySpan+ySpan*i, paint);
		}
		
		for(int i=0;i<9;i++)//������
		{
			canvas.drawLine(sXtart+xSpan+i*xSpan,sYtart+ySpan, sXtart+xSpan+xSpan*i, sYtart+ySpan*10, paint);
		}
		
		canvas.drawLine(sXtart+xSpan*4,sYtart+ySpan, sXtart+xSpan*6, sYtart+ySpan*3, paint);//���ƾŹ�б��
		canvas.drawLine(sXtart+xSpan*6,sYtart+ySpan, sXtart+xSpan*4, sYtart+ySpan*3, paint);
		
		canvas.drawLine(sXtart+xSpan*4,sYtart+ySpan*8, sXtart+xSpan*6, sYtart+ySpan*10, paint);
		canvas.drawLine(sXtart+xSpan*6,sYtart+ySpan*8, sXtart+xSpan*4, sYtart+ySpan*10, paint);
		
		//���Ʊ߿�
		paint.setStrokeWidth(5);//�����ߵĴ�ϸ
		canvas.drawLine(sXtart+0.8f*xSpan,sYtart+0.8f*ySpan, sXtart+9.2f*xSpan, sYtart+0.8f*ySpan, paint);
		canvas.drawLine(sXtart+0.8f*xSpan,sYtart+0.8f*ySpan, sXtart+0.8f*xSpan, sYtart+10.2f*ySpan, paint);
		canvas.drawLine(sXtart+9.2f*xSpan,sYtart+0.8f*ySpan, sXtart+9.2f*xSpan, sYtart+10.2f*ySpan, paint);
		canvas.drawLine(sXtart+0.8f*xSpan, sYtart+10.2f*ySpan,sXtart+9.2f*xSpan, sYtart+10.2f*ySpan, paint);
		
		canvas.drawBitmap(chuhe,sXtart+xSpan+1.8f,sYtart+5*ySpan+1.0f, null);//���Ƴ���
		canvas.drawBitmap(paotai,sXtart+2*xSpan-chessR*0.86f,sYtart+3*ySpan-chessR*0.86f, null);//������̨
		canvas.drawBitmap(paotai,sXtart+2*xSpan-chessR*0.86f,sYtart+8*ySpan-chessR*0.86f, null);//������̨
		canvas.drawBitmap(paotai,sXtart+8*xSpan-chessR*0.86f,sYtart+3*ySpan-chessR*0.86f, null);//������̨
		canvas.drawBitmap(paotai,sXtart+8*xSpan-chessR*0.86f,sYtart+8*ySpan-chessR*0.86f, null);//������̨
		
		canvas.drawBitmap(paotai2,sXtart+1*xSpan-chessR*0.86f,sYtart+4*ySpan-chessR*0.86f, null);//���Ʊ�̨
		canvas.drawBitmap(paotai,sXtart+3*xSpan-chessR*0.86f,sYtart+4*ySpan-chessR*0.86f, null);//���Ʊ���̨
		canvas.drawBitmap(paotai,sXtart+5*xSpan-chessR*0.86f,sYtart+4*ySpan-chessR*0.86f, null);//���Ʊ���̨
		canvas.drawBitmap(paotai,sXtart+7*xSpan-chessR*0.86f,sYtart+4*ySpan-chessR*0.86f, null);//���Ʊ���̨
		canvas.drawBitmap(paotai1,sXtart+9*xSpan-chessR*0.86f,sYtart+4*ySpan-chessR*0.86f, null);//���Ʊ���̨
		
		canvas.drawBitmap(paotai2,sXtart+1*xSpan-chessR*0.86f,sYtart+7*ySpan-chessR*0.86f, null);//���Ʊ�̨
		canvas.drawBitmap(paotai,sXtart+3*xSpan-chessR*0.86f,sYtart+7*ySpan-chessR*0.86f, null);//���Ʊ���̨
		canvas.drawBitmap(paotai,sXtart+5*xSpan-chessR*0.86f,sYtart+7*ySpan-chessR*0.86f, null);//���Ʊ���̨
		canvas.drawBitmap(paotai,sXtart+7*xSpan-chessR*0.86f,sYtart+7*ySpan-chessR*0.86f, null);//���Ʊ���̨
		canvas.drawBitmap(paotai1,sXtart+9*xSpan-chessR*0.86f,sYtart+7*ySpan-chessR*0.86f, null);//���Ʊ���̨
		
		//������
		for(int i=0;i<10;i++)//��������
		{
			for(int j=0;j<9;j++)
			{
				if(ucpcSquares[(i+3)*16+j+3]!=0)
				{
					canvas.drawBitmap(chessBitmap[ucpcSquares[(i+3)*16+j+3]/16][
					ucpcSquares[(i+3)*16+j+3]%8],sXtart+j*xSpan-chessR+xSpan,sYtart+i*ySpan-chessR+ySpan, null);
				}
			}
		}
		if(flag)//��������Ч��
		{
			//����ѡ��Ҫ�����ӵı�־
			canvas.drawBitmap(chessZouQiflag,sXtart+(Chess_LoadUtil.FILE_X(Chess_LoadUtil.SRC(xzgz))-2)*xSpan-chessR,
					sYtart+(Chess_LoadUtil.RANK_Y(Chess_LoadUtil.SRC(xzgz))-2)*ySpan-chessR, null);			
			canvas.drawBitmap(chessZouQiflag,sXtart+(bzcol+1)*xSpan-chessR,
			sYtart+(bzrow+1)*ySpan-chessR, null);//�����ƶ�ʱ�ƶ�����ĳ��
			
		}
		if(cMfleg&&stack.size()>0)//���Ƶ�������ı�־
		{			
			canvas.drawBitmap(chessZouQiflag,sXtart+(Chess_LoadUtil.FILE_X(Chess_LoadUtil.SRC(mvResult))-2)*xSpan-chessR,
					sYtart+(Chess_LoadUtil.RANK_Y(Chess_LoadUtil.SRC(mvResult))-2)*ySpan-chessR, null);
			
			canvas.drawBitmap(chessZouQiflag,sXtart+(Chess_LoadUtil.FILE_X(Chess_LoadUtil.DST(mvResult))-2)*xSpan-chessR,
					sYtart+(Chess_LoadUtil.RANK_Y(Chess_LoadUtil.DST(mvResult))-2)*ySpan-chessR, null);
		}		
		if(flag)
		{
			paint.setAlpha(200);//���ƷŴ������
			canvas.drawBitmap(scaleToFit(chessBitmap[ucpcSquares[xzgz]/16][
			ucpcSquares[xzgz]%8],2),xMove-2*chessR,yMove-2*chessR, paint);
		}
		//���ƻ���Ȳ˵�
	}
	
	public void onDrawWindowMenu(Canvas canvas,float sXtart,float sYtart)
	{
		
		
		canvas.drawBitmap(scaleToFit(minueBeijint,1f),sXtart,sYtart+11.0f*ySpan, null);//�˵�����ͼ
		
		canvas.drawBitmap(ifPlayChess,sXtart+1f*xSpan,sYtart+11.4f*ySpan, null);
		
		if(playChessflag)//����Ǻ췽����
		{
			canvas.drawBitmap(scaleToFit(chessBitmap[1][0],0.9f),sXtart+1.1f*xSpan,sYtart+11.45f*ySpan, null);
		}else{
			canvas.drawBitmap(scaleToFit(chessBitmap[0][0],0.9f),sXtart+1.1f*xSpan,sYtart+11.45f*ySpan, null);
		}
		
		//����ʱ��
		drawScoreStr(canvas,endTime/1000/60<10?"0"+endTime/1000/60:endTime/1000/60+"",
				sXtart+3f*xSpan,sYtart+11.4f*ySpan);
		canvas.drawBitmap(dunhao,sXtart+scoreWidth*2+3f*xSpan,sYtart+11.4f*ySpan, null);//�ٺ�
		drawScoreStr(canvas,endTime/1000%60<10?"0"+endTime/1000%60:endTime/1000%60+"",
				scoreWidth*3+sXtart+3f*xSpan,sYtart+11.4f*ySpan);
		

		if(huiqiFlag)//�Ƿ����˻��尴ť
		{
			canvas.drawBitmap(scaleToFit(huiqi,1.2f),sXtart+6.9f*xSpan,sYtart+11.25f*ySpan, null);//����
		}else{
			canvas.drawBitmap(huiqi,sXtart+7f*xSpan,sYtart+11.35f*ySpan, null);//����
		}

		canvas.drawBitmap(scaleToFit(minueBeijint,1f),sXtart,sYtart+12.8f*ySpan, null);//�˵�����ͼ
		if(chonwanFlag)//�¾�
		{
			canvas.drawBitmap(scaleToFit(chonWan,1.2f),sXtart+0.3f*xSpan,sYtart+12.9f*ySpan, null);//����
		}
		else
		{
			canvas.drawBitmap(chonWan,sXtart+0.8f*xSpan,sYtart+13.2f*ySpan, null);//����
		}
		if(isnoStart)//��ʼ��ͣ������Ѿ���ʼ
		{
			if(dianjiKaiShi)//�������˴���
			{
				canvas.drawBitmap(scaleToFit(suspend,1.2f),sXtart+3.0f*xSpan,sYtart+13.0f*ySpan, null);
			}else
			canvas.drawBitmap(suspend,sXtart+3.3f*xSpan,sYtart+13.2f*ySpan, null);
		}
		else{
			if(dianjiKaiShi)//�������˴���
			{
				canvas.drawBitmap(scaleToFit(start,1.2f),sXtart+3.0f*xSpan,sYtart+13.0f*ySpan, null);
			}else
			canvas.drawBitmap(start,sXtart+3.3f*xSpan,sYtart+13.2f*ySpan, null);
		}
		
		if(!isnoStart)//�Ѷ��Ƿ��ѡ�������ѡ�����ʾ��ǰ״̬�¿��Ե����Ϊ��ͣ״̬����Ϊ��ѡ
		{
			if(dianjiNanDu)
			{
				canvas.drawBitmap(scaleToFit(nandutiaoZ,1.2f),sXtart+5.5f*xSpan,sYtart+13.0f*ySpan, null);
			}else
			canvas.drawBitmap(nandutiaoZ,sXtart+5.8f*xSpan,sYtart+13.2f*ySpan, null);
		}
		else
		{

			canvas.drawBitmap(nonandutiaoZ,sXtart+5.8f*xSpan,sYtart+13.2f*ySpan, null);
		}
		
		if(isnoPlaySound)//�Ƿ�������,������Ѿ�����������
		{
			if(dianjishengyin)
			{
				canvas.drawBitmap(scaleToFit(isPlaySound,1.2f),sXtart+8.0f*xSpan,sYtart+13.1f*ySpan, null);				
			}else
			canvas.drawBitmap(isPlaySound,sXtart+8.3f*xSpan,sYtart+13.2f*ySpan, null);
		}else{
			if(dianjishengyin)
			{
				canvas.drawBitmap(scaleToFit(noPlaySound,1.2f),sXtart+8.0f*xSpan,sYtart+13.1f*ySpan, null);				
			}else
			canvas.drawBitmap(noPlaySound,sXtart+8.3f*xSpan,sYtart+13.2f*ySpan, null);
		}
		
		if(!isnoStart&&nanduBXZ)//���Ϊ�������Ѷ�
		{  
			
			canvas.drawBitmap(beijint3,sXtart,sYtart+14.6f*ySpan, null);
			Rect r=new Rect(50,50,200,200);
			
			if(dianjiJDT)
			{
				length=(int)((xMove-sXtart)/(xZoom*20));
				nanduXS=(int)((xMove-sXtart)/(90*xZoom));
				if(nanduXS<1)
				{
					nanduXS=1;
				}
				if(nanduXS>5)
				{
					nanduXS=5;
				}
				Constant.LIMIT_DEPTH=nanduXS*6;
			}
			
			
			if(length<1)
			{
				length=1;
			}else if(length>20)
			{
				length=20;
			}
			for(int i=0;i<length;i++)
			{
				paint.setARGB(color[i][0], color[i][1], color[i][2], 0);//���û�����ɫ	
				r=new Rect((int) (sXtart+60*xZoom+i*xZoom*18),(int)(sYtart+15.3f*ySpan),
						(int) (sXtart+60*xZoom+(i)*xZoom*18+13*xZoom),(int) (sYtart+15.3f*ySpan+32*xZoom));
				canvas.drawRect(r, paint);
			}

		}else
		{
			canvas.drawBitmap(minueBeijint,sXtart,sYtart+14.6f*ySpan, null);
			//���ƹ��
			float left=sXtart+40*xZoom;
			float top=sYtart+15f*ySpan;			
			float right=sXtart+9*xSpan;
			float bottom=sYtart+15.2f*ySpan+40*xZoom;
			canvas.save();
			canvas.clipRect(new RectF(left,top,right,bottom));
			canvas.drawBitmap(guanggao2,guanggao2X,sYtart+14.8f*ySpan, null);
			canvas.restore();
		}
	}
	public void initColer()
	{
		int r=(200-61)/20;
		int g=(159-24)/20;
		int b=(107-6)/20;
		for(int i=0;i<20;i++)
		{
			
				color[i][0]=61+i*r;
				color[i][1]=24+i*g;
				color[i][2]=5+i*b;			
		}
	}
	public void drawScoreStr(Canvas canvas,String s,float width,float height)//�����ַ�������
	{
    	//���Ƶ÷�
    	String scoreStr=s; 
    	if(s.length()<2)
    	{
    		s="0"+s;
    	}
    	for(int i=0;i<scoreStr.length();i++){//ѭ�����Ƶ÷�
    		int tempScore=scoreStr.charAt(i)-'0';
    		canvas.drawBitmap(iscore[tempScore], width+i*scoreWidth,height, null);
    		}
	}
	public void initBitmap()
	{
		float xZoom=ViewConstant.xZoom;

		beijint4=scaleToFit(BitmapFactory.decodeResource(getResources(), R.drawable.beijingkuangtu),xZoom);//���ÿ�
		beijint3=scaleToFit(BitmapFactory.decodeResource(getResources(), R.drawable.beijing3),xZoom);//�Ѷ�ѡ���϶�������
		fugaiTu=scaleToFit(BitmapFactory.decodeResource(getResources(), R.drawable.shuyingfugai),xZoom);//����ͼ
		winJiemian=scaleToFit(BitmapFactory.decodeResource(getResources(), R.drawable.yingjiemian),xZoom);//Ӯ����
		loseJiemian=scaleToFit(BitmapFactory.decodeResource(getResources(), R.drawable.shuijiemian),xZoom);//�����
		queDinButton=scaleToFit(BitmapFactory.decodeResource(getResources(), R.drawable.queding),xZoom);//����һ��
		guanggao1[0]=scaleToFit(BitmapFactory.decodeResource(getResources(), R.drawable.guanggao1),xZoom);
		guanggao1[1]=scaleToFit(BitmapFactory.decodeResource(getResources(), R.drawable.floor),xZoom);
		guanggao2=scaleToFit(BitmapFactory.decodeResource(getResources(), R.drawable.guanggao2),xZoom);//���12
		nonandutiaoZ=scaleToFit(BitmapFactory.decodeResource(getResources(), R.drawable.nonandu),xZoom);//�Ѷ�
		nandutiaoZ=scaleToFit(BitmapFactory.decodeResource(getResources(), R.drawable.nanduxuanz),xZoom);//�Ѷ�
		suspend=scaleToFit(BitmapFactory.decodeResource(getResources(), R.drawable.zhanting),xZoom);//��ͣ
		start=scaleToFit(BitmapFactory.decodeResource(getResources(), R.drawable.start),xZoom);//��ʼ
		isPlaySound=scaleToFit(BitmapFactory.decodeResource(getResources(), R.drawable.kaiqishengy),xZoom);//��������ͼƬ
		noPlaySound=scaleToFit(BitmapFactory.decodeResource(getResources(), R.drawable.guanbishengy),xZoom);//�ر�����ͼƬ
		ifPlayChess=scaleToFit(BitmapFactory.decodeResource(getResources(), R.drawable.caidanxiaqifang),xZoom);//�˵����巽����ͼ
		minueBeijint=scaleToFit(BitmapFactory.decodeResource(getResources(), R.drawable.beijing),xZoom);//�˵�����ͼ
		beijint=scaleToFit(BitmapFactory.decodeResource(getResources(), R.drawable.beijintu),xZoom);//����ͼ
		chonWan=scaleToFit(BitmapFactory.decodeResource(getResources(), R.drawable.chonwan),xZoom);//����
		huiqi=scaleToFit(BitmapFactory.decodeResource(getResources(), R.drawable.huiqi),xZoom);//����
		
		chessQipan2=scaleToFit(BitmapFactory.decodeResource(getResources(), R.drawable.floor2),xZoom);//����
		chuhe=scaleToFit(BitmapFactory.decodeResource(getResources(), R.drawable.chuhe),xZoom);//����
		chessZouQiflag=scaleToFit(BitmapFactory.decodeResource(getResources(), R.drawable.selected),xZoom);//��־λ
		iscore[0] = scaleToFit(BitmapFactory.decodeResource(getResources(), R.drawable.d0),xZoom);//����ͼ
		iscore[1] = scaleToFit(BitmapFactory.decodeResource(getResources(), R.drawable.d1),xZoom);
		iscore[2] = scaleToFit(BitmapFactory.decodeResource(getResources(), R.drawable.d2),xZoom);
		iscore[3] = scaleToFit(BitmapFactory.decodeResource(getResources(), R.drawable.d3),xZoom);
		iscore[4] = scaleToFit(BitmapFactory.decodeResource(getResources(), R.drawable.d4),xZoom);
		iscore[5] = scaleToFit(BitmapFactory.decodeResource(getResources(), R.drawable.d5),xZoom);
		iscore[6] = scaleToFit(BitmapFactory.decodeResource(getResources(), R.drawable.d6),xZoom);
		iscore[7] = scaleToFit(BitmapFactory.decodeResource(getResources(), R.drawable.d7),xZoom);
		iscore[8] = scaleToFit(BitmapFactory.decodeResource(getResources(), R.drawable.d8),xZoom);
		iscore[9] = scaleToFit(BitmapFactory.decodeResource(getResources(), R.drawable.d9),xZoom);
		
		dunhao=scaleToFit(BitmapFactory.decodeResource(getResources(), R.drawable.dunhao),xZoom);//�ٺ�
		xZoom=xZoom*0.6f;
		paotai=scaleToFit(BitmapFactory.decodeResource(getResources(), R.drawable.paotai),xZoom);//��̨
		paotai1=scaleToFit(BitmapFactory.decodeResource(getResources(), R.drawable.paotai1),xZoom);//��̨2
		paotai2=scaleToFit(BitmapFactory.decodeResource(getResources(), R.drawable.paotai2),xZoom);//��̨2
		xZoom=ViewConstant.xZoom*0.9f;
		
		chessBitmap=new Bitmap[][]{//����
				{
					scaleToFit(BitmapFactory.decodeResource(getResources(), R.drawable.bk),xZoom),
					scaleToFit(BitmapFactory.decodeResource(getResources(), R.drawable.ba),xZoom),
					scaleToFit(BitmapFactory.decodeResource(getResources(), R.drawable.bb),xZoom),
					scaleToFit(BitmapFactory.decodeResource(getResources(), R.drawable.bn),xZoom),
					scaleToFit(BitmapFactory.decodeResource(getResources(), R.drawable.br),xZoom),
					scaleToFit(BitmapFactory.decodeResource(getResources(), R.drawable.bc),xZoom),
					scaleToFit(BitmapFactory.decodeResource(getResources(), R.drawable.bp),xZoom),
				
				},{
					scaleToFit(BitmapFactory.decodeResource(getResources(), R.drawable.rk),xZoom),
					scaleToFit(BitmapFactory.decodeResource(getResources(), R.drawable.ra),xZoom),
				    scaleToFit(BitmapFactory.decodeResource(getResources(), R.drawable.rb),xZoom),
					scaleToFit(BitmapFactory.decodeResource(getResources(), R.drawable.rn),xZoom),
					scaleToFit(BitmapFactory.decodeResource(getResources(), R.drawable.rr),xZoom),
					scaleToFit(BitmapFactory.decodeResource(getResources(), R.drawable.rc),xZoom),
					scaleToFit(BitmapFactory.decodeResource(getResources(), R.drawable.rp),xZoom),
				}
		};
	}
	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {}
	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		
		this.holder=holder;        
		canvas = null;
        try {
        	// �����������������ڴ�Ҫ��Ƚϸߵ�����£����������ҪΪnull
        	canvas = holder.lockCanvas(null);
            synchronized (holder) {
            	
            	onDraw(canvas);//����
            }
        } finally {
            if (canvas != null) {
            	//���ͷ���
            	holder.unlockCanvasAndPost(canvas);
            }
        }
        newThread();
       
	}
	public void newThread()
	{
		 new Thread(){
	        	@Override
	        	public void run()
	        	{
	        		while(threadFlag)
	        		{
	        			if(isnoStart)
	        			{
	        				if(endTime-500<0)
		        			{
		        				if(!cMfleg)//��������������壬ʱ����ˣ���Ϊ��������
		        				{
		        					yingJMflag=true;
		        					LoadUtil.Startup();//��ʼ������
		        					initArrays();//��ʼ������
		        					endTime=zTime;
		        					isnoStart=false;
		        					dianjiJDT=false;
		        				}else{//��Ϊ�Լ�����
		        					shuJMflag=true;
		        					LoadUtil.Startup();//��ʼ������
		        					initArrays();//��ʼ������
		        					endTime=zTime;
		        					isnoStart=false;
		        					dianjiJDT=false;
		        					
		        				}
		        			}else
		        			{
		        				endTime-=500;
		        			}
	        			}
	        			guanggao2X-=10;
	        			if(guanggao2X<-400*xZoom)
	        			{
	        				guanggao2X=400*xZoom;
	        			}
	        			
	        			onDrawcanvas();//�ػ淽��
	        			try {
							Thread.sleep(500);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
	        			
	        		}
	        	}
	        }.start();
	}
	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {}
	@Override
	public boolean onTouchEvent(MotionEvent e){

		if(!cMfleg)//������ڽ��е�������
		{
			return false;
		}
		
		int col = (int)( (e.getX()-sXtart)/xSpan);
		int row = (int) ((e.getY()-sYtart)/ySpan);	
		if(	((e.getX()-col*xSpan-sXtart)*(e.getX()-col*xSpan-sXtart)+
			(e.getY()-row*ySpan-sYtart)*(e.getY()-row*ySpan-sYtart))<xSpan/2*xSpan/2)		
		{
			bzcol=col-1;
			bzrow=row-1;//��������һ��������
		}
		else if(((e.getX()-col*xSpan-sXtart)*(e.getX()-col*xSpan-sXtart)+
			(e.getY()-(row+1)*ySpan-sYtart)*(e.getY()-(row+1)*ySpan-sYtart))<xSpan/2*xSpan/2)	
		{
			bzcol=col-1;
			bzrow=row;
		}
		else if(((e.getX()-(1+col)*xSpan-sXtart)*(e.getX()-(1+col)*xSpan-sXtart)+
				(e.getY()-(row+1)*ySpan-sYtart)*(e.getY()-(row+1)*ySpan-sYtart))<xSpan/2*xSpan/2)	
		{
			bzcol=col;
			bzrow=row;
		}
		else if(
			((e.getX()-(1+col)*xSpan-sXtart)*(e.getX()-(1+col)*xSpan-sXtart)+
			(e.getY()-row*ySpan-sYtart)*(e.getY()-row*ySpan-sYtart))<xSpan/2*xSpan/2)
			{
				bzcol=col;
				bzrow=row-1;
			}
		if(e.getAction()==MotionEvent.ACTION_DOWN)//�������
		{
			if(yingJMflag||shuJMflag)//�����ǰΪӮ����
			{
				if(e.getX()>sXtart+3.9f*xSpan&&e.getY()>sYtart+8.5f*ySpan&&//˭����
						e.getY()<sYtart+8.5f*ySpan+1.5f*ySpan&&e.getX()<sXtart+3.9f*xSpan+2*xSpan)
				{
					dianjiQueDing=true;
				}
				
				return true;
			}
			if(e.getX()>sXtart+0.5f*xSpan&&e.getY()>sYtart+13.3f*ySpan&&//�¾ֱ�־
					e.getY()<sYtart+13.3f*ySpan+1*ySpan&&e.getX()<sXtart+0.5f*xSpan+2*xSpan)
			{
				chonwanFlag=true;
				return true;
			}
			if(e.getX()>sXtart+7f*xSpan&&e.getY()>sYtart+11.5f*ySpan&&//�����־
					e.getY()<sYtart+11.5f*ySpan+1*ySpan&&e.getX()<sXtart+7f*xSpan+2*xSpan)
			{
				huiqiFlag=true;
				return true;
			}
			if(e.getX()>sXtart+3.3f*xSpan&&e.getY()>sYtart+13.5f*ySpan&&
					e.getY()<sYtart+13.5f*ySpan+1*ySpan&&e.getX()<sXtart+3.3f*xSpan+2*xSpan)//��ʼ����
			{
				dianjiKaiShi=true;
				return true;  
			}
			if(e.getX()>sXtart+5.8f*xSpan&&e.getY()>sYtart+13.5f*ySpan&&
					e.getY()<sYtart+13.5f*ySpan+1*ySpan&&e.getX()<sXtart+5.8f*xSpan+2*xSpan)//�Ѷ�����
			{
				dianjiNanDu=true;  
				return true;  
			}
			if(e.getX()>sXtart+8.3f*xSpan&&e.getY()>sYtart+13.5f*ySpan&&
					e.getY()<sYtart+13.5f*ySpan+1*ySpan&&e.getX()<sXtart+8.3f*xSpan+2*xSpan)//��������
			{
				dianjishengyin=true;
				return true;  
			}
			
			if(e.getX()>sXtart&&e.getY()>sYtart+15.3f*ySpan&&
					e.getY()<sYtart+15.3f*ySpan+1*ySpan&&e.getX()<sXtart+10f*xSpan+2*xSpan)//����������
			{
				xMove=e.getX();
				dianjiJDT=true;
				return true;  
			}
			if((bzrow+3)*16+bzcol+3<0||(bzrow+3)*16+bzcol+3>255||!isnoStart)//���û�п�ʼ
			{
				return false;
			}
			if(ucpcSquares[(bzrow+3)*16+bzcol+3]!=0&&ucpcSquares[(bzrow+3)*16+bzcol+3]/16==0)
			{//������Լ�������
				
				xzflag=true;
				xzgz=(bzrow+3)*16+bzcol+3;//ѡ�еĸ�������ô��
				
				flag=true;//ѡ����
				onDrawcanvas();//�ػ淽��
				return true;
			}
		}
		else if(e.getAction()==MotionEvent.ACTION_MOVE)
		{
			 float x=xMove=e.getX();
			float y=yMove=e.getY();
			if(xzflag)//����Ѿ�ѡ�У����ΪҪ����
			{
				
				if(x>48*10*xZoom+sXtart-windowWidth/2)
				{
					x=48*10*xZoom+sXtart-windowWidth/2;
				}
				else if(x<sXtart+windowWidth/2+0*48*xZoom){
					x=sXtart+windowWidth/2-0*48*xZoom;
				}
				if(y>48*11*xZoom+sYtart-windowHeight/2)
				{
					y=48*11*xZoom+sYtart-windowHeight/2;
				}
				else if(y<sYtart+windowHeight/2+0*48*xZoom){
					y=sYtart+windowHeight/2-0*48*xZoom;
				}
				if(xMove>sXtart+5*xSpan)
				{
					sXtartCk=sXtart-(x-windowWidth/2-windowXstartLeft);
					sYtartCk=sYtart-(y-windowHeight/2-windowYstart);
					
				}
				else{
					sXtartCk=sXtart-(x-windowWidth/2-windowXstartRight);
					sYtartCk=sYtart-(y-windowHeight/2-windowYstart);
				}	
				onDrawcanvas();//�ػ淽��
			        return true;				
			}
			else
			{
				xzflag=false;//�ָ�����Ϊѡ��״̬
				flag=false;//ѡ����
				onDrawcanvas();//�ػ淽��
			        return true;
			}
		}
		else if(e.getAction()==MotionEvent.ACTION_UP)
		{

			if(yingJMflag||shuJMflag)//�����ǰΪӮ����
			{
				if(dianjiQueDing==true)
				{
					if(e.getX()>sXtart+3.9f*xSpan&&e.getY()>sYtart+8.5f*ySpan&&//˭����
							e.getY()<sYtart+8.5f*ySpan+1.5f*ySpan&&e.getX()<sXtart+3.9f*xSpan+2*xSpan)
					{
						shuJMflag=false;
						yingJMflag=false;
						LoadUtil.Startup();//��ʼ������
						initArrays();//��ʼ������
						LoadUtil.sdPlayer=0;//���巽Ϊ�Լ�
						endTime=zTime;//��ʱ��
						isnoStart=false;//Ϊ��ͣ״̬
					}
					dianjiQueDing=false;
				}
				onDrawcanvas();//�ػ淽��
				
				return true;
			}
		
			if(e.getX()>sXtart+7f*xSpan&&e.getY()>sYtart+11.5f*ySpan&&//�����־
					e.getY()<sYtart+11.5f*ySpan+1*ySpan&&e.getX()<sXtart+7f*xSpan+2*xSpan)
			{//����ǻ���
				if(huiqiFlag==true)
				{
					if(!stack.empty()&&stack.size()>1)
					{
						if(huiqibushu>huiqiBS)
						{
			    			Toast.makeText//ͬʱ��Toast.��ʾ���
			    			(
			    				father, 
			    				"���岽���Ѿ������涨!", 
			    				Toast.LENGTH_SHORT
			    			).show();
							return true;
						}
						huiqibushu++;
						StackplayChess chess=stack.pop();
						LoadUtil.UndoMovePiece(chess.mvResult, chess.pcCaptured);
						
						chess=stack.pop();
						LoadUtil.UndoMovePiece(chess.mvResult, chess.pcCaptured);
						if(!stack.empty())
						{
							mvResult=stack.peek().mvResult;
						}
						initArrays();//�������
					}
					huiqiFlag=false;
					onDrawcanvas();//�ػ淽��
					return false;
				}
				
			}else if(huiqiFlag)
			{
				huiqiFlag=false;
				onDrawcanvas();//�ػ淽��
				return true;
			}
			
			
			if(e.getX()>sXtart+0.5f*xSpan&&e.getY()>sYtart+13.3f*ySpan&&//�¾ֱ�־
					e.getY()<sYtart+13.3f*ySpan+1*ySpan&&e.getX()<sXtart+0.5f*xSpan+2*xSpan)
			{
				if(chonwanFlag==true)
				{
					
					shuJMflag=false;
					yingJMflag=false;   
					isnoStart=false;
					endTime=zTime;
					stack.clear();
					LoadUtil.Startup();//��ʼ������
					initArrays();//��ʼ������
				}
				
				chonwanFlag=false;		
				onDrawcanvas();//�ػ淽��
				return true;
			}
			else if(chonwanFlag)
			{
				chonwanFlag=false;
				onDrawcanvas();//�ػ淽��
				return true;
			}
			
			if(dianjiKaiShi)//��ʼ
			{
				if(e.getX()>sXtart+3.3f*xSpan&&e.getY()>sYtart+13.5f*ySpan&&
						e.getY()<sYtart+13.5f*ySpan+1*ySpan&&e.getX()<sXtart+3.3f*xSpan+2*xSpan)//��ʼ����
				{
					//�ô�Ϊ�ӵ����ʼʱ���ݲ�ͬ�������ͬ����
					isnoStart=!isnoStart;
					nanduBXZ=false;
					if(!isnoStart)
					{
						dianjiJDT=false;//����϶�
						isnoTCNDxuanz=false;
					}
					dianjiKaiShi=false;
					onDrawcanvas();//�ػ淽��
					
				}
				dianjiKaiShi=false;
				onDrawcanvas();//�ػ淽��
				return true;
			}
			
			
			if(dianjiNanDu)//�Ѷ�����
			{
				if(e.getX()>sXtart+5.8f*xSpan&&e.getY()>sYtart+13.5f*ySpan&&
						e.getY()<sYtart+13.5f*ySpan+1*ySpan&&e.getX()<sXtart+5.8f*xSpan+2*xSpan)//�Ѷ�����
				{
					if(!isnoStart)//���Ϊ��ͣ״̬�£��ſ���
					{
						nanduBXZ=!nanduBXZ;
						isnoTCNDxuanz=!isnoTCNDxuanz;
					}
					else{
						isnoTCNDxuanz=false;
						dianjiJDT=false;  
					}
					
					isnoNanDu=!isnoNanDu;  
				}
				
				dianjiNanDu=false;
				onDrawcanvas();//�ػ淽��
		        return true;
			}
			
			if(dianjishengyin)
			{
				if(e.getX()>sXtart+8.3f*xSpan&&e.getY()>sYtart+13.5f*ySpan&&
						e.getY()<sYtart+13.5f*ySpan+1*ySpan&&e.getX()<sXtart+8.3f*xSpan+2*xSpan)//��������
				{
					isnoPlaySound=!isnoPlaySound;
				}  
				
				dianjishengyin=false;
				onDrawcanvas();//�ػ淽��
				return true;
			}
			
			if(xzflag)//�Ƿ�Ϊѡ�������
			{
				if((bzrow+3)*16+bzcol+3<0||(bzrow+3)*16+bzcol+3>255)
				{
					xzflag=false;
					flag=false;
					onDrawcanvas();//�ػ淽��
					return true;
				}
				if(bzrow<0||bzrow>9||bzcol<0||bzcol>8)
				{
					xzflag=false;
					flag=false;
					onDrawcanvas();//�ػ淽��
					return true;
				}
					int sqDst = DST(xzgz+((bzrow+3)*16+bzcol+3)*256);
					int pcCaptured = ucpcSquares[sqDst];//�õ�Ŀ�ĸ��ӵ�����
					int mv=xzgz+((bzrow+3)*16+bzcol+3)*256;
					if(stack.size()>12&&//�ж��Ƿ��߳������
							mv==stack.get(stack.size()-4).mvResult&&
							stack.get(stack.size()-1).mvResult==stack.get(stack.size()-5).mvResult&&
							stack.get(stack.size()-5).mvResult==stack.get(stack.size()-9).mvResult&&
							stack.get(stack.size()-2).mvResult==stack.get(stack.size()-6).mvResult&&
							stack.get(stack.size()-6).mvResult==stack.get(stack.size()-10).mvResult&&							
							stack.get(stack.size()-3).mvResult==stack.get(stack.size()-7).mvResult&&
							stack.get(stack.size()-3).mvResult==stack.get(stack.size()-11).mvResult&&
							stack.get(stack.size()-4).mvResult==stack.get(stack.size()-8).mvResult&&
							stack.get(stack.size()-8).mvResult==stack.get(stack.size()-12).mvResult){
						xzflag=false;
						flag=false;
						onDrawcanvas();//�ػ淽��
						Toast.makeText(//ͬʱ��Toast.��ʾ���		    			
		    				father, 
		    				"�����ظ��������ӣ����������ӡ�", 
		    				Toast.LENGTH_SHORT).show();		    			
						return true;
					}
						 if (LegalMove(mv)) {//���������Ϲ���
						      if (MakeMove(mv, 0)) {//���û�б�����
						    	  initArrays();//��ʼ������
						    	  father.playSound(2,1);//b���������������
						    	  huiqibushu=0;//�����־����
						    	  onDrawcanvas();//�ػ淽��
						    	 stack.push(new StackplayChess(xzgz+((bzrow+3)*16+bzcol+3)*256,pcCaptured));//���岽����ջ
						    if (IsMate()) {//������Ӯ��		
						    	LoadUtil.Startup();//��ʼ������
								initArrays();//��ʼ������
						    	yingJMflag=true;
						    	father.playSound(4,1);//b��������,Ӯ��
						    	onDrawcanvas();//�ػ淽��
						        } else {
//						          //�������� 
							        new Thread(){//����һ���߳̽��е�������
							        	@Override
							        	public void run()
							        	{
							        		endTime=zTime;//ʱ���ʼ��
							        		playChessflag=true;//��������
							        		cMfleg=false;//���������־
							        		onDrawcanvas();//�ػ淽��
											//��������
											SearchMain();
											int sqDst = DST(mvResult);
											int pcCaptured = ucpcSquares[sqDst];//�õ�Ŀ�ĸ��ӵ�����
											MakeMove(mvResult, 0);
											
											stack.push(new StackplayChess(mvResult,pcCaptured));//���岽����ջ
											initArrays();//�������											
											if(LoadUtil.IsMate())//�������Ӯ�ˣ�
											{
												LoadUtil.Startup();//��ʼ������
												initArrays();//��ʼ������
												shuJMflag=true;
										    	father.playSound(5,1);//b��������,����										    	
											}else							
											father.playSound(2,1);//b��������,����������
											cMfleg=true;//�������ӣ���ҿ��Բٿ��ˡ�
											playChessflag=false;
											endTime=zTime;
											onDrawcanvas();//�ػ淽��
							        	}
							        }.start();
						        	
						        }
						      } 
					xzflag=false;  
					flag=false;
					onDrawcanvas();//�ػ淽��
				        
			}
				else{
					xzflag=false;
					flag=false;
					onDrawcanvas();//�ػ淽��
				}
				

			}
			 
			return true;
		}

		return super.onTouchEvent(e);
	}
	public void onDrawcanvas()
	{
		try {
        	// �����������������ڴ�Ҫ��Ƚϸߵ�����£����������ҪΪnull
        	canvas = holder.lockCanvas(null);
            synchronized (holder) {
            	onDraw(canvas);//����
            }
        } finally {
            if (canvas != null) {
            	//���ͷ���
            	holder.unlockCanvasAndPost(canvas);
            }
        }
	}
}
