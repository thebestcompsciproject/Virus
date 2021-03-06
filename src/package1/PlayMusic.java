package package1;

import java.io.BufferedInputStream;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class PlayMusic{
	java.io.InputStream MM;
	java.io.InputStream click;
	java.io.InputStream PU;
	java.io.InputStream replenish;
	java.io.InputStream paralyze;
	java.io.InputStream shoot;
	java.io.InputStream infected;
	java.io.InputStream pickUp;
	
	Clip mainClip;
	
	private FloatControl musicController;
	
	public void setMute(boolean b) {
		musicController= (FloatControl)mainClip.getControl(FloatControl.Type.MASTER_GAIN);
		float range = musicController.getMaximum() - musicController.getMinimum();
		if(!b) {
			float gain = (range * 1) + musicController.getMinimum();
			musicController.setValue(gain);
		}
		else {
			float gain = musicController.getMinimum();
			musicController.setValue(gain);
		}
	}
	
	public void inGameChange(boolean state)
	{
		musicController= (FloatControl)mainClip.getControl(FloatControl.Type.MASTER_GAIN);
		float range = musicController.getMaximum() - musicController.getMinimum();
		
		if(!state) { //Main Menu 
			float gain = (range * 1) + musicController.getMinimum();
			musicController.setValue(gain);
		}
		else if(state) { //In Game
			float gain = (range * 0.8f) + musicController.getMinimum();
			musicController.setValue(gain);
		}
	}
	
	public void playBG() {
		try {
			MM = getClass().getResourceAsStream("Music/mainMusic.wav"); 
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new BufferedInputStream(MM));
			mainClip = AudioSystem.getClip();
			mainClip.open(audioInputStream);
		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		mainClip.loop(mainClip.LOOP_CONTINUOUSLY);
		mainClip.start();
	}
	
	public void playButtonSound() {
		try {
			click = getClass().getResourceAsStream("Music/buttonPress.wav");
			AudioInputStream audio = AudioSystem.getAudioInputStream(new BufferedInputStream(click));
			Clip clip = AudioSystem.getClip();
			clip.open(audio);
			clip.start();
		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void playCollectPU() {
		try {
			PU = getClass().getResourceAsStream("Music/collectPU.wav");
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new BufferedInputStream(PU));
			Clip clip = AudioSystem.getClip();
			clip.open(audioInputStream);
			clip.start();
		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void playReplenishPU() {
		try {
			replenish = getClass().getResourceAsStream("Music/collectReplenish.aiff");
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new BufferedInputStream(replenish));
			Clip clip = AudioSystem.getClip();
			clip.open(audioInputStream);
			clip.start();
		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void playBeingParalyzed() {
		try {
			paralyze = getClass().getResourceAsStream("Music/paralyzeSound.wav");
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new BufferedInputStream(paralyze));
			Clip clip = AudioSystem.getClip();
			clip.open(audioInputStream);
			clip.start();
		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void shootBulletAudio() {
		try {
			shoot = getClass().getResourceAsStream("Music/shootFinal.aiff");
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new BufferedInputStream(shoot));
			Clip clip = AudioSystem.getClip();
			clip.open(audioInputStream);
			clip.start();
		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void infectedSound() {
		try {
			infected = getClass().getResourceAsStream("Music/infectedSound.wav");
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new BufferedInputStream(infected));
			Clip clip = AudioSystem.getClip();
			clip.open(audioInputStream);
			clip.start();
		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void pickingUpTriangles() {
		try {
			pickUp = getClass().getResourceAsStream("Music/pickingUpBulleta.wav");
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new BufferedInputStream(pickUp));
			Clip clip = AudioSystem.getClip();
			clip.open(audioInputStream);
			clip.start();
		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
