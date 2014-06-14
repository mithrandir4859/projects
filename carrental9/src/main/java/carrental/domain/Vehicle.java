package carrental.domain;

import java.io.Serializable;

public class Vehicle implements Serializable {
	/**
	 * 
	 */

	// Constants
	// ----------------------------------------------------------------------------------------------------
	private static final long serialVersionUID = 5657729289976885394L;
	public static final long MAX_MODEL_LEN = 25;

	// Fields
	// ----------------------------------------------------------------------------------------------------
	private String model;
	private int mileage, year;
	private Integer vehicleId;

	// Constructors
	// ----------------------------------------------------------------------------------------------------

	public Vehicle(String model, int mileage, int year, Integer vehicleId) {
		this.model = model;
		this.mileage = mileage;
		this.year = year;
		this.vehicleId = vehicleId;
	}

	// Getters
	// ----------------------------------------------------------------------------------------------------

	public String getModel() {
		return model;
	}

	public int getMileage() {
		return mileage;
	}

	public int getYear() {
		return year;
	}

	public Integer getVehicleId() {
		return vehicleId;
	}

	// Setters
	// ----------------------------------------------------------------------------------------------------

	public void setModel(String model) {
		this.model = model;
	}

	public void setMileage(int mileage) {
		this.mileage = mileage;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public void setVehicleId(Integer vehicleId) {
		this.vehicleId = vehicleId;
	}

	// Object overrides
	// ----------------------------------------------------------------------------------------------------

	@Override
	public int hashCode() {
		return (vehicleId != null) ? (this.getClass().hashCode() + vehicleId.hashCode()) : super.hashCode();
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Vehicle [model=").append(model).append(", mileage=").append(mileage).append(", year=").append(year)
				.append(", vehicleId=").append(vehicleId).append("]");
		return builder.toString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Vehicle other = (Vehicle) obj;
		if (vehicleId == null) {
			if (other.vehicleId != null)
				return false;
		} else if (!vehicleId.equals(other.vehicleId))
			return false;
		return true;
	}

}
