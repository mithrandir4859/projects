package carrental.web;

import static carrental.domain.UserStatus.ADMIN;
import static carrental.domain.UserStatus.ANON;
import static carrental.domain.UserStatus.USER;
import static java.util.EnumSet.allOf;
import static java.util.EnumSet.of;

import java.util.Collection;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import carrental.domain.UserStatus;

public class CopyOfPathAuthResolverImpl implements PathAuthResolver {
	
	//InternalResourceViewResolver

	private static final String STR_PATTERN_LAST_WORD_OF_PATH = "[^/\\\\]*$";

	private static final String STR_PATTERN_ANY_STRING = ".*";
	
	private static final String STR_WEB_INF = "WEB-INF[/\\\\]";
	
	private static final Pattern PATTERN_GET_RID_OF_ALL_BEFORE_WEB_INF = Pattern.compile(STR_WEB_INF + STR_PATTERN_ANY_STRING);

	private static final Pattern PATTERN_GET_LAST_WORD = Pattern.compile(STR_PATTERN_LAST_WORD_OF_PATH);

	private static final String[] STR_PATTERNS = { getRegex("admin"), getRegex("anon"), getRegex("user"), getRegex("all") };

	@SuppressWarnings("unchecked")
	private static final EnumSet<UserStatus>[] ACCESS_RIGHTS =
			(EnumSet<UserStatus>[]) new EnumSet[] { of(ADMIN), of(ANON), of(USER), allOf(UserStatus.class) };

	private static String getRegex(String string) {
		String firstLetter = string.charAt(0) + "";
		return new StringBuilder()
				.append(STR_PATTERN_ANY_STRING)
				.append("([/\\.\\\\])")
				.append("[")
				.append(firstLetter.toUpperCase())
				.append(firstLetter)
				.append("]")
				.append(string.substring(1))
				.append("\\1").append("")
				.append(STR_PATTERN_ANY_STRING)
				.toString();
	}

	private Map<String, EnumSet<UserStatus>> authorizationRules;
	private Map<String, String> logicalToRealPath;

	public CopyOfPathAuthResolverImpl(Collection<String> jspPaths) {
		authorizationRules = new HashMap<>();
		logicalToRealPath = new HashMap<>();
		for (int i = 0; i < ACCESS_RIGHTS.length; i++)
			process(jspPaths, STR_PATTERNS[i], ACCESS_RIGHTS[i]);
	}

	private void process(Collection<String> paths, String givenPattern, EnumSet<UserStatus> allowedGuys) {
		for (String realPath : paths)
			if (realPath.matches(givenPattern)) {
				String logicPath = getMatchingSubstring(realPath, PATTERN_GET_LAST_WORD);
				realPath = getMatchingSubstring(realPath, PATTERN_GET_RID_OF_ALL_BEFORE_WEB_INF);
				putRule(logicPath, realPath, allowedGuys);
			}
	}

	private String getMatchingSubstring(String realPath, Pattern pattern) {
		Matcher matcher = pattern.matcher(realPath);
		matcher.find();
		return matcher.group();
	}

	private void putRule(String logicPath, String realPath, EnumSet<UserStatus> allowedGuys) {
		if (logicalToRealPath.containsKey(logicPath) || authorizationRules.containsKey(logicPath))
			throw new IllegalArgumentException("Logical pass " + logicPath + " already exists");
		logicalToRealPath.put("/" + logicPath, realPath);
		authorizationRules.put("/" + logicPath, allowedGuys);
	}

	@Override
	public String getRealPath(String logicalPath) throws IllegalArgumentException, NullPointerException {
		return logicalToRealPath.get(logicalPath);
	}

}
