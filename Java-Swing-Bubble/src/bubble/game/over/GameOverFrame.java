package bubble.game.over;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import bubble.game.BubbleFrame;
import bubble.game.component.Player;

public class GameOverFrame extends JLabel {

	private BubbleFrame mContext;
	private Player player; //플레이어가 죽었을 때 GameOver라벨을 띄운다
	private ImageIcon gameOver;
	
	public GameOverFrame(BubbleFrame mContext) {
		this.mContext = mContext;
		 initObject();
		 initSetting();
		
	}//GameOverFrame
	
	public void initSetting() {
		setIcon(gameOver);
		setBounds(340,-90,300,300);
		new Thread(()->{
			for(int i = -90; i < 0; i++) {
				setLocation(340,i);
				try {
					Thread.sleep(5);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}//end for
		}).start();
	}
	
	public void initObject() {
		gameOver = new ImageIcon("image/game_over.png");
	}
}//class
