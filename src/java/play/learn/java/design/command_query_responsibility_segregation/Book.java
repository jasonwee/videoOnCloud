package play.learn.java.design.command_query_responsibility_segregation;

import java.util.Objects;


public class Book {

	private String title;
	private double price;

	/**
	 * 
	 * @param title
	 *            title of the book
	 * @param price
	 *            price of the book
	 */
	public Book(String title, double price) {
		this.title = title;
		this.price = price;
	}

	public Book() {
	}

	public String getTitle() {
		return title;
	}

	public double getPrice() {
		return price;
	}

	@Override
	public String toString() {
		return "BookDTO [title=" + title + ", price=" + price + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(title, price);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Book)) {
			return false;
		}
		Book book = (Book) obj;
		return title.equals(book.getTitle()) && price == book.getPrice();
	}

}
