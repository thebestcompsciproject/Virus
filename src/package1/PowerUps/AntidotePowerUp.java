//Gurram-Muruhathasan
package package1.PowerUps;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

import package1.GameObject;
import package1.GamePanel;

public class AntidotePowerUp extends GameObject {
	private long spawnTime;
	private double dRadius;
	private double radius;
	private boolean grow = false;
	private BufferedImage antidote;
		
	public AntidotePowerUp(double x, double y, double radius) {
		super();
		this.x = x;
		this.y = y;
		this.radius = radius;
		dRadius = radius;
		spawnTime = System.currentTimeMillis();
		antidote = GamePanel.antidote;
	}
	
	public void draw(Graphics g) {
		g.setColor(Color.WHITE);
		g.fillOval((int) x, (int) y, (int)(2*dRadius), (int) (2*dRadius));
		g.setColor(Color.BLACK);
		g.drawOval((int) x, (int) y, (int)(2*dRadius), (int) (2*dRadius));
		g.drawImage(antidote, (int)(x+dRadius/2), (int)(y+2*dRadius/5), (int)(dRadius), (int)(6*dRadius/5), null);
	}
		
	public void update() {
		pulse();
		if( (System.currentTimeMillis() - 7000) > spawnTime) {
			this.kill();
		}
	}
		
	private void pulse() {
		if(dRadius>=radius)
			grow = false;
		if(dRadius<=2*radius/3)
			grow = true;
		
		if(grow) {
			dRadius+=.15;
			x-=.15;
			y-=.15;
		}
		else {
			dRadius-=.15;
			x+=.15;
			y+=.15;
		}
	}
		
	public double getRadius() {
		return dRadius;
	}
}

