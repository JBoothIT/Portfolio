import java.util.SortedSet;
import java.util.TreeSet;

import java.io.FileNotFoundException;
import java.io.PrintStream;

public class UniqueWords 
{
	private SortedSet<String> words;

	public UniqueWords() 
	{
		words = new TreeSet<String>();
	}

	public void add(String word)
	{
		if ((word != null) && (word.length() > 0))
		{
			if (!words.contains(word.toLowerCase())) 
			{
				words.add(word.toLowerCase());
			}
		}
	}

	public void writeWordList()
	{
		PrintStream ps = null;

		try {
			ps = new PrintStream("words.txt");
		} catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}

		if (ps != null)
		{
			for (String word : words) 
			{
				ps.println(word);
			}

			ps.close();
		}
	}
}
