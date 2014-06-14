package carrental.domain;

import java.io.Serializable;
import java.util.List;

import org.joda.time.LocalDate;

public class Order implements Serializable {
	/**
	 * 
	 */

	// Constants
	// ----------------------------------------------------------------------------------------------------
	private static final long serialVersionUID = -1747324716684117657L;

	// Fields
	// ----------------------------------------------------------------------------------------------------
	private Integer orderId, userId, vehicleId;
	private long startTimeMillis, endTimeMillis;
	private OrderStatus orderStatus;
	private List<OrderHistory> orderHistoryList;

	// Constructors
	// ----------------------------------------------------------------------------------------------------

	public Order() {
	}

	public Order(Integer userId, Integer vehicleId, long startTime, long endTime, OrderStatus orderStatus, Integer orderId) {
		this.userId = userId;
		this.vehicleId = vehicleId;
		this.startTimeMillis = startTime;
		this.endTimeMillis = endTime;
		this.orderStatus = orderStatus;
		this.orderId = orderId;
	}

	// Some private helpers
	// ----------------------------------------------------------------------------------------------------

	// private void validateState() throws ModelCtorException {
	// ModelCtorException ex = new ModelCtorException();
	//
	// if (FAILS == Check.required(userId))
	// ex.add("User required.");
	//
	// if (FAILS == Check.required(vehicleId))
	// ex.add("Auto is required");
	//
	// if (FAILS == Check.required(orderStatus))
	// ex.add("Order status time is required");
	//
	// if (FAILS == Check.required(endTime, Check.min(startTime)))
	// ex.add("End time must be after start time");
	//
	// if (ex.isNotEmpty())
	// throw ex;
	// }

	// Getters
	// ----------------------------------------------------------------------------------------------------

	public Integer getOrderId() {
		return orderId;
	}

	public Integer getUserId() {
		return userId;
	}

	public Integer getVehicleId() {
		return vehicleId;
	}

	public long getStartTimeMillis() {
		return startTimeMillis;
	}

	public long getEndTimeMillis() {
		return endTimeMillis;
	}

	public OrderStatus getOrderStatus() {
		return orderStatus;
	}

	public LocalDate getStartDate() {
		return new LocalDate(startTimeMillis);
	}

	public LocalDate getEndDate() {
		return new LocalDate(endTimeMillis);
	}

	// Setters
	// ----------------------------------------------------------------------------------------------------

	public void setStartDate(LocalDate date) {
		startTimeMillis = date.toDateTimeAtStartOfDay().getMillis();
	}

	public void setEndDate(LocalDate date) {
		endTimeMillis = date.toDateTimeAtStartOfDay().getMillis();
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public void setVehicleId(Integer vehicleId) {
		this.vehicleId = vehicleId;
	}

	public void setStartTimeMillis(long startTime) {
		this.startTimeMillis = startTime;
	}

	public void setEndTimeMillis(long endTime) {
		this.endTimeMillis = endTime;
	}

	public void setOrderStatus(OrderStatus orderStatus) {
		this.orderStatus = orderStatus;
	}

	// Object overrides
	// ----------------------------------------------------------------------------------------------------

	/**
	 * The order ID is unique for each Order. So Order with same ID should
	 * return same hashcode.
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return (orderId != null) ? (this.getClass().hashCode() + orderId.hashCode()) : super.hashCode();
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Order [orderId=").append(orderId).append(", userId=").append(userId).append(", vehicleId=").append(vehicleId)
				.append(", startTime=").append(startTimeMillis).append(", endTime=").append(endTimeMillis).append(", orderStatus=")
				.append(orderStatus)
				.append("]");
		return builder.toString();
	}

	/**
	 * The order ID is unique for each Order. So this should compare Order by ID
	 * only.
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Order other = (Order) obj;
		if (orderId == null) {
			if (other.orderId != null)
				return false;
		} else if (!orderId.equals(other.orderId))
			return false;
		return true;
	}

	public List<OrderHistory> getOrderHistoryList() {
		return orderHistoryList;
	}

	public void setOrderHistoryList(List<OrderHistory> orderHistoryList) {
		this.orderHistoryList = orderHistoryList;
	}

}
