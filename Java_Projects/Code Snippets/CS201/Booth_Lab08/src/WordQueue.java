import java.util.LinkedList;
import java.util.Queue;

public class WordQueue 
{
	private Queue<String> queue;

	public WordQueue() 
	{
		queue = new LinkedList<String>();
	}

	void put(String word) 
	{
		if ((word != null) && (word.length() > 0)) 
		{
			// if good word then enter into monitor
			synchronized (this) 
			{
				if (queue.peek() == null)
				{
					notify();
				}

				// notified thread won't execute
				// before this thread comes out of monitor
				queue.offer(word);
			}
		}
	}

	synchronized String get() 
	{
		// must be a while loop instead of if
		// when there are multiple consumers
		while (queue.peek() == null) 
		{
			try 
			{
				wait();
			} 
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		}

		return queue.poll();
	}
}
