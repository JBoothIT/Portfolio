import java.util.StringTokenizer;

public class PhoneBookUtility 
{
	private static final int length[] = { 3, 3, 4 };

	private static boolean isDigit(String string)
	{
		for (int i = 0; i < string.length(); i++)
		{
			if (string.charAt(i) < '0')
			{
				return false;
			} 
			else if (string.charAt(i) > '9')
			{
				return false;
			}
		}

		return true;
	}

	public static boolean isPhoneNumber(String string)
	{
		StringTokenizer tokenizer = new StringTokenizer(string, "-\r");

		if (tokenizer.countTokens() == 3)
		{
			for (int i = 0; i < length.length; i++)
			{
				String token = tokenizer.nextToken();

				if (token.length() != length[i]) 
				{
					return false;
				} else if (!isDigit(token))
				{
					return false;
				}
			}
		} 
		else 
		{
			return false;
		}

		return true;
	}
}
