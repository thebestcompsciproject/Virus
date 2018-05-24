package Main;

import java.awt.Color;
import java.util.ArrayList;

public class MGPowerUp extends GameObject

{
	private double side;
	private double height;
	
	public MGPowerUp(double x, double y, double direction, double side, Color color) 
	{
		super();
		this.x = x;
		this.y = y;
		this.direction = direction;
		this.color = color;
		this.side = side;
		height = (Math.sqrt(3)*side)/2;
}
	
}
