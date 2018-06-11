//Muruhathasan
package package1;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;

public class MapTriangle extends GameObject{

	protected double side;
	protected double height;
	
	public MapTriangle(double x, double y, double direction, double side, Color color) {
		super();
		this.x = x;
		this.y = y;
		this.direction = direction;
		this.color = color;
		this.side = side;
		height = (Math.sqrt(3)*side)/2;
	}
	
	public void draw(Graphics g) {
		int[] xcord = new int[3];
		int[] ycord = new int[3];
		
		for(int i = 0 ; i<3; i++) {
			xcord[i] = (int)(x+2.0/3*height*Math.sin(Math.toRadians(direction+i*120)));
			ycord[i] = (int)(y+2.0/3*height*Math.cos(Math.toRadians(direction+i*120)));
		}
		
		Polygon p = new Polygon(xcord, ycord, 3);
		g.setColor(color);
		g.fillPolygon(p);
		g.setColor(Color.BLACK);
		g.drawPolygon(p);
	}
}
