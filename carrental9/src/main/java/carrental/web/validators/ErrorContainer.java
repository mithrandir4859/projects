package carrental.web.validators;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ErrorContainer{
	private Map<String, List<String>> errorMap = new HashMap<>();
	private Set<String> errors = new HashSet<>();

	public void addError(String error) {
		errors.add(error);
	}

	public void addError(String error, String paramName) {
		List<String> errorListForGivenParamName ;
		if (errorMap.get(paramName) == null)
			errorMap.put(paramName, errorListForGivenParamName = new LinkedList<String>());
		else
			errorListForGivenParamName = errorMap.get(paramName);
		errorListForGivenParamName.add(error);
	}

	public Iterable<String> getErrors() {
		return Collections.unmodifiableSet(errors);
	}

	public Map<String, List<String>> getErrorMap() {
		return Collections.unmodifiableMap(errorMap);
	}
	
	public boolean isEmpty() {
		return errorMap.isEmpty() && errors.isEmpty();
	}

	public boolean hasErrors() {
		return !isEmpty();
	}
	
	public void addError(String error, String paramName, boolean saveErrorIntoMap) {
		if (saveErrorIntoMap)
			addError(error, paramName);
		else 
			addError(error);
	}

}