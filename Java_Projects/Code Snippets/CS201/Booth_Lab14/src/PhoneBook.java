import java.util.HashMap;
import java.util.Map;

public class PhoneBook 
{
	// private Map<String, String> nameToNumber;
	private Map<String, String> numberToName;
	private Map<String, String> nameToNumber;
	public PhoneBook() 
	{
		// nameToNumber = new HashMap<String, String>();
		numberToName = new HashMap<String, String>();
		nameToNumber = new HashMap<String, String>();
	}

	public void add(String name, String number) 
	{
		// add entries to both maps
		if(!numberToName.containsKey(name))
		{
			if(!numberToName.containsKey(number))
			{
				numberToName.put(number, name);
				nameToNumber.put(name, number);
			}
		}
	}

	public String getNumberByName(String name)
	{
		// implementation of this method is similar to
		// getNameByNumber method
		if(nameToNumber.containsKey(name))
		{
			return nameToNumber.get(name);
		}
		else
		{
			return null;
		}
	}

	public String getNameByNumber(String number)
	{
		if (numberToName.containsKey(number))
		{
			return numberToName.get(number);
		}
		else 
		{
			return null;
		}
	}
}