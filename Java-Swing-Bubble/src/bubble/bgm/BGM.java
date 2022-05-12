package bubble.bgm;

import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

public class BGM {

	public BGM() {
		try {
			AudioInputStream ais = AudioSystem.getAudioInputStream(new File("sound/bgm.wav"));
			Clip clip = AudioSystem.getClip();
			clip.open(ais);
			
			//�Ҹ� ���� 
			FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
			
			//���� ����
			gainControl.setValue(-20.0f);
			
			clip.start();
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
	}//BGM
}//class
