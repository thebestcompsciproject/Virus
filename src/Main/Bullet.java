//Muruhathasan
package Main;

import java.awt.Color;
import java.awt.Graphics;

public class Bullet extends MapTriangle{

	private double finalD;
	private double velX = 0;
	private double velY = 0;
	private int pIndex;
	private boolean attract;
	
	public Bullet(double x, double y, double direction, double side, Color color, double finalD, int pIndex, boolean attract) {
		super(x, y, direction, side, color);
		this.finalD = finalD;
		this.pIndex = pIndex;
		velY = Math.cos(Math.toRadians(finalD))*8;
		velX = Math.sin(Math.toRadians(finalD))*8;
		this.attract = attract;
	}
	
	public void update() {
		/*if(Math.sqrt(Math.pow(velX, 2)+Math.pow(velY, 2))<10) {
			velX+=Math.sin(Math.toRadians(finalD))*.4;
			velY+=Math.cos(Math.toRadians(finalD))*.4;
		}*/
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

	public double getFinalD() {
		return finalD;
	}

	public boolean getAttract() {
		return attract;
	}
}
