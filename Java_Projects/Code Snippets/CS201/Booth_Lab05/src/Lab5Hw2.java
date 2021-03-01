import java.io.*;
import java.net.*;

public class Lab5Hw2 
{
	public static void main(String args[]) 
	{
		URLConnection connection = null;
		InputStream in = null;

		if (args.length != 1) 
		{
			System.err.println("Usage: java Lab5Hw2 <URL>");
			System.exit(1);
		}

		try {
			URL url = new URL(args[0]); // malformed url exception
			connection = url.openConnection(); // io exception
			in = connection.getInputStream(); // io exception

			int c;

			while ((c = in.read()) != -1) 
			{ // io exception
				System.out.print((char) c);
			}
		}
		catch (MalformedURLException e) 
		{
			e.printStackTrace();
		}
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		finally 
		{
			if (in != null) 
			{
				try 
				{
					in.close();
				}
				catch (IOException e) 
				{
					e.printStackTrace();
				}
			}
		}
	}
}
