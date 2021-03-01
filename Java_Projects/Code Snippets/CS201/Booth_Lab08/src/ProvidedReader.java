import java.io.File;
import java.io.FileNotFoundException;

import java.util.Scanner;

public class ProvidedReader implements Runnable 
{
	private String fileNames[];
	private WordQueue queue;

	public ProvidedReader(String fileNames[], WordQueue queue)
	{
		this.fileNames = fileNames;
		this.queue = queue;
	}

	public void run()
	{
		for (int i = 0; i < fileNames.length; i++)
		{
			Scanner scanner = null;

			try
			{
				scanner = new Scanner(new File(fileNames[i]));
			}
			catch (FileNotFoundException e)
			{
				e.printStackTrace();
			}

			if (scanner != null)
			{
				while (scanner.hasNextLine()) 
				{
					String line = scanner.nextLine();
					String[] words = line.split("\\s");

					for (int j = 0; j < words.length; j++)
					{
						if (probableDictionaryWord(words[j]))
						{
							queue.put(words[j]);
						}
					}
				}

				scanner.close();
			}

			queue.put("#"); // end marker
		}
	}

	private boolean probableDictionaryWord(String str)
	{
		if ((str != null) && (str.length() > 0))
		{
			for (int i = 0; i < str.length(); i++)
			{
				if (!Character.isLowerCase(str.charAt(i))&& !Character.isUpperCase(str.charAt(i))) 
				{
					return false;
				}
			}
		}

		return true;
	}
}
