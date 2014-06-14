package carrental.web.controllers.actions;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.reflections.Reflections;

import carrental.domain.UserStatus;
import carrental.web.controllers.actions.annotations.Access;

public class ActionFactoryImpl implements ActionFactory {

	private static final String DEFAULT_PACKAGE_TO_SCAN = "carrental.web.controllers.actions.impl";
	private Map<String, Action> actions = new HashMap<>();
	private Map<String, EnumSet<UserStatus>> actionsAccessRights = new HashMap<>();
	private Map<String, ?> fields;

	public ActionFactoryImpl( Map<String, ?> fields) throws InstantiationException,
			IllegalAccessException {
		this(fields, DEFAULT_PACKAGE_TO_SCAN);
	}

	public ActionFactoryImpl(Map<String, ?> fields, String... packageNames)
			throws InstantiationException,
			IllegalAccessException {
		this.fields = fields;
		Set<Class<? extends GenericAction>> actionClasses = getClasses(packageNames, GenericAction.class);
		for (Class<? extends Action> actionClass : actionClasses) {
			String actionName = getName(actionClass);
			putActionInAMap(actionClass, actionName);
			putAccessRightsInAMap(actionClass, actionName);
		}
	}

	protected String getName(Class<?> klass) {
		String actionName = klass.getSimpleName();
		return String.valueOf(actionName.charAt(0)).toLowerCase() + actionName.substring(1);
	}

	protected <T> Set<Class<? extends T>> getClasses(String[] packageNames, Class<T> klass) {
		Set<Class<? extends T>> allClasses = new HashSet<>();
		for (String packageName : packageNames) {
			Reflections reflections = new Reflections(packageName);
			allClasses.addAll(reflections.getSubTypesOf(klass));
		}
		return allClasses;
	}

	protected void putActionInAMap(Class<? extends Action> actionClass, String actionName) throws InstantiationException,
			IllegalAccessException {
		Action action = actionClass.newInstance();
		if (action instanceof GenericAction) {
			GenericAction superAction = (GenericAction) action;
			superAction.setFields(fields);
		}
		actions.put(actionName, action);
	}

	protected void putAccessRightsInAMap(Class<? extends Action> actionClass, String actionName) {
		Access accessAnnotation = actionClass.getAnnotation(Access.class);
		EnumSet<UserStatus> accessRights = EnumSet.noneOf(UserStatus.class);
		for (UserStatus userStatus : accessAnnotation.value())
			accessRights.add(userStatus);
		actionsAccessRights.put(actionName, accessRights);
	}

	@Override
	public Action getAction(String name) {
		exists(name);
		return actions.get(name).clone();
	}

	@Override
	public boolean exists(String name) throws NullPointerException {
		if (name == null)
			throw new NullPointerException();
		return actions.containsKey(name);
	}

	@Override
	public boolean isAuthorized(UserStatus userStatus, String logicalPath) throws IllegalArgumentException, NullPointerException {
		if (!exists(logicalPath))
			throw new IllegalArgumentException();
		return actionsAccessRights.get(logicalPath).contains(userStatus);
	}

}
