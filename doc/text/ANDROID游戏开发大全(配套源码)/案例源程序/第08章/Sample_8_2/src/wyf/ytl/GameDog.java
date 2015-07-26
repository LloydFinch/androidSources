package wyf.ytl;
public class GameDog {
	Sample_8_2 activity = null;//activity������
	private DogState currentState=DogState.COMMON_STATE;//���ó���ĳ�ʼ��ǰ״̬Ϊ��ͨ״̬
	public GameDog(Sample_8_2 activity){//������
		this.activity = activity;
	}
	public boolean updateState(MasterAction ma){//��������������״̬�ķ���
		boolean result=true;
		switch(currentState){
			case HAPPY_STATE://��ǰΪ����״̬
			    switch(ma){
			    	case ALONE_ACTION://����ָ��ʱ�����˴���
			    	   currentState=DogState.COMMON_STATE;//�л�״̬
			    	   activity.myImageView.setImageResource(R.drawable.common);//��ͼ
			    	   activity.myTextView.setText("״̬����ͨ");
			    	break;
			    	default:
			    	   result=false;//����false��ʾ״̬�л�����
			    }
			break;
			case COMMON_STATE://��ǰΪ��ͨ״̬
			    switch(ma){
			    	case ALONE_ACTION://����ָ��ʱ�����˴���
			    	   currentState=DogState.AWAY_STATE;//�л�״̬
			    	   activity.myImageView.setImageResource(R.drawable.away);//��ͼ
			    	   activity.myTextView.setText("״̬������");
			    	break;
			    	case BATH_ACTION:  //ϴ��
			    	case ENGAGE_ACTION://��Ū
			    	   currentState=DogState.HAPPY_STATE;//�л�״̬
			    	   activity.myImageView.setImageResource(R.drawable.happy);//��ͼ
			    	   activity.myTextView.setText("״̬������");
			    	break;
			    	default:
			    	   result=false;//����false��ʾ״̬�л�����
			    }			
			break;
			case AWAY_STATE://��ǰΪ����״̬
			    switch(ma){
			    	case FIND_ACTION://Ѱ��
			    	   currentState=DogState.COMMON_STATE;//�л�״̬
			    	   activity.myImageView.setImageResource(R.drawable.common);//��ͼ
			    	   activity.myTextView.setText("״̬����ͨ");
			    	break;
			    	default:
			    	   result=false;//����false��ʾ״̬�л�����
			    }			
			break;
		}
		return result;//����true��ʾ״̬�л��ɹ�
	}
}