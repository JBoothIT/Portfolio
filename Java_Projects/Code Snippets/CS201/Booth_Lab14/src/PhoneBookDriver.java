import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.StringTokenizer;

public class PhoneBookDriver
{
	private static PhoneBook createPhoneBook(String fileName) 
	{
		PhoneBook phoneBook = new PhoneBook();

		try {
			Scanner scanner = new Scanner(new File(fileName));

			while (scanner.hasNextLine())
			{
				StringTokenizer tokenizer = new StringTokenizer(scanner.nextLine(), ",\t\r\n");

				if (tokenizer.countTokens() == 2)
				{
					phoneBook.add(tokenizer.nextToken(), tokenizer.nextToken());
				} else {
					System.err.println("Bad input.");
				}
			}

			scanner.close();
		}
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		}

		return phoneBook;
	}

	private static void processQuery(PhoneBook phoneBook, String fileName) 
	{
		try {
			Scanner scanner = new Scanner(new File(fileName));

			while (scanner.hasNextLine()) 
			{
				String query = scanner.nextLine();

				if (PhoneBookUtility.isPhoneNumber(query))
				{
					System.out.println("The phone number " + query + " is of "
							+ phoneBook.getNameByNumber(query));
				}
				else 
				{
					System.out.println("The phone number of " + query + " is "
							+ phoneBook.getNumberByName(query));
				}
			}

			scanner.close();
		} 
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
	}

	public static void main(String[] args)
	{
		if (args.length == 2) 
		{
			processQuery(createPhoneBook(args[0]), args[1]);
		} 
		else 
		{
			System.err.println("File names are missing.");
		}
	}
}
