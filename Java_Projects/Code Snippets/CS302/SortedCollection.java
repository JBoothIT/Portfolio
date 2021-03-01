public interface SortedCollection<E extends Comparable<? super E>> 
{
	boolean add(E e);
	boolean addAll(SortedCollection<? extends E> c);
	
	boolean contains(E e);
	boolean containsAll(SortedCollection<? extends E> c);
	
	boolean remove(E e);
	boolean removeAll(SortedCollection<? extends E> c);
	
	int size();
	E get(int i);
	void clear();
	boolean isEmpty();
}
