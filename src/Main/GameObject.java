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
    protected boolean isAlive;
    
    public GameObject()
    {
    		isAlive = true;
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
    
    public void setX(double x) {
    		this.x = x;
    }
    
    public void setY(double y) {
		this.y = y;
    }
    
    public void setDirection(double direction) {
		this.direction = direction;
    }
    
    public Color getColor() {
    		return color;
    }
    
	public boolean isAlive() { //finish
		return isAlive;
	}
	
	public void kill() {
		isAlive = false;
	}

    public void draw(Graphics g) {

    }

	public void update() {
		
	}
}



