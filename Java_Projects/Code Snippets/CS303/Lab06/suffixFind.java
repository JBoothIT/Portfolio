package Lab06;

public class suffixFind 
{
	public static String find(String a, String b)
	{
		int count = 0;
		int length = a.length();
		if(length > b.length())
		{
			length = b.length();		
		}
		for(int i = length - 1; i >= 0; i--)
		{
			if(a.charAt(length - count - 1) ==  b.charAt(i))
			{
				count++;
			}
			if(a.charAt(i) != b.charAt(i))
			{
				count = 0; 
			}
		}
		return b.substring(0, count);	
	}
	public static void main(String[] args)
	{
		String a = "aabbaaas";
		String b = "aaccaa";
		System.out.println(find(a,b));
	}
}

