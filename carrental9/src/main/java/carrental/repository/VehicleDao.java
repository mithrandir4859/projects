package carrental.repository;

import java.util.List;

import org.apache.jasper.tagplugins.jstl.core.If;
import org.joda.time.Interval;

import carrental.domain.Vehicle;


public interface VehicleDao {
	// Create methods (from CRUDL)
	// ----------------------------------------------------------------------------------------------------
	void create(Vehicle vehicle) throws IllegalArgumentException;

	// Read methods (from CRUDL)
	// ----------------------------------------------------------------------------------------------------
	Vehicle find(Integer vehicleId);

	List<Vehicle> findAllAvailable(Interval interval);

	List<Interval> findWhenAvailable(Integer vehicleId) throws IllegalArgumentException;

	List<Interval> findWhenBooked(Integer vehicleId) throws IllegalArgumentException;

	// Update methods (from CRUDL)
	// ----------------------------------------------------------------------------------------------------
	void update(Vehicle vehicle) throws IllegalArgumentException;

	// Delete methods (from CRUDL)
	// ----------------------------------------------------------------------------------------------------
	void delete(Vehicle vehicle) throws IllegalArgumentException;

	void delete(Integer vehicleId) throws IllegalArgumentException;

	// List methods (from CRUDL)
	// ----------------------------------------------------------------------------------------------------

	/**
	 * @return number of vehicles in database
	 * @throws if something fails at database level
	 */
	int getVehicleCounter();

	/**
	 * Returns a list of all vehicles from the database ordered by vehicles ID.
	 * The list is never null and is empty when the database does not contain
	 * any vehicle.
	 * @return A list of all vehicles from the database ordered by vehicles ID.
	 * @throws If something fails at database level.
	 */
	List<Vehicle> list();

	/**
	 * @param startId the least vehicle Id in list
	 * @param howMany number of vehicles in list. The actual list size can be
	 * less then given number if database does not contain enough vehicles
	 * @return list of vehicles with IDs in specified range
	 * @throws if something fails at database level.
	 */
	List<Vehicle> list(Integer startId, int howMany);

}
