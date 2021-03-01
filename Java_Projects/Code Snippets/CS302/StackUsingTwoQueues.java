import java.util.EmptyStackException;
import java.util.LinkedList;
import java.util.Queue;

public class StackUsingTwoQueues<E> 
{
	private Queue<E> queue1;
	private Queue<E> queue2;

	public StackUsingTwoQueues()
	{
		queue1 = new LinkedList<E>();
		queue2 = new LinkedList<E>();
	}

	public void push(E e) 
	{
	/*	add code here*/
		queue1.add(e);
		while(queue2 != null && !queue1.contains(queue2))
		{
			queue1.add(queue2.poll());
		}	
	}

	public E pop() throws EmptyStackException 
	{
		/* add code here */
		if(queue1 != null)
		{
			return queue1.poll();
		}
		throw new EmptyStackException();
	}

	public boolean empty()
	{
		/* add code here */
		if(queue1.isEmpty())
		{
			return true;
		}
		else
			return false;
	}
}
