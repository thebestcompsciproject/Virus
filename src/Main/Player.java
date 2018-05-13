//Muruhathasan
package Main;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

public class Player extends GameObject{

	private ArrayList<PlayerTriangle> reserve;
	private ArrayList<Boolean> drawn;
	private Core core;
	private double[] velocity;
	private int pIndex;
	
	double side = 40;
	double height = (Math.sqrt(3)*side)/2;
	
	public Player(double x, double y, double direction, Color color, int pIndex) {
		super();
		reserve = new ArrayList<PlayerTriangle>();
		drawn = new ArrayList<Boolean>();
		this.x = x;
		this.y = y;
		this.direction = direction;
		this.color = color;
		velocity = new double[2];
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
					reserve.add(new PlayerTriangle(x, y, dir, side, "layer", this, index, color));
					index++;
					x += Math.sin(Math.toRadians(j*60+120-60*(k%2)))*(2*height/3);
					y += Math.cos(Math.toRadians(j*60+120-60*(k%2)))*(2*height/3);
					dir = (dir+180)%360;
				}
				reserve.add(new PlayerTriangle(x, y, dir, side, "layer", this, index, color));
				index++;
				dir = (dir+180)%360;
			}
		}
	}
	
	public void constructDrawn() {
		for(int i = 0; i<18; i++) {
			drawn.add(true);
		}
		for(int i = 0; i<210-18; i++) {
			drawn.add(false);
		}
	}
	
	public void update() {
		x+=velocity[0];
		y+=velocity[1];
		if(x<0) {
			setVelocity(-velocity[0],velocity[1]);
		}
		if(y<0) {
			setVelocity(velocity[0],-velocity[1]);
		}
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
		
		g.drawLine((int)x, (int)y, (int)(x+2*Math.sin(Math.toRadians(direction))*side), (int)(y+2*Math.cos(Math.toRadians(direction))*side));
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
	
	public void removeLastTriangle() {
		for(int i = 210-1; i>=0; i--) {
			if(drawn.get(i)) {
				drawn.set(i, false);
				break;
			}
		}
	}
	
	public void updateVelocity(double x, double y) {
		velocity[0]+=x;
		velocity[1]+=y;
	}
	
	public void setVelocity(double x, double y) {
		velocity[0] = x;
		velocity[1] = y;
	}
	
	public void updateDirection(double change) {
		direction = (direction+change)%360;
	}
		
	public ArrayList<PlayerTriangle> getReserve(){
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
	
	public double[] getVelocity() {
		return velocity;
	}
	
	public int getPIndex() {
		return pIndex;
	}
}