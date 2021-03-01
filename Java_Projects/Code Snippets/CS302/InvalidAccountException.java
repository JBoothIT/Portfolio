@SuppressWarnings("serial")
public class InvalidAccountException extends Exception
{
	private Account account;
	public InvalidAccountException(Account Acct)
	{
		account = Acct;
	}
	public String getMessage()
	{
		return "InvalidAccountException";
	}
	
}
