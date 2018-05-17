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
	private BufferedImage drawButton; Button(double x, double y, double width, double height, BufferedImage img1, BufferedImage img2, BufferedImage img3) 
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
	
	
	/*
	 * Check if the cursor is in the area of the button
	 * 
	 * 	**INCOMPLETE**
	 */
	public boolean contains(double x, double y)
	{
		if(x>=this.x&&x<=this.x+width) {
			if(y>=this.y&&y<this.y+height) {
				return true;
			}
		}
		return false;
	}
	
	
	/*
	 * Constructs the button in the JPanel
	 * 
	 * 	**INCOMPLETE**
	 */
	public void draw(Graphics g) {
		 g.drawImage(drawButton, (int)x, (int)y, (int)width, (int)height, null);
	}
	
	/*
	 * Switches the buttons
	 * 
	 * 	**INCOMPLETE**
	 */
	public void hoverButton() {
		drawButton = hoverButton;
	}
	
	public void defautlButton() {
		drawButton = defaultButton;
	}
	
}
