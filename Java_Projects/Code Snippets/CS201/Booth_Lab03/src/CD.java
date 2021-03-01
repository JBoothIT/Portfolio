public class CD implements Comparable<CD> {
	private final String title, artist;
	private final double price;
	private final int numTracks;

	public CD(String title, String artist, double price, int numTracks) {
		this.title = title;
		this.artist = artist;
		this.price = price;
		this.numTracks = numTracks;
	}

	public String getTitle() {
		return title;
	}

	public String getArtist() {
		return artist;
	}

	public double getPrice() {
		return price;
	}

	public int getTracks() {
		return numTracks;
	}

	public String toString() {
		return "CD(Title: " + title + ", Artist: " + artist + ", Tracks: "
				+ numTracks + ", Price: " + price + ")";
	}

	@Override
	public int compareTo(CD arg0) {
		if (this.title.compareTo(arg0.title) == 0) {
			return this.artist.compareTo(arg0.artist);
		} else {
			return this.title.compareTo(arg0.title);
		}
	}
}
