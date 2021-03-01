public class MySortedCollection<E extends Comparable<? super E>> implements SortedCollection<E>
{
	static final int dS = 3;
	private int count;
	private Object collection[];
	public MySortedCollection()
	{
		collection = new Object[dS];
		count = 0;
	}
	//add
	public boolean add(E e)
	{
		if (count == collection.length) 
		{
		Object temp[] = new Object[collection.length + dS];
			for (int i = 0; i < collection.length; i++) 
			{
				temp[i] = collection[i];
			}
			collection = temp;
		collection[count] = e;
		count++;
		}
		return true;
	}
	//addAll
	public boolean addAll(SortedCollection<? extends E> c) 
	{
		for(int i = 0; i < c.size(); i++)
		{
			add(c.get(i));
		}
		return true;
	}
	//contains
	public boolean contains(E e)
	{
		for(Object x : collection)
		{
			if(x.equals(e))
			{
				return true;
			}
		}
		return false;
	}
	//containsAll
	public boolean containsAll(SortedCollection<? extends E> c) 
	{
		for(int i = 0; i < c.size(); i++)
		{
			contains(c.get(i));
		}
		return true;
	}
	//remove
	public boolean remove(E e) 
	{
		if(contains(e))
		{
			
			return true;
		}
		else
			return false;
	}
	//removeAll
	public boolean removeAll(SortedCollection<? extends E> c) 
	{
		for(int i = 0; i < c.size(); i++)
		{
			remove(c.get(i));
		}
		return true;
	}
	//size
	public int size() 
	{
		return collection.length;
	}
	//get
	public E get(int i) 
	{
		if( i > 0 && i < count)
			return (E)collection[i];
		else
			return null;
	}
	//clear
	public void clear() 
	{
		collection = null;
	}
	//isEmpty
	public boolean isEmpty() 
	{
		if(size() == 0)
		{
			return true;
		}
		else
			return false;
	}
	public String toString()
	{
		String string = this.getClass().getName() + "\n";
			for (int i = 0; i < count; i++) 
		{
			if (i > 0) 
			{
				string += "\n";
			}
				string += (i + 1) + ": " + collection[i].toString();
		}
			return string + "\n";
	}
}
