//Gurram-Muruhathasan
package Main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;
import javax.swing.Timer;

public class GamePanel extends JPanel implements KeyListener, ActionListener, MouseListener{

	Timer timer;
	Player p;
	boolean[] isDown;
	
	public GamePanel() {
		timer = new Timer(5, this);
		p = new Player(400,400, 0, Color.PINK);
		initiateIsDown();
		
	}
	
	public void initiateIsDown() {
		isDown = new boolean[8];
		for(int i = 0; i<8; i++) {
			isDown[i] = false;
		}
	}
	
	public void start() {
		timer.start();
	}
	
	public void paint(Graphics g) {
		super.paint(g);
		p.draw(g);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		repaint();
		p.update();
		for(int i = 0; i<8; i++) {
			if(isDown[i]) {
				p.updateVelocity(0.01*(i%2)*(2*(i/2)-1),0.01*((i+1)%2)*(2*(i/2)-1));
			}
		}
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Use link below to attempt a smoother moving player
		//https://stackoverflow.com/questions/15329117/smooth-out-java-paint-animations?utm_medium=organic&utm_source=google_rich_qa&utm_campaign=google_rich_qa
		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			p.addTriangle();
		}
		if(e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
			p.removeTriangle();
		}
		if(e.getKeyCode() == KeyEvent.VK_W) {
			isDown[0] = true;
		}
		if(e.getKeyCode() == KeyEvent.VK_A) {
			isDown[1] = true;

		}
		if(e.getKeyCode() == KeyEvent.VK_S) {
			isDown[2] = true;

		}
		if(e.getKeyCode() == KeyEvent.VK_D) {
			isDown[3] = true;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		if(e.getKeyCode() == KeyEvent.VK_W) {
			isDown[0] = false;
		}
		if(e.getKeyCode() == KeyEvent.VK_A) {
			isDown[1] = false;
		}
		if(e.getKeyCode() == KeyEvent.VK_S) {
			isDown[2] = false;
		}
		if(e.getKeyCode() == KeyEvent.VK_D) {
			isDown[3] = false;
		}
	}

}
