package carrental.web.tags;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.jsp.JspContext;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;

public class LocalizedMap extends SimpleTagSupport {

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
		@SuppressWarnings("unchecked")
		Map<String, List<String>> errorMap = (Map<String, List<String>>) jspContext.getAttribute(name, PageContext.REQUEST_SCOPE);
		if (errorMap == null || errorMap.isEmpty())
			return;
		utils.print("<table class=\"withBorders\"");
		for (String key: errorMap.keySet()){
			List<String> errors = errorMap.get(key);
			utils.print("<tr><th rowspan=\"" + errors.size() + "\">" + key + "</th>");
			utils.printLocalizedList(errors, "<td>", "</td>", prefix + key + ".");
			utils.print("</tr>");
		}
		utils.print("</table>");
	}

}
