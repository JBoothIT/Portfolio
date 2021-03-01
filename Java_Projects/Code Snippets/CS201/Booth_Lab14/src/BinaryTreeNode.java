import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.SortedSet;

public class BinaryTreeNode<T> 
{
	private BinaryTreeNode<T> left;
	private BinaryTreeNode<T> right;

	private T data;

	public BinaryTreeNode() 
	{
		this(null, null, null);
	}

	public BinaryTreeNode(T theData)
	{
		this(theData, null, null);
	}

	public BinaryTreeNode(T theData, BinaryTreeNode<T> leftChild, BinaryTreeNode<T> rightChild)
	{
		data = theData;

		left = leftChild;
		right = rightChild;
	}

	public T getData()
	{
		return data;
	}

	public BinaryTreeNode<T> getLeft()
	{
		return left;
	}

	public BinaryTreeNode<T> getRight() 
	{
		return right;
	}

	public void setLeft(BinaryTreeNode<T> newLeft)
	{
		left = newLeft;
	}

	public void setRight(BinaryTreeNode<T> newRight) 
	{
		right = newRight;
	}

	public void setData(T newData)
	{
		data = newData;
	}

	public void preOrder()
	{
		System.out.println(data);

		if (left != null) 
		{
			left.preOrder();
		}

		if (right != null)
		{
			right.preOrder();
		}
	}

	public int height() 
	{
		int lh = 0;
		int rh = 0;

		if (left != null) 
		{
			lh = left.height();
		}

		if (right != null) 
		{
			rh = right.height();
		}

		if (lh > rh) 
		{
			return lh + 1;
		} 
		else 
		{
			return rh + 1;
		}
	}

	public String toStringPreOrder(String pathString)
	{
		String treeString = pathString + " : " + data + "\n";

		if (left != null) 
		{
			treeString += left.toStringPreOrder(pathString + "L");
		}

		if (right != null) 
		{
			treeString += right.toStringPreOrder(pathString + "R");
		}

		return treeString;
	}

	public void levelOrder(SortedMap<Integer, List<BinaryTreeNode<T>>> accumulator, int depth)
	{
		// add (this) to accumulator
		// if (left) is not null invoke this method for left
		// do the same for (right)
		if(!accumulator.containsKey(depth))
		{
			accumulator.put(depth, new ArrayList<BinaryTreeNode<T>>());
		}
		accumulator.get(depth).add(this);
		if(left != null)
		{
			left.levelOrder(accumulator, depth + 1);
		}
		if(right != null)
		{
			right.levelOrder(accumulator, depth + 1);
		}
	}
	public void FriendList(SortedMap<String, SortedSet<String>> friend)
	{
		
	}
}
