package package1;

import sun.audio.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.net.URL;
import javax.sound.sampled.*;

public class PlayMusic1 
{
	private int currentMusic;
	
	private final int mainMusic = 1;
	private final int clickButtonSound = 10;

	private AudioPlayer mainMusicPlayer = AudioPlayer.player;
	private AudioPlayer soundEffectsPlayer = AudioPlayer.player;
	
	private AudioStream currentMusicStream;
	
	private File mainMusic_File;
	private File clickSound_File;
	private File collectPUSound_File;
	private File collectReplenish_File;
	private File beingParalyzed_File;
	private File shootingbullets_File;
	private File isInfectedSound_File;
	private File pickUpTriangles_File;
	
	private InputStream iMMStream;
	private AudioStream MMStream;
	
    private FloatControl musicController;
    
    private Clip mainMusicClip;
	
	public PlayMusic1()
	{
		currentMusic = 0;
	}
	
	public void setMute(boolean b) {
		musicController= (FloatControl)mainMusicClip.getControl(FloatControl.Type.MASTER_GAIN);
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
	
	public void loadInDaMusic() {        
		try {
			mainMusic_File = new File("src/package1/music/mainMusic.wav");
			/*clickSound_File = new File("src/package1/music/buttonPress.wav");
        		collectPUSound_File = new File("src/package1/music/collectPU.wav");
        		collectReplenish_File = new File("src/package1/music/collectReplenish.aiff");
        		beingParalyzed_File = new File("src/package1/music/paralyzeSound.wav");
        		shootingbullets_File = new File("src/package1/music/shootingSound.wav");
        		isInfectedSound_File =  new File("src/package1/music/infectedSound.wav");
        		pickUpTriangles_File = new File("src/package1/music/pickingUpBulleta.wav");*/
        		
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
			AudioInputStream audio = AudioSystem.getAudioInputStream(mainMusic_File);
			mainMusicClip = AudioSystem.getClip();
			mainMusicClip.open(audio);
			mainMusicClip.loop(Clip.LOOP_CONTINUOUSLY);
			mainMusicClip.start();
		} catch (LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		musicController= (FloatControl)mainMusicClip.getControl(FloatControl.Type.MASTER_GAIN);
	}
	
	public void inGameChange(boolean state)
	{
		musicController= (FloatControl)mainMusicClip.getControl(FloatControl.Type.MASTER_GAIN);
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
	public void checkDaMusic() {
		if(currentMusic == 1) {
            currentMusicStream = MMStream;
		}
	}	
	
	public void changeMusic(int num) {
		currentMusic = num;
	}
	
	public void playButtonSound() throws UnsupportedAudioFileException, IOException {
		try {
			Clip clip = AudioSystem.getClip();
			AudioInputStream audio = AudioSystem.getAudioInputStream(clickSound_File);
			clip.open(audio);
			clip.start();
		} catch (LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public void playCollectPU() throws UnsupportedAudioFileException, IOException {
		try {
			Clip clip = AudioSystem.getClip();
			AudioInputStream audio = AudioSystem.getAudioInputStream(collectPUSound_File);
			clip.open(audio);
			clip.start();
		} catch (LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void playReplenishPU() throws UnsupportedAudioFileException, IOException {
		try {
			Clip clip = AudioSystem.getClip();
			AudioInputStream audio = AudioSystem.getAudioInputStream(collectReplenish_File);
			clip.open(audio);
			clip.start();
		} catch (LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void playParalyzePU() throws UnsupportedAudioFileException, IOException {
		try {
			Clip clip = AudioSystem.getClip();
			AudioInputStream audio2 = AudioSystem.getAudioInputStream(collectPUSound_File);
			clip.open(audio2);
			clip.start();
		} catch (LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void playBeingParalyzed() throws UnsupportedAudioFileException, IOException {
		try {
			Clip clip = AudioSystem.getClip();
			AudioInputStream audio2 = AudioSystem.getAudioInputStream(beingParalyzed_File);
			clip.stop();
			clip.open(audio2);
			clip.loop(3);
			clip.start();
		} catch (LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void shootBulletAudio() throws UnsupportedAudioFileException, IOException {
		try {
			Clip clip = AudioSystem.getClip();
			AudioInputStream audio2 = AudioSystem.getAudioInputStream(shootingbullets_File);
			clip.open(audio2);
			clip.start();
		} catch (LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void infectedSound() throws UnsupportedAudioFileException, IOException {
		try 
			{
			Clip clip = AudioSystem.getClip();
			AudioInputStream audio = AudioSystem.getAudioInputStream(isInfectedSound_File);
			clip.open(audio);
			clip.loop(1);
			clip.start();
			} catch (LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void pickingUpTriangles() throws UnsupportedAudioFileException, IOException {
		try {
			Clip clip = AudioSystem.getClip();
			AudioInputStream audio2 = AudioSystem.getAudioInputStream(pickUpTriangles_File);
			clip.open(audio2);
			clip.start();
		} catch (LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}