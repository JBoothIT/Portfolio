public class Library {
	static final int defaultSize = 3;

	private int count;
	private Book collection[];

	public Library() {
		collection = new Book[defaultSize];
		count = 0;
	}

	public void insert(Book book) {
		if (count == collection.length) {
			Book temp[] = new Book[collection.length + defaultSize];

			for (int i = 0; i < collection.length; i++) {
				temp[i] = collection[i];
			}

			collection = temp;
		}

		collection[count] = book;
		count++;
	}

	public void delete(int i) {
		if (i >= 0 && i < count) {
			if (i != count - 1) {
				collection[i] = collection[count - 1];
			}

			count--;
		}
	}

	public void delete(Book book) {
		delete(index(book));
	}

	public Book get(int i) {
		if (i >= 0 && i < count) {
			return collection[i];
		} else {
			return null;
		}
	}

	public boolean set(int i, Book book) {
		if (i >= 0 && i < count) {
			collection[i] = book;
			return true;
		} else {
			return false;
		}
	}

	public boolean has(Book book) {
		return index(book) != -1;
	}

	public int getCount() {
		return count;
	}

	private int index(Book book) {
		for (int i = 0; i < count; i++) {
			if (collection[i].compareTo(book) == 0) {
				return i;
			}
		}

		return -1;
	}

	public String toString() {
		String string = this.getClass().getName() + "\n";

		for (int i = 0; i < count; i++) {
			if (i > 0) {
				string += "\n";
			}

			string += (i + 1) + ": " + collection[i].toString();
		}

		return string + "\n";
	}
}
