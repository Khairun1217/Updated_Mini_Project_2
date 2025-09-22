package com.guvi.test;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.guvi.pageObjects.CartManagerPage;
import com.guvi.pageObjects.CartPage;
import com.guvi.pageObjects.LoginPage;
import com.guvi.pageObjects.OrderPage;
import com.guvi.pageObjects.SignupPage;


public class TestOrchestrator  extends BaseTest{
private ExtentReports extent;
private ExtentTest test;
private String username;
private String password;

	@BeforeTest
	public void initalize() throws IOException {
		String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
		String reportName = System.getProperty("user.dir") + "/test-output/ExtentReporter_" + timestamp + ".html";
		// Initialize ExtentReports
        ExtentSparkReporter spark = new ExtentSparkReporter(reportName);
        extent = new ExtentReports();
        extent.attachReporter(spark);
		
	}
	@BeforeMethod
	public void setupApp() throws IOException {
		System.out.println("Launch Application");
		launchApp();
	}
	
	@AfterMethod
	public void closeDriver() {
		driver.close();
	}
	@AfterTest
	public void teardown() {		
		driver.quit();
		 System.out.println("\n✅ All scenarios completed successfully via TestNG!");
		  // Flush the ExtentReports
        if (extent != null) {
            extent.flush();
        }
	}

    @Test(priority = 1,dataProvider = "getData")
    public void signupTest (HashMap<String, String> map) throws InterruptedException, IOException {
    	
    	test = extent.createTest("signup Test");
    	try {
    	WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    	SignupPage singup = new SignupPage(driver, wait,test); 	
    	this.username = map.get("username");
    	this.password = map.get("password");
    			
    	singup.testSignup(username, password);
    	test.pass("signup Test is passed");
    	}catch(Exception e) {
    		test.fail("signup Test is failed");
    	}
    }

    @Test(priority = 2,dataProvider = "getData")
    public void loginTestandPlaceOrder(HashMap<String, String> map) {
    	test = extent.createTest("login Test");
    	try {
    		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    		LoginPage login = new LoginPage(driver, wait, test);
    		login.testLogin(username, password);
    		test.pass("Login Test is passed");
    		CartPage cart = new CartPage(driver, wait, test);
    		cart.addProductToCart(map);
    		test.pass("Add to Cart Test is passed");
    		OrderPage order = new OrderPage(driver, wait, test);
    		order.placeOrderWithValidDetails(map);
    		test.pass("Place Order Test is passed");
    		cart.viewCartAndVerify();
    		CartManagerPage cartManage = new CartManagerPage(driver, wait, test);
    		cartManage.removeAllProductsFromCart();
    		test.pass("Verify And Clean Cart is passed");
    		
    	}catch(Exception e) {
    		test.fail("loginTest and PlaceOrder Test is failed");
    	}
    }
/*
    @Test(priority = 3,dataProvider = "getData")
    public void addToCartTest(HashMap<String, String> map) {
    	test = extent.createTest("Add To Cart Test");
    	try {
    		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    		CartPage cart = new CartPage(driver, wait, test);
    		cart.addProductToCart(map);
    		test.pass("Add to Cart Test is passed");
    	}catch(Exception e) {
    		test.fail("Add To Cart Test is failed");
    	}
        //CartPage.addProductToCart("Samsung galaxy s6");
        //CartPage.addProductToCart("Nokia lumia 1520");
    }

    @Test(priority = 4,dataProvider = "getData")
    public void placeOrderTest(HashMap<String, String> map) {
    	test = extent.createTest("Place Order Test");
    	try {
    		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    		OrderPage order = new OrderPage(driver, wait, test);
    		order.placeOrderWithValidDetails(map);
    		test.pass("Place Order Test is passed");
    	}catch(Exception e) {
    		test.fail("Place Order Test is failed");
    	}      
    }

    @Test(priority = 5,dataProvider = "getData")
    public void verifyAndCleanCart() {
    	test = extent.createTest("Verify And Clean Cart Test");
    	try {
    		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    		CartPage cart = new CartPage(driver, wait, test);
    		cart.viewCartAndVerify();
    		CartManagerPage cartManage = new CartManagerPage(driver, wait, test);
    		cartManage.removeAllProductsFromCart();
    		test.pass("Verify And Clean Cart is passed");
    	}catch(Exception e) {
    		test.fail("Verify And Clean Cart Test is failed");
    	}        
    }

    */
    @DataProvider
	public Object[][] getData(Method method) throws IOException{
		
		String projectPath = System.getProperty("user.dir");
        String filePath = projectPath + "/resources/testData.json";
     // Read JSON into List<HashMap<String, String>>
        FileReader reader = new FileReader(filePath);
        Gson gson = new Gson();
        java.lang.reflect.Type type = new TypeToken<List<HashMap<String, String>>>() {}.getType();
        List<HashMap<String, String>> data = gson.fromJson(reader, type);
        reader.close();
        
     // Filter JSON objects matching the current test name
        List<HashMap<String, String>> filtered = new ArrayList<>();
        for (HashMap<String, String> map : data) {
            if (map.get("testName").equalsIgnoreCase(method.getName())) {
                // dynamic email if empty
                if (map.get("username") == null || map.get("username").isEmpty()) {
                    String dynamicuser = "user_" + UUID.randomUUID().toString().substring(0, 5);
                    map.put("username", dynamicuser);
                }
                filtered.add(map);
            }
        }

        Object[][] returnData = new Object[filtered.size()][1];
        for (int i = 0; i < filtered.size(); i++) {
            returnData[i][0] = filtered.get(i);
        }
        return returnData;
	}
    
}
