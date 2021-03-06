//Fok-Muruhathasan
package package1;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Button {

	private double x;
	private double y;
	private double width;
	private double height;
	
	private BufferedImage defaultButton;
	private BufferedImage hoverButton;
	private BufferedImage drawButton;
	
	Button(BufferedImage img1, BufferedImage img2) 
	{
		this.width = 0;
		this.height = 0;
		this.x = 0;
		this.y = 0;
		
		defaultButton = img1;
		hoverButton = img2;
		drawButton = defaultButton;
	}
	
	public boolean contains(double x1, double y1)
	{
		y1-=20;
		if(x1>=x&&x1<=x+width) {
			if(y1 >= y && y1 <= (y + height)) {
				return true;
			}
		}
		return false;
	}
	
	public void draw(Graphics g) {
		 g.drawImage(drawButton, (int)x, (int)y, (int)width, (int)height, null);
	}
	
	public void updateLocation(double x, double y, double width, double height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	public void hoverButton() {
		drawButton = hoverButton;
	}
	
	public void defaultButton() {
		drawButton = defaultButton;
	}
}
