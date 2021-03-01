import java.io.*;
import java.io.FileInputStream;

public class SumInteger
{
	private static int accumulator = 0;
	public static void display(int a)
	{
		System.out.println();
		System.out.println(a);
	}
	public static int acc(FileInputStream file) throws IOException
	{
		try
		{
			System.out.println("Reading.");
			while(file.read() != ' ')
			{
				 accumulator += 10 * accumulator + file.read();
			}
			return accumulator;
		}
		catch(IOException e)
		{
			System.out.println("IOException!");
		}
		return accumulator;
	}
	public static void main(String[] args) throws Exception
	{
		try
		{
			FileInputStream file = null;
			for(int i = 0; i < args.length; i++)
			{
				file = new FileInputStream(new File(args[i]));
			}
				display(acc(file));
		}
		catch(FileNotFoundException e)
		{
			e.getMessage();
		}
		catch(IOException e)
		{
			e.getMessage();
		}
		catch(NullPointerException e)
		{
			e.getMessage();
		}
	}
}
