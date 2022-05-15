package bubble.game.service;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import bubble.game.component.Enemy;
import bubble.game.component.Player;

/**
 * 백그라운드 충돌 감지 클래스(색을 통해서)
 */
public class BackgroundEnemyService implements Runnable{

	private BufferedImage image;
	private Enemy enemy;
	
	
	public BackgroundEnemyService(Enemy enemy) {
		this.enemy = enemy;
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
			Color leftColor = new Color(image.getRGB(enemy.getX()-10, enemy.getY()+25));//캐릭터 기준 왼쪽 RGB
			Color rightColor = new Color(image.getRGB(enemy.getX()+65, enemy.getY()+25));//캐릭터 기준 오른쪽 RGB
			
			int leftBottomColor = image.getRGB(enemy.getX()+10, enemy.getY()+55); // 좌측 아래 bottom 흰색일 경우 -1
			int rightBottomColor = image.getRGB(enemy.getX()+40, enemy.getY()+55); // 우측 아래 bottom 흰색일 경우 -1
			
			if(leftBottomColor != -1 || rightBottomColor != -1) { //캐릭터 우측아래, 좌측아래  background 색이 흰색이 아닐 경우
				enemy.setDown(false);// 멈춤 
			}else if(!enemy.isUp() && !enemy.isDown() ) {
					enemy.down();
			}//end else
			
			if(leftColor.getRed()==255 && leftColor.getGreen()==0 && leftColor.getBlue()==0) {
				//System.out.println("왼쪽 벽에 충돌");
				enemy.setLeft(false);
				if(!enemy.isRight()) {//버그대비
					enemy.right();//벽에 충돌했을 경우 반대 방향으로
				}
			}else if(rightColor.getRed()==255 && rightColor.getGreen()==0 && rightColor.getBlue()==0) {
				//System.out.println("오른쪽 벽에 충돌");
				enemy.setRight(false);
				if(!enemy.isLeft()) {//버그대비
					enemy.left();//벽에 충돌했을 경우 반대 방향으로
				}
			}
			 
//			try {
//				Thread.sleep(10);
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}//end catch
		}//end while
	}//run
	
}//class
