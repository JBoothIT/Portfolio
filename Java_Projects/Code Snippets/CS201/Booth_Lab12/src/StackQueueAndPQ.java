import java.util.LinkedList;
import java.util.Scanner;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;

public class StackQueueAndPQ
{
	private static void printStack(Stack<Character> stack)
	{
		/**
		 * print out all elements of stack on a line, i.e. use print() method
		 * instead of println(). use empty() and pop() methods in order to
		 * achieve the goal.
		 */
		while(!stack.empty())
		{
			System.out.print(stack.pop());
		}
		System.out.println();
	}

	private static void printQueue(Queue<Character> queue) 
	{
		/**
		 * print out all elements of queue on a line. use peek() and poll()
		 * methods to accomplish the task.
		 */
		while(queue.peek() != null)
		{
			System.out.print(queue.poll());
		}
		System.out.println();
	}

	private static void printPriorityQueue(PriorityQueue<Character> pq)
	{
		/**
		 * print out all elements of priority queue on a line. use peek() and
		 * poll() methods to do it.
		 */
		while(pq.peek() != null)
		{
			System.out.print(pq.poll());
		}
		System.out.println();
	}

	public static void main(String[] args) 
	{
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);

		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();
			
			// instantiate a Stack of Characters
			Stack<Character> stack = new Stack<Character>();
		
			// create a Queue of Characters: use reference of type Queue
			// to refer to an instance of LinkedList of Characters
			Queue<Character> queue = new LinkedList<Character>();
			
			// instantiate a PriorityQueue of Characters
			PriorityQueue<Character> priority = new PriorityQueue<Character>(); 
		
			for (int i = 0; i < line.length(); i++) 
			{
				// use push() method to insert a Character into the stack
				stack.push(line.charAt(i));
				// use add() methods for queue and priority queue
				queue.add(line.charAt(i));
				priority.add(line.charAt(i));
			}
			System.out.println("from stack: ");
			// invoke printStack()
			printStack(stack);

			System.out.println("from queue: ");
			// invoke printQueue()
			printQueue(queue);
			System.out.println("from priority queue: ");
			// invoke printPriorityQueue()
			printPriorityQueue(priority);
		}
	}
}
