import java.util.Scanner;

public class VocabularyBuilder 
{
	public static void main(String[] args)
	{
		Scanner scanner = null;
		UniqueWords myList = new UniqueWords();
		scanner = new Scanner(System.in);
		while (scanner.hasNextLine()) 
		{
			String line = scanner.nextLine();
			String[] words = line.split("\\s");

			for (int i = 0; i < words.length; i++) 
			{
				myList.add(words[i]);
			}
		}

		String words[] = myList.getWords();

		if (words != null) 
		{
			for (int i = 0; i < words.length; i++) 
			{
				System.out.println(words[i]);
			}
		}
	}
}
