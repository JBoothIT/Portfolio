package HW5;

/**@author Jeremy Booth
 * CS355 - Probability & Statistics
 * 
 * Creates a triangle object using points and performs probabilistic operations to find the CDP and PDF of the triangle.
 * */

public class Triangle 
{
	private Point p0;
	private Point p1;
	private Point p2;
	public Triangle()
	{
		// default
	}
	public Triangle(Point a, Point b, Point c)
	{
		this.p0 = a;
		this.p1 = b;
		this.p2 = c;
	}
	
	//Returns origin point.
	public Point oP()
	{
		return this.p0;
	}
	//returns set x point
	public Point xP()
	{
		return this.p1;
	}
	//experimental point
	public Point exP()
	{
		return this.p2;
	}
	
	//returns the base of the triangle
	public double getBase()
	{
		return Math.sqrt(
				Math.pow((xP().getX() - oP().getX()),2)
				+
				Math.pow((xP().getY() - oP().getY()),2));
	}
	
	//returns the height of the triangle
	public double getHeight()
	{
		if(p0.getX() == p2.getX()) //returns distance from (x, 1)->(0,0)
		{
			return Math.sqrt(
					Math.pow((exP().getX() - oP().getX()),2)
					+
					Math.pow((exP().getY() - oP().getY()),2));
		}
		else if(p1.getX() == p2.getX()) //returns distance from (x, 1)->(1,0)
		{
			return Math.sqrt(
					Math.pow((exP().getX() - xP().getX()),2)
					+
					Math.pow((exP().getY() - xP().getY()),2));
		}
		else
			return Math.sqrt(
					Math.pow(
						Math.sqrt(
							Math.pow((exP().getX() - oP().getX()),2)
							+
							Math.pow((exP().getY() - oP().getY()),2)),2)- 
					Math.pow((getBase()/2), 2));
	}
	
	//returns the CDF of the triangle
	public void getCDF(int i)
	{
		System.out.println("CDF of triangle " + i);
		/*System.out.println(getHeight());
		System.out.println(getBase());*/
		for(double x = 0; x <= 1; x+=.5)
		{
			if(x < 0 )
			{
				System.out.print("x = 0");
			}
			if(x > getHeight())
			{
				System.out.print("x = 1");
			}
			if (x >= 0 || x <= getHeight())
			{
				double res = (1 - Math.pow(
						((getHeight()-x)/getHeight()),2));
				System.out.println("x = " + x + ": " + res);
			}
		}
	}
	
	//returns the PDF of the triangle.
	public void getPDF(int i)
	{
		System.out.println("PDF of triangle " + i);
		for(double x = 0; x <= 1; x+=.5)
		{
			if(x < 0 || x > getHeight())
			{
				System.out.print("x = 0");
			}
			if (x >= 0 || x <= getHeight())
			{
				double res = (2*(getHeight() - x)/Math.pow(getHeight(),2));
				System.out.println("x = " + x + ": " + res);
			}
		}
	}
	
	//Magic Zone
	public static void main(String[] args)
	{
		double[] x = {0, .5, 1};
		for(int i = 0; i < x.length; i++)
		{
			Triangle alpha = new Triangle(
					new Point(0, 0), 
					new Point(1, 0), 
					new Point(x[i], 1));
			alpha.getCDF(i+1);
			alpha.getPDF(i+1);
			System.out.println();
		}
		
	}
}
