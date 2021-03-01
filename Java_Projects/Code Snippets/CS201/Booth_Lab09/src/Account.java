abstract public class Account
{
	private int accountNum;
	private String name;
	public Account(int account, String n)
	{
		this.accountNum = account;
		this.name = n;
	}
	public String getName()
	{
		return name;
	}
	public int getAccount()
	{
		return accountNum;
	}
	public abstract double balance(); 
	public abstract double interest();
}
