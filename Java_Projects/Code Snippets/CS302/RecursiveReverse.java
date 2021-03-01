import java.io.PrintStream;
import java.util.*;

public class RecursiveReverse
{

    public RecursiveReverse()
    {
    }

    public static void recursiveReverse(List list)
    {
        Object add = list.get(0);
        list.remove(0);
        if(list.size() != 0)
        {
            recursiveReverse(list);
        }
        list.add(add);
    }

    public static void print(List list)
    {
        if(list != null)
        {
            Object e;
            for(Iterator iterator = list.iterator(); iterator.hasNext(); System.out.println(e))
            {
                e = (Object)iterator.next();
            }

        }
    }

    public static void main(String args[])
    {
        List sl = new ArrayList();
        sl.add("one");
        sl.add("two");
        sl.add("three");
        recursiveReverse(sl);
        print(sl);
        List il = new LinkedList();
        il.add(Integer.valueOf(1));
        il.add(Integer.valueOf(2));
        il.add(Integer.valueOf(3));
        recursiveReverse(il);
        print(il);
    }
}
