package bubble.player;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 * ���� ĳ���� class
 * 
 */
@SuppressWarnings("serial")
public class Player extends JLabel implements Moveable{

	private int x,y; // ĳ���� �̵� ��ǥ
	private ImageIcon playerR,playerL; // ĳ���� ��,�� �̹���
	private boolean left,right,up,down;
	
	public Player() {
		initObject();
		initSetting();
		setVisible(true);
	}//Player
	
	@Override
	public void left() {// x�� -10��ŭ �̵� ���� �̹��� ����
		x-=10;
		setIcon(playerL);
		setLocation(x,y);
	}//left

	@Override
	public void right() {// x�� +10��ŭ �̵� ������ �̹��� ����
		x+=10;
		setIcon(playerR);
		setLocation(x,y);
	}//right

	@Override
	public void up() {
	}//up

	@Override
	public void down() {
	}//down

	public void initSetting() {
		x=55;
		y=535;//���� ��ġ ���� �Ʒ�
		setBounds(x,y,50,50);
		setIcon(playerR);// ���� ĳ���� �̹��� 
	
	}//initSetting
	
	public void initObject() {
		playerR = new ImageIcon("image/playerR.png");
		playerL = new ImageIcon("image/playerL.png");
	}//initObject
	
}//class
