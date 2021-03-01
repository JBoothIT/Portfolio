public class Test {
	static private void testCDCollection() {
		GenericCollection<CD> cdCollection = new GenericCollection<CD>();
		CD cd[] = new CD[4];

		cd[0] = new CD("Boy in Detention", "Chris Brown", 16.75, 20);
		cd[1] = new CD("One Day", "David Burnham", 14.95, 11);
		cd[2] = new CD("Slave Ambient", "The War on Drugs", 15.65, 12);
		cd[3] = new CD("Stranger Me", "Amy Lavere", 14.75, 11);

		CD myCD = new CD("Slippery When Wet", "Bon Jovi", 16.25, 10);

		for (int i = 0; i < 4; i++) {
			cdCollection.insert(cd[i]);
		}

		if (cdCollection.has(myCD)) {
			System.out.println(myCD + " is in " + cdCollection);
		} else {
			System.out.println(myCD + " is not in " + cdCollection);
		}

		System.out.println(cd[2] + " is in " + cdCollection);
		System.out.println("I hired " + cd[2]);
		cdCollection.delete(cd[2]);
		System.out.println("Now " + cd[2] + " is not in " + cdCollection);
	}

	static private void testLibrary() {
		GenericCollection<Book> uabLibrary = new GenericCollection<Book>();
		GenericCollection<Book> uahLibrary = new GenericCollection<Book>();

		uabLibrary.insert(new Book("Effective Java", "Joshua Bloch",
				"978-0321356680"));
		uabLibrary.insert(new Book("Learning Java", "Jonathan B. Knudsen",
				"978-0596008734"));
		uabLibrary.insert(new Book("Thinking in Java", "Bruce Eckel",
				"978-0131872486"));
		uabLibrary.insert(new Book("Head First Java", "Kathy Sierra",
				"978-0596009205"));

		uahLibrary.insert(new Book("Thinking in Java", "Bruce Eckel",
				"978-0131872486"));
		uahLibrary.insert(new Book("Head First Java", "Kathy Sierra",
				"978-0596009205"));
		uahLibrary.insert(new Book("C++ Programming Language",
				"Bjarne Stroustrup", "978-0201889543"));

		System.out.println("UAB " + uabLibrary);
		System.out.println("UAH " + uahLibrary);

		for (int i = 0; i < uahLibrary.getCount(); i++) {
			Book book = uahLibrary.get(i);

			System.out.println("UAH has " + book);

			if (uabLibrary.has(book)) {
				System.out.println("UAB also has it\n");
			} else {
				System.out.println("UAB is borrowing it from UAH\n");

				uabLibrary.insert(uahLibrary.get(i));
				uahLibrary.delete(i);
				i--;
			}
		}

		System.out.println("UAB " + uabLibrary);
		System.out.println("UAH " + uahLibrary);
	}

	static public void main(String args[]) {
		testCDCollection();
		testLibrary();
	}
}
