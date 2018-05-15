package Main;

import java.awt.Color;
import java.awt.Graphics;

public class Bullet extends MapTriangle{

	private double velX;
	private double velY;
	
	public Bullet(int x, int y, int direction, double side, Color color, double velX, double velY) {
		super(x, y, direction, side, color);
		this.velX = velX;
		this.velY = velY;
	}
	
	public void update() {
		x+=velX;
		y+=velY;
	}
}
