package carrental.web.i18n.impl;

import java.util.Properties;

import carrental.web.i18n.L10nBundle;

public class L10nBundleImpl extends Properties implements L10nBundle {

	private static final long serialVersionUID = 1L;

	@Override
	public String get(String name) {
		return getProperty(name);
	}

	

}
