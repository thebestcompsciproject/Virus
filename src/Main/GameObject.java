//Tomar
package Main;

import java.awt.Color;
import java.awt.Graphics;

public class GameObject
{
    protected double x;
    protected double y;
    protected double direction;
    protected Color color;
    
    public GameObject()
    {
    		
    }

    public double getDirection()
    {
        return direction;
    }

    public double getY()
    {
        return y;
    }

    public double getX()
    {
        return x;  
    }
    
    public Color getColor() {
    		return color;
    }

    public void draw(Graphics g)
    {

    }

	public void update() {
		
	}

	public boolean isAlive() { //finish
		return false;
	}
}



