package carrental.domain;

import java.io.Serializable;

public class User implements Serializable {
	/**
	 * 
	 */

	// Constants
	// ----------------------------------------------------------------------------------------------------
	private static final long serialVersionUID = -2834778306232001744L;

	// Fields
	// ----------------------------------------------------------------------------------------------------
	private String email, password, firstname, lastname, phone;
	private UserStatus userStatus;
	private Integer userId;

	// Constructors
	// ----------------------------------------------------------------------------------------------------

	public User() {
	}

	public User(String email, String password, String firstname, String lastname, String phone, UserStatus userStatus, Integer userId) {
		this.email = email;
		this.password = password;
		this.firstname = firstname;
		this.lastname = lastname;
		this.phone = phone;
		this.userStatus = userStatus;
		this.userId = userId;
	}

	// Getters
	// ----------------------------------------------------------------------------------------------------

	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}

	public String getFirstname() {
		return firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public String getPhone() {
		return phone;
	}

	public UserStatus getUserStatus() {
		return userStatus;
	}

	public Integer getUserId() {
		return userId;
	}

	// Setters
	// ----------------------------------------------------------------------------------------------------

	public void setUserId(Integer id) {
		userId = id;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public void setUserStatus(UserStatus userStatus) {
		this.userStatus = userStatus;
	}

	// Object overrides
	// ----------------------------------------------------------------------------------------------------
	
	

	/**
	 * The user ID is unique for each User. So users with same IDs should return
	 * same hashcode.
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return (userId != null) ? (this.getClass().hashCode() + userId.hashCode()) : super.hashCode();
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("User [email=").append(email).append(", password=").append(password).append(", firstname=").append(firstname)
				.append(", lastname=").append(lastname).append(", phone=").append(phone).append(", userStatus=").append(userStatus)
				.append(", userId=").append(userId).append("]");
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
		User other = (User) obj;
		if (userId == null) {
			if (other.userId != null)
				return false;
		} else if (!userId.equals(other.userId))
			return false;
		return true;
	}

	// Some private helpers
	// ----------------------------------------------------------------------------------------------------

	// private void validateState() throws ModelCtorException {
	// ModelCtorException ex = new ModelCtorException();
	//
	// if (FAILS == Check.required(password))
	// ex.add("Password is required");
	//
	// if (FAILS == Check.required(email, Check.email()))
	// ex.add("Email is required, must be like: blahblah@domen.bhah");
	//
	// if (ex.isNotEmpty())
	// throw ex;
	// }

}
