package carrental.web.tags;

import static carrental.web.tags.TagUtils.L10N_BUNDLE;

import java.io.IOException;

import javax.servlet.jsp.JspContext;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import carrental.web.i18n.L10nBundle;
import carrental.web.i18n.L10nBundleProvider;

public class SetLocale extends SimpleTagSupport {
	public static String DEFAULT_L10N_BUNDLE_PROVIDER_NAME = "l10nBundleProvider";
	public static String DEFAULT_LANGUAGE = "en";

	private String language;
	private String l10nBundleProviderName;

	public void setLanguage(String language) {
		this.language = language;
	}

	public void setL10nBundleProviderName(String l10nBundleProviderName) {
		this.l10nBundleProviderName = l10nBundleProviderName;
	}

	@Override
	public void doTag() throws JspException, IOException {
		JspContext jspContext = getJspContext();
		if (l10nBundleProviderName == null)
			l10nBundleProviderName = DEFAULT_L10N_BUNDLE_PROVIDER_NAME;
		L10nBundleProvider l10nBundleProvider = (L10nBundleProvider) getJspContext().getAttribute(l10nBundleProviderName,
				PageContext.APPLICATION_SCOPE);
		String viewName = null;
		L10nBundle l10nBundle = l10nBundleProvider.get(viewName, language);
		if (l10nBundle == null)
			l10nBundle = l10nBundleProvider.get(viewName, DEFAULT_LANGUAGE);
		jspContext.setAttribute(L10N_BUNDLE, l10nBundle, PageContext.REQUEST_SCOPE);
	}

}
