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
 * ���� ���� ���� ��� ���� �� ĳ���� ����
 */
@SuppressWarnings("serial")
@Getter
@Setter
public class BubbleFrame extends JFrame {

	JLabel backgroundMap;
	Player player;
	BubbleFrame mContext=this;//���� ��ü ������ Bubble ��ü�� �����ϱ� ���� ����
	
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
		player = new Player(mContext);
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
			public void keyPressed(KeyEvent e) {//Ű���� Ŭ������ ���� ����
				switch(e.getKeyCode()) {
				case KeyEvent.VK_LEFT : if(!player.isLeft() && !player.isLeftWallCrash()) player.left(); break; // ���� ����Ű -> x�� -10��ŭ �̵� ���� ĳ�����̹��� ����
				case KeyEvent.VK_RIGHT :if(!player.isRight() && !player.isRightWallCrash())player.right(); break; // ������ ����Ű -> x�� +10��ŭ �̵� ������ ĳ�����̹��� ����
				case KeyEvent.VK_UP : if(!player.isUp()&&!player.isDown())player.up(); break; // ���� ����Ű
				case KeyEvent.VK_SPACE: 
//					Bubble bubble = new Bubble(mContext);//���� ��ü ������ �Ѱ��� --> ������ �������谡 player�̹Ƿ�
					//BubbleFrame���� �����ϴ� ���� ���� ���� --> Player ��ü�� �����丵
//					add(bubble);
					player.attack();
					break; // �����̽��ٸ� ���� ��� ����� �߰�
				}//end switch
			}//keyPressed

			@Override
			public void keyReleased(KeyEvent e) {//Ű���� ������ �� ����
				switch(e.getKeyCode()) {
				case KeyEvent.VK_LEFT : player.setLeft(false); break; // ���� ����Ű -> x�� -10��ŭ �̵� ���� ĳ�����̹��� ����
				case KeyEvent.VK_RIGHT :player.setRight(false); break; // ������ ����Ű -> x�� +10��ŭ �̵� ������ ĳ�����̹��� ����
				case KeyEvent.VK_UP : player.setUp(false); break; // ���� ����Ű  
				}//end switch
			}//keyReleased
		});
	}//initListner
	
	public static void main(String[] args) {
		new BubbleFrame();
	}//main

}//class
