//Fok-Muruhathasan
package package1.PowerUps;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;

import package1.GameObject;

public class ParalyzePowerUp extends GameObject {
	private long spawnTime;
	private double dRadius;
	private double radius;
	private boolean grow = false;
	
	public ParalyzePowerUp( double x, double y, double radius) {
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
		drawBolt(g);
	}
	
	private void drawBolt(Graphics g) {
		g.setColor(Color.YELLOW);
		int[] xC = new int[6];
		int[] yC = new int[6];
		
		xC[0] = (int)(x+4*dRadius/3);
		xC[1] = (int)(x+2*dRadius/3);
		xC[2] = (int)(x+dRadius);
		xC[3] = (int)(x+2*dRadius/3);
		xC[4] = (int)(x+4*dRadius/3);
		xC[5] = (int)(x+dRadius);
		
		yC[0] = (int)(y+dRadius/3);
		yC[1] = (int)(y+3*dRadius/4);
		yC[2] = (int)(y+5*dRadius/4);
		yC[3] = (int)(y+5*dRadius/3);
		yC[4] = (int)(y+5*dRadius/4);
		yC[5] = (int)(y+3*dRadius/4);
		
		Polygon p = new Polygon(xC, yC, 6);
		g.fillPolygon(p);
		g.setColor(new Color(69, 69, 69));
		g.drawPolygon(p);
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
