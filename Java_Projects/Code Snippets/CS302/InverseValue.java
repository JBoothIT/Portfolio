/*Changes:
 * Change the class file name from ExceptionTest.java to InverseValue.java
 * Add the throws keyword for Exception to the method printValue
 * Add a catch for Exception in the main method
 * Switch the places of the catches with each other in the inverse method so that ArithmeticException(specified) is checked before Exception(generic).
 * Edit Arithmetic exception to throw an exception
 * Add a try block around the statements in the main method to check for exceptions and add a catch for Exception e which prints error messages.*/
public class InverseValue 
{
	public static void printValue(int value) throws Exception 
	{
		if (value >= 0 && value <= 10) 
		{
			System.out.println("value: " + value);
		} 
		else 
		{
			throw new Exception("invalid value: " + value);
		}
	}

	public static int inverse(int n) 
	{
		int inv = 0;

		try 
		{
			inv = 1 / n;
		}
		catch (ArithmeticException  ae) 
		{
			System.out.println(ae);
			throw new ArithmeticException();
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return inv;
	}

	public static void main(String args[]) 
	{
		int a[] = { 1, 0, -1 };

		for (int i = 0; i < a.length; i++) 
		{
			try
			{
				System.out.println("processing element [" + i + "]: " + a[i]);
				printValue(a[i]);
				printValue(inverse(a[i]));
			}
			catch(Exception e)
			{
				System.out.println(e.getMessage());
				System.err.println(e.getMessage());
			}
		}
		System.out.println("all elements are processed");
	}
}
