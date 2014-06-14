package carrental.repository;

import java.util.List;

import carrental.domain.User;
import carrental.domain.UserStatus;

/**
 * This interface represents a contract for a DAO for the {@link User} model.
 * Note that all methods which returns the {@link User} from the DB, will not
 * fill the model with the password, due to security reasons.
 */
public interface UserDao {

	// Create methods (from CRUDL)
	// ----------------------------------------------------------------------------------------------------

	/**
	 * Create the given user in the database. The user ID must be null,
	 * otherwise it will throw IllegalArgumentException. After creating, the DAO
	 * will set the obtained ID in the given user.
	 * @param user The user to be created in the database.
	 * @throws IllegalArgumentException If the user ID is NOT null.
	 * @throws DaoException If something fails at database level.
	 */
	void create(User user) throws IllegalArgumentException;

	// Read methods (from CRUDL)
	// ----------------------------------------------------------------------------------------------------

	/**
	 * Returns the user from the database matching the given ID, otherwise null.
	 * @param id The ID of the user to be returned.
	 * @return The user from the database matching the given ID, otherwise null.
	 * @throws DaoException If something fails at database level.
	 */
	User find(Integer id) ;

	/**
	 * Returns the user from the database matching the given email and password,
	 * otherwise null.
	 * @param email The email of the user to be returned.
	 * @param password The password of the user to be returned.
	 * @return The user from the database matching the given email and password,
	 * otherwise null.
	 * @throws DaoException If something fails at database level.
	 */
	User find(String email, String password);
	
	User find(String email);

	// Update methods (from CRUDL)
	// ----------------------------------------------------------------------------------------------------

	/**
	 * Update the given user in the database. The user ID must not be null,
	 * otherwise it will throw IllegalArgumentException. Note: the password will
	 * NOT be updated. Use changePassword() instead.
	 * @param user The user to be updated in the database.
	 * @throws IllegalArgumentException If the user ID is null.
	 * @throws DaoException If something fails at database level.
	 */
	void update(User user) throws IllegalArgumentException;

	/**
	 * Update the status of user with given ID in the database with the given
	 * new status
	 * @param userId ID of user whom status is about to be updated
	 * @param newStatus
	 * @throws DaoException If something fails at database level.
	 */
	void update(Integer userId, UserStatus newStatus);

	/**
	 * Change the password of the user with given id
	 * @param userId
	 * @param oldPassword must match the password, saved in database, otherwise
	 * IllegalArgumentException will be thrown
	 * @param newPassword
	 * @throws DaoException If something fails at database level.
	 * @throws IllegalArgumentException if userId doesn't exist in database or
	 * if the oldPassword isn't equal to saved pass
	 */
	void changePassword(Integer userId, String oldPassword, String newPassword) throws IllegalArgumentException;

	// Delete methods (from CRUDL)
	// ----------------------------------------------------------------------------------------------------
	
	/**
	 * Delete the given user from the database. After deleting, the DAO will set
	 * the ID of the given user to null.
	 * @param user The user to be deleted from the database.
	 * @throws DaoException If something fails at database level.
	 */
	void delete(User user);
	
	void delete(Integer userId);
	
	// List methods (from CRUDL)
	// ----------------------------------------------------------------------------------------------------
	
	public int getUsersCounter();
		
	/**
	 * Returns a list of all users from the database ordered by user ID. The
	 * list is never null and is empty when the database does not contain any
	 * user.
	 * @return A list of all users from the database ordered by user ID.
	 * @throws DaoException If something fails at database level.
	 */
	List<User> list();

	List<User> list(Integer startId, int howMany);
	

}