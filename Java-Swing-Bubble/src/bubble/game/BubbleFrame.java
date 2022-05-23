package bubble.game;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.security.Key;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import bubble.bgm.BGM;
import bubble.game.component.Bubble;
import bubble.game.component.Enemy;
import bubble.game.component.Player;
import bubble.game.over.GameOverFrame;
import lombok.Getter;
import lombok.Setter;

/**
 * ���� ���� ���� ��� ���� �� ĳ���� ����
 */
@SuppressWarnings("serial")
@Getter
@Setter
public class BubbleFrame extends JFrame {

	private JLabel backgroundMap;
	private Player player;
	private List<Enemy> enemies;
	private Enemy enemy;
	private BubbleFrame mContext=this;//���� ��ü ������ Bubble ��ü�� �����ϱ� ���� ����
	
	public BubbleFrame() {
		initSetting();
		initListner();
		new BGM();
		initObject();
		setVisible(true);
	}//BubbleFrame
	
	
	/**
	 * backgroundMap�� �����Ѵ�
	 */
	public void initObject() {
		backgroundMap = new JLabel(new ImageIcon("image/backgroundMap.png"));
		setContentPane(backgroundMap);
		player = new Player(mContext);
		createEnemies();
		add(player);//player�� jframe�� �߰� 
//		enemy = new Enemy(mContext);
//		add(enemy);//���� �߰�
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
	
	public void createEnemies() { // ���� 3���� ���� -> ��ġ �����Ͽ�
		enemies = new ArrayList<>();
		new Thread(()->{
				try {
					for(int i = 0; i < 3; i++) {
						enemies.add(new Enemy(mContext,100+(i*400),178));
						add(enemies.get(i));
					Thread.sleep(2000+(i*1000));
					}//end for
				} catch (InterruptedException e) {
					e.printStackTrace();
				}//end catch
		}).start();
	}//createEnemies
	
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
				case KeyEvent.VK_UP : break; // ���� ����Ű  
				}//end switch
			}//keyReleased
		});
	}//initListner
	
	public static void main(String[] args) {
		new BubbleFrame();
	}//main

}//class
