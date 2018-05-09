package Main;

import java.awt.Graphics;
/**
 * Write a description of class GameObject here.
 * 
 * Authored by: Aditya Tomar 
 * @version (a version number or a date)
 */
public class GameObject
{
    private double x;
    private double y;
    private double direction;

    /**
     * Constructor for objects of class GameObject
     */
    public GameObject(double myX, double myY, double myDirection)
    {
        x = myX;
        y = myY;
        direction = myDirection;
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
}



