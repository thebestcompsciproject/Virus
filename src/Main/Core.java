//Tomar-Fok-Muruhathasan

package Main;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

public class Core extends GameObject{

	private ArrayList<Triangle> reserve;
	private ArrayList<Boolean> drawn;
	private Player player;
	
	public Core(Player player) {
		super();
		this.player = player;
		reserve = new ArrayList<Triangle>();
		drawn = new ArrayList<Boolean>();
		initiateReserve();
		initiateDrawn();
		this.color = Color.BLACK;
	}
	
	public void initiateReserve() {
		double direction = 180;
		double height = player.getHeight();
		for(int i = 0; i<6; i++) {
			Triangle add = new Triangle((2*height/3)*Math.sin(Math.toRadians(i*60)), (2*height/3)*Math.cos(Math.toRadians(i*60)), direction, height, "core", player, i, color);
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
			if(drawn.get(i))
				reserve.get(i).draw(g);
		}
	}
	
	public void removeTriangle(int index) {
		drawn.set(index, false);
		if(index%2==1)
			drawn.set(index-1, false);
		else
			drawn.set(index+1, false);
	}
	
	public ArrayList<Triangle> getReserve(){
		return reserve;
	}
	
	public ArrayList<Boolean> getDrawn(){
		return drawn;
	}
}

