//Gurram-Muruhathasan-Fok
package Main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.awt.MouseInfo;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.Timer;

public class GamePanel extends JPanel implements KeyListener, ActionListener, MouseListener{

	private Timer timer;
	private boolean[] isDown;
	private ObjectManager manager;
	private int width;
	private int height;
	private int frameX;
	private int frameY;
	
	private int fps;
	long fpsTime;
	int fpsDraw;
	
	private final int reloadTime = 250;
	private long timeSave1 = 0;
	private long timeSave2 = 0;
	
	public BufferedImage defaultPlay;
	public BufferedImage hoverPlay;
	public BufferedImage clickedPlay;
	private boolean mouseClicked;
	Button p1;
	
	public GamePanel(int width, int height) {
		timer = new Timer(15, this);
		manager = new ObjectManager(width, height);
		mouseClicked = false;
		this.width = width;
		this.height = height;
		frameX = 0;
		frameY = 0;
		initiateIsDown();
		initiateFps();
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
	
	public void readImages() {
		try {
			defaultPlay = ImageIO.read(this.getClass().getResourceAsStream("PlayBlank.png"));
			hoverPlay = ImageIO.read(this.getClass().getResourceAsStream("PlayHover.png"));
			clickedPlay = ImageIO.read(this.getClass().getResourceAsStream("PlayClicked.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void makeButtons() {
		p1 = new Button(400, 400, 500, 250, defaultPlay, hoverPlay, clickedPlay);
	}
	
	public void initiateIsDown() {
		isDown = new boolean[11];
		for(int i = 0; i<8; i++) {
			isDown[i] = false;
		}
	}
	
	public void initiateFps() {
		fps = 60;
		fpsTime = 0;
		fpsDraw = 60;
	}
	
	public void start() {
		timer.start();
	}
	
	public void drawGameState(Graphics g) {
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, width, height);
		g.setColor(new Color(192, 192, 192));
		
		for(int i = 0; i<width; i+=80)
			g.drawLine(i, 0, i, height);
		for(int i = 0; i<height; i+=80)
			g.drawLine(0, i, width, i);
		
		manager.draw(g);
		fps++;
		
		if(System.currentTimeMillis()-1000 >= fpsTime) {
			fpsTime = System.currentTimeMillis();
			fpsDraw = fps;
			fps = 0;
		}
		
		g.setColor(Color.BLACK);
		g.drawString("FPS: " + Integer.toString(fpsDraw), 50, 50);
	}
	
	public void paint(Graphics g) {
		super.paint(g);
		drawGameState(g);
		//p1.draw(g);
	}
	
	public void resistance() {
		manager.getPlayers().get(0).updateVelocity(-manager.getPlayers().get(0).getVelocity()[0]*.01, -manager.getPlayers().get(0).getVelocity()[1]*.01);
		manager.getPlayers().get(1).updateVelocity(-manager.getPlayers().get(1).getVelocity()[0]*.01, -manager.getPlayers().get(1).getVelocity()[1]*.01);
	}
	
	public void gameKeysUpdate() {
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
	
	public void screenMouseUpdate() {
		if(!mouseClicked) {
			double xM = MouseInfo.getPointerInfo().getLocation().x-frameX;
			double yM = MouseInfo.getPointerInfo().getLocation().y-frameY;
		
			if(p1.contains(xM, yM)) {
				p1.hoverButton();
			}
			else {
				p1.defautlButton();
			}
		}
	}
	
	public void gameMouseUpdate() {
		double x1 = manager.getPlayers().get(1).getX();
		double y1 = manager.getPlayers().get(1).getY();
		double x2 = MouseInfo.getPointerInfo().getLocation().getX()-frameX;
		double y2 = MouseInfo.getPointerInfo().getLocation().getY()-frameY;
		double angle = getAngle(x1, y1, x2, y2);
		angle = (angle+360)%360;
		//manager.getPlayers().get(1).setDirection(angle);
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
	
	public double getAngle(double x1, double y1, double x2, double y2) {
		double delX = x2 - x1;
		double delY = y2 - y1;
		double thetaR = Math.atan2(delX, delY);
		return thetaR*360/(2*Math.PI);
	}
	
	
	public void gameUpdate() {
		manager.update();
		gameKeysUpdate();
		resistance();
		gameMouseUpdate();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		repaint();
		gameUpdate();
		//screenMouseUpdate();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void buttonChecks() {
		if(p1.contains(MouseInfo.getPointerInfo().getLocation().getX()-frameX, MouseInfo.getPointerInfo().getLocation().getY()-frameY)) {
			p1.clickedButton();
		}
	}
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		//buttonChecks();
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
	
	public void gameP1Controls(KeyEvent e, boolean state) {
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
	
	public void gameP2Controls(KeyEvent e, boolean state) {
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
		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			manager.getPlayers().get(0).addTriangle();
		}
		if(e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
			manager.getPlayers().get(0).removeLastTriangle();
		}
		
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
