package florist.domain.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import florist.domain.Bouquet;
import florist.domain.BouquetAccessory;
import florist.domain.Flower;
import florist.domain.Merchandise;

public class BouquetImpl extends MerchandiseImpl implements Bouquet {

	// Fields
	// -----------------------------------------------
	private List<Flower> flowers;
	private List<BouquetAccessory> accessories;

	// Constructors
	// -----------------------------------------------
	public BouquetImpl(BigDecimal price, String name, List<Flower> flowers, List<BouquetAccessory> accessories) {
		super(price, name);
		if (flowers.size() == 0)
			throw new IllegalArgumentException("There must be at least one flower in a bouquet");
		this.flowers = new ArrayList<Flower>(flowers);
		this.accessories = accessories == null ? new ArrayList<BouquetAccessory>() : new ArrayList<BouquetAccessory>(accessories);
	}

	@SuppressWarnings("unchecked")
	public BouquetImpl(String name, List<Flower> flowers, List<BouquetAccessory> accessories) {
		this(countPrice(accessories, flowers), name, flowers, accessories);
	}

	// Static helper methods
	// -----------------------------------------------

	public static BigDecimal countPrice(List<? extends Merchandise>... merchandiseLists) {
		BigDecimal sum = new BigDecimal(0);
		sum.setScale(DECIMAL_NUMBERS);
		for (List<? extends Merchandise> mList : merchandiseLists)
			for (Merchandise m : mList)
				sum = sum.add(m.getPrice());
		return sum;
	}

	// Getters
	// -----------------------------------------------

	@Override
	public List<Flower> getFlowers() {
		return Collections.unmodifiableList(flowers);
	}

	@Override
	public List<BouquetAccessory> getAccessories() {
		return Collections.unmodifiableList(accessories);
	}

	// Object overrides
	// -----------------------------------------------

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((accessories == null) ? 0 : accessories.hashCode());
		result = prime * result + ((flowers == null) ? 0 : flowers.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		BouquetImpl other = (BouquetImpl) obj;
		if (accessories == null) {
			if (other.accessories != null)
				return false;
		} else if (!accessories.equals(other.accessories))
			return false;
		if (flowers == null) {
			if (other.flowers != null)
				return false;
		} else if (!flowers.equals(other.flowers))
			return false;
		return true;
	}

	@Override
	public String toString() {
		final int maxLen = 5;
		StringBuilder builder = new StringBuilder();
		builder.append("Bouquet ").append(super.toString()).append(", flowers: ")
				.append(flowers != null ? flowers.subList(0, Math.min(flowers.size(), maxLen)) : null)
				.append(", accessories: ").
				append(accessories != null ? accessories.subList(0, Math.min(accessories.size(), maxLen)) : null);
		return builder.toString();
	}

}
