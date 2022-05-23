package bubble.game.component;

import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import bubble.game.BubbleFrame;
import bubble.game.Moveable;
import bubble.game.service.BackgroundBubbleService;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Bubble extends JLabel implements Moveable{
	
	private int x,y; // 캐릭터 이동 좌표
	
	private boolean left,right,up; // 움직임 상태
	
	private boolean touched;
	
	private BackgroundBubbleService backgroundBubbleService;//물방울의 충돌 상태를 감지하기 위해

	private ImageIcon bubble,bubbled,bomb; // 일반물방울, 적을 가둔 물방울, 물방울터짐
	
	private Player player; // player의 좌표를 얻기위해 생성
	
	private List<Enemy> enemies; // 물방울이 적군의  정보를 얻어서 의존관계 성립
	
	private BubbleFrame mContext; // 모든 자바 프로그램 main 메소드가 존재하는 클래스에는 프로젝트 모든 객체 정보를 얻을 수 있다
	//버블 2초 후 소멸하기 위해서는 프레임 자체를 repaint하는 작업이 필요
	
	//적군을 맞춘 상태
	private int state; // 0(물방울), 1(적을 가둔 물방울)
	
	public Bubble(BubbleFrame mContext) {//bubbleFrame를 받으면 player 객체에 대한 정보도 얻을 수 있음
		this.mContext = mContext;
		this.player = mContext.getPlayer();//getter를 통해 받는다.
		this.enemies = mContext.getEnemies();
		
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
		//상태 초기화
		left=false;
		right=false;
		up=false;
		
		//플레이어의 현재 좌표로 물방울 발사위치를 정한다.
		x=player.getX();
		y=player.getY();
		
		setIcon(bubble);
		setBounds(x,y,50,50);
		
		state=0;//기본 물방울
	}//initSetting

// thread는 player 객체에서 처리 
//	public void initThread() {//물방울은 움직임이 일관(?)되어 스레드를 통합하여 생성
//		new Thread(()->{
//			if(player.getPlayerWay()==PlayerWay.RIGHT) {
//				//플레이어의 방향이 오른쪽일때
//				right();
//			}else {
//				//플레이어의 방향이 왼쪽일때
//				left();
//			}//end else
//			up(); //제한된 거리 이동 후 하늘로 날라간다.
//		}).start();
//		
//	}//initThread
	
	@Override
	public void left() {
		left=true;
		for(int i = 0; i < 400; i++) { //수평으로 날아가는 물방울은 거리제한을 둔다
			x--; // 왼쪽 방향
			setLocation(x, y);
			if(backgroundBubbleService.leftWall()) {
				break; // 왼쪽 벽에 충돌했을 경우 for문을 종료
			}//end if
			for(int j = 0; j < enemies.size(); j++) { // list를 통해 모든 적군 정보를 얻는다
			if(Math.abs(x-enemies.get(j).getX())<10 &&(Math.abs(y-enemies.get(j).getY())>0 && Math.abs(y-enemies.get(j).getY())<50)) { // 적군의 좌표를 얻어와 차를 통해 충돌상태를 얻어낸다
				if(enemies.get(j).getState()==0) { 
				//? 적군을 메모리에서 삭제해도 가비지 컬렉터에 남아있을 경우 실행되기 때문에 적군이 물방울에 안맞은 경우만 실행
				attackBubble(j);
				break;
				}//end if
			}//end if
			}//end for
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
		for(int i = 0; i < 400; i++) { //수평으로 날아가는 물방울은 거리제한을 둔다
			x++; // 오른쪽 방향
			setLocation(x, y);
			if(backgroundBubbleService.rightWall()) {
				break; // 오른쪽 벽에 충돌했을 경우 for문을 종료
			}//end if
			for(int j = 0; j < enemies.size(); j++) { // 다수의 적군 비교를 위한 for문
				if(Math.abs(x-enemies.get(j).getX())<10 &&(Math.abs(y-enemies.get(j).getY())>0 && Math.abs(y-enemies.get(j).getY())<50)) { // 적군의 좌표를 얻어와 차를 통해 충돌상태를 얻어낸다
					if(enemies.get(j).getState()==0) { 
					//? 적군을 메모리에서 삭제해도 가비지 컬렉터에 남아있을 경우 실행되기 때문에 적군이 물방울에 안맞은 경우만 실행
					attackBubble(j);
					break;
					}//end if
				}//end if
			}//end for
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
		while(up) {//위로 무한대로 날라간다.
			y--;
			setLocation(x, y);
			if(backgroundBubbleService.upWall()) {
				break; // 위쪽 벽에 충돌했을 경우 while문을 종료
			}
			try {
				if(state==1) {//적을 가둔 물방울일 경우 무거운 효과를 위해 sleep을 길게 준다
					Thread.sleep(10);
				}else {
					Thread.sleep(1);//기본 상태
				}//end else
			} catch (InterruptedException e) {
				e.printStackTrace();
			}//end catch
		}//end while
		if(state==0) {
			//적을 가두지 않은 상태에서만 소멸
			clearBubble();
		}else {
			touchBubble();
		}//end else
		
	}//up
	
	private void clearBubble() {
		//while이 종료 후 버블 소멸 작업 실행
		try {
			Thread.sleep(3000);//물방울은 3초간 생존
			setIcon(bomb);//터지는 이미지로 변경
			Thread.sleep(500);//0.5초 간 유지
			mContext.remove(this);//현재 객체(컴포넌트)를 메모리에서 지운다
			mContext.repaint();//메모리에 남아있는 객체들을 다시 그린다.
		} catch (InterruptedException e) {
			e.printStackTrace();
		}//end catch
	}//clearBubble
	
	/**
	 * 적군이 물방울을 맞았을때 이미지 변동 
	 */
	public void attackBubble(int enemyIdx) { // 물방울을 맞은 적군의 인덱스를 받아와 메소드 처리
		state=1; // 적을 가둔 상태
		enemies.get(enemyIdx).setState(1);//적군 물방울 맞은 상태
		setIcon(bubbled); //적을 가둔 이미지
		mContext.remove(enemies.get(enemyIdx)); // 적 소멸
		mContext.repaint(); // 리페인팅
	}//attackBubble
	
	
	public void touchBubble() {//물풍선 터치 시 터짐 ->syso를 빼면 실행안됨..해결
		touched = true;
		while(touched) {
			try {
				if(Math.abs(player.getX()-x)<20 && Math.abs(player.getY()-y)>0 && Math.abs(player.getY()-y)<60) {
					setIcon(bomb);
						Thread.sleep(500);
						mContext.remove(this);
						mContext.repaint();
						touched =false;
				}//end if
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}//end catch
		}//end while
	}//touchBubble
}//class
