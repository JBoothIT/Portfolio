import java.util.*;
public class ListSplit 
{
	public static Integer average = 0;
	public static <E> void print(List<E> list)
	{
		if(list != null)
		{
			for(E e : list)
			{
				System.out.println(e);
			}
		}
	}
	@SuppressWarnings("null")
	public static List<Integer>[] SplitList(List<Integer> list)
	{
		@SuppressWarnings("rawtypes")
		List[] a;
		Collections.sort(list);
		System.out.println(list);
		for(int i = 0; i < list.size()/2; i++)
		{
			//a = new List[list.subList(i, list.size()/2)];
		}
		return null ;
	}
	public static void main(String[] args)
	{
		List<Integer> a = new ArrayList<Integer>();
		a.add(4);
		a.add(1);
		a.add(3);
		a.add(2);
		
		SplitList(a);
		print(a);
	}
}
