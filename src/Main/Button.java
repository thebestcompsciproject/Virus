//Fok
package Main;

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
	private BufferedImage pressButton;
	private BufferedImage drawButton;
	Button(double x, double y, double width, double height, BufferedImage img1, BufferedImage img2, BufferedImage img3) 
	{
		this.width = width;
		this.height = height;
		this.x = x;
		this.y = y;
		
		defaultButton = img1;
		hoverButton = img2;
		pressButton = img3;
		drawButton = defaultButton;
	}
	
	public boolean contains(double x1, double y1)
	{
		y1-=45;
		if(x1>=x&&x1<=x+width) {
			if(y1>=y&&y1<=y+height) {
				//System.out.println(y + " " + y1 + " " + (y+height));
				return true;
			}
		}
		return false;
	}
	
	public void draw(Graphics g) {
		 g.drawImage(drawButton, (int)x, (int)y, (int)width, (int)height, null);
		 g.drawRect((int)400, (int)400, (int)width, (int)height);
	}
	
	public void hoverButton() {
		drawButton = hoverButton;
	}
	
	public void defautlButton() {
		drawButton = defaultButton;
	}
	
	public void clickedButton() {
		drawButton = pressButton;
	}
	
}
