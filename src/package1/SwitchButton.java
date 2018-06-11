package package1;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class SwitchButton {

	double x;
	double y;
	double width;
	double height;
	BufferedImage off;
	BufferedImage on;
	BufferedImage current;
	boolean state;
	
	public SwitchButton(BufferedImage a, BufferedImage b) {
		off = a;
		on = b;
		current = off;
		state = false;
		x = 0;
		y = 0;
		width = 0;
		height = 0;
	}
	
	public void updateLocation(double x, double y, double width, double height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	public void draw(Graphics g) {
		g.drawImage(current, (int)x, (int)y, (int)width, (int)height, null);
	}
	
	public void switchState() {
		state = !state;
		if(current.equals(on)) {
			current = off;
		}
		else {
			current = on;
		}
	}
	
	public boolean contains(double x1, double y1) {
		y1-=20;
		if(x1>=x&&x1<=x+width) {
			if(y1 >= y && y1 <= (y + height)) {
				return true;
			}
		}
		return false;
	}
	
	public boolean getState() {
		return state;
	}
}
