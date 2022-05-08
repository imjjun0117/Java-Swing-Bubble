package bubble.player;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 * 게임 캐릭터 class
 * 
 */
@SuppressWarnings("serial")
public class Player extends JLabel implements Moveable{

	private int x,y; // 캐릭터 이동 좌표
	private ImageIcon playerR,playerL; // 캐릭터 좌,우 이미지
	private boolean left,right,up,down;
	
	public Player() {
		initObject();
		initSetting();
		setVisible(true);
	}//Player
	
	@Override
	public void left() {// x축 -10만큼 이동 왼쪽 이미지 설정
		x-=10;
		setIcon(playerL);
		setLocation(x,y);
	}//left

	@Override
	public void right() {// x축 +10만큼 이동 오른쪽 이미지 설정
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
		y=535;//시작 위치 왼쪽 아래
		setBounds(x,y,50,50);
		setIcon(playerR);// 우측 캐릭터 이미지 
	
	}//initSetting
	
	public void initObject() {
		playerR = new ImageIcon("image/playerR.png");
		playerL = new ImageIcon("image/playerL.png");
	}//initObject
	
}//class
