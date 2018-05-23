//Gurram-Muruhathasan-Fok
package Main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.awt.MouseInfo;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.Timer;

public class GamePanel extends JPanel implements KeyListener, ActionListener, MouseListener{

	private Timer timer;
	private boolean[] isDown;
	private ObjectManager manager;
	Font testFont;
	
	private int width;
	private int height;
	private int frameX;
	private int frameY;
	
	private int fps;
	private long fpsTime;
	private int fpsDraw;
	
	private final int reloadTime = 250;
	private long timeSave1 = 0;
	private long timeSave2 = 0;
	
	public BufferedImage defaultPlay;
	public BufferedImage hoverPlay;
	public BufferedImage clickedPlay;
	
	public BufferedImage defaultHTP;
	public BufferedImage hoverHTP;
	public BufferedImage clickedHTP;
	
	public BufferedImage defaultCredits;
	public BufferedImage hoverCredits;
	public BufferedImage clickedCredits;
	
	public BufferedImage mainMenu;
	
	private boolean mouseClicked;
	
	private Button play;
	private Button HTP;
	private Button credits;
	
	ArrayList<Boolean> switchScreen;
	
	public GamePanel(int width, int height) {
		timer = new Timer(15, this);
		manager = new ObjectManager(width, height);
		mouseClicked = false;
		this.width = width;
		this.height = height;
		frameX = 0;
		frameY = 0;
		testFont = new Font ("Courier New", 1, 30);
		
		switchScreen = new ArrayList<Boolean>();
		initiateIsDown();
		initiateFps();
		initiateSwitchScreen();
		readImages();
		makeButtons();
	}
	
	public void updateInfo(int w, int h, int x, int y) {
		width = w;
		height = h;
		frameX = x;
		frameY = y;
		manager.updateInfo(width, height, frameX, frameY);
	}
	
	public void initiateSwitchScreen()
	{
		for (int i = 0; i < 3; i ++)
		{
			switchScreen.add(i,false); // button choices
		}
	}
	
	public void insertMainMenu(Graphics g)
	{
		g.drawImage(mainMenu,300, 100, 850 ,400 , null);
	}
	private void readImages() {
		try {
			defaultPlay = ImageIO.read(this.getClass().getResourceAsStream("PlayBlank.png"));
			hoverPlay = ImageIO.read(this.getClass().getResourceAsStream("PlayHover.png"));
			clickedPlay = ImageIO.read(this.getClass().getResourceAsStream("PlayClicked.png"));
			
			defaultHTP = ImageIO.read(this.getClass().getResourceAsStream("HowToPlay1.png"));
			hoverHTP = ImageIO.read(this.getClass().getResourceAsStream("HowToPlay2.png"));
			clickedHTP = ImageIO.read(this.getClass().getResourceAsStream("HowToPlay3.png"));
			
			defaultCredits = ImageIO.read(this.getClass().getResourceAsStream("Credits1.png"));
			hoverCredits = ImageIO.read(this.getClass().getResourceAsStream("Credits2.png"));
			clickedCredits = ImageIO.read(this.getClass().getResourceAsStream("Credits3.png"));
			
			mainMenu  = ImageIO.read(this.getClass().getResourceAsStream("MainLogo.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void makeButtons() {
		play = new Button(560, 380, 300, 150, defaultPlay, hoverPlay, clickedPlay);
		HTP = new Button(560, 550, 300, 150, defaultHTP, hoverHTP, clickedHTP);
		credits = new Button(560, 720, 300, 150, defaultCredits, hoverCredits, clickedCredits);
	}
	
	private void initiateIsDown() {
		isDown = new boolean[11];
		for(int i = 0; i<8; i++) {
			isDown[i] = false;
		}
	}
	
	private void initiateFps() {
		fps = 60;
		fpsTime = 0;
		fpsDraw = 60;
	}
	
	public void start() {
		timer.start();
	}
	
	private void drawGameState(Graphics g) {
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, width, height);
		g.setColor(new Color(192, 192, 192));
		
		for(int i = 0; i<width; i+=80)
			g.drawLine(i, 0, i, height);
		for(int i = 0; i<height; i+=80)
			g.drawLine(0, i, width, i);
		
		manager.draw(g);
				
		g.setColor(Color.BLACK);
		g.setFont(testFont);
		g.drawString("FPS: " + Integer.toString(fpsDraw), 50, 50);
		
	}
	
	public void paint(Graphics g) {
		super.paint(g);
		if(switchScreen.get(0) == false && switchScreen.get(1) == false && switchScreen.get(2) == false ) {
			play.draw(g);
			HTP.draw(g);
			credits.draw(g);
			insertMainMenu(g);
		}
		if(switchScreen.get(0) == true) {
			repaint();
			drawGameState(g);
		}
	}
	
	private void gameKeysUpdate() {
		int j = 0;
		int sign = 1;
		
		for(int i = 0; i<8; i++) {
			j = i/4;
			sign = (2*((i/2)%2)-1);
			if(isDown[i]&&manager.getPlayers().get(j).getVelocity()[(i+1)%2]*sign<6.0) {
				manager.getPlayers().get(j).updateVelocity(0.06*(i%2)*sign,0.06*((i+1)%2)*sign);
			}
		}
	
		if(isDown[8]) {
			manager.getPlayers().get(0).updateDirection(3.0);
		}
		
		if(isDown[9]) {
			manager.getPlayers().get(0).updateDirection(-3.0);
		}
		
		if(isDown[10]) {
			if(System.currentTimeMillis()-reloadTime>timeSave1) {
				manager.shootBullet(0);
				timeSave1 = System.currentTimeMillis();
			}
		}
	}
	
	private void screenMouseUpdate() {
		if(!mouseClicked) {
			double xM = MouseInfo.getPointerInfo().getLocation().x-frameX;
			double yM = MouseInfo.getPointerInfo().getLocation().y-frameY;

			if(play.contains(xM, yM)) {
				play.hoverButton();
			}
			else {
				play.defautlButton();
			}
			if(HTP.contains(xM, yM)) {
				HTP.hoverButton();
			}
			else {
				HTP.defautlButton();
			}
			if(credits.contains(xM, yM)) {
				credits.hoverButton();
			}
			else {
				credits.defautlButton();
			}
		}
	}
	
	private void gameMouseUpdate() {
		double x1 = manager.getPlayers().get(1).getX();
		double y1 = manager.getPlayers().get(1).getY();
		double x2 = MouseInfo.getPointerInfo().getLocation().getX()-frameX;
		double y2 = MouseInfo.getPointerInfo().getLocation().getY()-frameY;
		double angle = getAngle(x1, y1, x2, y2);
		angle = (angle+360)%360;
		
		if(manager.getPlayers().get(1).getDirection()<180) {
			if(angle>manager.getPlayers().get(1).getDirection()&&angle<(manager.getPlayers().get(1).getDirection()+180)) {
				manager.getPlayers().get(1).updateDirection(3.0);
			}
			else {
				manager.getPlayers().get(1).updateDirection(-3.0);
			}
		}
		else {
			if(angle>manager.getPlayers().get(1).getDirection()||angle<(manager.getPlayers().get(1).getDirection()+180)%360) {
				manager.getPlayers().get(1).updateDirection(3.0);
			}
			else {
				manager.getPlayers().get(1).updateDirection(-3.0);
			}
		}
		
		if(mouseClicked&&System.currentTimeMillis()-reloadTime>timeSave2) {
			manager.shootBullet(1);
			timeSave2 = System.currentTimeMillis();
		}
	}
	
	private double getAngle(double x1, double y1, double x2, double y2) {
		double delX = x2 - x1;
		double delY = y2 - y1;
		double thetaR = Math.atan2(delX, delY);
		return thetaR*360/(2*Math.PI);
	}
	
	
	private void gameUpdate() {
		manager.update();
		gameKeysUpdate();
		gameMouseUpdate();
	}
	
	private void fpsUpdate() {
		fps++;
		if(System.currentTimeMillis()-1000 >= fpsTime) {
			fpsTime = System.currentTimeMillis();
			fpsDraw = fps;
			fps = 0;
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		repaint();
		
		if(switchScreen.get(0) == true)
		{
			repaint();
			gameUpdate();
			fpsUpdate();
		}
		
		screenMouseUpdate();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	private void buttonChecks() {
		
		if(play.contains(MouseInfo.getPointerInfo().getLocation().getX()-frameX, MouseInfo.getPointerInfo().getLocation().getY()-frameY)) {
			play.clickedButton();
			switchScreen.set(0, true);
		}
		else if(HTP.contains(MouseInfo.getPointerInfo().getLocation().getX()-frameX, MouseInfo.getPointerInfo().getLocation().getY()-frameY))
		{
			HTP.clickedButton();
			switchScreen.set(1, true);
		}
		else if(credits.contains(MouseInfo.getPointerInfo().getLocation().getX()-frameX, MouseInfo.getPointerInfo().getLocation().getY()-frameY))
		{
			credits.clickedButton();
			switchScreen.set(2, true);
		}
	}
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		buttonChecks();
		mouseClicked = true;
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		mouseClicked = false;
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {

	}
	
	private void gameP1Controls(KeyEvent e, boolean state) {
		if(e.getKeyCode() == KeyEvent.VK_W) {
			isDown[0] = state;
		}
		if(e.getKeyCode() == KeyEvent.VK_A) {
			isDown[1] = state;
		}
		if(e.getKeyCode() == KeyEvent.VK_S) {
			isDown[2] = state;
		}
		if(e.getKeyCode() == KeyEvent.VK_D) {
			isDown[3] = state;
		}
		if(e.getKeyCode() == KeyEvent.VK_T) {
			isDown[8] = state;
		}
		if(e.getKeyCode() == KeyEvent.VK_Y) {
			isDown[9] = state;
		}
		if(e.getKeyCode() == KeyEvent.VK_SPACE) {
			isDown[10] = state;
		}
	}
	
	private void gameP2Controls(KeyEvent e, boolean state) {
		if(e.getKeyCode() == KeyEvent.VK_UP) {
			isDown[4] = state;
		}
		if(e.getKeyCode() == KeyEvent.VK_LEFT) {
			isDown[5] = state;
		}
		if(e.getKeyCode() == KeyEvent.VK_DOWN) {
			isDown[6] = state;
		}
		if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
			isDown[7] = state;
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
		/*if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			manager.getPlayers().get(0).addTriangle();
		}
		if(e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
			manager.getPlayers().get(0).removeLastTriangle();
		}*/
		
		gameP1Controls(e, true);
		gameP2Controls(e, true);
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		gameP1Controls(e, false);
		gameP2Controls(e, false);
	}

}
