package bubble.enemy;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import bubble.bubble.Bubble;
import bubble.frame.BubbleFrame;
import bubble.move.Moveable;
import bubble.player.PlayerWay;
import bubble.service.BackgroundEnemyService;
import bubble.service.BackgroundPlayerService;
import lombok.Getter;
import lombok.Setter;

/**
 * ���� ĳ���� class
 */
@Getter
@Setter
@SuppressWarnings("serial")
public class Enemy extends JLabel implements Moveable{

	private final int SPEED = 4; // ���� �ӵ� ����
	private final int JUMP_SPEED=4; // ���� ���ǵ�
	
	private BubbleFrame mContext;// ����� ��ü ������ ���� ����
	
	private int state; // ������ ���� 0(�⺻) 1(����� ���� ����)
	
	private int x,y; // ĳ���� �̵� ��ǥ
	private ImageIcon enemyR,enemyL; // ĳ���� ��,�� �̹���
	private boolean left,right,up,down; // ������ ����
	
	private boolean leftWallCrash; // ���� ���� �浹�� ����
	private boolean rightWallCrash; // ������ ���� �浹�� ����
	
	private EnemyWay enemyWay;//������ ������ ������� ����
	
	
	public Enemy(BubbleFrame mContext) { // Bubble�� ������ ��� ���� mContext�� �޴´�.
		this.mContext = mContext; 
		initObject();
		initSetting();
		initBackgroundEnemyService();
		right();
	}//Player
	
	
	@Override
	public void left() {// x�� -10��ŭ �̵� ���� �̹��� ����(�̺�Ʈ �ڵ鷯)
		//System.out.println("left thread ����");
		enemyWay = EnemyWay.LEFT;//���⼳��
		left=true;
		new Thread(()->{
			while(left) {//���� ������ �ܺ����� ���Ѵ�� ������ �� ����
			x-=SPEED;
			setIcon(enemyL);
			setLocation(x,y);
			try {
				Thread.sleep(15);// 0.012�ʷ� 
			} catch (InterruptedException e) {
				e.printStackTrace();
			}//end catch 
			}//end while
		}).start();
	}//left

	@Override
	public void right() {// x�� +10��ŭ �̵� ������ �̹��� ����(�̺�Ʈ �ڵ鷯)  
		//System.out.println("right thread ����");
		enemyWay = EnemyWay.RIGHT;//���⼳��
		right = true;
		new Thread(()->{
			while(right) { // ������ ������ �ܺ����� ���Ѵ�� ������ �� ����
				x+=SPEED;
				setIcon(enemyR);
				setLocation(x,y);
				try {//�̵��ӵ�(����)�� ���߱� ���� sleep
					Thread.sleep(15);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}//end catch
			}//end while
		}).start();
	}//right

	@Override
	public void up() {
		//System.out.println("up");
		up=true;
		new Thread(()->{
			//������ ���Ѵ�� �̵��� �� ���� for���� �̿�
			for(int i = 0; i < 130/JUMP_SPEED; i++) { //���� ���̸� ���߱� ���� JUMP_SPEED�� �����ش�
				y-=JUMP_SPEED; // ���� �ӵ�
				System.out.println(y);
				setLocation(x,y);
				try {
					Thread.sleep(8);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}//end catch
			}//end for
			up=false;
			down();
		}).start();
	}//up

	@Override
	public void down() {
		//System.out.println("down");
		down=true;
		new Thread(()->{
			//������ �ϰ� �ϰ�
			while(down) {// �������� �����ϱ� ���� for���� �ƴ� while���� ��� 
				y+=JUMP_SPEED; // �ϰ� �ӵ�
				setLocation(x,y);
				try {
					Thread.sleep(8);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}//end catch
			}//end while
			down=false;
		}).start();
	}//down

	public void initSetting() {
		x = 480;
		y = 178;//���� ��ġ 
		setIcon(enemyR);// ���� ĳ���� �̹��� 
		setBounds(x,y,50,50);
	
	}//initSetting
	
	public void initObject() {
		enemyR = new ImageIcon("image/enemyR.png");
		enemyL = new ImageIcon("image/enemyL.png");
	}//initObject
	
	private void initBackgroundEnemyService() {
		new Thread(new BackgroundEnemyService(this)).start();
	}//initBackgroundEnemyService


	
	
}//class
