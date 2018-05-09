package Main;

import java.awt.Graphics;
import java.util.ArrayList;

public class Core extends Player{

	private ArrayList<Triangle> reserve;
	private ArrayList<Boolean> drawn;
	private Player player;
	
	public Core(double x, double y, double direction, Player player) {
		super(x, y, direction);
		this.player = player;
		reserve = new ArrayList<Triangle>();
		drawn = new ArrayList<Boolean>();
	}
	
	public void initiateReserve() {
		double direction = 180;
		height = player.getHeight();
		for(int i = 0; i<6; i++) {
			Triangle add = new Triangle((2*height/3)*Math.sin(Math.toRadians(i*60)), (2*height/3)*Math.cos(Math.toRadians(i*60)), direction, height, "core", player, i);
			reserve.add(add);
			direction = (direction+180)%360;
		}
	}
	
	public void initiateDrawn() {
		for(int i = 0; i<6; i++) {
			drawn.add(true);
		}
	}

	public void draw(Graphics g) {
		// TODO Auto-generated method stub
		for(int i = 0; i<6; i++) {
			if(drawn.get(i)) {
				reserve.get(i).draw(g);
			}
		}
	}
	
	public void removeTriangle(int index) {
		drawn.set(index, false);
		if(index%2==1)
			drawn.set(index-1, false);
		else
			drawn.set(index+1, false);
	}
}


