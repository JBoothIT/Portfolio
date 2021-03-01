import java.util.Scanner;

public class Palindrome 
{
	private static boolean palindrome(String string) 
	{
		
		if(string.length() > 1)
		{
			if(string.charAt(0) == string.charAt(string.length() - 1))
			{
				palindrome(string.substring(1, string.length() - 1));
			}
			else
				return false;
		}
		return true;
	}
	private static String onlyLetters(String string, int index, String letters) 
	{
		if(index > string.length() - 1)
		{
			return letters;
		}
		else
		{
				letters += string.charAt(index);	
		}
		return onlyLetters(string, index + 1, letters);
	}
	public static void main(String args[]) 
	{
		String line, letters;
		Scanner scanner = new Scanner(System.in);

		System.out.print("string: ");

		while (scanner.hasNextLine()) 
		{
			line = scanner.nextLine();
			letters = onlyLetters(line, 0, "");

			if (line.length() > 0) 
			{
				System.out.print("\"" + line + "\"" + " is ");

				if (palindrome(letters.toLowerCase())) 
				{
					System.out.println("a palindrome");
				}
				else
				{
					System.out.println("not a palindrome");
				}
			}

			System.out.print("string: ");
		}
	}
}