package carrental.repository;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import javax.sql.DataSource;

public class FileLoader
{
	// Constants
	// ----------------------------------------------------------------------------------------------------
	private final static char QUERY_ENDS = ';';
	private final static char COMMENT_INDICATOR = '#';

	// Private constructor, so nobody can instantiate class
	// ----------------------------------------------------------------------------------------------------
	private FileLoader() {
	}

	public static InputStream getResourceAsStream(String resource) {
		return Thread.currentThread().getContextClassLoader().getResourceAsStream(resource);
	}

	public static Properties loadPropertiesFromClasspath(String filename) {
		if (!filename.contains("."))
			filename += ".properties";
		InputStream inputStream = getResourceAsStream(filename);
		Properties properties = new Properties();
		try {
			if (filename.endsWith(".xml"))
				properties.loadFromXML(inputStream);
			else {
				InputStreamReader in = new InputStreamReader(inputStream, "UTF-8");
				properties.load(in);
			}
		} catch (IOException e) {
			throw new RuntimeException("Error during reading property file \"" + filename + "\" from classpath.", e);
		}
		return properties;
	}

	/**
	 * This method reads and executes all queries from a given file using given
	 * data source
	 * @param dbCreationFile
	 * @param ds
	 */
	public static void executeQueries(InputStream inputStream, DataSource ds) {
		try (Connection connection = ds.getConnection();
				Statement stmt = connection.createStatement();
				BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
			StringBuffer currentQuery = new StringBuffer();
			String line;
			while ((line = reader.readLine()) != null) {
				if (isComment(line))
					continue;
				currentQuery.append(line);
				if (currentQueryEndsNow(line)) {
					// stmt.addBatch(currentQuery.toString());
					currentQuery.deleteCharAt(currentQuery.length() - 1);
					stmt.execute(currentQuery.toString());
					currentQuery.setLength(0);
				}
			}

			// stmt.executeBatch();

		} catch (SQLException | IOException e) {
			throw new RuntimeException(e);
		}

	}

	private static boolean isComment(String line) {
		if ((line != null) && (line.length() > 0))
			return (line.charAt(0) == COMMENT_INDICATOR);
		return false;
	}

	private static boolean currentQueryEndsNow(String s) {
		return (s.indexOf(QUERY_ENDS) != -1);
	}
}