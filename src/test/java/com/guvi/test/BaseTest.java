package com.guvi.test;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseTest {
public WebDriver driver;
public String url;
	
	public WebDriver initializeTest() throws IOException {

		Properties prob = new Properties();
		 String projectPath = System.getProperty("user.dir");
         String filePath = projectPath + "/resources/GlobalData.properties";
		FileInputStream fis = new FileInputStream(filePath);
		prob.load(fis);
		
		String browser = prob.getProperty("browser");
		url = prob.getProperty("url");
		System.out.println(browser);
		
		if (browser.equals("Chrome")) {
			WebDriverManager.chromedriver().setup();
			ChromeOptions options = new ChromeOptions();
	        options.setUnhandledPromptBehaviour(UnexpectedAlertBehaviour.ACCEPT);
			driver = new ChromeDriver();
			
		}else if (browser.equals("Firefox")) {
			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver();
			
		}

		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		driver.manage().window().maximize();
		
	return driver;
	}
	
	public void launchApp() throws IOException {
		driver = initializeTest();
		driver.get(url);
	}
}
