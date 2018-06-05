//Muruhathasan
package Main.PowerUps;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;
import java.awt.image.BufferedImage;

import Main.GameObject;
import Main.GamePanel;

public class DartPowerUp extends GameObject {
	private long spawnTime;
	private double dRadius;
	private double radius;
	private boolean grow = false;
	private BufferedImage infection;
	
	public DartPowerUp(double x, double y, double radius) {
		super();
		this.x = x;
		this.y = y;
		this.radius = radius;
		dRadius = radius;
		spawnTime = System.currentTimeMillis();
		infection = GamePanel.infection;
	}
	
	public void draw(Graphics g) {
		g.setColor(Color.WHITE);
		g.fillOval((int)x, (int)y, (int)(2*dRadius), (int) (2*dRadius));
		g.setColor(Color.BLACK);
		g.drawOval((int)x, (int)y, (int)(2*dRadius), (int) (2*dRadius));
		g.drawImage(infection, (int)(x+dRadius/3), (int)(y+dRadius/3), (int)(4*dRadius/3), (int)(4*dRadius/3), null);
	}
	
	public void update() {
		pulse();
		if( (System.currentTimeMillis() - 9000) > spawnTime) {
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
	
	
