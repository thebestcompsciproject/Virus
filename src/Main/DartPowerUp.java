package Main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;

public class DartPowerUp extends GameObject 
{
	private double radius;
	private double height;
	
	public DartPowerUp(double x, double y, double direction, double radius, Color color) 
	
	{
		super();
		this.x = x;
		this.y = y;
		this.direction = direction;
		this.color = color;
		this.radius = radius;
	}
	
	public void draw(Graphics g) 
	{
		g.setColor(color);
		g.fillOval((int) x, (int) y, (int)(2*radius), (int) (2*radius));
		g.setColor(Color.BLACK);
		g.drawOval((int) x, (int) y, (int)(2*radius), (int) (2*radius));
		
	}
	
	public double getRadius()
	{
		return radius;
	}
}
	
	
