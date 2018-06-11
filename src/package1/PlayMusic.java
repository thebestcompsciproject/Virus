package package1;

import java.io.BufferedInputStream;
import java.io.IOException;

import java.util.Timer;
import java.util.TimerTask;

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
	
	AudioInputStream audioInputStream;
	Clip clip;
	Timer timer;
	private FloatControl musicController;
	
	public PlayMusic() {
		timer = new Timer();
		loadInDaMusic();
	}
	
	public void loadInDaMusic() {
		MM = getClass().getResourceAsStream("Music/mainMusic.wav"); 
		click = getClass().getResourceAsStream("Music/buttonPress.wav");
		PU = getClass().getResourceAsStream("Music/collectPU.wav");
		replenish = getClass().getResourceAsStream("Music/collectReplenish.wav");
		paralyze = getClass().getResourceAsStream("Music/paralyzeSound.wav");
		shoot = getClass().getResourceAsStream("Music/bulletAudio.wav");
		infected = getClass().getResourceAsStream("Music/infectedSound.wav");
		pickUp = getClass().getResourceAsStream("Music/pickingUpTri.wav");
	}
	
	public void setMute(boolean b) {
		musicController= (FloatControl)clip.getControl(FloatControl.Type.MASTER_GAIN);
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
	
<<<<<<< HEAD
=======
	public void loadInDaMusic() {        
		try {
			mainMusic_File = new File("src/package1/music/mainMusic.wav");
			clickSound_File = new File("src/package1/music/buttonPress.wav");
        		collectPUSound_File = new File("src/package1/music/collectPU.wav");
        		collectReplenish_File = new File("src/package1/music/collectReplenish.aiff");
        		beingParalyzed_File = new File("src/package1/music/paralyzeSound.wav");
        		shootingbullets_File = new File("src/package1/music/shootFinal.aiff");
        		isInfectedSound_File =  new File("src/package1/music/infectedSound.wav");
        		pickUpTriangles_File = new File("src/package1/music/pickingUpBulleta.wav");
        		
        		iMMStream = new FileInputStream(mainMusic_File);           
        		MMStream = new AudioStream(iMMStream);
		}
		catch(FileNotFoundException e){
			e.printStackTrace();
		}
		catch(IOException e) {
			e.printStackTrace();
        }
    }
	
	public void playBGMusic() throws UnsupportedAudioFileException, IOException{
		//ContinuousAudioDataStream loop = null;
		
		//mainMusicPlayer.start(currentMusicStream);
	   // mainMusicPlayer.start(loop);
		try {
			mainMusicClip = AudioSystem.getClip();
			AudioInputStream audio = AudioSystem.getAudioInputStream(mainMusic_File);
			mainMusicClip.open(audio);
			mainMusicClip.loop(Clip.LOOP_CONTINUOUSLY);
			mainMusicClip.start();
		} catch (LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		musicController= (FloatControl)mainMusicClip.getControl(FloatControl.Type.MASTER_GAIN);
	}
	
>>>>>>> 222888bb0a22d2823c3dc648f8fa4aebc66e84c3
	public void inGameChange(boolean state)
	{
		musicController= (FloatControl)clip.getControl(FloatControl.Type.MASTER_GAIN);
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
			audioInputStream = AudioSystem.getAudioInputStream(new BufferedInputStream(MM));
			clip = AudioSystem.getClip();
			clip.open(audioInputStream);
		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		clip.loop(clip.LOOP_CONTINUOUSLY);
	}
	
	public void playButtonSound() {
		try {
			audioInputStream = AudioSystem.getAudioInputStream(new BufferedInputStream(click));
			clip = AudioSystem.getClip();
			clip.open(audioInputStream);
		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		clip.start();
	}
	
	public void playCollectPU() {
		try {
			audioInputStream = AudioSystem.getAudioInputStream(new BufferedInputStream(PU));
			clip = AudioSystem.getClip();
			clip.open(audioInputStream);
		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		clip.start();
	}
	
	public void playReplenishPU() {
		try {
			audioInputStream = AudioSystem.getAudioInputStream(new BufferedInputStream(replenish));
			clip = AudioSystem.getClip();
			clip.open(audioInputStream);
		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		clip.start();
	}
	
	public void playBeingParalyzed() {
		try {
			audioInputStream = AudioSystem.getAudioInputStream(new BufferedInputStream(paralyze));
			clip = AudioSystem.getClip();
			clip.open(audioInputStream);
		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		clip.start();
	}
	
	public void shootBulletAudio() {
		try {
			audioInputStream = AudioSystem.getAudioInputStream(new BufferedInputStream(replenish));
			clip = AudioSystem.getClip();
			clip.open(audioInputStream);
		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		clip.start();
	}
	
	public void infectedSound() {
		try {
			audioInputStream = AudioSystem.getAudioInputStream(new BufferedInputStream(infected));
			clip = AudioSystem.getClip();
			clip.open(audioInputStream);
		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		clip.start();
	}
	
	public void pickingUpTriangles() {
		try {
			audioInputStream = AudioSystem.getAudioInputStream(new BufferedInputStream(pickUp));
			clip = AudioSystem.getClip();
			clip.open(audioInputStream);
		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		clip.start();
	}
	
	/*public static void main(String[] args) {
		AudioTest audio = new AudioTest();
		audio.playBG();
	}*/
}
