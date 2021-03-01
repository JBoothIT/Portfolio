import java.util.Stack;

public class QueueUsingTwoStacks<E> 
{
	private Stack<E> stack1;
	private Stack<E> stack2;
	public QueueUsingTwoStacks()
	{
		stack1 = new Stack<E>();
		stack2 = new Stack<E>();
	}
	public void add(E e)
	{
		stack1.add(e);
		while(stack1 != null && !stack1.contains(stack2))
		{
			stack1.push(stack2.pop());
		}
	}
	public E peek()
	{
		if(stack1 != null)
		{
			return stack1.peek();
		}
		else
			return null;
	}
	public E poll()
	{
		if(stack1 != null)
		{
			return stack1.pop();
		}
		else
			return null;
	}
	public boolean isEmpty()
	{
		if(stack1.isEmpty())
		{
			return true;
		}
		else 
			return false;
	}
}
