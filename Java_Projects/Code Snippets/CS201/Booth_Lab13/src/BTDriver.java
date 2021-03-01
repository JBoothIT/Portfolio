import java.util.Scanner;
import java.util.StringTokenizer;

import org.w3c.dom.traversal.TreeWalker;

public class BTDriver
{
	public static void main(String args[])
	{
		BinaryTree<Character> tree = new BinaryTree<Character>();
		BinaryTree<Character> tree2 = new BinaryTree<Character>();
		Scanner scanner = new Scanner(System.in);
		while (scanner.hasNextLine())
		{
			String line = scanner.nextLine();
			StringTokenizer tokenizer = new StringTokenizer(line);

			switch (tokenizer.countTokens())
			{
			case 1:
				switch (tokenizer.nextToken().charAt(0))
				{
				case '.':
					tree.toRoot();
					break;

				case 'L':
					tree.toLeftChild();
					break;

				case 'R':
					tree.toRightChild();
				}

				break;

			case 2:
				if (tokenizer.nextToken().equals("insl"))
				{
					tree.insertLeft(tokenizer.nextToken().charAt(0));
				} 
				else 
				{ /* insr */
					tree.insertRight(tokenizer.nextToken().charAt(0));
				}
			}
		}
		/* demo */
		System.out.println("pre order traversal");
		tree.preOrder();
		System.out.println();

		System.out.println("to string");
		System.out.println(tree);

		/* problem 1 */
		System.out.println("problem 1");
		System.out.println("leaf count: " + tree.leafCount());
		System.out.println();

		/* problem 2 */
		System.out.println("problem 2");
		tree.mirror();
		System.out.println("mirrored tree:\n" + tree);
		
		/*problem 3*/
		System.out.println("problem 3");
		System.out.println(tree.isFull());
		
		/*problem 4*/
		/*System.out.println("problem 4");
		System.out.println(tree.distantNode());*/
		/*problem 5*/
		/*System.out.println("Problem 5");
		System.out.println(tree.deepCompare(tree2));*/
		
	}
}
