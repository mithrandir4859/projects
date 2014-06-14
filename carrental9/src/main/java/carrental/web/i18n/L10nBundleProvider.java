package carrental.web.i18n;


public interface L10nBundleProvider {
	L10nBundle get(String viewName, String language);
}
