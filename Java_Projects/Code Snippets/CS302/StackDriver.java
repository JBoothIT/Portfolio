import java.util.Scanner;

public class StackDriver
{
	private static void printStack(StackUsingTwoQueues<Character> stack)
	{
		/* add code here */
		while(!stack.empty())
		{
			System.out.print(stack.pop());
		}
	}

	public static void main(String[] args) 
	{
		Scanner scanner = new Scanner(System.in);

		while (scanner.hasNextLine())
		{
			String line = scanner.nextLine();

			StackUsingTwoQueues<Character> stack = new StackUsingTwoQueues<Character>();

			for (int i = 0; i < line.length(); i++) 
			{
				if(scanner.hasNext());
				{
					stack.push(line.charAt(i));
				}
			}

			System.out.println("from stack: ");
			printStack(stack);
		}
	}
}
