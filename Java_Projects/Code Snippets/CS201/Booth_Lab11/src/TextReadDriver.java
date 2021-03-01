import java.io.PrintStream;
import java.util.Collection;
import java.util.LinkedList;

public class TextReadDriver
{

    public TextReadDriver()
    {
    }

    public static void main(String args[])
    {
        TextReader reader = new TextReader(System.in);
        TextWriter writer = new TextWriter(System.out);
        reader.read();
        LinkedList list = new LinkedList();
        list.addAll((Collection)reader);
        System.out.println("using iterator ...");
        writer.printListUsingIterator(list);
        System.out.println("using for each loop ...");
        writer.printListUsingForEachLoop(list);
    }
}
