package com.guvi.pageObjects;

import java.util.HashMap;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.aventstack.extentreports.ExtentTest;

import appUtils.SeleniumUtils;
import basePage.BasePage;

public class OrderPage extends BasePage {
	WebDriver driver;
    WebDriverWait wait;
    ExtentTest test;
    public OrderPage(WebDriver driver, WebDriverWait wait,ExtentTest test) {
        super(driver);
    	this.driver = driver;
        this.wait = wait;
        this.test = test;
        PageFactory.initElements(driver,this);
    }
	
    @FindBy(id="cartur")
    WebElement cartur;
    
    @FindBy(xpath = "//button[text()='Place Order']")
    WebElement placeOrder;
    
    @FindBy(xpath = "//button[text()='Purchase']")
    WebElement purchaseOrder;
    
  
    @FindBy(xpath = "//a[text()='Delete']")
    WebElement btnDelete;
    
    @FindBy(id="name")
    WebElement name;
    
    @FindBy(id="country")
    WebElement country;
    
    @FindBy(id="city")
    WebElement city;
    
    @FindBy(id="card")
    WebElement card;
    
    @FindBy(id="month")
    WebElement month;
    
    @FindBy(id="year")
    WebElement year;
    
    @FindBy(id="logout2")
    WebElement logout;
    
    @FindBy(xpath = "//button[text()='OK']")
    WebElement btnOk;
    
    @FindBy(xpath = "//div[@id='orderModal']//button[text()='Close']")
    WebElement btnClose;
    
    
    
  
	
    public void placeOrderWithValidDetails(HashMap<String, String> map) {
        try {
            // Navigate to cart
            SeleniumUtils.clickElement(cartur);
            wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//h2[contains(text(),'Products')]")));
            actionDelay();

            // Click Place Order         
            SeleniumUtils.clickElement(placeOrder);
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("orderModal")));
            actionDelay();

            // Fill order form
            fillOrderForm(map);

            // Click Purchase
            SeleniumUtils.clickElement(purchaseOrder);           

            // Handle confirmation, modal close, cart cleanup, logout
            handleOrderConfirmation();
            
            test.info("Order placement is Success");

        } catch (Exception e) {
            System.out.println("❌ Order placement failed: " + e.getMessage());
            test.fail("❌ Order placement failed: " + e.getMessage());
        }
    }


	private void fillOrderForm(HashMap<String, String> map) {
        SeleniumUtils.typeText(name, map.get("name"));
        actionDelay();
        SeleniumUtils.typeText(country, map.get("country"));
        actionDelay();
        SeleniumUtils.typeText(city, map.get("city"));
        actionDelay();
        SeleniumUtils.typeText(card, map.get("card"));
        actionDelay();
        SeleniumUtils.typeText(month, map.get("month"));
        actionDelay();
        SeleniumUtils.typeText(year, map.get("year"));
        actionDelay();
        System.out.println("✅ Order form filled");
    }

    private void handleOrderConfirmation() {
        try {
            WebElement confirmation = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//div[contains(@class,'sweet-alert')]")));

            if (confirmation.getText().contains("Thank you")) {
                System.out.println("✅ Order placed successfully");
                System.out.println("🧾 Order Details:\n" + confirmation.getText());
                actionDelay();
                // Click OK to confirm          
                SeleniumUtils.clickElement(btnOk);
                wait.until(ExpectedConditions.invisibilityOf(confirmation));
                actionDelay();
                System.out.println("✅ Confirmation modal closed");

                // Try to close modal if still visible
                try {
                    SeleniumUtils.clickElement(btnClose);
                    actionDelay();
                    System.out.println("✅ Order modal closed with 'Close' button");
                } catch (Exception e) {
                    System.out.println("ℹ️ 'Close' button not found — already dismissed.");
                }

                // Go back to Cart
                SeleniumUtils.clickElement(cartur);
                wait.until(ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//h2[contains(text(),'Products')]")));
                actionDelay();

                // Delete all items
                deleteAllCartItems();

                // Logout
                logout();

                // Confirm home redirection
                if (driver.getCurrentUrl().contains("index.html")) {
                    System.out.println("🏠 Successfully redirected to homepage after logout");
                } else {
                    System.out.println("⚠️ Not redirected to homepage properly");
                }

            } else {
                System.out.println("❌ Unexpected confirmation message:\n" + confirmation.getText());
            }

        } catch (Exception e) {
            System.out.println("❌ Order confirmation or cleanup failed: " + e.getMessage());
        }
    }

    private void logout() {
		// TODO Auto-generated method stub
    	try {
            if (driver.findElements(By.id("logout2")).size() > 0) {
                driver.findElement(By.id("logout2")).click();
                System.out.println("✅ Automatic logout successful");
                actionDelay();
            }
        } catch (Exception e) {
            System.out.println("⚠️ No active session to logout");
        }
	}


	private void deleteAllCartItems() {
        try {
            while (driver.findElements(By.xpath("//a[text()='Delete']")).size() > 0) {                
            	btnDelete.click();
                actionDelay();  
                test.info("Delete all cart items");
            }
        } catch (Exception e) {
        	test.fail("✅ All cart items deleted or no items to delete : " + e.getMessage());
            System.out.println("✅ All cart items deleted or no items to delete.");
        }
    }
}
