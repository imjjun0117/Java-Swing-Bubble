package bubble.frame;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.security.Key;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import bubble.player.Player;

/**
 * ���� ���� ���� ��� ���� �� ĳ���� ����
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
	 * backgroundMap�� �����Ѵ�
	 */
	public void initObject() {
		backgroundMap = new JLabel(new ImageIcon("image/backgroundMap.png"));
		setContentPane(backgroundMap);
		player = new Player();
		add(player);//player�� jframe�� �߰� 
	}//initObject
	
	/**
	 * JFrame�� �����Ѵ�
	 */
	public void initSetting() {
		setSize(1000,640);
		setLayout(null);
		setLocationRelativeTo(null); //jframe�� ��� ��ġ
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}//initSetting
	
	public void initListner() {
		addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				switch(e.getKeyCode()) {
				case KeyEvent.VK_LEFT : player.left(); break; // ���� ����Ű -> x�� -10��ŭ �̵� ���� ĳ�����̹��� ����
				case KeyEvent.VK_RIGHT : player.right(); break; // ������ ����Ű -> x�� +10��ŭ �̵� ������ ĳ�����̹��� ����
				}//end switch
			}//keyPressed
		});
	}//initListner
	
	public static void main(String[] args) {
		new BubbleFrame();
	}//main

}//class
