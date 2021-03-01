public class Student
{
	private String name;
	private Address address;
	private Account account;

	public Student(String name, Address address, Account account)
	{
		this.name = name;
		this.address = address;
		this.account = account;
	}

	public Account getAccount()
	{
		return account;
	}

	public String toString()
	{
		return "Student(" + name + ", " + address + ", " + account + ")";
	}
}
