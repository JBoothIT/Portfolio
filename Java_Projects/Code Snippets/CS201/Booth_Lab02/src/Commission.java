/**@author Jeremy Booth*/
public class Commission extends Hourly
{
	/*Instance variables*/
	public double total_Sales;
	public double commissionRate;
	/*Constructor for Commission class.  Acquires values from the parent class and sets commissionRate to 0.*/
	public Commission(String eName, String eAddress, String ePhone,	String socSecNumber, double rate, double comRate)
	{
		super(eName, eAddress, ePhone, socSecNumber, rate);
		commissionRate = 0.0;
	}
	/**@param Assigns total_Sales to have a value of totalSales plus the current value of total_Sales.*/
	public void addSales(double totalSales)
	{
		total_Sales = totalSales + total_Sales;
	}
	/*Overrides the pay() method in the parent class.*/
	/**@param Calls the pay() method in hourly and add the value of commissionRate.
	 * @return The value of payment.*/
	public double pay()
	{
		double payment = super.pay() + commissionRate;
		total_Sales = 0;
		
		return payment;
	}
	/*Overrides the toString() method in the parent class.*/
	/**@return The String value of result.*/
	public String toString()
	{
		String result = super.toString();
		result += "\nTotal sales: " + total_Sales;
		
		return result;
	}
}
