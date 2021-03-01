import java.awt.Shape;
import java.awt.*;

public class Compare<T extends Comparable<T>> 
{
	public static <T extends Comparable<T>> T findMax(T[] a)
	{
		int maxIndex = 0;
		for(int i = 1; i < a.length; i++)
		{
			if(a[i].compareTo(a[maxIndex]) > 0)
			{
				maxIndex = i;
			}
		}
		return a[maxIndex];
	}
	public static void main()
	{
		String[] s = {"John","Jim","Jane", "Alice"};
		System.out.println(findMax(s));
		System.out.println();
		/*Shape[] shapes = {new Circle(2.0), new Square(3.0), new Rectangle(3.0, 4.0)};
		System.out.println(findMax(shapes));*/
	}
	
}
