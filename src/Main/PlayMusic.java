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
	
	private AudioStream MMStream;
	private AudioStream ButtonStream;
	
	private AudioPlayer mainMusicPlayer = AudioPlayer.player;
	private AudioPlayer soundEffectsPlayer = AudioPlayer.player;
	
	private AudioStream currentMusicStream;
	
	private File clickSound_File;
	
	private InputStream iMMStream;
	private InputStream iButtonStream;
	
	public PlayMusic()
	{
		currentMusic = 0;
	}
	
	public void loadInDaMusic()
	{        
        try
        {
        	File mainMusic_File = new File("Music/mainMusic.wav");
        	clickSound_File = new File("Music/click.wav");
        	
            iMMStream = new FileInputStream(mainMusic_File);
            iButtonStream = new FileInputStream(clickSound_File);
            
            MMStream = new AudioStream(iMMStream);
            ButtonStream = new AudioStream(iButtonStream);


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
	    mainMusicPlayer.start(loop);
	    
		mainMusicPlayer.start(currentMusicStream);
		
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
	
	public void checkDaMusic()
	{
		if(currentMusic == 1)
		{
            currentMusicStream = MMStream;
		}
	}	

}
	
