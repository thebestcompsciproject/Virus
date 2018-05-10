//Muruhathasan
package Main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;

public class Triangle extends GameObject{

	private double xref;
	private double yref;
	private double side;
	private String type;
	private Player player;
	private int index;
	
	private double height;
	
	public Triangle(double xref, double yref, double direction, double side, String type, Player player, int index, Color color) {
		super();
		this.xref = xref;
		this.yref = yref;
		this.direction = direction;
		this.side = side;
		this.type = type;
		this.player = player;
		this.index = index;
		this.x = xref + player.getX();
		this.y = yref + player.getY();
		height = player.getHeight();
		this.color = color;
	}
	
	public void update() {
		x = xref+player.getX();
		y = yref+player.getY();
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
		g.drawPolygon(p);
	}
	
	public void setCenter(double x, double y) {
		this.x = x+xref;
		this.y = y+yref;
	}
	
	public double getSide() {
		return side;
	}
	
	public String getType() {
		return type;
	}
	
	public int getIndex() {
		return index;
	}
}