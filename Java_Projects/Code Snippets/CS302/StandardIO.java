import java.io.FileInputStream;
import java.util.Scanner;
import java.io.*;

public class StandardIO 
{
	public static void main(String[] args) 
	{
		Scanner scanner = null;
		PrintStream print = null;
		if(args.length == 2)
		{
			try
			{
				scanner = new Scanner(new FileInputStream(args[0]));
				print = new PrintStream(args[1]);
			}
			catch(FileNotFoundException e)
			{
				System.err.println(e);
			}
		}
		else
		{
			scanner = new Scanner(System.in);
			print = System.out;
			
		}
		while (scanner.hasNextInt()) 
		{
			int a = scanner.nextInt();
			int b = a * a;
			print.println(b);
		}
	}
}
