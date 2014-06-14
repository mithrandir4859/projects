package carrental.web.tags;

import java.io.IOException;

import javax.servlet.jsp.JspContext;
import javax.servlet.jsp.PageContext;

import carrental.web.i18n.L10nBundle;

public class TagUtils {
	public static final String L10N_BUNDLE = "localizedBundle";
	private JspContext jspContext;

	public TagUtils(JspContext jspContext) {
		this.jspContext = jspContext;
	}

	public L10nBundle getLocalizedBundle() {
		return (L10nBundle) jspContext.getAttribute(L10N_BUNDLE, PageContext.REQUEST_SCOPE);
	}

	public void localizedPrint(String paramName) throws IOException {
		String strToPrint = getLocalizedBundle().get(paramName);
		print(strToPrint == null || strToPrint.isEmpty() ? paramName : strToPrint);
	}

	public void print(String str) throws IOException {
		if (str == null)
			return;
		jspContext.getOut().print(str);
	}

	public void printLocalizedList(String listName, String wrappingTag) throws IOException {
		String openTag = "<" + (wrappingTag += ">");
		String closingTag = "</" + wrappingTag;
		printLocalizedList(listName, openTag, closingTag);
	}

	public void printLocalizedList(Iterable<String> list, String openTag, String closingTag, String prefix) throws IOException {
		if (list == null)
			return;
		for (String str : list) {
			print(openTag);
			localizedPrint(prefix + str);
			print(closingTag);
		}
	}
	
	public void printLocalizedList(Iterable<String> list, String openTag, String closingTag) throws IOException {
		printLocalizedList(list, openTag, closingTag, "");
	}

	public void printLocalizedList(String listName, String openTag, String closingTag) throws IOException {
		printLocalizedList(getList(listName), openTag, closingTag);
	}
	
	public void printLocalizedList(String listName, String openTag, String closingTag, String prefix) throws IOException {
		printLocalizedList(getList(listName), openTag, closingTag, prefix);
	}

	@SuppressWarnings("unchecked")
	public Iterable<String> getList(String listName) {
		return (Iterable<String>) jspContext.getAttribute(listName, PageContext.REQUEST_SCOPE);
	}

}
