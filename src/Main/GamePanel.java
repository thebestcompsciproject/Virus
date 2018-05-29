//Gurram-Muruhathasan-Fok
package Main;
//test test test
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
	private Font testFont;
	
	private int width;
	private int height;
	private int frameX;
	private int frameY;
	
	private int fps;
	private long fpsTime;
	private int fpsDraw;
	
	private long loadingInitial = -1;
	private long loadingTime = 2000;
	
	private final int reloadTime = 250;
	private long timeSave1 = 0;
	private long timeSave2 = 0;
	
	public BufferedImage defaultPlay;
	public BufferedImage hoverPlay;
	
	public BufferedImage defaultHTP;
	public BufferedImage hoverHTP;
	
	public BufferedImage defaultCredits;
	public BufferedImage hoverCredits;
	
	public BufferedImage defaultBack;
	public BufferedImage hoverBack;
	
	public BufferedImage defaultPA;
	public BufferedImage hoverPA;
	
	public BufferedImage Logo;
	public BufferedImage creditsScreen;
	public BufferedImage HTPScreen;
	public BufferedImage winScreen1;
	public BufferedImage winScreen2;
	public BufferedImage loading;
	
	private boolean mouseClicked;
	
	private Button play;
	private Button HTP;
	private Button credits;
	private Button backHTP;
	private Button backCredits;
	private Button PA;
	private Button backPA;
	
	ArrayList<Boolean> switchScreen;
	
	public GamePanel(int width, int height) {
		timer = new Timer(15, this);
		manager = null;
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
	
	public void start() {
		timer.start();
	}
	
	public void updateInfo(int w, int h, int x, int y) {
		width = w;
		height = h-10;
		frameX = x;
		frameY = y;
		if(manager != null)
			manager.updateInfo(width, height, frameX, frameY);
	}
	
	public void initiateSwitchScreen() {
		//Play, HTP, Credits, Win, Loading
		for (int i = 0; i < 5; i ++) {
			switchScreen.add(i,false); 
		}
	}
	
	private void initiateIsDown() {
		isDown = new boolean[11];
		for(int i = 0; i < 8; i++) {
			isDown[i] = false;
		}
	}
	
	private void initiateFps() {
		fps = 60;
		fpsTime = 0;
		fpsDraw = 60;
	}
	
	private void readImages() {
		try {
			defaultPlay = ImageIO.read(this.getClass().getResourceAsStream("PlayA.png"));
			hoverPlay = ImageIO.read(this.getClass().getResourceAsStream("PlayB.png"));
			
			defaultHTP = ImageIO.read(this.getClass().getResourceAsStream("HTPA.png"));
			hoverHTP = ImageIO.read(this.getClass().getResourceAsStream("HTPB.png"));
			
			defaultCredits = ImageIO.read(this.getClass().getResourceAsStream("CreditsA.png"));
			hoverCredits = ImageIO.read(this.getClass().getResourceAsStream("CreditsB.png"));

			defaultBack = ImageIO.read(this.getClass().getResourceAsStream("BackA.png"));
			hoverBack = ImageIO.read(this.getClass().getResourceAsStream("BackB.png"));
			
			defaultPA = ImageIO.read(this.getClass().getResourceAsStream("PlayAgainA.png"));
			hoverPA = ImageIO.read(this.getClass().getResourceAsStream("PlayAgainB.png"));
			
			Logo  = ImageIO.read(this.getClass().getResourceAsStream("Logo.png"));
			creditsScreen = ImageIO.read(this.getClass().getResourceAsStream("Credits.png"));
			HTPScreen = ImageIO.read(this.getClass().getResourceAsStream("HTP.png"));
			winScreen1 = ImageIO.read(this.getClass().getResourceAsStream("P1Win.png"));
			winScreen2 = ImageIO.read(this.getClass().getResourceAsStream("P2Win.png"));
			loading = ImageIO.read(this.getClass().getResourceAsStream("Loading.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void makeButtons() {
		play = new Button(width*525/1280, height*275/725, width*200/1280, height*100/725, defaultPlay, hoverPlay);
		HTP = new Button(width*525/1280, height*400/725, width*200/1280, height*100/725, defaultHTP, hoverHTP);
		credits = new Button(width*525/1280, height*525/725, width*200/1280, height*100/725, defaultCredits, hoverCredits);
		backHTP = new Button(width*10/1280, height*600/725, width*200/1280, height*100/725, defaultBack, hoverBack);
		backCredits = new Button(width*10/1280, height*600/725, width*200/1280, height*100/725, defaultBack, hoverBack);
		PA = new Button(width*525/1280, height*400/725, width*200/1280, height*100/725, defaultPA, hoverPA);
		backPA =  new Button(width*10/1280, height*600/725, width*200/1280, height*100/725, defaultBack, hoverBack);
	}
	
	public void paint(Graphics g) {
		super.paint(g);
		
		if(switchScreen.get(0)) {
			drawGameState(g);
		}
		else if(switchScreen.get(1)) {
			drawHTP(g);
		}
		else if(switchScreen.get(2)) {
			drawCredits(g);
		}
		else if(switchScreen.get(3)) {
			drawWin(g);
		}
		else if(switchScreen.get(4)) {
			drawLoading(g);
		}
		else {
			drawMainMenu(g);
		}
	}
	
	private void drawGameState(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, width, height);
		
		g.setColor(new Color(50, 50, 50));
		for(int i = 0; i<width; i+=80)
			g.drawLine(i, 0, i, height);
		for(int i = 0; i<height; i+=80)
			g.drawLine(0, i, width, i);
		
		manager.draw(g);
				
		g.setColor(Color.BLACK);
		g.setFont(testFont);
		g.drawString("FPS: " + Integer.toString(fpsDraw), 50, 50);
	}
	
	public void drawMainMenu(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, width, height);
		g.drawImage(Logo, width*140/1280, height*50/725, width*1000/1280, height*175/725, null);
		play.draw(g);
		HTP.draw(g);
		credits.draw(g);
	}
	
	public void drawHTP(Graphics g) {
		g.drawImage(HTPScreen, 0, 0, width, height, null);
		backHTP.draw(g);
	}
	
	public void drawCredits(Graphics g) {
		g.drawImage(creditsScreen, 0, 0, width, height, null);
		backCredits.draw(g);
	}
	
	public void drawWin(Graphics g) {
		drawGameState(g);
		/*Color opaqueWhite = new Color(255, 255, 255, 50);
		g.setColor(opaqueWhite);
		g.fillRect(0, 0, width, height);*/
		if(manager.getPlayers().get(0).getPIndex() == 0) {
			g.drawImage(winScreen1, width*5/18, (height-width*4/15)/2, width*4/9, width*4/15, null);
		}
		else {
			g.drawImage(winScreen2, width*5/18, (height-width*4/15)/2, width*4/9, width*4/15, null);
		}
		backPA.draw(g);
		PA.draw(g);
	}
	
	public void drawLoading(Graphics g) {
		g.drawImage(loading, 0, 0, width, height, null);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		repaint();
		
		if(switchScreen.get(0)) {
			gameUpdate();
			fpsUpdate();
		}
		else if(switchScreen.get(1)) {
			updateHTP();
		}
		else if(switchScreen.get(2)) {
			updateCredits();
		}
		else if(switchScreen.get(3)) {
			updateWin();
		}
		else if(switchScreen.get(4)) {
			updateLoading();
		}
		else {
			updateMenu();
		}
		
		MenuMouseUpdate();
	}
	
	private void gameKeysUpdate() {
		int j = 0;
		int sign = 1;
		
		for(int i = 0; i<8; i++) {//checks keys for movement
			j = i/4;
			sign = (2*((i/2)%2)-1);
			if(isDown[i]&&manager.getPlayers().get(j).getVelocity()[(i+1)%2]*sign<6.0) {
				manager.getPlayers().get(j).updateVelocity(0.1*(i%2)*sign,0.1*((i+1)%2)*sign);
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
	
	private void MenuMouseUpdate() {
		if(!mouseClicked) {
			double xM = MouseInfo.getPointerInfo().getLocation().x-frameX;
			double yM = MouseInfo.getPointerInfo().getLocation().y-frameY;
			
			checkButton(play, xM, yM);
			checkButton(HTP, xM, yM);
			checkButton(credits, xM, yM);
			checkButton(backHTP, xM, yM);
			checkButton(backCredits, xM, yM);
			checkButton(backPA, xM, yM);
			checkButton(PA, xM, yM);
		}
	}
	
	private void checkButton(Button b, double x, double y) {
		if(b.contains(x, y))
			b.hoverButton();
		else
			b.defaultButton();
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
		if(manager.getPlayers().size()<2) {
			switchScreen.set(0, false);
			switchScreen.set(3, true);
			return;
		}
		gameKeysUpdate();
		gameMouseUpdate();
		manager.update();
	}
	
	private void fpsUpdate() {
		fps++;
		if(System.currentTimeMillis()-1000 >= fpsTime) {
			fpsTime = System.currentTimeMillis();
			fpsDraw = fps;
			fps = 0;
		}
	}
	
	private void updateMenu() {
		play.updateLocation(width*525/1280, height*275/725, width*200/1280, height*100/725);
		HTP.updateLocation(width*525/1280, height*400/725, width*200/1280, height*100/725);
		credits.updateLocation(width*525/1280, height*525/725, width*200/1280, height*100/725);
	}
	
	private void updateHTP() {
		backHTP.updateLocation(width*10/1280, height*600/725, width*200/1280, height*100/725);
	}
	
	private void updateCredits() {
		backCredits.updateLocation(width*10/1280, height*600/725, width*200/1280, height*100/725);
	}
	
	private void updateWin() { //fix
		PA.updateLocation(width*665/1280, (height-width*100/1280)/2 + width/15, width*200/1280, width*100/1280);
		backPA.updateLocation(width*425/1280, (height-width*100/1280)/2 + width/15, width*200/1280, width*100/1280);
	}
	
	private void updateLoading() { //fix
		if(loadingInitial<0) {
			loadingInitial = System.currentTimeMillis();
		}
		
		if(System.currentTimeMillis()-loadingTime>loadingInitial) {
			loadingInitial = -1;
			switchScreen.set(4, false);
			switchScreen.set(0, true);
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		if(!switchScreen.get(0)&&!switchScreen.get(4)) {
			if(switchScreen.get(1)) {
				buttonChecksHTP();
			}
			else if(switchScreen.get(2)) {
				buttonChecksCredits();
			}
			else if(switchScreen.get(3)) {
				buttonChecksWin();
			}
			else {
				buttonChecksMain();
			}
		}
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
	
	private void buttonChecksMain() {
		if(play.contains(MouseInfo.getPointerInfo().getLocation().getX()-frameX, MouseInfo.getPointerInfo().getLocation().getY()-frameY)) {
			switchScreen.set(4, true);
			manager = new ObjectManager(width, height);
		}
		else if(HTP.contains(MouseInfo.getPointerInfo().getLocation().getX()-frameX, MouseInfo.getPointerInfo().getLocation().getY()-frameY)) {
			repaint();
			switchScreen.set(1, true);
		}
		else if(credits.contains(MouseInfo.getPointerInfo().getLocation().getX()-frameX, MouseInfo.getPointerInfo().getLocation().getY()-frameY)) {
			repaint();
			switchScreen.set(2, true);
		}
	}
	
	private void buttonChecksHTP() {
		if(backHTP.contains(MouseInfo.getPointerInfo().getLocation().getX()-frameX, MouseInfo.getPointerInfo().getLocation().getY()-frameY)) {
			repaint();
			switchScreen.set(1, false);
		}
	}
	
	private void buttonChecksCredits() {
		if(backCredits.contains(MouseInfo.getPointerInfo().getLocation().getX()-frameX, MouseInfo.getPointerInfo().getLocation().getY()-frameY)) {
			repaint();
			switchScreen.set(2, false);
		}
	}
	
	private void buttonChecksWin() { //fix
		if(PA.contains(MouseInfo.getPointerInfo().getLocation().getX()-frameX, MouseInfo.getPointerInfo().getLocation().getY()-frameY)) {
			repaint();
			switchScreen.set(3, false);
			switchScreen.set(0,  true);
			manager = new ObjectManager(width, height);
		}
		if(backPA.contains(MouseInfo.getPointerInfo().getLocation().getX()-frameX, MouseInfo.getPointerInfo().getLocation().getY()-frameY)) {
			repaint();
			switchScreen.set(3, false);
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		gameP1Controls(e, true);
		gameP2Controls(e, true);
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		gameP1Controls(e, false);
		gameP2Controls(e, false);
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
}