/*GenericCollection is a generic class which uses T and extends the Comparable class.*/
public class GenericCollection<T extends Comparable<T>>
{
	/*Instance methods defaultSize(type int), count(type int), and Collection(type Object)*/
	static final int defaultSize = 3;
	private int count;
	private Object collection[];
	/**@category Constructor creates an new object array of defaultSize and assigns the value of count to 0.*/
	public GenericCollection() 
	{
		collection = new Object[defaultSize];
		count = 0;
	}
	/**@return returns a type of void*/
	/*inserts values in the collection array*/
	public void insert(T o) 
	{
		if (count == collection.length) 
		{
		Object temp[] = new Object[collection.length + defaultSize];

			for (int i = 0; i < collection.length; i++) 
			{
				temp[i] = collection[i];
			}
			collection = temp;
		}

		collection[count] = o;
		count++;
	}
	/**@returns type void*/
	/*deletes values of 1 from count in the collection array*/
	public void delete(int i) 
	{
		if (i >= 0 && i < count) 
		{
			if (i != count - 1) 
			{
				collection[i] = collection[count - 1];
			}

			count--;
		}
	}
	public void delete(T o) 
	{
		delete(index(o));
	}
	public T get(int i) 
	{
		if (i >= 0 && i < count) 
		{
			return (T)collection[i];
		}
		else
		{
			return null;
		}
	}
		public boolean set(int i, CD cd) 
	{
		if (i >= 0 && i < count) 
		{
			collection[i] = cd;
			return true;
		} 
		else 
		{
			return false;
		}
	}
		public boolean has(T cd) 
	{
		return index(cd) != -1;
	}
		public int getCount()
	{
		return count;
	}
		@SuppressWarnings("unchecked")
		private int index(T o)
	{
		for (int i = 0; i < count; i++)
		{
			if (((T)collection[i]).compareTo(o) == 0) 
			{
				return i;
			}
		}
			return -1;
	}
		public String toString()
	{
		String string = this.getClass().getName() + "\n";
			for (int i = 0; i < count; i++) 
		{
			if (i > 0) {
				string += "\n";
			}
				string += (i + 1) + ": " + collection[i].toString();
		}
			return string + "\n";
	}
}

