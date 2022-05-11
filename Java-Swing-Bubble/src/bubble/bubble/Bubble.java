package bubble.bubble;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import bubble.frame.BubbleFrame;
import bubble.move.Moveable;
import bubble.player.Player;
import bubble.service.BackgroundBubbleService;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Bubble extends JLabel implements Moveable{
	
	private int x,y; // 캐릭터 이동 좌표
	
	private boolean left,right,up; // 움직임 상태
	
	private BackgroundBubbleService backgroundBubbleService;//물방울의 충돌 상태를 감지하기 위해

	private ImageIcon bubble,bubbled,bomb; // 일반물방울, 적을 가둔 물방울, 물방울터짐
	
	private Player player; // player의 좌표를 얻기위해 생성
	
	private BubbleFrame bubbleFrame; // 모든 자바 프로그램 main 메소드가 존재하는 클래스에는 프로젝트 모든 객체 정보를 얻을 수 있다
	//버블 2초 후 소멸하기 위해서는 프레임 자체를 repaint하는 작업이 필요
	
	//적군을 맞춘 상태
	private int state; // 0(물방울), 1(적을 가둔 물방울)
	
	public Bubble(BubbleFrame bubbleFrame) {//bubbleFrame를 받으면 player 객체에 대한 정보도 얻을 수 있음
		this.bubbleFrame = bubbleFrame;
		this.player = bubbleFrame.getPlayer();//getter를 통해 받는다.
		
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
				break; // 위쪽 벽에 충돌했을 경우 for문을 종료
			}
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}//end while
		clearBubble();
	}//up
	
	private void clearBubble() {
		//while이 종료 후 버블 소멸 작업 실행
		try {
			Thread.sleep(3000);//물방울은 3초간 생존
			setIcon(bomb);//터지는 이미지로 변경
			Thread.sleep(500);//0.5초 간 유지
			bubbleFrame.remove(this);//현재 객체(컴포넌트)를 메모리에서 지운다
			bubbleFrame.repaint();//메모리에 남아있는 객체들을 다시 그린다.
		} catch (InterruptedException e) {
			e.printStackTrace();
		}//end catch
	}//clearBubble
	
}//class
