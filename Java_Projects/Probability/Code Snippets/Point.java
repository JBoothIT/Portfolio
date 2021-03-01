package HW5;

/**@author Jeremy Booth
 * CS355 - Probability & Statistics
 * 
 * Creates an object for a point object with associated get and set methods.
 * */

public class Point
{
	private double x;
	private double y;
	public Point(double x0, double y0)
	{
		this.x = x0;
		this.y = y0;
	}
	
	//returns x-coordinate
	public double getX()
	{
		return this.x;
	}
	
	//returns the y-coordinate
	public double getY()
	{
		return this.y;
	}
	
	//sets the x-coordinate
	public void setX(double x0)
	{
		this.x = x0;
	}
	
	//sets the y-coordinate
	public void sety(double y0)
	{
		this.y = y0;
	}
}
