package carrental.web.tags;

import java.io.IOException;

import javax.servlet.jsp.JspContext;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;

public class DropdownList extends SimpleTagSupport {

	private String name;
	private Integer start, end;
	private JspWriter out;
	private Integer selectedValue;

	public void setName(String name) {
		this.name = name;
	}

	public void setStart(Integer start) {
		this.start = start;
	}

	public void setEnd(Integer end) {
		this.end = end;
	}

	@Override
	public void doTag() throws JspException, IOException {
		extractSelectedValue();
		out.println("<select name='" + name + "'>");
		int i = start;
		for (; i < selectedValue; i++)
			printOptionTag(i);
		printSelectedOptionTag(i++);
		for (; i < end; i++)
			printOptionTag(i);
		out.println("</select>");
	}

	private void extractSelectedValue() {
		JspContext jspContext = getJspContext();
		out = jspContext.getOut();
		try {
			selectedValue = (Integer) jspContext.getAttribute(name, PageContext.REQUEST_SCOPE);
		} catch (Exception e) {
			// NOP
		}
		try {
			selectedValue = Integer.parseInt(((PageContext) jspContext).getRequest().getParameter(name));
		} catch (Exception e) {
			// NOP
		}
		if (selectedValue == null) {
			selectedValue = start;
			return;
		}
		if (selectedValue > end || selectedValue < start)
			selectedValue = start;
	}

	private void printSelectedOptionTag(Integer i) throws IOException {
		printOptionTag(i, "selected");
	}

	private void printOptionTag(Integer i, String attributes) throws IOException {
		out.println("<option value='" + i + "' " + attributes + ">" + i + "</option>");
	}

	private void printOptionTag(Integer i) throws IOException {
		printOptionTag(i, "");
	}
}
