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
		// TODO Auto-generated method stub
		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			p.addTriangle();
		}
		if(e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
			p.removeTriangle();
		}
		if(e.getKeyCode() == KeyEvent.VK_W) {
			p.changeVelocity(0,-0.25);
		}
		if(e.getKeyCode() == KeyEvent.VK_A) {
			p.changeVelocity(-0.25,0);
		}
		if(e.getKeyCode() == KeyEvent.VK_S) {
			p.changeVelocity(0,0.25);
		}
		if(e.getKeyCode() == KeyEvent.VK_D) {
			p.changeVelocity(0.25,0);
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

}
