import java.io.InputStream;
import java.util.LinkedList;
import java.util.Scanner;

public class TextReader
{

    private Scanner scanner;
    private LinkedList text;

    public TextReader(InputStream in)
    {
        scanner = new Scanner(in);
        text = new LinkedList();
    }

    public void read()
    {
        for(; scanner.hasNextLine(); text.add(Character.valueOf('\n')))
        {
            String line = scanner.nextLine();
            for(int i = 0; i < line.length(); i++)
            {
                text.add(Character.valueOf(line.charAt(i)));
            }

        }

    }

    public LinkedList getText()
    {
        return text;
    }
}
