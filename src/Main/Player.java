package Main;

import java.awt.Graphics;
import java.util.ArrayList;

public class Player extends GameObject{

	private ArrayList<Triangle> reserve;
	private ArrayList<Boolean> drawn;
	private Core core;
	
	double side = 1;
	double height = (Math.sqrt(3)*side)/2;
	
	public Player(double x, double y, double direction) {
		super();
		reserve = new ArrayList<Triangle>();
		drawn = new ArrayList<Boolean>();
		this.x = x;
		this.y = y;
		this.direction = direction;
		core = new Core(this);
		constructTriangles();
		constructDrawn();
	}
	
	public void constructTriangles() {
		double height = Math.sqrt(3)*side/2;
		double dir = 180;
		int index = 0;
		
		for(int i = 0; i<5; i++) {
			for(int j = 0; j<6; j++) {
				double xref = Math.sin(Math.toRadians(j*60-30))*(side/2+side*(i+1));
				double yref = Math.cos(Math.toRadians(j*60-30))*(side/2+side*(i+1));
				double x = xref + Math.sin(Math.toRadians(j*60+60))*(height/3);
				double y = yref + Math.cos(Math.toRadians(j*60+60))*(height/3);
				for(int k = 0 ; k<(i+1)*2; k++) {
					System.out.println("(" + x + ", " + y + ")"); //add a new triangle
					reserve.add(new Triangle(x, y, dir, side, "layer", this, index));
					index++;
					x += Math.sin(Math.toRadians(j*60+120-60*(k%2)))*(2*height/3);
					y += Math.cos(Math.toRadians(j*60+120-60*(k%2)))*(2*height/3);
					dir = (dir+180)%360;
				}
				System.out.println("(" + x + ", " + y + ")"); //add a new triangle
				reserve.add(new Triangle(x, y, dir, side, "layer", this, index));
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
	
	public void draw(Graphics g) {
		core.draw(g);
		for(int i = 0; i<210; i++) {
			if(drawn.get(i))
				reserve.get(i).draw(g);
		}
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
		// TODO Auto-generated method stub
		return height;
	}
}


