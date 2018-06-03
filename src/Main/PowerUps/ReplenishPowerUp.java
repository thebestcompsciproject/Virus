//Fok-Muruhathasan
package Main.PowerUps;

import java.awt.Color;
import java.awt.Graphics;

import Main.GameObject;

public class ReplenishPowerUp extends GameObject {
	private long spawnTime;
	private double dRadius;
	private double radius;
	private boolean grow = false;
	
	public ReplenishPowerUp( double x, double y, double radius) {
		super();
		this.x = x;
		this.y = y;
		this.radius = radius;
		dRadius = radius;
		spawnTime = System.currentTimeMillis();
	}
	
	public void draw(Graphics g) {
		g.setColor(Color.WHITE);
		g.fillOval((int) x, (int) y, (int)(2*dRadius), (int) (2*dRadius));
		g.setColor(Color.BLACK);
		g.drawOval((int) x, (int) y, (int)(2*dRadius), (int) (2*dRadius));
		g.setColor(Color.RED);
		g.fillRect((int)(x+(9*dRadius/10)), (int)(y+(dRadius/2)), (int)(2*dRadius/10), (int)dRadius);
		g.fillRect((int)(x+(dRadius/2)), (int)(y+(9*dRadius/10)), (int)dRadius, (int)(2*dRadius/10));
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
		return radius;
	}
}
