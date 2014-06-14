package carrental.web.i18n.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.InvalidPropertiesFormatException;
import java.util.Map;

import carrental.web.i18n.L10nBundle;
import carrental.web.i18n.L10nBundleProvider;

public class TrivialL10nBundleProvider implements L10nBundleProvider {
	private static final String BUNDLE_EN = "l10n/en.properties";
	private static final String BUNDLE_UK = "l10n/uk.txt";

	private Map<String, L10nBundle> bundleMap;

	public TrivialL10nBundleProvider() throws InvalidPropertiesFormatException, IOException {
		bundleMap = new HashMap<>();
		{
			L10nBundleImpl bundle = new L10nBundleImpl();
			InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(BUNDLE_EN);
			bundle.load(inputStream);
			bundleMap.put("en", bundle);
		}
		{
			L10nBundleImpl bundle = new L10nBundleImpl();
			InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(BUNDLE_UK);
			InputStreamReader in = new InputStreamReader(inputStream, "UTF-8");
			bundle.load(in);
			bundleMap.put("uk", bundle);
		}
	}

	@Override
	public L10nBundle get(String viewName, String language) {
		return bundleMap.get(language);
	}

}
