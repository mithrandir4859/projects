package carrental.web.controllers.actions;

import static carrental.web.controllers.FrontController.REDIRECT_PREFIX;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import carrental.repository.OrderDao;
import carrental.repository.OrderHistoryDao;
import carrental.repository.PassportInfoDao;
import carrental.repository.UserDao;
import carrental.repository.VehicleDao;
import carrental.web.controllers.actions.annotations.Mapping;
import carrental.web.validators.ErrorContainer;
import carrental.web.validators.Validator;

public abstract class GenericAction implements Action {
	protected OrderDao orderDao;
	protected UserDao userDao;
	protected VehicleDao vehicleDao;
	protected Validator validator;
	protected OrderHistoryDao orderHistoryDao;
	protected String view, errorView;
	protected PassportInfoDao passportInfoDao;
	protected Logger logger = Logger.getLogger(getClass());
	protected Map<String, ?> fields;

	public GenericAction() {
		Mapping mappingAnnotation = getClass().getAnnotation(Mapping.class);
		String errorView = mappingAnnotation.errorView();
		if (errorView != null && !errorView.isEmpty())
			this.errorView = errorView;
		view = mappingAnnotation.view();
	}

	public void setFields(Map<String, ?> fields) {
		this.fields = fields;
		try {
			orderDao = (OrderDao) fields.get("orderDao");
			userDao = (UserDao) fields.get("userDao");
			vehicleDao = (VehicleDao) fields.get("vehicleDao");
			validator = (Validator) fields.get("validator");
			orderHistoryDao = (OrderHistoryDao) fields.get("orderHistoryDao");
			passportInfoDao = (PassportInfoDao) fields.get("passportInfoDao");
		} catch (ClassCastException e) {
			throw new IllegalArgumentException("Incorrect fields provided", e);
		}
	}

	public void process(HttpServletRequest request, HttpServletResponse response, ErrorContainer ex)
			throws ServletException, IOException{}

	@Override
	public String process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ErrorContainer errorContainer = new ErrorContainer();
		try {
			process(request, response, errorContainer);
		} catch (org.springframework.dao.DataAccessException dataAccessException) {
			logger.error("Exception during process() method", dataAccessException);
			throw dataAccessException;
		}
		if (errorContainer.isEmpty())
			return view;
		if (errorView.startsWith(REDIRECT_PREFIX)) {
			HttpSession session = request.getSession();
			session.setAttribute("errorMap", errorContainer.getErrorMap());
			session.setAttribute("errorList", errorContainer.getErrors());
		} else {
			request.setAttribute("errorMap", errorContainer.getErrorMap());
			request.setAttribute("errorList", errorContainer.getErrors());
		}

		return errorView;
	}

	protected void setAttributeIfOldValueIsNull(HttpServletRequest request, String attributeName, Object value) {
		if (request.getAttribute(attributeName) == null)
			request.setAttribute(attributeName, value);
	}

	protected Integer parseInt(HttpServletRequest request, String parameterName, ErrorContainer errorContainer) {
		String candidateInteger = request.getParameter(parameterName);
		try {
			if (!validator.errorEmpty(parameterName, candidateInteger, errorContainer))
				return Integer.parseInt(candidateInteger);
		} catch (NumberFormatException | NullPointerException e) {
			errorContainer.addError("badFormat", parameterName);
		}
		return null;
	}

	protected Long parseLong(HttpServletRequest request, String parameterName, ErrorContainer errorContainer) {
		String candidateLong = request.getParameter(parameterName);
		try {
			if (!validator.errorEmpty(parameterName, candidateLong, errorContainer))
				return Long.parseLong(candidateLong);
		} catch (NumberFormatException | NullPointerException e) {
			errorContainer.addError("badFormat", parameterName);
		}
		return null;
	}

	@Override
	public GenericAction clone() {
		try {
			GenericAction action = getClass().newInstance();
			action.setFields(fields);
			return action;
		} catch (InstantiationException | IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}

}
