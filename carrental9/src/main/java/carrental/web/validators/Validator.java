package carrental.web.validators;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Validator {
	private static final String DEFAULT_PROPERTY_FILE = "validatorConstraintDescriptor.xml";

	private Properties constraintDescriptor;

	public Validator() {
		InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(DEFAULT_PROPERTY_FILE);
		constraintDescriptor = new Properties();
		try {
			constraintDescriptor.loadFromXML(inputStream);
		} catch (IOException e) {
			throw new RuntimeException("Error during reading property file \"" + DEFAULT_PROPERTY_FILE + "\" from classpath.", e);
		}
	}

	public Validator(Properties constraintDescriptor) {
		this.constraintDescriptor = constraintDescriptor;
	}

	public void check(String paramName, String paramValue, ErrorContainer errorContainer) {
		if (isEmpty(paramName))
			throw new IllegalArgumentException("Empty paramName");
		if (errorEmpty(paramName, paramValue, errorContainer))
			return;
		errorTooLong(paramName, paramValue, errorContainer);
		errorTooShort(paramName, paramValue, errorContainer);
		errorDoesntMatchRegex(paramName, paramValue, errorContainer);
	}

	private boolean errorTooShort(String paramName, String paramValue, ErrorContainer errorContainer) {
		String strMinLength = constraintDescriptor.getProperty(paramName + ".minLength");
		if (isEmpty(strMinLength))
			return false;
		Integer minLength = Integer.parseInt(strMinLength);
		if (paramValue.length() < minLength) {
			errorContainer.addError("tooShort", paramName);
			return true;
		}
		return false;
	}

	private boolean errorTooLong(String paramName, String paramValue, ErrorContainer errorContainer) {
		String strMaxLength = constraintDescriptor.getProperty(paramName + ".maxLength");
		if (isEmpty(strMaxLength))
			return false;
		Integer maxLength = Integer.parseInt(strMaxLength);
		if (paramValue.length() > maxLength) {
			errorContainer.addError("tooLong", paramName);
			return true;
		}
		return false;
	}

	private boolean errorDoesntMatchRegex(String paramName, String paramValue, ErrorContainer errorContainer) {
		String regex = constraintDescriptor.getProperty(paramName + ".regex");
		if (!isEmpty(regex))
			if (!paramValue.matches(regex = regex.trim())) {
				errorContainer.addError("badFormat", paramName);
				return true;
			}
		return false;
	}

	private boolean isEmpty(String paramValue) {
		return paramValue == null || paramValue.isEmpty();
	}

	public boolean errorEmpty(String paramName, String paramValue, ErrorContainer errorContainer) {
		if (isEmpty(paramValue)) {
			errorContainer.addError("empty", paramName);
			return true;
		}
		return false;
	}

}
