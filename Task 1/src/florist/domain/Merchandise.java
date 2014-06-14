package florist.domain;

import java.math.BigDecimal;

public interface Merchandise {
	
	int DECIMAL_NUMBERS = 2;

	BigDecimal getPrice();

	void setPrice(BigDecimal price);

	String getName();

	void setName(String name);

}
