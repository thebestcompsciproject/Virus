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
	
	public GamePanel() {
		timer = new Timer(5, this);
		p = new Player(400,400, 0, Color.PINK);
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
		
		int lead = 0; //front
		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			p.addTriangle();
		}
		if(e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
			p.removeTriangle();
		}
		if(e.getKeyCode() == KeyEvent.VK_W) {
			if (lead != 0){
				p.changeVelocity(0, -0.5);
			}
			else {			
			p.changeVelocity(0,-0.1);
			lead = 0;
			}
		}
		if(e.getKeyCode() == KeyEvent.VK_A) {
			if (lead != -90){ //left
				p.changeVelocity(-.5, 0);
			}
			else {
			p.changeVelocity(-0.1,0);
			lead = -90;
			}
		}
		if(e.getKeyCode() == KeyEvent.VK_S) {
			if (lead != 180){
				p.changeVelocity(0,0.50);
			}
			else {
			p.changeVelocity(0,0.1);
			lead = 180;
			}
		}
		if(e.getKeyCode() == KeyEvent.VK_D) {
			if (lead != 90){
				p.changeVelocity(0.50, 0);
			}
			else {
			p.changeVelocity(0.1,0);
			lead = 90;
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

}
