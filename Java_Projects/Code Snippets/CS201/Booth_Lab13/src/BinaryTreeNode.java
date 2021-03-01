public class BinaryTreeNode<T> 
{
	private BinaryTreeNode<T> left; // the left child
	private BinaryTreeNode<T> right; // the right child
	private T data; // the data in this node
	public BinaryTreeNode()
	{
		this(null, null, null);
	}

	public BinaryTreeNode(T theData) 
	{
		this(theData, null, null);
	}

	public BinaryTreeNode(T theData, BinaryTreeNode<T> leftChild,
			BinaryTreeNode<T> rightChild)
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
		int leftHeight = 0; // Height of the left subtree
		int rightHeight = 0; // Height of the right subtree
		int height = 0; // The height of this subtree

		// If we have a left subtree, determine its height
		if (left != null) 
		{
			leftHeight = left.height();
		}

		// If we have a right subtree, determine its height
		if (right != null) 
		{
			rightHeight = right.height();
		}

		// The height of the tree rooted at this node is one more than the
		// height of the 'taller' of its children.
		if (leftHeight > rightHeight) 
		{
			height = 1 + leftHeight;
		} else {
			height = 1 + rightHeight;
		}

		// Return the answer
		return height;
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

	public int leafCount() 
	{
		int counter = 0;
		
		if(left != null)
		{
			counter += left.leafCount();
		}
		if(right != null)
		{
			counter += right.leafCount();
		}
		if(left == null && right == null)
		{
			counter++;
		}
		return counter;
	}
	
	public void mirror()
	{
		BinaryTreeNode<T> temporary = null;
		temporary = left;
		left = right;
		right = temporary;
		if(left != null)
		{
			left.mirror();
		}
		if(right != null)
		{
			right.mirror();
		}
	}

	public boolean isFull()
	{
		if(LeftSideCheck() == rightSideCheck())
		{
			if(heightCheck() == true)
			{
				return true;
			}
		}
		return false;
	}
	public int LeftSideCheck()
	{
		int LeftCount = 0;
		if(left != null)
		{
			LeftCount += left.LeftSideCheck();
		}
		return LeftCount;
	}
	public int rightSideCheck()
	{
		int rightCount = 0;
		if(right != null)
		{
			rightCount += right.LeftSideCheck();
		}
		return rightCount;
	}
	public boolean heightCheck()
	{
		if(leftHeight() == rightHeight())
		{
			return true;
		}
		return false;
	}
	public int leftHeight()
	{
		int leftHeight = 0;
		if(left != null)
		{
			leftHeight = left.leftHeight();
		}
		return leftHeight;
	}
	public int rightHeight()
	{
		int rightHeight = 0;
		if(right != null)
		{
			rightHeight = right.rightHeight();
		}
		return rightHeight;
	}
	/*public BinaryTreeNode<T> distantNode()
	{
		if(left != null)
		{
			//left
		}
		return null;
	}*/
}
