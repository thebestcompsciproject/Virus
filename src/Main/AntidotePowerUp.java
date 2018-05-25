package Main;

import java.awt.Color;
import java.awt.Graphics;

public class AntidotePowerUp extends GameObject 
	{
		private double radius;
		
		public AntidotePowerUp(double x, double y, double direction, double radius, Color color) 
		
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

