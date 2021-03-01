import java.util.Scanner;

public class Encryption 
{
	public static String encode(String text)
	{
		char[] characters = new char[text.length()]; 
		int[] a = new int[characters.length];
		String txt = "";
		for(int i = 0; i < text.length(); i++)
		{
			characters[i] = text.charAt(i);
			a[i] = (int)characters[i] + 50;
			txt += (char)a[i];
		}
		return txt;
	}
	public static String decode(String text)
	{
	char[] characters =new char[text.length()];
	int[] a = new int[characters.length];
	String txt = "";
		for(int i = 0; i < text.length(); i++)
		{
			characters[i] = text.charAt(i);
			a[i] = (int)characters[i] - 50;
			txt += (char)a[i];
		}
		return txt;
	}
	public static void main(String[] args)
	{
		Scanner scanner = new Scanner(System.in);
		while(scanner.hasNextLine())
		{
			String line = scanner.nextLine();
			System.out.println("Encode: " + encode(line));
			System.out.println("Decode: " + decode(encode(line)));
		}
		}
	}
