package carrental.web.tags;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspContext;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;

public class AdvancedInput extends SimpleTagSupport {

	private String localizedLabelName, inputName;
	private JspContext jspContext;
	private TagUtils utils;

	private String type;
	private String attributes;

	public void setType(String type) {
		this.type = type;
	}

	public void setAttributes(String attributes) {
		this.attributes = attributes;
	}

	public void setLabel(String localizedLabelName) {
		this.localizedLabelName = localizedLabelName;
	}

	public void setName(String inputName) {
		this.inputName = inputName;
	}

	@Override
	public void doTag() throws JspException, IOException {
		jspContext = getJspContext();
		utils = new TagUtils(jspContext);
		setNameFromLabelIfNeeded();
		printErrors();
		printLabelAndInput();
	}

	private void setNameFromLabelIfNeeded() {
		if (inputName != null && inputName.length() != 0)
			return;
		Pattern pattern = Pattern.compile("[^\\.]*$");
		Matcher matcher = pattern.matcher(localizedLabelName);
		matcher.find();
		inputName = matcher.group();
	}

	private void printErrors() throws IOException {
		@SuppressWarnings("unchecked")
		Map<String, List<String>> errorMap = (Map<String, List<String>>) jspContext.getAttribute("errorMap", PageContext.REQUEST_SCOPE);
		if (errorMap == null)
			return;
		List<String> errorList = errorMap.get(inputName);
		if (errorList == null || errorList.isEmpty())
			return;

		utils.print("<tr>");
		utils.printLocalizedList(errorList, "<td colspan=\"2\">", "</td>", "error." + inputName + ".");
		utils.print("</tr>");
	}

	private void printLabelAndInput() throws IOException {
		utils.print("<tr><th>");
		utils.localizedPrint(localizedLabelName);
		utils.print("</th><td>");

		boolean isPassword = false;
		if (type == null || type.isEmpty()) {
			isPassword = inputName.toLowerCase().contains("password");
			type = isPassword ? "password" : "text";
		}
		utils.print("<input type=\"" + type + "\" ");

		if (!isPassword)
			printValueAttribute();

		utils.print("name=\"" + inputName + "\" " + (attributes == null ? "" : attributes) + " />");
		utils.print("</td></tr>");
	}

	private void printValueAttribute() throws IOException {
		PageContext pageContext = (PageContext) jspContext;
		HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();

		String value = request.getParameter(inputName);
		if (value == null || value.isEmpty()){
			Object candidateValue =  request.getAttribute(inputName); 
			value = candidateValue == null ? null : candidateValue.toString();
		}
		
		if (value != null && value.length() != 0){
			utils.print("value=\"" + value + "\" ");
		}
	}

}
