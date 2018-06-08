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
	
	private InputStream iMMStream;
	
	private AudioStream MMStream;
	
    private FloatControl musicController;
	
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
        	collectParalyze_File = new File("Music/ParalyzePU.wav");
        	
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
	
	public void playDaMusic()
	{
		ContinuousAudioDataStream loop = null;
		
		//mainMusicPlayer.start(currentMusicStream);
	    mainMusicPlayer.start(loop);
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
			AudioInputStream test = AudioSystem.getAudioInputStream(clickSound_File);
			clip.open(test);
			clip.start();
		} catch (LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void playCollectPU() throws UnsupportedAudioFileException, IOException
	{
		try {
			Clip clip2 = AudioSystem.getClip();
			AudioInputStream test2 = AudioSystem.getAudioInputStream(collectPUSound_File);
			clip2.open(test2);
			clip2.start();
		} catch (LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void playReplenishPU() throws UnsupportedAudioFileException, IOException
	{
		try {
			Clip clip2 = AudioSystem.getClip();
			AudioInputStream test2 = AudioSystem.getAudioInputStream(collectReplenish_File);
			clip2.open(test2);
			clip2.start();
		} catch (LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void playParalyzePU() throws UnsupportedAudioFileException, IOException
	{
		try {
			Clip clip2 = AudioSystem.getClip();
			AudioInputStream test2 = AudioSystem.getAudioInputStream(collectParalyze_File);
			clip2.open(test2);
			clip2.start();
		} catch (LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	


}
	
