import java.util.TreeSet;

public class UniqueWords 
{
	TreeSet<String> words;

	public UniqueWords() 
	{
		words = new TreeSet<String>();
	}

	public void add(String word) 
	{
		if (word != null && word.length() > 0) 
		{
			if (!words.contains(word.toLowerCase())) 
			{
				words.add(word.toLowerCase());
			}
		}
	}

	public String[] getWords() 
	{
		if (words.size() == 0) 
		{
			return null;
		} 
		else
		{
			return words.toArray(new String[words.size()]);
		}
	}
}
