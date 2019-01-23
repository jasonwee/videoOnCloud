package play.learn.java.design.api_gateway;

public class DesktopProduct {
	/**
	 * The price of the product
	 */
	private String price;

	/**
	 * The path to the image of the product
	 */
	private String imagePath;

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
}
