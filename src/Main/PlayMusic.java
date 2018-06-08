package Main;

import sun.audio.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.net.URL;
import javax.sound.sampled.*;

public class PlayMusic 
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
	private File collectParalyze_File;
	private File beingParalyzed_File;
	private File shootingbullets_File;
	
	private InputStream iMMStream;
	
	private AudioStream MMStream;
	
    private FloatControl musicController;
    
    private boolean shocked = false;
    
    private Clip mainMusicClip;
    
    private boolean switchMMVolume = true; //true: in game	false: NOT in game
	
	public PlayMusic()
	{
		currentMusic = 0;
	}
	
	public void loadInDaMusic()
	{        
        try
        {
        	mainMusic_File = new File("Music/mainMusic.wav");
        	
        	clickSound_File = new File("Music/click.wav");
        	collectPUSound_File = new File("Music/collectPU.wav");
        	collectReplenish_File = new File("Music/collectReplenish.aiff");
        	collectParalyze_File = new File("Music/collectParalyze.wav");
        	beingParalyzed_File = new File("Music/paralyzeSound.wav");
        	shootingbullets_File = new File("Music/shootingSound.wav");
        	
            iMMStream = new FileInputStream(mainMusic_File);           
            MMStream = new AudioStream(iMMStream);


        }
        catch(FileNotFoundException e){
            System.out.print(e.toString());
        }
        catch(IOException error)
        {
            System.out.print(error.toString());
        }
       
        
    }
	
	public void playBGMusic() throws UnsupportedAudioFileException, IOException
	{
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
	}
	
	public void inGameChange()
	{
		switchMMVolume = !switchMMVolume;
		FloatControl gainController = (FloatControl)mainMusicClip.getControl(FloatControl.Type.MASTER_GAIN);
		
		if(switchMMVolume == false)
		{
			gainController.setValue(-20.0f);
		}
		else if(switchMMVolume == true)
		{
			gainController.setValue(20.0f);
		}
		
	}
	public void checkDaMusic()
	{
		if(currentMusic == 1)
		{
            currentMusicStream = MMStream;
		}
	}	
	
	public void changeMusic(int num)
	{
		currentMusic = num;
	}
	
	public void playButtonSound() throws UnsupportedAudioFileException, IOException
	{
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
	
	public void playButtonSound1() throws UnsupportedAudioFileException, IOException
	{
		
	}
	
	public void playCollectPU() throws UnsupportedAudioFileException, IOException
	{
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
	
	public void playReplenishPU() throws UnsupportedAudioFileException, IOException
	{
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
	
	public void playParalyzePU() throws UnsupportedAudioFileException, IOException
	{
		try {
			Clip clip = AudioSystem.getClip();
			AudioInputStream audio2 = AudioSystem.getAudioInputStream(collectParalyze_File);
			clip.open(audio2);
			clip.start();
		} catch (LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void playBeingParalyzed() throws UnsupportedAudioFileException, IOException
	{
		if(!shocked)
		{
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
		shocked = true;
	}
	
	public void shootBulletAudio() throws UnsupportedAudioFileException, IOException
	{
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

}
	
