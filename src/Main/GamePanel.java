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
import java.awt.MouseInfo;

import javax.swing.JPanel;
import javax.swing.Timer;

public class GamePanel extends JPanel implements KeyListener, ActionListener, MouseListener{

	private Timer timer;
	private boolean[] isDown;
	private ObjectManager manager;
	private int fps;
	long fpsTime;
	int fpsDraw;
	
	public GamePanel() {
		timer = new Timer(15, this);
		manager = new ObjectManager();
		initiateIsDown();
		initiateFps();
		
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
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, 1000, 800);
		g.setColor(new Color(192, 192, 192));
		/*for(int i = 0; i<13; i++) {
			g.drawLine(i*80, 0, i*80, 1000);
			g.drawLine(0, i*80, 1000, i*80);
		}*/
		manager.draw(g);
	}
	
	public void paint(Graphics g) {
		super.paint(g);
		drawGameState(g);
		fps++;
		if(System.currentTimeMillis()-1000 >= fpsTime) {
			fpsTime = System.currentTimeMillis();
			fpsDraw = fps;
			fps = 0;
		}
		g.setColor(Color.BLACK);
		g.drawString("FPS: " + Integer.toString(fpsDraw), 50, 50);
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
			manager.getPlayers().get(0).updateDirection(2.0);
		}
		if(isDown[9]) {
			manager.getPlayers().get(0).updateDirection(-2.0);
		}
		if(isDown[10]) {
			manager.shootBullet(0);
		}
	}
	
	public void gameMouseUpdate() {
		double xM = MouseInfo.getPointerInfo().getLocation().x;
		double yM = MouseInfo.getPointerInfo().getLocation().y;
		//getAngle();
	}
	
	public void gameUpdate() {
		manager.update();
		gameKeysUpdate();
		resistance();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		repaint();
		gameUpdate();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		manager.shootBullet(1);
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
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
