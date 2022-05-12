package bubble.enemy;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import bubble.bubble.Bubble;
import bubble.frame.BubbleFrame;
import bubble.move.Moveable;
import bubble.player.PlayerWay;
import bubble.service.BackgroundEnemyService;
import bubble.service.BackgroundPlayerService;
import lombok.Getter;
import lombok.Setter;

/**
 * 적군 캐릭터 class
 */
@Getter
@Setter
@SuppressWarnings("serial")
public class Enemy extends JLabel implements Moveable{

	private final int SPEED = 4; // 적군 속도 상태
	private final int JUMP_SPEED=4; // 점프 스피드
	
	private BubbleFrame mContext;// 물방울 객체 생성을 위해 선언
	
	private int state; // 적군의 상태 0(기본) 1(물방울 맞은 상태)
	
	private int x,y; // 캐릭터 이동 좌표
	private ImageIcon enemyR,enemyL; // 캐릭터 좌,우 이미지
	private boolean left,right,up,down; // 움직임 상태
	
	private boolean leftWallCrash; // 왼쪽 벽에 충돌한 상태
	private boolean rightWallCrash; // 오른쪽 벽에 충돌한 상태
	
	private EnemyWay enemyWay;//적군의 방향을 얻기위한 변수
	
	
	public Enemy(BubbleFrame mContext) { // Bubble의 정보를 얻기 위해 mContext를 받는다.
		this.mContext = mContext; 
		initObject();
		initSetting();
		initBackgroundEnemyService();
		right();
	}//Player
	
	
	@Override
	public void left() {// x축 -10만큼 이동 왼쪽 이미지 설정(이벤트 핸들러)
		//System.out.println("left thread 생성");
		enemyWay = EnemyWay.LEFT;//방향설정
		left=true;
		new Thread(()->{
			while(left) {//왼쪽 방향은 외벽까지 무한대로 움직일 수 있음
			x-=SPEED;
			setIcon(enemyL);
			setLocation(x,y);
			try {
				Thread.sleep(15);// 0.012초로 
			} catch (InterruptedException e) {
				e.printStackTrace();
			}//end catch 
			}//end while
		}).start();
	}//left

	@Override
	public void right() {// x축 +10만큼 이동 오른쪽 이미지 설정(이벤트 핸들러)  
		//System.out.println("right thread 생성");
		enemyWay = EnemyWay.RIGHT;//방향설정
		right = true;
		new Thread(()->{
			while(right) { // 오른쪽 방향은 외벽까지 무한대로 움직일 수 있음
				x+=SPEED;
				setIcon(enemyR);
				setLocation(x,y);
				try {//이동속도(연산)를 늦추기 위한 sleep
					Thread.sleep(15);
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
			//점프는 무한대로 이동할 수 없어 for문을 이용
			for(int i = 0; i < 130/JUMP_SPEED; i++) { //점프 높이를 맞추기 위해 JUMP_SPEED를 나눠준다
				y-=JUMP_SPEED; // 점프 속도
				System.out.println(y);
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
			//점프를 하고 하강
			while(down) {// 떨어짐을 제어하기 위해 for문이 아닌 while문을 사용 
				y+=JUMP_SPEED; // 하강 속도
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
		x = 480;
		y = 178;//시작 위치 
		setIcon(enemyR);// 우측 캐릭터 이미지 
		setBounds(x,y,50,50);
	
	}//initSetting
	
	public void initObject() {
		enemyR = new ImageIcon("image/enemyR.png");
		enemyL = new ImageIcon("image/enemyL.png");
	}//initObject
	
	private void initBackgroundEnemyService() {
		new Thread(new BackgroundEnemyService(this)).start();
	}//initBackgroundEnemyService


	
	
}//class
