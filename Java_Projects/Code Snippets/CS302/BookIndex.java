import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

public class BookIndex 
{
	private SortedMap<String, SortedSet<Integer>> wordIndices;

	public BookIndex()
	{
		wordIndices = new TreeMap<String, SortedSet<Integer>>();
	}

	public void addWord(String word, int line) 
	{
		// add code to this method only
		// according to problem description
		SortedSet<Integer> index = new TreeSet<Integer>();
		index.add(line);
		if(!wordIndices.containsKey(word.toLowerCase()))
		{
			wordIndices.put(word.toLowerCase(), index);
		}
		else
		{
			wordIndices.get(word.toLowerCase()).add(line);
		}
	}
	public String toString() 
	{
		String string = "";
		
		for (String word : wordIndices.keySet())
		{
			string += word + ":";

			for (Integer line : wordIndices.get(word)) 
			{
				string += " " + line;
			}

			string += "\n";
		}

		return string;
	}
}
