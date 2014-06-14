package carrental.repository.jdbc;

import org.springframework.jdbc.JdbcUpdateAffectedIncorrectNumberOfRowsException;

public class DaoUtils {

	public static void assertOnlyOneRowAffected(int numberOfRowsAffected) {
		if (numberOfRowsAffected != 1)
			throw new JdbcUpdateAffectedIncorrectNumberOfRowsException("Wrong number of rows was affected", 1, numberOfRowsAffected);
	}

	public static void assertNotNull(Integer someId) {
		if (someId == null)
			throw new IllegalArgumentException("id isn't allowed to be null");
	}

	public static void assertIsNull(Integer someId) {
		if (someId != null)
			throw new IllegalArgumentException("you can't save new model object with non-null id");
	}

}
