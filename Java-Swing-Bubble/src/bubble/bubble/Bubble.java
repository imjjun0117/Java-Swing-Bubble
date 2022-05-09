package bubble.bubble;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import bubble.player.Moveable;
import bubble.player.Player;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Bubble extends JLabel implements Moveable{
	
	private int x,y; // ĳ���� �̵� ��ǥ
	
	private boolean left,right,up; // ������ ����

	private ImageIcon bubble,bubbled,bomb; // �Ϲݹ����, ���� ���� �����, ���������
	
	private Player player; // player�� ��ǥ�� ������� ����
	
	//������ ���� ����
	private int state; // 0(�����), 1(���� ���� �����)
	
	public Bubble(Player player) {
		this.player = player;
		initObject();
		initSetting();
		initThread();
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

	public void initThread() {//������� �������� �ϰ�(?)�Ǿ� �����带 �����Ͽ� ����
		new Thread(()->{
			if(player.getPlayerWay()==PlayerWay.RIGHT) {
				//�÷��̾��� ������ �������϶�
				right();
			}else {
				//�÷��̾��� ������ �����϶�
				left();
			}//end else
			up(); //���ѵ� �Ÿ� �̵� �� �ϴ÷� ���󰣴�.
		}).start();
		
	}//initThread
	
	@Override
	public void left() {
		left=true;
		for(int i = 0; i < 400; i++) { //�������� ���ư��� ������� �Ÿ������� �д�
			x--; // ���� ����
			setLocation(x, y);
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
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}//end while
	}//up
	
	
}//class
