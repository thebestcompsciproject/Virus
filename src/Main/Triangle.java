package GamePractice;

import java.awt.Graphics;

public class Triangle extends GameObject{

	private double xref;
	private double yref;
	private double direction;
	private double side;
	private String type;
	private Player player;
	private int index;
	
	private double height = Math.sqrt(3)*side/2;
	private double x;
	private double y;
	
	public Triangle(double xref, double yref, double direction, double side, String type, Player player, int index) {
		this.xref = xref;
		this.yref = yref;
		this.direction = direction;
		this.side = side;
		this.type = type;
		this.player = player;
		this.index = index;
		x = xref + player.getX();
		y = yref + player.getY();
	}
	
	public void update() {
		x = xref+player.getX();
		y = yref+player.getY();
	}
	
	public void draw(Graphics g) {
		int[] xcord = new int[3];
		int[] ycord = new int[3];
		for(int i = 0 ; i<3; i++) {
			xcord[i] = (int)(x+2*(height/3)*Math.sin(Math.toRadians(direction+i*120)));
			ycord[i] = (int)(y+2*(height/3)*Math.cos(Math.toRadians(direction+i*120)));
		}
		g.drawPolygon(xcord, ycord, 3);
	}

	public double getX() {
		return x;
	}
	
	public double getY() {
		return y;
	}
	
	public double getDirection() {
		return direction;
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




