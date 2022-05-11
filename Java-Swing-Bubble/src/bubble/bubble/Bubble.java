package bubble.bubble;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import bubble.frame.BubbleFrame;
import bubble.move.Moveable;
import bubble.player.Player;
import bubble.service.BackgroundBubbleService;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Bubble extends JLabel implements Moveable{
	
	private int x,y; // ĳ���� �̵� ��ǥ
	
	private boolean left,right,up; // ������ ����
	
	private BackgroundBubbleService backgroundBubbleService;//������� �浹 ���¸� �����ϱ� ����

	private ImageIcon bubble,bubbled,bomb; // �Ϲݹ����, ���� ���� �����, ���������
	
	private Player player; // player�� ��ǥ�� ������� ����
	
	private BubbleFrame bubbleFrame; // ��� �ڹ� ���α׷� main �޼ҵ尡 �����ϴ� Ŭ�������� ������Ʈ ��� ��ü ������ ���� �� �ִ�
	//���� 2�� �� �Ҹ��ϱ� ���ؼ��� ������ ��ü�� repaint�ϴ� �۾��� �ʿ�
	
	//������ ���� ����
	private int state; // 0(�����), 1(���� ���� �����)
	
	public Bubble(BubbleFrame bubbleFrame) {//bubbleFrame�� ������ player ��ü�� ���� ������ ���� �� ����
		this.bubbleFrame = bubbleFrame;
		this.player = bubbleFrame.getPlayer();//getter�� ���� �޴´�.
		
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
				break; // ���� ���� �浹���� ��� for���� ����
			}
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}//end while
		clearBubble();
	}//up
	
	private void clearBubble() {
		//while�� ���� �� ���� �Ҹ� �۾� ����
		try {
			Thread.sleep(3000);//������� 3�ʰ� ����
			setIcon(bomb);//������ �̹����� ����
			Thread.sleep(500);//0.5�� �� ����
			bubbleFrame.remove(this);//���� ��ü(������Ʈ)�� �޸𸮿��� �����
			bubbleFrame.repaint();//�޸𸮿� �����ִ� ��ü���� �ٽ� �׸���.
		} catch (InterruptedException e) {
			e.printStackTrace();
		}//end catch
	}//clearBubble
	
}//class
