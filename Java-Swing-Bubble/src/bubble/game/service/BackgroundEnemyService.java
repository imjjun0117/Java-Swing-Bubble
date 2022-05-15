package bubble.game.service;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import bubble.game.component.Enemy;
import bubble.game.component.Player;

/**
 * ��׶��� �浹 ���� Ŭ����(���� ���ؼ�)
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
			//RGB�� Ȯ��
			Color leftColor = new Color(image.getRGB(enemy.getX()-10, enemy.getY()+25));//ĳ���� ���� ���� RGB
			Color rightColor = new Color(image.getRGB(enemy.getX()+65, enemy.getY()+25));//ĳ���� ���� ������ RGB
			
			int leftBottomColor = image.getRGB(enemy.getX()+10, enemy.getY()+55); // ���� �Ʒ� bottom ����� ��� -1
			int rightBottomColor = image.getRGB(enemy.getX()+40, enemy.getY()+55); // ���� �Ʒ� bottom ����� ��� -1
			
			if(leftBottomColor != -1 || rightBottomColor != -1) { //ĳ���� �����Ʒ�, �����Ʒ�  background ���� ����� �ƴ� ���
				enemy.setDown(false);// ���� 
			}else if(!enemy.isUp() && !enemy.isDown() ) {
					enemy.down();
			}//end else
			
			if(leftColor.getRed()==255 && leftColor.getGreen()==0 && leftColor.getBlue()==0) {
				//System.out.println("���� ���� �浹");
				enemy.setLeft(false);
				if(!enemy.isRight()) {//���״��
					enemy.right();//���� �浹���� ��� �ݴ� ��������
				}
			}else if(rightColor.getRed()==255 && rightColor.getGreen()==0 && rightColor.getBlue()==0) {
				//System.out.println("������ ���� �浹");
				enemy.setRight(false);
				if(!enemy.isLeft()) {//���״��
					enemy.left();//���� �浹���� ��� �ݴ� ��������
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
