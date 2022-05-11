package bubble.service;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import bubble.player.Player;

/**
 * 백그라운드 충돌 감지 클래스(색을 통해서)
 */
public class BackgroundPlayerService implements Runnable{

	private BufferedImage image;
	private Player player;
	
	
	public BackgroundPlayerService(Player player) {
		this.player = player;
		try {
			image = ImageIO.read(new File("image/backgroundMapService.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}//end catch
		
	}//BackgroundPlayerService
	
	@Override
	public void run() {
		while(true) {
			//RGB를 확인
			Color leftColor = new Color(image.getRGB(player.getX()-10, player.getY()+25));//캐릭터 기준 왼쪽 RGB
			Color rightColor = new Color(image.getRGB(player.getX()+65, player.getY()+25));//캐릭터 기준 오른쪽 RGB
			
			int leftBottomColor = image.getRGB(player.getX()+10, player.getY()+55); // 좌측 아래 bottom 흰색일 경우 -1
			int rightBottomColor = image.getRGB(player.getX()+40, player.getY()+55); // 우측 아래 bottom 흰색일 경우 -1
			
			if(leftBottomColor != -1 || rightBottomColor != -1) { //캐릭터 우측아래, 좌측아래  background 색이 흰색이 아닐 경우
				//System.out.println("바닥 충돌");
				player.setDown(false);// 멈춤 
			}else {
				if(!player.isUp() && !player.isDown() ) {
					//!player.isUp() -> up 상태가 아닐 경우만 down실행 
					//!player.isDown() -> while로 인힌 무한 down(버그) 방지 
					player.down();
				}//end if
			}//end else
			
			if(leftColor.getRed()==255 && leftColor.getGreen()==0 && leftColor.getBlue()==0) {
				//System.out.println("왼쪽 벽에 충돌");
				player.setLeftWallCrash(true);
				player.setLeft(false);
			}else if(rightColor.getRed()==255 && rightColor.getGreen()==0 && rightColor.getBlue()==0) {
				//System.out.println("오른쪽 벽에 충돌");
				player.setRightWallCrash(true);
				player.setRight(false);
			}else {
				player.setLeftWallCrash(false);
				player.setRightWallCrash(false);
			}
			 
//			try {
//				Thread.sleep(10);
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}//end catch
		}//end while
	}//run
	
}//class
