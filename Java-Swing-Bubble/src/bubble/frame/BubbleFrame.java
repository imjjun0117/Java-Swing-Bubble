package bubble.frame;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.security.Key;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import bubble.bubble.Bubble;
import bubble.player.Player;
import lombok.Getter;
import lombok.Setter;

/**
 * 버블 버블 게임 배경 설정 및 캐릭터 설정
 */
@SuppressWarnings("serial")
@Getter
@Setter
public class BubbleFrame extends JFrame {

	JLabel backgroundMap;
	Player player;
	BubbleFrame mContext=this;//현재 객체 정보를 Bubble 객체로 전달하기 위해 선언
	
	public BubbleFrame() {
		initSetting();
		initObject();
		initListner();
		setVisible(true);
	}//BubbleFrame
	
	
	/**
	 * backgroundMap을 설정한다
	 */
	public void initObject() {
		backgroundMap = new JLabel(new ImageIcon("image/backgroundMap.png"));
		setContentPane(backgroundMap);
		player = new Player(mContext);
		add(player);//player를 jframe에 추가 
	}//initObject
	
	/**
	 * JFrame을 설정한다
	 */
	public void initSetting() {
		setSize(1000,640);
		setLayout(null);
		setLocationRelativeTo(null); //jframe을 가운데 배치
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}//initSetting
	
	public void initListner() {
		addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {//키보드 클릭했을 때의 동작
				switch(e.getKeyCode()) {
				case KeyEvent.VK_LEFT : if(!player.isLeft() && !player.isLeftWallCrash()) player.left(); break; // 왼쪽 방향키 -> x축 -10만큼 이동 왼쪽 캐릭터이미지 설정
				case KeyEvent.VK_RIGHT :if(!player.isRight() && !player.isRightWallCrash())player.right(); break; // 오른쪽 방향키 -> x축 +10만큼 이동 오른쪽 캐릭터이미지 설정
				case KeyEvent.VK_UP : if(!player.isUp()&&!player.isDown())player.up(); break; // 윗쪽 방향키
				case KeyEvent.VK_SPACE: 
//					Bubble bubble = new Bubble(mContext);//현재 객체 정보를 넘겨줌 --> 물방울과 의존관계가 player이므로
					//BubbleFrame에서 정의하는 것은 좋지 않음 --> Player 객체로 리팩토링
//					add(bubble);
					player.attack();
					break; // 스페이스바를 누를 경우 물방울 추가
				}//end switch
			}//keyPressed

			@Override
			public void keyReleased(KeyEvent e) {//키보드 떼었을 때 동작
				switch(e.getKeyCode()) {
				case KeyEvent.VK_LEFT : player.setLeft(false); break; // 왼쪽 방향키 -> x축 -10만큼 이동 왼쪽 캐릭터이미지 설정
				case KeyEvent.VK_RIGHT :player.setRight(false); break; // 오른쪽 방향키 -> x축 +10만큼 이동 오른쪽 캐릭터이미지 설정
				case KeyEvent.VK_UP : player.setUp(false); break; // 윗쪽 방향키  
				}//end switch
			}//keyReleased
		});
	}//initListner
	
	public static void main(String[] args) {
		new BubbleFrame();
	}//main

}//class
