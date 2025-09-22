package config;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {
	public Properties initializeConfig() throws IOException {
		Properties prob = new Properties();
		 String projectPath = System.getProperty("user.dir");
        String filePath = projectPath + "/src/test/resources/config.properties";
		FileInputStream fis = new FileInputStream(filePath);
		prob.load(fis);		
		return prob;
	}
}
