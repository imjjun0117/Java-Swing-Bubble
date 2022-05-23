package bubble.game.component;

import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import bubble.game.BubbleFrame;
import bubble.game.Moveable;
import bubble.game.service.BackgroundBubbleService;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Bubble extends JLabel implements Moveable{
	
	private int x,y; // ĳ���� �̵� ��ǥ
	
	private boolean left,right,up; // ������ ����
	
	private boolean touched;
	
	private BackgroundBubbleService backgroundBubbleService;//������� �浹 ���¸� �����ϱ� ����

	private ImageIcon bubble,bubbled,bomb; // �Ϲݹ����, ���� ���� �����, ���������
	
	private Player player; // player�� ��ǥ�� ������� ����
	
	private List<Enemy> enemies; // ������� ������  ������ �� �������� ����
	
	private BubbleFrame mContext; // ��� �ڹ� ���α׷� main �޼ҵ尡 �����ϴ� Ŭ�������� ������Ʈ ��� ��ü ������ ���� �� �ִ�
	//���� 2�� �� �Ҹ��ϱ� ���ؼ��� ������ ��ü�� repaint�ϴ� �۾��� �ʿ�
	
	//������ ���� ����
	private int state; // 0(�����), 1(���� ���� �����)
	
	public Bubble(BubbleFrame mContext) {//bubbleFrame�� ������ player ��ü�� ���� ������ ���� �� ����
		this.mContext = mContext;
		this.player = mContext.getPlayer();//getter�� ���� �޴´�.
		this.enemies = mContext.getEnemies();
		
		backgroundBubbleService = new BackgroundBubbleService(this);
		initObject();
		initSetting();
	}//Bubble
	
	public void initObject() {
		bubble = new ImageIcon("image/bubble.png");
		bubbled = new ImageIcon("image/bubbled.png");
		bomb = new ImageIcon("image/bomb.png");
	}//initObject
	public void initSetting() {
		//���� �ʱ�ȭ
		left=false;
		right=false;
		up=false;
		
		//�÷��̾��� ���� ��ǥ�� ����� �߻���ġ�� ���Ѵ�.
		x=player.getX();
		y=player.getY();
		
		setIcon(bubble);
		setBounds(x,y,50,50);
		
		state=0;//�⺻ �����
	}//initSetting

// thread�� player ��ü���� ó�� 
//	public void initThread() {//������� �������� �ϰ�(?)�Ǿ� �����带 �����Ͽ� ����
//		new Thread(()->{
//			if(player.getPlayerWay()==PlayerWay.RIGHT) {
//				//�÷��̾��� ������ �������϶�
//				right();
//			}else {
//				//�÷��̾��� ������ �����϶�
//				left();
//			}//end else
//			up(); //���ѵ� �Ÿ� �̵� �� �ϴ÷� ���󰣴�.
//		}).start();
//		
//	}//initThread
	
	@Override
	public void left() {
		left=true;
		for(int i = 0; i < 400; i++) { //�������� ���ư��� ������� �Ÿ������� �д�
			x--; // ���� ����
			setLocation(x, y);
			if(backgroundBubbleService.leftWall()) {
				break; // ���� ���� �浹���� ��� for���� ����
			}//end if
			for(int j = 0; j < enemies.size(); j++) { // list�� ���� ��� ���� ������ ��´�
			if(Math.abs(x-enemies.get(j).getX())<10 &&(Math.abs(y-enemies.get(j).getY())>0 && Math.abs(y-enemies.get(j).getY())<50)) { // ������ ��ǥ�� ���� ���� ���� �浹���¸� ����
				if(enemies.get(j).getState()==0) { 
				//? ������ �޸𸮿��� �����ص� ������ �÷��Ϳ� �������� ��� ����Ǳ� ������ ������ ����￡ �ȸ��� ��츸 ����
				attackBubble(j);
				break;
				}//end if
			}//end if
			}//end for
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}//end for
	 	
	}//left

	@Override
	public void right() {
		right=true;
		for(int i = 0; i < 400; i++) { //�������� ���ư��� ������� �Ÿ������� �д�
			x++; // ������ ����
			setLocation(x, y);
			if(backgroundBubbleService.rightWall()) {
				break; // ������ ���� �浹���� ��� for���� ����
			}//end if
			for(int j = 0; j < enemies.size(); j++) { // �ټ��� ���� �񱳸� ���� for��
				if(Math.abs(x-enemies.get(j).getX())<10 &&(Math.abs(y-enemies.get(j).getY())>0 && Math.abs(y-enemies.get(j).getY())<50)) { // ������ ��ǥ�� ���� ���� ���� �浹���¸� ����
					if(enemies.get(j).getState()==0) { 
					//? ������ �޸𸮿��� �����ص� ������ �÷��Ϳ� �������� ��� ����Ǳ� ������ ������ ����￡ �ȸ��� ��츸 ����
					attackBubble(j);
					break;
					}//end if
				}//end if
			}//end for
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}//end for
	}//right

	@Override
	public void up() {
		up=true;
		while(up) {//���� ���Ѵ�� ���󰣴�.
			y--;
			setLocation(x, y);
			if(backgroundBubbleService.upWall()) {
				break; // ���� ���� �浹���� ��� while���� ����
			}
			try {
				if(state==1) {//���� ���� ������� ��� ���ſ� ȿ���� ���� sleep�� ��� �ش�
					Thread.sleep(10);
				}else {
					Thread.sleep(1);//�⺻ ����
				}//end else
			} catch (InterruptedException e) {
				e.printStackTrace();
			}//end catch
		}//end while
		if(state==0) {
			//���� ������ ���� ���¿����� �Ҹ�
			clearBubble();
		}else {
			touchBubble();
		}//end else
		
	}//up
	
	private void clearBubble() {
		//while�� ���� �� ���� �Ҹ� �۾� ����
		try {
			Thread.sleep(3000);//������� 3�ʰ� ����
			setIcon(bomb);//������ �̹����� ����
			Thread.sleep(500);//0.5�� �� ����
			mContext.remove(this);//���� ��ü(������Ʈ)�� �޸𸮿��� �����
			mContext.repaint();//�޸𸮿� �����ִ� ��ü���� �ٽ� �׸���.
		} catch (InterruptedException e) {
			e.printStackTrace();
		}//end catch
	}//clearBubble
	
	/**
	 * ������ ������� �¾����� �̹��� ���� 
	 */
	public void attackBubble(int enemyIdx) { // ������� ���� ������ �ε����� �޾ƿ� �޼ҵ� ó��
		state=1; // ���� ���� ����
		enemies.get(enemyIdx).setState(1);//���� ����� ���� ����
		setIcon(bubbled); //���� ���� �̹���
		mContext.remove(enemies.get(enemyIdx)); // �� �Ҹ�
		mContext.repaint(); // ��������
	}//attackBubble
	
	
	public void touchBubble() {//��ǳ�� ��ġ �� ���� ->syso�� ���� ����ȵ�..�ذ�
		touched = true;
		while(touched) {
			try {
				if(Math.abs(player.getX()-x)<20 && Math.abs(player.getY()-y)>0 && Math.abs(player.getY()-y)<60) {
					setIcon(bomb);
						Thread.sleep(500);
						mContext.remove(this);
						mContext.repaint();
						touched =false;
				}//end if
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}//end catch
		}//end while
	}//touchBubble
}//class
