package carrental.web.tags;

import java.io.IOException;

import javax.servlet.jsp.JspContext;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

public class LocalizedList extends SimpleTagSupport {

	private String name;
	private JspContext jspContext;
	private TagUtils utils;
	private String prefix;
	
	

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public void doTag() throws JspException, IOException {
		jspContext = getJspContext();
		utils = new TagUtils(jspContext);
		if (prefix == null || prefix.isEmpty())
			utils.printLocalizedList(name, "p");
		else
			utils.printLocalizedList(name, "<p>", "</p>", prefix);
	}

}
