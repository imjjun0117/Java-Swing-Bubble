package bubble.bubble;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import bubble.player.Moveable;
import bubble.player.Player;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Bubble extends JLabel implements Moveable{
	
	private int x,y; // 캐릭터 이동 좌표
	
	private boolean left,right,up; // 움직임 상태

	private ImageIcon bubble,bubbled,bomb; // 일반물방울, 적을 가둔 물방울, 물방울터짐
	
	private Player player; // player의 좌표를 얻기위해 생성
	
	//적군을 맞춘 상태
	private int state; // 0(물방울), 1(적을 가둔 물방울)
	
	public Bubble(Player player) {
		this.player = player;
		initObject();
		initSetting();
		initThread();
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

	public void initThread() {//물방울은 움직임이 일관(?)되어 스레드를 통합하여 생성
		new Thread(()->{
			if(player.getPlayerWay()==PlayerWay.RIGHT) {
				//플레이어의 방향이 오른쪽일때
				right();
			}else {
				//플레이어의 방향이 왼쪽일때
				left();
			}//end else
			up(); //제한된 거리 이동 후 하늘로 날라간다.
		}).start();
		
	}//initThread
	
	@Override
	public void left() {
		left=true;
		for(int i = 0; i < 400; i++) { //수평으로 날아가는 물방울은 거리제한을 둔다
			x--; // 왼쪽 방향
			setLocation(x, y);
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
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}//end while
	}//up
	
	
}//class
