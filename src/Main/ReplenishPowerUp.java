package Main;
import java.awt.Color;
import java.awt.Graphics;

public class ReplenishPowerUp extends GameObject
{
	private double radius;
	private long spawnTime;
	
	public ReplenishPowerUp( double x, double y, double direction, double radius, Color color)
	{
		super();
		this.x = x;
		this.y = y;
		this.direction = direction;
		this.color = color;
		this.radius = radius;
		spawnTime = System.currentTimeMillis();
	}
	
	public void draw(Graphics g)
	{
		g.setColor(color);
		g.fillOval((int) x, (int) y, (int)(2*radius), (int) (2*radius));
		g.setColor(Color.BLACK);
		g.drawOval((int) x, (int) y, (int)(2*radius), (int) (2*radius));
	}
	
	public void update()
	{
		if( (System.currentTimeMillis() - 7000) > spawnTime)
		{
			this.kill();
		}
	}
	
	public double getRadius()
	{
		return radius;
	}
}
