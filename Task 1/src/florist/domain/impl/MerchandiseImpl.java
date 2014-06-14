package florist.domain.impl;

import java.math.BigDecimal;

import florist.domain.Merchandise;

public class MerchandiseImpl implements Merchandise {
	
	// Constants
	// -----------------------------------------------
	private static int MIN_NAME_LENGHT = 3;
	
	// Fields
	// -----------------------------------------------
	private BigDecimal price;
	private String name;

	// Constructor
	// -----------------------------------------------
	
	public MerchandiseImpl(BigDecimal price, String name) {
		super();
		checkName(name);
		checkPrice(price);
		this.price = price;
		this.name = name;
	}
	
	// Setters & Getters
	// -----------------------------------------------

	@Override
	public BigDecimal getPrice() {
		return price;
	}

	@Override
	public void setPrice(BigDecimal price) {
		checkPrice(price);
		this.price = price; 
	}
	
	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		checkName(name);
		this.name = name;
	}
	
	// Some helper methods
	// -----------------------------------------------
	
	static protected void checkPrice(BigDecimal price){
		if (price.longValue() < 0)
			throw new IllegalArgumentException();
	}

	
	static protected void checkName(String name){
		if (name == null)
			throw new NullPointerException("Name is not allowed to be null");
		if (name.length() < MIN_NAME_LENGHT)
			throw new IllegalArgumentException("Length of name should be at least " + MIN_NAME_LENGHT + " characters.");
	}
	
	// Object overrides
	// -----------------------------------------------

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((price == null) ? 0 : price.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MerchandiseImpl other = (MerchandiseImpl) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (price == null) {
			if (other.price != null)
				return false;
		} else if (!price.equals(other.price))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Name: ").append(name).append(", price: ").append(price);
		return builder.toString();
	}
	
	

}
