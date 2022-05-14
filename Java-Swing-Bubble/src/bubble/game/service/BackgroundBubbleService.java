package bubble.game.service;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import bubble.game.component.Bubble;
import bubble.game.component.Player;

/**
 * 물방울의 백그라운드 충돌 감지 클래스(색을 통해서)
 * !스레드를 사용하지 않고 상태만 변경해준다!
 */
public class BackgroundBubbleService{

	private BufferedImage image;
	private Bubble bubble;
	
	
	public BackgroundBubbleService(Bubble bubble) {
		this.bubble = bubble;
		try {
			image = ImageIO.read(new File("image/backgroundMapService.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}//end catch
		
	}//BackgroundPlayerService
	
	public boolean leftWall() {
		boolean leftFlag = false;//왼쪽 벽에 충돌했을 경우 상태
		Color leftColor = new Color(image.getRGB(bubble.getX()-10, bubble.getY()+25));
		if(leftColor.getRed()==255 && leftColor.getGreen()==0 && leftColor.getBlue()==0) {
			leftFlag = true;
		}
		return leftFlag;
	}//leftWall
	
	public boolean rightWall() {
		boolean rightFlag = false;//오른쪽 벽에 충돌했을 경우 상태
		Color rightColor = new Color(image.getRGB(bubble.getX()+50+15, bubble.getY()+25));
		if(rightColor.getRed()==255 && rightColor.getGreen()==0 && rightColor.getBlue()==0) {
			rightFlag = true;
		}
		return rightFlag;
	}//rightWall
	
	public boolean upWall() {
		boolean upFlag = false;//위쪽 벽에 충돌했을 경우 상태
		Color leftColor = new Color(image.getRGB(bubble.getX()+25, bubble.getY()-10));//위쪽 가운데(여유10만큼) 
		if(leftColor.getRed()==255 && leftColor.getGreen()==0 && leftColor.getBlue()==0) {
			upFlag = true;
		}
		return upFlag;
	}//upWall
	
	
}//class
