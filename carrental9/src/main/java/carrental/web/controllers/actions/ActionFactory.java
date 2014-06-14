package carrental.web.controllers.actions;

import carrental.domain.UserStatus;

public interface ActionFactory {

	/**
	 * @param name
	 * @return Action for a given name or null if given action doesn't exist
	 * @throws NullPointerException if name is null
	 */
	Action getAction(String name) throws NullPointerException;

	/**
	 * @param name
	 * @return true if an action with given name exists
	 * @throws NullPointerException if name is null
	 */
	boolean exists(String name) throws NullPointerException;

	/**
	 * @param userStatus
	 * @param name
	 * @return true if user with a given userStatus has a right to access an action with a given name
	 * @throws IllegalArgumentException if an action with given name doesn't exist
	 * @throws NullPointerException if userStatus is null or name is null (or both)
	 */
	boolean isAuthorized(UserStatus userStatus, String logicalPath) throws IllegalArgumentException, NullPointerException;

}
