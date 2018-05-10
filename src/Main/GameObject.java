package Main;

import java.awt.Graphics;

public class GameObject
{
    protected double x;
    protected double y;
    protected double direction;
    
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

    public void draw(Graphics g)
    {

    }

	public void update() {
		
	}

	public boolean isAlive() {
		// TODO Auto-generated method stub
		return false;
	}
}



