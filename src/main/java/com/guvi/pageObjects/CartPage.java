package com.guvi.pageObjects;

import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.aventstack.extentreports.ExtentTest;

import appComponents.SeleniumUtils;

public class CartPage extends BasePage{
    WebDriver driver;
    WebDriverWait wait;
    ExtentTest test;
    public CartPage(WebDriver driver, WebDriverWait wait,ExtentTest test) {
        super(driver);
    	this.driver = driver;
        this.wait = wait;
        this.test = test;
        PageFactory.initElements(driver,this);
    }
    
    
    @FindBy(xpath ="//a[text()='Add to cart']")
    WebElement addToCart;
    
    @FindBy(xpath ="//a[contains(text(),'Home')]")
    WebElement lnkhome;
    
    @FindBy(id="cartur")
    WebElement cartur;
    
    public void addProductToCart(HashMap<String, String> map) {
        try {
            driver.findElement(By.xpath("//a[contains(text(),'" + map.get("product") + "')]")).click();
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2[contains(text(),'" + map.get("product") + "')]")));
            actionDelay();
                     
            SeleniumUtils.clickElement(addToCart);
            actionDelay();
            handleAlert();
            
            System.out.println("✅ Added to cart: " + map.get("product"));
            
            // Return to home
            SeleniumUtils.clickElement(lnkhome);            
            actionDelay();
            test.info("Add to cart is Success");
            
        } catch (Exception e) {
            System.out.println("❌ Failed to add product '" + map.get("product") + "': " + e.getMessage());
            test.fail("❌ Failed to add product '" + map.get("product") + "': " + e.getMessage());
        }
    }
    
    public void viewCartAndVerify() {
        try {            
            SeleniumUtils.clickElement(cartur);
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2[contains(text(),'Products')]")));
            actionDelay();
            
            List<WebElement> items = driver.findElements(By.xpath("//tbody/tr"));
            System.out.println("🛒 Cart contains " + items.size() + " items");
            test.info("🛒 Cart contains " + items.size() + " items");
        } catch (Exception e) {
            System.out.println("❌ Cart verification failed: " + e.getMessage());
            test.fail("❌ Cart verification failed: " + e.getMessage());
        }
    }
    
    public void removeAllProducts() {
        try {
        	SeleniumUtils.clickElement(cartur);            
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2[contains(text(),'Products')]")));
            actionDelay();
            
            while (driver.findElements(By.xpath("//a[text()='Delete']")).size() > 0) {
                driver.findElement(By.xpath("//a[text()='Delete']")).click();
                actionDelay();
            }
            System.out.println("✅ All products removed from cart");
            test.info("✅ All products removed from cart");
        } catch (Exception e) {
            System.out.println("❌ Failed to remove products: " + e.getMessage());
            test.fail("❌ Failed to remove products: " + e.getMessage());
        }
    }
}