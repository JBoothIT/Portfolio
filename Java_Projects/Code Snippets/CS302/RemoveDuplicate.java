import java.io.PrintStream;
import java.util.*;

public class RemoveDuplicate
{

    public RemoveDuplicate()
    {
    }

    public static void removeDuplicate(List list)
    {
        for(int i = 0; i < list.size() - 1; i++)
        {
            for(int j = i + 1; j < list.size(); j++)
            {
                if(list.get(i).equals(list.get(j)))
                {
                    list.remove(j);
                    j--;
                }
            }

        }

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
        sl.add("two");
        sl.add("three");
        sl.add("three");
        sl.add("three");
        removeDuplicate(sl);
        print(sl);
        List il = new LinkedList();
        il.add(Integer.valueOf(1));
        il.add(Integer.valueOf(2));
        il.add(Integer.valueOf(2));
        il.add(Integer.valueOf(3));
        il.add(Integer.valueOf(3));
        il.add(Integer.valueOf(3));
        removeDuplicate(il);
        print(il);
    }
}
