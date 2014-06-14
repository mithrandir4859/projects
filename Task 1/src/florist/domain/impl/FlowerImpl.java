package florist.domain.impl;

import java.math.BigDecimal;

import florist.domain.Flower;
import florist.domain.Freshness;

public class FlowerImpl extends MerchandiseImpl implements Flower {

	// Constants
	// -----------------------------------------------
	private static final int DEFAULT_STEM_LENGTH = 35;
	private static final Freshness DEFAULT_FRESHNESS = Freshness.GOOD;

	// Fields
	// -----------------------------------------------
	private Freshness freshness;
	private int stemLength;

	// Constructors
	// -----------------------------------------------

	public FlowerImpl(BigDecimal price, String name) {
		this(price, name, DEFAULT_FRESHNESS, DEFAULT_STEM_LENGTH);
	}

	public FlowerImpl(BigDecimal price, String name, Freshness freshness, int stemLength) {
		super(price, name);
		checkStemLength(stemLength);
		this.stemLength = stemLength;
		this.freshness = freshness;
	}

	// Setters & Getters
	// -----------------------------------------------

	@Override
	public Freshness getFreshness() {
		return freshness;
	}

	@Override
	public void setFreshness(Freshness freshness) {
		this.freshness = freshness;
	}

	@Override
	public int getStemLength() {
		return stemLength;
	}

	@Override
	public void setStemLength(int length) {
		checkStemLength(length);
		stemLength = length;
	}

	// Some helper methods
	// -----------------------------------------------

	protected static void checkStemLength(int length) {
		if (length < 0)
			throw new IllegalArgumentException("Stem lengths should be non-negative");
	}

	// Object overrides
	// -----------------------------------------------

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((freshness == null) ? 0 : freshness.hashCode());
		result = prime * result + stemLength;
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
		FlowerImpl other = (FlowerImpl) obj;
		if (freshness != other.freshness)
			return false;
		if (stemLength != other.stemLength)
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(super.toString()).append(", stemLength: ").append(stemLength).append(", freshness: ").append(freshness);
		return builder.toString();
	}

}
