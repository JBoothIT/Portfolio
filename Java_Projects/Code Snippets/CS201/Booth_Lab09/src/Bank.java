public class Bank
{
	public int account;
	private String alias;
	private double bal;
	private double inter;
	public Bank(int AccountNum, String name, double balance, double interest)
	{
		this.account = AccountNum;
		this.alias = name;
		this.bal = balance;
		this.inter = interest;
	}
	public void accountLengthCheck() throws InvalidAccountException
	{
		try
		{
			if(account > 9999999)
			{
				throw new InvalidAccountException();
			}
		}
		catch(InvalidAccountException e)
		{
			e.error();
		}
	}
	public int getAccNum()
	{
		return account;
	}
	public String getName()
	{
		return alias;
	}
	public double getBalance()
	{
		return bal;
	}
	public double getInterest()
	{
		return inter;
	}
	
}
