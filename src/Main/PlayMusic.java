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
	private int currentSoundEffect;
	
	private final int mainMusic = 1;
	private final int clickButtonSound = 10;
	
	private AudioStream MMStream;
	private AudioStream ButtonStream;
	
	private AudioPlayer mainMusicPlayer = AudioPlayer.player;
	private AudioPlayer soundEffectsPlayer = AudioPlayer.player;
	
	private AudioStream currentMusicStream;
	private AudioStream currentEffectStream;
	
	private InputStream iMMStream;
	private InputStream iButtonStream;
	
	public PlayMusic()
	{
		currentMusic = 0;
		currentSoundEffect = 0;
	}
	
	public void loadInDaMusic()
	{        
        try
        {
        	File mainMusic_File = new File("Music/mainMusic.wav");
        	File clickSound_File = new File("Music/click.wav");
        	
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
		soundEffectsPlayer.start(currentEffectStream);
		
	}
	
	public void changeMusic(int num)
	{
		currentMusic = num;
	}
	
	public void checkDaMusic()
	{
		if(currentMusic == 1)
		{
            currentMusicStream = MMStream;
		}
		if(currentSoundEffect == 10)
		{
			currentEffectStream = ButtonStream;
		}
	}
}
	
