public class CDCollection {
	static final int defaultSize = 3;

	private int count;
	private CD collection[];

	public CDCollection() {
		collection = new CD[defaultSize];
		count = 0;
	}

	public void insert(CD cd) {
		if (count == collection.length) {
			CD temp[] = new CD[collection.length + defaultSize];

			for (int i = 0; i < collection.length; i++) {
				temp[i] = collection[i];
			}

			collection = temp;
		}

		collection[count] = cd;
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

	public void delete(CD cd) {
		delete(index(cd));
	}

	public CD get(int i) {
		if (i >= 0 && i < count) {
			return collection[i];
		} else {
			return null;
		}
	}

	public boolean set(int i, CD cd) {
		if (i >= 0 && i < count) {
			collection[i] = cd;
			return true;
		} else {
			return false;
		}
	}

	public boolean has(CD cd) {
		return index(cd) != -1;
	}

	public int getCount() {
		return count;
	}

	private int index(CD cd) {
		for (int i = 0; i < count; i++) {
			if (collection[i].compareTo(cd) == 0) {
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
