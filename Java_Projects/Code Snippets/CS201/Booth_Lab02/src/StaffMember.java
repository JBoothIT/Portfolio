abstract public class StaffMember {
	protected String name;
	protected String address;
	protected String phone;

	public StaffMember(String eName, String eAddress, String ePhone) {
		name = eName;
		address = eAddress;
		phone = ePhone;
	}

	public String toString() {
		String result = "Name: " + name + "\n";

		result += "Address: " + address + "\n";
		result += "Phone: " + phone;

		return result;
	}

	// -----------------------------------------------------------------
	// Derived classes must define the pay method for each type of
	// employee.
	// -----------------------------------------------------------------
	public abstract double pay();
}
