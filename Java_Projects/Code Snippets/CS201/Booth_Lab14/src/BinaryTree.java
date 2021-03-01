import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

public class BinaryTree<T> 
{
	private BinaryTreeNode<T> root;
	private BinaryTreeNode<T> cursor;

	public void toRoot() 
	{
		cursor = root;
	}

	public boolean hasLeftChild()
	{
		return cursor.getLeft() != null;
	}

	public boolean hasRightChild()
	{
		return cursor.getRight() != null;
	}

	public void toLeftChild() 
	{
		cursor = cursor.getLeft();
	}

	public void toRightChild() 
	{
		cursor = cursor.getRight();
	}

	public void insertLeft(T data)
	{
		BinaryTreeNode<T> newNode = new BinaryTreeNode<T>(data);

		if (root == null) 
		{
			root = newNode;
			cursor = root;
		}
		else 
		{
			cursor.setLeft(newNode);
		}
	}

	public void insertRight(T data) 
	{
		BinaryTreeNode<T> newNode = new BinaryTreeNode<T>(data);

		if (root == null)
		{
			root = newNode;
			cursor = root;
		} 
		else
		{
			cursor.setRight(newNode);
		}
	}

	public T get()
	{
		return cursor.getData();
	}

	public void set(T data)
	{
		cursor.setData(data);
	}

	public int height() 
	{
		if (root != null) 
		{
			return root.height();
		}
		else 
		{
			return 0;
		}
	}

	public void preOrder() 
	{
		if (root != null) 
		{
			root.preOrder();
		}
	}

	public String toString() 
	{
		if (root != null)
		{
			return root.toStringPreOrder(".");
		}
		else
		{
			return "";
		}
	}

	public void levelOrder()
	{
		if (root != null)
		{
			SortedMap<Integer, List<BinaryTreeNode<T>>> accumulator;

			root.levelOrder(accumulator = new TreeMap<Integer, List<BinaryTreeNode<T>>>(), 1);

			for (Integer level : accumulator.keySet()) 
			{
				System.out.print(level + ":");

				for (BinaryTreeNode<T> node : accumulator.get(level))
				{
					System.out.print(" " + node.getData());
				}

				System.out.println();
			}
		}
	}
	public void facebook()
	{
		
	}
}
