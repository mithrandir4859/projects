package test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.util.Properties;

public class ResourceBundleConverter {

	private static final String[] RESOURCE_BUNDLE_FOR_CONVERT = {"utf8bundle_ru.txt", "utf8bundle_uk.txt" };
//	private static final String[] RESOURCE_BUNDLE_OUT = {"text_ru.properties", "text_uk.properties" };
	private static final String[] RESOURCE_BUNDLE_OUT = {"text_ru.xml", "text_uk.xml" };
	private static final String BUNDLE_FOLDER = "src/main/resources/";

	public static void main(String[] args) throws IOException, URISyntaxException {
		System.out.println("wtf");
		convertResourceBundles();
	}

	private static void convertResourceBundles() throws IOException, URISyntaxException {
		for (int i = 0; i < RESOURCE_BUNDLE_FOR_CONVERT.length; i++) {
			convertResourceBundles(BUNDLE_FOLDER, RESOURCE_BUNDLE_FOR_CONVERT[i], RESOURCE_BUNDLE_OUT[i]);
		}
	}

	private static void convertResourceBundles(String folder, String srcFile, String endFile) throws IOException, URISyntaxException {
		File outFile = new File(folder + endFile);
		if (!outFile.exists()){
			outFile.delete();
			outFile.createNewFile();
		}
		try (InputStreamReader inputStream = new InputStreamReader(Thread.currentThread().getContextClassLoader().getResourceAsStream(srcFile), "UTF-8");
				OutputStream out = new FileOutputStream(folder + endFile);) {
			Properties properties = new Properties();
			properties.load(inputStream);
			Properties newProperties = new Properties();
			for (Object key: properties.keySet())
				newProperties.setProperty(key.toString(), properties.getProperty(key.toString()).toString());
//			properties.store(out, "ha");
			properties.storeToXML(out, "ha", "UTF-8");
			
		}
	}
}
