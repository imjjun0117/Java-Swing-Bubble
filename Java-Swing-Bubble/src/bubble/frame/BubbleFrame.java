package bubble.frame;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.security.Key;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import bubble.player.Player;

/**
 * 버블 버블 게임 배경 설정 및 캐릭터 설정
 * @author imjju
 */
@SuppressWarnings("serial")
public class BubbleFrame extends JFrame {

	JLabel backgroundMap;
	Player player;
	
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
		player = new Player();
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
			public void keyPressed(KeyEvent e) {
				switch(e.getKeyCode()) {
				case KeyEvent.VK_LEFT : player.left(); break; // 왼쪽 방향키 -> x축 -10만큼 이동 왼쪽 캐릭터이미지 설정
				case KeyEvent.VK_RIGHT : player.right(); break; // 오른쪽 방향키 -> x축 +10만큼 이동 오른쪽 캐릭터이미지 설정
				}//end switch
			}//keyPressed
		});
	}//initListner
	
	public static void main(String[] args) {
		new BubbleFrame();
	}//main

}//class
