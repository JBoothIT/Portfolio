public class BinaryTree<T> 
{
	private BinaryTreeNode<T> root; // the root of the tree
	private BinaryTreeNode<T> cursor; // the current node

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
		} else 
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
		} else {
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
		} else {
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
		} else
		{
			return "";
		}
	}

	public int leafCount() 
	{
		if (root != null)
		{
			return root.leafCount();
		} else {
			return 0;
		}
	}

	public void mirror()
	{
		if (root != null)
		{
			root.mirror();
		}
	}

	public boolean isFull() throws NullPointerException
	{
		if(root != null)
		{
			return root.isFull();
		}
		else
			return true;
	}
	/*public boolean deepCompare(BinaryTree<Character> tree2)
	{
		if(root.equals(tree2))
		{
			return true;
		}
		return false;
	}*/
}
