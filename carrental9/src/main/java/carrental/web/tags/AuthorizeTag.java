package carrental.web.tags;

import java.io.IOException;
import java.io.StringWriter;
import java.util.EnumSet;

import javax.servlet.jsp.JspContext;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import carrental.domain.User;
import carrental.domain.UserStatus;

public class AuthorizeTag extends SimpleTagSupport {

	private String access;

	public void setAccess(String access) {
		this.access = access;
	}

	@Override
	public void doTag() throws JspException, IOException {
		JspContext jspContext = getJspContext();
		UserStatus currentUserStatus = getCurrentUserStatus(jspContext);
		if (getAllowed().contains(currentUserStatus)) {
			StringWriter sw = new StringWriter();
			getJspBody().invoke(sw);
			JspWriter out = jspContext.getOut();
			out.println(sw.toString());
		}
	}

	UserStatus getCurrentUserStatus(JspContext jspContext) {
		Object objUser;

		try {
			objUser = jspContext.getAttribute("user", PageContext.SESSION_SCOPE);
		} catch (IllegalArgumentException | IllegalStateException e) {
			return UserStatus.ANON;
		}

		if (objUser == null)
			return UserStatus.ANON;
		
		User user = (User) objUser;
		return user.getUserStatus();
	}

	EnumSet<UserStatus> getAllowed() {
		EnumSet<UserStatus> allowed = EnumSet.noneOf(UserStatus.class);
		for (UserStatus userStatus : UserStatus.values())
			if (access.contains(userStatus.toString()))
				allowed.add(userStatus);
		return allowed;
	}

}
