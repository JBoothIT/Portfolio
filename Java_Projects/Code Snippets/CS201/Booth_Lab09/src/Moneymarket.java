public class Moneymarket extends Account
{
	private double minBalance = 1000;
	private double interest = 0.10;
	private double balance;
	public Moneymarket(int account, String n, double bal) 
	{
		super(account, n);
		this.balance = bal;
	}
	public double balance() 
	{
		return balance;
	}
	public double interest() 
	{
		return interest;
	}
}
