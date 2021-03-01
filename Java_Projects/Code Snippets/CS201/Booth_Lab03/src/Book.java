public class Book implements Comparable<Book> {
	private final String title;
	private final String author;
	private final String isbn;

	public Book(String title, String author, String isbn) {
		this.title = title;
		this.author = author;
		this.isbn = isbn;
	}

	public String getAuthor() {
		return author;
	}

	public String getTitle() {
		return title;
	}

	public String getISBN() {
		return isbn;
	}

	public String toString() {
		return "Book(Title: " + title + ", Author: " + author + ", ISBN: "
				+ isbn + ")";
	}

	@Override
	public int compareTo(Book arg0) {
		return this.isbn.compareTo(arg0.isbn);
	}
}
