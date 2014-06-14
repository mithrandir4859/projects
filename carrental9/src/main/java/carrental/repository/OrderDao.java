package carrental.repository;

import java.util.List;

import org.apache.jasper.tagplugins.jstl.core.If;

import carrental.domain.Order;
import carrental.domain.OrderStatus;


public interface OrderDao {

	// Create methods (from CRUDL)
	// ----------------------------------------------------------------------------------------------------
	/**
	 * Creates the given order in the database. The order ID must be null,
	 * otherwise it will throw IllegalArgumentException. After creating, the DAO
	 * will set the obtained ID in the given order.
	 * @param order The order to be created in the database.
	 * @throws IllegalArgumentException If the order ID is NOT null.
	 * @throws If something fails at database level.
	 */
	void create(Order order);

	// Read methods (from CRUDL)
	// ----------------------------------------------------------------------------------------------------
	/**
	 * Returns the order from the database matching the given ID, otherwise
	 * null.
	 * @param id The ID of the order to be returned.
	 * @return The order from the database matching the given ID, otherwise
	 * null.
	 * @throws If something fails at database level.
	 */
	Order find(Integer orderId);

	/**
	 * Returns the list of orders from the database matching the given user ID,
	 * otherwise empty list. Never returns null
	 * @param userId The ID of the user to match orders to return with
	 * @return List of orders from the database matching the given user ID,
	 * otherwise empty list
	 * @throws if something fails at database level.
	 */
	List<Order> findByUser(Integer userId);

	/**
	 * Returns the list of orders from the database matching the given vehicle
	 * ID, otherwise empty list. Never returns null
	 * @param vehicleId The ID of the user to match orders to return with
	 * @return List of orders from the database matching the given vehicle ID,
	 * otherwise empty list
	 * @throws if something fails at database level.
	 */
	List<Order> findByVehicle(Integer vehicleId);

	// Update methods (from CRUDL)
	// ----------------------------------------------------------------------------------------------------
	/**
	 * Updates the given order, using its id and data, provided in given Order
	 * object
	 * @param order Order with new data
	 * @throws if something fails at database level
	 * @throws IllegalArgumentException If order ID is null
	 */
	void update(Order order) throws IllegalArgumentException;

	/**
	 * Updates the status of order with a given ID
	 * @param orderId ID of order to be updated
	 * @param orderStatus New status for order
	 * @throws if something fails at database level
	 */
	void update(Integer orderId, OrderStatus orderStatus);

	// Delete methods (from CRUDL)
	// ----------------------------------------------------------------------------------------------------
	/**
	 * Deletes given order. Sets orderId property of given Order object to null
	 * @param order Order to be deleted
	 * @throws if something fails at database level
	 * @throws IllegalArgumentException if orderId of given Order object is null
	 */
	void delete(Order order) throws IllegalArgumentException;
	void delete(Integer orderId) throws IllegalArgumentException;

	// Listing methods (from CRUDL)
	// ----------------------------------------------------------------------------------------------------
	/**
	 * @return number of orders in database
	 * @throws if something fails at database level
	 */
	int getOrderCounter();

	/**
	 * Returns a list of all orders from the database ordered by order ID. The
	 * list is never null and is empty when the database does not contain any
	 * order.
	 * @return A list of all orders from the database ordered by order ID.
	 * @throws If something fails at database level.
	 */
	List<Order> list();

	/**
	 * @param startId the least order Id in list
	 * @param howMany number of orders in list. The actual list size can be
	 * less, then given number if database does not contain more orders
	 * @return list of orders with IDs in specified range
	 * @throws if something fails at database level.
	 */
	List<Order> list(Integer startId, int howMany);

}
