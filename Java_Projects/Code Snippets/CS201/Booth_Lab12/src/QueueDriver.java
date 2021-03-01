import java.util.Scanner;

public class QueueDriver {
	private static void printQueue(QueueUsingTwoStacks<Character> queue) 
	{
		/* add code here */
		while(!queue.isEmpty())
		{
			System.out.print(queue.poll());
		}
	}

	public static void main(String[] args)
	{
		Scanner scanner = new Scanner(System.in);

		while (scanner.hasNextLine()) 
		{
			String line = scanner.nextLine();

			QueueUsingTwoStacks<Character> queue = new QueueUsingTwoStacks<Character>();

			for (int i = 0; i < line.length(); i++)
			{
				queue.add(line.charAt(i));
			}
			System.out.println("from queue: ");
			printQueue(queue);
		}
	}
}
