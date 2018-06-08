//Muruhathasan-Fok-Gurram-Tomar
package Main;

import java.awt.Color;
import java.awt.Cursor;
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
import java.net.URL;
import java.util.ArrayList;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Toolkit;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

public class GamePanel extends JPanel implements KeyListener, ActionListener, MouseListener{

	private Timer timer;
	private boolean[] isDown;
	private ObjectManager manager;
	private Font testFont;
	
	private int opacity = 0;
	private boolean opacityControl = false;
	private boolean runTransition = false;
	
	private int width;
	private int height;
	private int frameX;
	private int frameY;
	
	private int fps;
	private long fpsTime;
	private int fpsDraw;
	
	private long loadingInitial = -1;
	private long loadingTimeMenu = 1500;
	
	private final int reloadTime = 300;
	private long timeSave1 = 0;
	private long timeSave2 = 0;
	
	private int lateImages = -5;
	
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
	
	public BufferedImage puListDefault;
	public BufferedImage puListHover;
	
	public BufferedImage Logo;
	public BufferedImage creditsScreen;
	public BufferedImage HTPScreen;
	public BufferedImage puListScreen;
	public BufferedImage winScreen1;
	public BufferedImage winScreen2;
	public BufferedImage loading;
		
	public static BufferedImage arcReactDefault;
	public static BufferedImage arcReactVirus;
	public static BufferedImage arcReactParalyze;
	
	public static BufferedImage antidote;
	public static BufferedImage infection;
	
	private boolean mouseClicked;
	
	private Button play;
	private Button HTP;
	private Button credits;
	private Button backHTP;
	private Button backCredits;
	private Button PA;
	private Button backPA;
	private Button toPUList;
	private Button backPUList;
	
	private final int menuState = 0;
	private final int playState = 1;
	private final int HTPState = 2;
	private final int creditsState = 3;
	private final int winState = 4;
	private final int loadingState = 5;
	private final int PUState = 6;
	
	private int currentState = loadingState;
	private int futureState = menuState;
	private int nextState = menuState;
	
	//CONSTRUCTOR
	
	public GamePanel(int width, int height) {
		timer = new Timer(15, this);
		mouseClicked = false;
		this.width = width;
		this.height = height;
		frameX = 0;
		frameY = 0;
		testFont = new Font ("Courier New", 1, 30);
		
		initiateIsDown();
		initiateFps();
		readLoading();
		makeButtons();
		PlayMusic test = new PlayMusic();
		test.loadInDaMusic();
		test.changeMusic(1);
		test.checkDaMusic();
		test.playDaMusic();
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
	
	private void initiateIsDown() {
		isDown = new boolean[12];
		for(int i = 0; i < 8; i++) {
			isDown[i] = false;
		}
	}
	
	private void initiateFps() {
		fps = 60;
		fpsTime = 0;
		fpsDraw = 60;
	}
	
	private void readLoading() {
		URL loading1 = this.getClass().getResource("Loading.png");
		
		try {
			loading = ImageIO.read(loading1);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	private void readLateImages() {
		URL defaultPlay_URL = this.getClass().getResource("PlayA.png");
		URL hoverPlay_URL = this.getClass().getResource("PlayB.png");
		URL defaultHTP_URL = this.getClass().getResource("HTPA.png");
		URL hoverHTP_URL = this.getClass().getResource("HTPB.png");
		URL defaultCredits_URL = this.getClass().getResource("CreditsA.png");
		URL hoverCredits_URL = this.getClass().getResource("CreditsB.png");
		URL defaultBack_URL = this.getClass().getResource("BackA.png");
		URL hoverBackURL = this.getClass().getResource("BackB.png");
		URL defaultPA_URL = this.getClass().getResource("PlayAgainA.png");
		URL hoverPA_URL = this.getClass().getResource("PlayAgainB.png");
		URL defaultPUL_URL = this.getClass().getResource("PUListdefault.PNG");
		URL hoverPUL_URL = this.getClass().getResource("PUListHover.PNG");

		
		URL logoURL = this.getClass().getResource("Logo1.png");
		URL credits_URL = this.getClass().getResource("Credits.png");
		URL HTP_URL = this.getClass().getResource("HTP.png");
		URL win1_URL = this.getClass().getResource("Win1.png");
		URL win2_URL = this.getClass().getResource("Win2.png");
		URL anti_URL = this.getClass().getResource("Anti.png");
		URL infection_URL = this.getClass().getResource("virus_v2.jpg");
		URL PUListS_URL = this.getClass().getResource("PUListScreen.PNG");
		
		URL arcReactorDefault_URL = this.getClass().getResource("arcReactorDefault.jpg");
		URL arcReactorVirus_URL = this.getClass().getResource("arcReactorVirus.jpg");
		URL arcReactorParalyze_URL = this.getClass().getResource("arcReactorParalyze.jpg");
		
		try {
			defaultPlay = ImageIO.read(defaultPlay_URL);
			hoverPlay = ImageIO.read(hoverPlay_URL);
			defaultHTP = ImageIO.read(defaultHTP_URL);
			hoverHTP = ImageIO.read(hoverHTP_URL);
			defaultCredits = ImageIO.read(defaultCredits_URL);
			hoverCredits = ImageIO.read(hoverCredits_URL);
			defaultBack = ImageIO.read(defaultBack_URL);
			hoverBack = ImageIO.read(hoverBackURL);
			defaultPA = ImageIO.read(defaultPA_URL);
			hoverPA = ImageIO.read(hoverPA_URL);
			puListDefault = ImageIO.read(defaultPUL_URL);
			puListHover = ImageIO.read(hoverPUL_URL);
			
			Logo  = ImageIO.read(logoURL);
			creditsScreen = ImageIO.read(credits_URL);
			HTPScreen = ImageIO.read(HTP_URL);
			winScreen1 = ImageIO.read(win1_URL);
			winScreen2 = ImageIO.read(win2_URL);
			puListScreen = ImageIO.read(PUListS_URL);
			
			arcReactDefault = ImageIO.read(arcReactorDefault_URL);
			arcReactVirus = ImageIO.read(arcReactorVirus_URL);
			arcReactParalyze = ImageIO.read(arcReactorParalyze_URL);
			
			antidote = ImageIO.read(anti_URL);
			infection = ImageIO.read(infection_URL);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		makeButtons();
	}
	
	private void makeButtons() {
		play = new Button(width*525/1280, height*275/725, width*200/1280, height*100/725, defaultPlay, hoverPlay);
		HTP = new Button(width*525/1280, height*400/725, width*200/1280, height*100/725, defaultHTP, hoverHTP);
		credits = new Button(width*525/1280, height*525/725, width*200/1280, height*100/725, defaultCredits, hoverCredits);
		backHTP = new Button(width*300/1280, height*600/725, width*200/1280, height*100/725, defaultBack, hoverBack);
		backCredits = new Button(width*10/1280, height*600/725, width*200/1280, height*100/725, defaultBack, hoverBack);
		PA = new Button(width*665/1280, (height-width*100/1280)/2 + width/15, width*200/1280, width*100/1280, defaultPA, hoverPA);
		backPA =  new Button(width*425/1280, (height-width*100/1280)/2 + width/15, width*200/1280, width*100/1280, defaultBack, hoverBack);
		toPUList  = new Button(width*10/1280 + width*200/1280 + width/100, (height-width*100/1280)/2 + width/15, width*200/1280, height*100/725, puListDefault, puListHover); //CHANGE COORDINATES
		backPUList = new Button(width*10/1280, height*600/725, width*200/1280, height*100/725, defaultBack, hoverBack);
	}
	
	//GRAPHICS
	
	public void paint(Graphics g) {
		super.paint(g);
		
		if(currentState == menuState) {
			drawMainMenu(g);
		}
		if(currentState == playState) {
			drawGameState(g);
		}
		else if(currentState == HTPState) {
			drawHTP(g);
		}
		else if(currentState == creditsState) {
			drawCredits(g);
		}
		else if(currentState == winState) {
			drawWin(g);
		}
		else if(currentState == loadingState) {
			drawLoading(g);
		}
		else if(currentState == PUState) {
			drawPUState(g);
		}
		
		if(runTransition) {
			drawTransition(g);
		}
		
		drawMouse(g);
	}
	
	public void drawMouse(Graphics g) {
		Toolkit toolkit = Toolkit.getDefaultToolkit();
	    Point hotSpot = new Point(0,0);
	    BufferedImage cursorImage = new BufferedImage(1, 1, BufferedImage.TRANSLUCENT); 
	    Cursor invisibleCursor = toolkit.createCustomCursor(cursorImage, hotSpot, "InvisibleCursor");        
	    setCursor(invisibleCursor);
	    double xM = MouseInfo.getPointerInfo().getLocation().x-frameX;
		double yM = MouseInfo.getPointerInfo().getLocation().y-frameY-20;
		g.setColor(new Color(241, 194, 50));
	    g.fillRect((int)(xM-1), (int)(yM-15), 2, 30);
	    g.fillRect((int)(xM-15), (int)(yM-1), 30, 2);
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
				
		g.setColor(Color.WHITE);
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
		toPUList.draw(g);
	}
	
	public void drawCredits(Graphics g) {
		g.drawImage(creditsScreen, 0, 0, width, height, null);
		backCredits.draw(g);
		
	}
	
	public void drawWin(Graphics g) {
		drawGameState(g);
		Color opaqueWhite = new Color(255, 255, 255, 50);
		g.setColor(opaqueWhite);
		g.fillRect(0, 0, width, height);
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
	
	public void drawPUState(Graphics g) {
		g.drawImage(puListScreen, 0, 0, width, height, null);
		backPUList.draw(g);
	}
	
	private void drawTransition(Graphics g) {
		g.setColor(new Color(0, 0, 0, opacity));
		g.fillRect(0, 0, width, height);
	}
	
	//UPDATES
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		repaint();
		updateAll();
		if(runTransition) {
			updateTransition();
		}
		else if(currentState == playState) {
			gameUpdate();
			fpsUpdate();
		}
		else if(currentState == loadingState) {
			updateLoading();
		}
		
		MenuMouseUpdate();
		images();
	}
	
	private void images(){
		if(lateImages < 0) {
			if(lateImages==-1) {
				readLateImages();
			}
			lateImages++;
		}
	}
	
	private void gameKeysUpdate() {
		int j = 0;
		int sign = 1;
		
		for(int i = 0; i<8; i++) {//checks keys for movement
			j = i/4;
			sign = (2*((i/2)%2)-1);
			if(!manager.getPlayers().get(j).isParalyzed()&&isDown[i]&&manager.getPlayers().get(j).getVelocity()[(i+1)%2]*sign<6.0) {
				manager.getPlayers().get(j).updateVelocity(0.1*(i%2)*sign,0.1*((i+1)%2)*sign);
			}
		}
	
		if(isDown[8]) {
			if(!manager.getPlayers().get(0).isParalyzed())
				manager.getPlayers().get(0).updateDirection(3.0);
		}
		
		if(isDown[9]) {
			if(!manager.getPlayers().get(0).isParalyzed())
				manager.getPlayers().get(0).updateDirection(-3.0);
		}
		
		if(isDown[10]) {
			if(!manager.getPlayers().get(0).isParalyzed()) {
				if(manager.getPlayers().get(0).getMG()) {
					if(System.currentTimeMillis()-2*reloadTime/3>timeSave1) {
						manager.shootBullet(0, 20);
						timeSave1 = System.currentTimeMillis();
					}
				}
				else if(System.currentTimeMillis()-reloadTime>timeSave1) {
					manager.shootBullet(0, 40);
					timeSave1 = System.currentTimeMillis();
				}
			}
		}
		if(isDown[11]){
			if(!manager.getPlayers().get(0).isParalyzed()) {
				if(manager.getPlayers().get(0).hasIDart()){
					manager.shootInfectedBullet(0);
				}
				if(manager.getPlayers().get(0).hasPDart()){
					manager.shootParalyzedBullet(0);
				}
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
			checkButton(toPUList, xM, yM);
			checkButton(backPUList, xM, yM);
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
		
		if(mouseClicked) {
			if(!manager.getPlayers().get(1).isParalyzed()) {
				if(manager.getPlayers().get(1).getMG()) {
					if(System.currentTimeMillis()-reloadTime*2/3>timeSave2) {
						manager.shootBullet(1, 20);
						timeSave2 = System.currentTimeMillis();
					}
				}
				else if(System.currentTimeMillis()-reloadTime>timeSave2) {
					manager.shootBullet(1, 40);
					timeSave2 = System.currentTimeMillis();
				}
			}
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
			currentState = winState;
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
	
	private void updateAll() {
		updateMenu();
		updateHTP();
		updateCredits();
		updateWin();
		updatePUList();
	}
	
	private void updateMenu() {
		play.updateLocation(width*525/1280, height*275/725, width*200/1280, height*100/725);
		HTP.updateLocation(width*525/1280, height*400/725, width*200/1280, height*100/725);
		credits.updateLocation(width*525/1280, height*525/725, width*200/1280, height*100/725);
	}
	
	private void updateHTP() {
		backHTP.updateLocation(width*175/1280, height*7/10, width*200/1280, height*100/725);
		toPUList.updateLocation(width*375/1280+width/50, height*7/10, width*200/1280, height*100/725); //CHANGE COORDINATES
	}
	
	private void updatePUList()
	{
		backPUList.updateLocation(width*10/1280, height*600/725, width*200/1280, height*100/725); //CHANGE COORDINATES
	}
	
	private void updateCredits() {
		backCredits.updateLocation(width*10/1280, height*600/725, width*200/1280, height*100/725);
	}
	
	private void updateWin() {
		PA.updateLocation(width*665/1280, (height-width*100/1280)/2 + width/15, width*200/1280, width*100/1280);
		backPA.updateLocation(width*425/1280, (height-width*100/1280)/2 + width/15, width*200/1280, width*100/1280);
		
	}
	
	private void updateLoading() {
		if(manager == null||manager.getPlayers().size()<2) {
			manager = new ObjectManager(width, height);
		}
		if(loadingInitial<0) {
			loadingInitial = System.currentTimeMillis();
		}
		
		if(System.currentTimeMillis()-loadingTimeMenu>loadingInitial) {
			loadingInitial = -1;
			manager = new ObjectManager(width, height);
			futureState = nextState;
			nextState = playState;
			runTransition = true;
		}
	}
	
	private void updateTransition() {
		if(opacity>=255) {
			opacityControl = true;
			currentState = futureState;
		}
		if(!opacityControl)
			opacity+=15;
		else
			opacity-=15;
		if(opacity == 0) {
			runTransition = false;
			opacityControl = false;
		}
	}

	//MOUSECHECKS
	
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		mouseClicked = true;
		if(currentState == playState) {
			if(SwingUtilities.isRightMouseButton(e)) {
				if(!manager.getPlayers().get(1).isParalyzed()) {
					if(manager.getPlayers().get(1).hasIDart()) {
						manager.shootInfectedBullet(1);
					}
					if(manager.getPlayers().get(1).hasPDart()) {
						manager.shootParalyzedBullet(1);
					}
				}
				mouseClicked = false;
			}
		}
		if(currentState == menuState) {
			buttonChecksMain();
		}
		if(currentState == HTPState) {
			buttonChecksHTP();
		}
		if(currentState == creditsState) {
			buttonChecksCredits();
		}
		if(currentState == PUState) {
			buttonChecksPUList();
		}
		if(currentState == winState) {
			buttonChecksWin();
		}
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
			futureState = loadingState;
			manager = new ObjectManager(width, height);
			runTransition = true;
		}
		else if(HTP.contains(MouseInfo.getPointerInfo().getLocation().getX()-frameX, MouseInfo.getPointerInfo().getLocation().getY()-frameY)) {
			futureState = HTPState;
			runTransition = true;
		}
		else if(credits.contains(MouseInfo.getPointerInfo().getLocation().getX()-frameX, MouseInfo.getPointerInfo().getLocation().getY()-frameY)) {
			futureState = creditsState;
			runTransition = true;
		}
	}
	
	private void buttonChecksHTP() {
		if(backHTP.contains(MouseInfo.getPointerInfo().getLocation().getX()-frameX, MouseInfo.getPointerInfo().getLocation().getY()-frameY)) {
			futureState = menuState;
			runTransition = true;
		}
		
		if(toPUList.contains(MouseInfo.getPointerInfo().getLocation().getX()-frameX, MouseInfo.getPointerInfo().getLocation().getY()-frameY)) {
			futureState = PUState;
			runTransition = true;
		}
	}
	
	private void buttonChecksPUList()
	{
		if(backPUList.contains(MouseInfo.getPointerInfo().getLocation().getX()-frameX, MouseInfo.getPointerInfo().getLocation().getY()-frameY)) {
			futureState = HTPState;
			runTransition = true;
		}
	}
	
	private void buttonChecksCredits() {
		if(backCredits.contains(MouseInfo.getPointerInfo().getLocation().getX()-frameX, MouseInfo.getPointerInfo().getLocation().getY()-frameY)) {
			futureState = menuState;
			runTransition = true;
		}
	}
	
	private void buttonChecksWin() {
		if(PA.contains(MouseInfo.getPointerInfo().getLocation().getX()-frameX, MouseInfo.getPointerInfo().getLocation().getY()-frameY)) {
			futureState = loadingState;
			runTransition = true;
		}
		if(backPA.contains(MouseInfo.getPointerInfo().getLocation().getX()-frameX, MouseInfo.getPointerInfo().getLocation().getY()-frameY)) {
			futureState = menuState;
			runTransition = true;
		}
	}
	
	//KEYCHECKS

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
		if(e.getKeyCode() == KeyEvent.VK_J) {
			isDown[8] = state;
		}
		if(e.getKeyCode() == KeyEvent.VK_K) {
			isDown[9] = state;
		}
		if(e.getKeyCode() == KeyEvent.VK_SPACE) {
			isDown[10] = state;
		}
		if(e.getKeyCode() == KeyEvent.VK_L){
			isDown[11] = state;
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