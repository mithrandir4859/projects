package carrental.web;

public interface PathAuthResolver {
	
	/**
	 * 
	 * @param logicalPath
	 * @return the realPath corresponding to the given logical
	 * @throws IllegalArgumentException if a given logicalPath doesn't exist (is illegal)
	 * @throws NullPointerException if logicalPath is null
	 */
	String getRealPath(String logicalPath) throws IllegalArgumentException, NullPointerException;
	
}
