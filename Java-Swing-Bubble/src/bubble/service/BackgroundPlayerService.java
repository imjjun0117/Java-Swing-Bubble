package bubble.service;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import bubble.player.Player;

/**
 * ��׶��� �浹 ���� Ŭ����(���� ���ؼ�)
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
			//RGB�� Ȯ��
			Color leftColor = new Color(image.getRGB(player.getX()-10, player.getY()+25));//ĳ���� ���� ���� RGB
			Color rightColor = new Color(image.getRGB(player.getX()+65, player.getY()+25));//ĳ���� ���� ������ RGB
			
			int leftBottomColor = image.getRGB(player.getX()+10, player.getY()+55); // ���� �Ʒ� bottom ����� ��� -1
			int rightBottomColor = image.getRGB(player.getX()+40, player.getY()+55); // ���� �Ʒ� bottom ����� ��� -1
			
			if(leftBottomColor != -1 || rightBottomColor != -1) { //ĳ���� �����Ʒ�, �����Ʒ�  background ���� ����� �ƴ� ���
				//System.out.println("�ٴ� �浹");
				player.setDown(false);// ���� 
			}else {
				if(!player.isUp() && !player.isDown() ) {
					//!player.isUp() -> up ���°� �ƴ� ��츸 down���� 
					//!player.isDown() -> while�� ���� ���� down(����) ���� 
					player.down();
				}//end if
			}//end else
			
			if(leftColor.getRed()==255 && leftColor.getGreen()==0 && leftColor.getBlue()==0) {
				//System.out.println("���� ���� �浹");
				player.setLeftWallCrash(true);
				player.setLeft(false);
			}else if(rightColor.getRed()==255 && rightColor.getGreen()==0 && rightColor.getBlue()==0) {
				//System.out.println("������ ���� �浹");
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
