package bubble.player;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import bubble.bubble.Bubble;
import bubble.frame.BubbleFrame;
import bubble.move.Moveable;
import bubble.service.BackgroundPlayerService;
import lombok.Getter;
import lombok.Setter;

/**
 * ���� ĳ���� class
 */
@Getter
@Setter
@SuppressWarnings("serial")
public class Player extends JLabel implements Moveable{

	private final int SPEED = 4; // �÷��̾� �ӵ� ����
	private final int JUMP_SPEED=4; // ���� ���ǵ�
	
	private BubbleFrame mContext;// ����� ��ü ������ ���� ����
	
	private int x,y; // ĳ���� �̵� ��ǥ
	private ImageIcon playerR,playerL; // ĳ���� ��,�� �̹���
	private boolean left,right,up,down; // ������ ����
	
	private boolean leftWallCrash; // ���� ���� �浹�� ����
	private boolean rightWallCrash; // ������ ���� �浹�� ����
	
	private PlayerWay playerWay;//player�� ������ ������� ����
	
	
	public Player(BubbleFrame mContext) { // Bubble�� ������ ��� ���� mContext�� �޴´�.
		this.mContext = mContext; 
		initObject();
		initSetting();
		initBackgroundPlayerService();
	}//Player
	
	/**
	 * �÷��̾ ������� �߻��ϴ� ���� �ϴ� method 
	 */
	@Override
	public void attack() {
		new Thread(()->{
			Bubble bubble = new Bubble(mContext);
			mContext.add(bubble);
			if(playerWay == PlayerWay.LEFT) { // �÷��̾ ������ �� ������ ���
				bubble.left();// �������� �߻�!
			}else { // �÷��̾ �������� �� ����
				bubble.right();//������ �߻�!
			}//end else
			bubble.up();
		}).start();
	}//attack
	
	@Override
	public void left() {// x�� -10��ŭ �̵� ���� �̹��� ����(�̺�Ʈ �ڵ鷯)
		//System.out.println("left thread ����");
		playerWay = PlayerWay.LEFT;//���⼳��
		left=true;
		new Thread(()->{
			while(left) {//���� ������ �ܺ����� ���Ѵ�� ������ �� ����
			x-=SPEED;
			setIcon(playerL);
			setLocation(x,y);
			try {
				Thread.sleep(8);// 0.008�ʷ� 
			} catch (InterruptedException e) {
				e.printStackTrace();
			}//end catch 
			}//end while
		}).start();
	}//left

	@Override
	public void right() {// x�� +10��ŭ �̵� ������ �̹��� ����(�̺�Ʈ �ڵ鷯)  
		//System.out.println("right thread ����");
		playerWay = PlayerWay.RIGHT;//���⼳��
		right = true;
		new Thread(()->{
			while(right) { // ������ ������ �ܺ����� ���Ѵ�� ������ �� ����
				x+=SPEED;
				setIcon(playerR);
				setLocation(x,y);
				try {//�̵��ӵ�(����)�� ���߱� ���� sleep
					Thread.sleep(8);
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
		x=80;
		y=535;//���� ��ġ ���� �Ʒ�
		setIcon(playerR);// ���� ĳ���� �̹��� 
		setBounds(x,y,50,50);
	
	}//initSetting
	
	public void initObject() {
		playerR = new ImageIcon("image/playerR.png");
		playerL = new ImageIcon("image/playerL.png");
	}//initObject
	
	private void initBackgroundPlayerService() {
		new Thread(new BackgroundPlayerService(this)).start();
	}//initBackgroundPlayerService


	
	
}//class
