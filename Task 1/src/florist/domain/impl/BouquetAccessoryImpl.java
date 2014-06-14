package florist.domain.impl;

import java.math.BigDecimal;

import florist.domain.BouquetAccessory;

public class BouquetAccessoryImpl extends MerchandiseImpl implements BouquetAccessory {

	public BouquetAccessoryImpl(BigDecimal price, String name) {
		super(price, name);
	}

}
