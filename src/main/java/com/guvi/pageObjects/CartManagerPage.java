package com.guvi.pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.aventstack.extentreports.ExtentTest;

import appComponents.SeleniumUtils;

public class CartManagerPage extends BasePage{
    WebDriver driver;
    WebDriverWait wait;
    ExtentTest test;
    public CartManagerPage(WebDriver driver, WebDriverWait wait,ExtentTest test) {
        super(driver);
    	this.driver = driver;
        this.wait = wait;
        this.test = test;
        PageFactory.initElements(driver,this);
    }
    
    @FindBy(id="cartur")
    WebElement cartur;
    
    @FindBy(xpath="//a[text()='Delete']")
    WebElement btnDelete;

    // Delete all products from cart
    public  void removeAllProductsFromCart() {
        try {
	        	SeleniumUtils.clickElement(cartur);
	            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2[contains(text(),'Products')]")));
	            actionDelay();
	            while (driver.findElements(By.xpath("//a[text()='Delete']")).size() > 0) {
	               SeleniumUtils.clickElement(btnDelete);                
	                actionDelay();
	            }            
	            test.info("✅ All products removed from cart");
        } catch (Exception e) {
            System.out.println("✅ Cart cleanup complete or no more items to delete.");
            test.fail("❌ Failed to remove products: " + e.getMessage());
        }
    }

    // Delete first product from cart (if needed separately)
    public void removeFirstProduct() {
        try {
            SeleniumUtils.clickElement(btnDelete);
            actionDelay();
            System.out.println("✅ First item removed from cart");
            test.info("✅ First item removed from cart");
        } catch (Exception e) {
            System.out.println("⚠️ Failed to remove product: " + e.getMessage());
            test.fail("⚠️ Failed to remove product: " + e.getMessage());
        }
    }
}
