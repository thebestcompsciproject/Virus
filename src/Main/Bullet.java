//Muruhathasan
package Main;

import java.awt.Color;
import java.awt.Graphics;

public class Bullet extends MapTriangle{

	private double finalD;
	private double velX = 0;
	private double velY = 0;
	private int pIndex;
	
	public Bullet(double x, double y, double direction, double side, Color color, double finalD, int pIndex) {
		super(x, y, direction, side, color);
		this.finalD = finalD;
		this.pIndex = pIndex;
		velY = Math.cos(Math.toRadians(finalD))*8;
		velX = Math.sin(Math.toRadians(finalD))*8;
	}
	
	public void update() {
		velX = Math.sin(Math.toRadians(finalD))*10;
		velY = Math.cos(Math.toRadians(finalD))*10;
		x+=velX;
		y+=velY;
	}

	public int getPIndex() {
		return pIndex;
	}
	
	public void updateVelocity(double x, double y) {
		velX+=x;
		velY+=y;
	}
	
	public void updateFinalD(double d) {
		finalD+=d;
	}

	public double getFinalD() {
		return finalD;
	}
}
