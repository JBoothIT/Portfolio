import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Iterator;
import java.util.LinkedList;

public class TextWriter
{

    private PrintStream ps;

    public TextWriter(OutputStream out)
    {
        ps = new PrintStream(out);
    }

    public void printListUsingIterator(LinkedList list)
    {
        for(Iterator iterate = list.iterator(); iterate.hasNext(); ps.print(iterate.next())) { }
    }

    public void printListUsingForEachLoop(LinkedList list)
    {
        Character a;
        for(Iterator iterator = list.iterator(); iterator.hasNext(); ps.print(a))
        {
            a = (Character)iterator.next();
        }

    }
}