//Muruhathasan
package Main;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

public class Player extends GameObject{

	private ArrayList<Triangle> reserve;
	private ArrayList<Boolean> drawn;
	private Core core;
	private double xVel;
	private double yVel;
	
	double side = 40;
	double height = (Math.sqrt(3)*side)/2;
	
	public Player(double x, double y, double direction, Color color) {
		super();
		reserve = new ArrayList<Triangle>();
		drawn = new ArrayList<Boolean>();
		this.x = x;
		this.y = y;
		this.direction = direction;
		this.color = color;
		xVel = 0;
		yVel = 0;
		core = new Core(this);
		constructTriangles();
		constructDrawn();
	}
	
	public void constructTriangles() {
		double dir = 180;
		int index = 0;
		
		for(int i = 0; i<5; i++) {
			for(int j = 0; j<6; j++) {
				double xref = Math.sin(Math.toRadians(j*60-30))*(side/2+side*(i+1));
				double yref = Math.cos(Math.toRadians(j*60-30))*(side/2+side*(i+1));
				double x = xref + Math.sin(Math.toRadians(j*60+60))*(height/3);
				double y = yref + Math.cos(Math.toRadians(j*60+60))*(height/3);
				for(int k = 0 ; k<(i+1)*2; k++) {
					//System.out.println("(" + x + ", " + y + ")");
					reserve.add(new Triangle(x, y, dir, side, "layer", this, index, color));
					index++;
					x += Math.sin(Math.toRadians(j*60+120-60*(k%2)))*(2*height/3);
					y += Math.cos(Math.toRadians(j*60+120-60*(k%2)))*(2*height/3);
					dir = (dir+180)%360;
				}
				//System.out.println("(" + x + ", " + y + ")");
				reserve.add(new Triangle(x, y, dir, side, "layer", this, index, color));
				index++;
				dir = (dir+180)%360;
			}
		}
	}
	
	public void constructDrawn() {
		for(int i = 0; i<210; i++) {
			drawn.add(false);
		}
	}
	
	public void update() {
		x+=xVel;
		y+=yVel;
		for(int i = 0; i<210; i++) {
			reserve.get(i).setCenter(x, y);
		}
		for(int i = 0; i<6; i++) {
			core.getReserve().get(i).setCenter(x, y);
		}
	}
	public void draw(Graphics g) {
		core.draw(g);
		for(int i = 0; i<210; i++) {
			if(drawn.get(i))
				reserve.get(i).draw(g);
		}
	}
	
	public void addTriangle() {
		for(int i = 0; i<210; i++) {
			if(!drawn.get(i)) {
				drawn.set(i, true);
				break;
			}
		}
	}
	
	public void removeTriangle(int index) {
		drawn.set(index, false);
	}
	
	public void removeTriangle() {
		for(int i = 210-1; i>=0; i--) {
			if(drawn.get(i)) {
				drawn.set(i, false);
				break;
			}
		}
	}
	
	public void updateVelocity(double x, double y) {
		xVel+=x;
		yVel+=y;
	}
		
	public ArrayList<Triangle> getReserve(){
		return reserve;
	}
	
	public ArrayList<Boolean> getDrawn(){
		return drawn;
	}
	
	public Core getCore() {
		return core;
	}

	public double getHeight() {
		return height;
	}
}