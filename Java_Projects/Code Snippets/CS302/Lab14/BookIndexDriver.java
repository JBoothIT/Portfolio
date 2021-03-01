import java.util.Scanner;
import java.util.StringTokenizer;

public class BookIndexDriver 
{
	public static void main(String args[]) 
	{
		int line = 0;
		BookIndex index = new BookIndex();
		Scanner scanner = new Scanner(System.in);

		while (scanner.hasNextLine()) 
		{
			line++;
			StringTokenizer tokenizer = new StringTokenizer(scanner.nextLine(),
					" \t\n\r\f,.;:");

			while (tokenizer.hasMoreTokens()) 
			{
				String word = tokenizer.nextToken();

				if (word.matches("[a-zA-Z]+"))
				{
					index.addWord(word, line);
				}
			}
		}
		System.out.print("book index\n" + index);
	}
}
