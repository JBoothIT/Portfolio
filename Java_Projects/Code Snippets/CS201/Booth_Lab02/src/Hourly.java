public class Hourly extends Employee {
	private int hoursWorked;

	public Hourly(String eName, String eAddress, String ePhone,
			String socSecNumber, double rate) {
		super(eName, eAddress, ePhone, socSecNumber, rate);

		hoursWorked = 0;
	}

	// -----------------------------------------------------------------
	// Adds the specified number of hours to this employee's
	// accumulated hours.
	// -----------------------------------------------------------------
	public void addHours(int moreHours) {
		hoursWorked += moreHours;
	}

	// -----------------------------------------------------------------
	// Computes and returns the pay for this hourly employee.
	// -----------------------------------------------------------------
	public double pay() {
		double payment = payRate * hoursWorked;
		hoursWorked = 0;

		return payment;
	}

	public String toString() {
		String result = super.toString();
		result += "\nCurrent hours: " + hoursWorked;

		return result;
	}
}
