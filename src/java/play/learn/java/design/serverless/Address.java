package play.learn.java.design.serverless;

import java.io.Serializable;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;

@DynamoDBDocument
public class Address implements Serializable {
	private static final long serialVersionUID = 6760844284799736970L;

	private String addressLineOne;
	private String addressLineTwo;
	private String city;
	private String state;
	private String zipCode;

	@DynamoDBAttribute(attributeName = "addressLineOne")
	public String getAddressLineOne() {
		return addressLineOne;
	}

	public void setAddressLineOne(String addressLineOne) {
		this.addressLineOne = addressLineOne;
	}

	@DynamoDBAttribute(attributeName = "addressLineTwo")
	public String getAddressLineTwo() {
		return addressLineTwo;
	}

	public void setAddressLineTwo(String addressLineTwo) {
		this.addressLineTwo = addressLineTwo;
	}

	@DynamoDBAttribute(attributeName = "city")
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	@DynamoDBAttribute(attributeName = "state")
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	@DynamoDBAttribute(attributeName = "zipCode")
	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}

		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		Address address = (Address) o;

		if (addressLineOne != null ? !addressLineOne.equals(address.addressLineOne) : address.addressLineOne != null) {
			return false;
		}

		if (addressLineTwo != null ? !addressLineTwo.equals(address.addressLineTwo) : address.addressLineTwo != null) {
			return false;
		}

		if (city != null ? !city.equals(address.city) : address.city != null) {
			return false;
		}
		if (state != null ? !state.equals(address.state) : address.state != null) {
			return false;
		}
		return zipCode != null ? zipCode.equals(address.zipCode) : address.zipCode == null;
	}

	@Override
	public int hashCode() {
		int result = addressLineOne != null ? addressLineOne.hashCode() : 0;
		result = 31 * result + (addressLineTwo != null ? addressLineTwo.hashCode() : 0);
		result = 31 * result + (city != null ? city.hashCode() : 0);
		result = 31 * result + (state != null ? state.hashCode() : 0);
		result = 31 * result + (zipCode != null ? zipCode.hashCode() : 0);
		return result;
	}

	@Override
	public String toString() {
		return "Address{" + "addressLineOne='" + addressLineOne + '\'' + ", addressLineTwo='" + addressLineTwo + '\''
				+ ", city='" + city + '\'' + ", state='" + state + '\'' + ", zipCode='" + zipCode + '\'' + '}';
	}
}
