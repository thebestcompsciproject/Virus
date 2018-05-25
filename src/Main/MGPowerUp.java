package Main;
//test
import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

public class MGPowerUp extends GameObject

{
	private double x;
	private double y;
	private double radius;
	private double height;
	
	public MGPowerUp(double x, double y, double direction, double radius, Color color) 
	{
		super();
		this.x = x;
		this.y = y;
		this.direction = direction;
		this.color = color;
		this.radius = radius;
	}
	public void draw(Graphics g )
	{
		g.setColor(color);
		g.fillOval((int)x, (int)y, (int)(2*radius), (int)height);
		g.setColor(Color.BLACK);
		g.drawOval((int)x, (int)y, (int)(2*radius), (int)height);
	}
	public double getRadius()
	{
		return radius;
	}
}
