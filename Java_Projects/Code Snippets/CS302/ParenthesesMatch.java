import java.util.Scanner;
import java.util.Stack;

public class ParenthesesMatch 
{
	private static int LeftParCount = 0;
	private static int RightParCount = 0;
	public static void sort(Stack<Character> Stack)
	{
		for(int i = 0; i < Stack.size(); i++)
		{
			if(Stack.elementAt(i) == '(')
			{
				LeftParCount++;
			}
			else if(Stack.elementAt(i) == ')')
			{
				RightParCount++;
			}		
		}
	}
	public static boolean print()
	{
		if(LeftParCount == RightParCount)
		{
			return true;
		}
		else 
			return false;	
	}
	public static void main(String[] args)
	{
		Scanner scanner = new Scanner(System.in);
		while(scanner.hasNext())
		{
			String line = scanner.nextLine();
			Stack<Character> stack = new Stack<Character>();
			for(int i = 0; i < line.length();i++)
			{
					stack.add(line.charAt(i));	
			}
			sort(stack);
			System.out.println(print());
		}
		
	}
}

