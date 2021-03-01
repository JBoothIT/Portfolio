public class VocabularyBuilder 
{
	public static void main(String[] args)
	{
		WordQueue queue = new WordQueue();
		Thread[] threadA = new Thread[args.length];
		for(int i = 0; i < threadA.length; i++)
		{
			threadA[i] = new Thread(new SolutionReader(args[i], queue));
			threadA[i].start();
		}
		for(int i = 0; i < threadA.length; i++)
		{
			try 
			{
				threadA[i].join();
			} 
			catch (InterruptedException e) 
			{
				e.printStackTrace();
			}
		}
		UniqueWords words = new UniqueWords();

		for (int endMarkerCount = 0; endMarkerCount < args.length;) 
		{
			String word = queue.get();

			if (!word.equals("#")) 
			{
				words.add(word);
			}
			else 
			{
				endMarkerCount++;
			}
		}

		words.writeWordList();
	}
}
