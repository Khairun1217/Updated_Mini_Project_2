package appUtils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SeleniumUtils {
	
	
	//Slow Type
    public static void slowType(WebElement element, String text) {
        for (char ch : text.toCharArray()) {
            element.sendKeys(String.valueOf(ch));
            try {
                Thread.sleep(30);  
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    
    //slowtype with delay
    public static void slowType(WebElement element, String text, int delay) {
        for (char ch : text.toCharArray()) {
            element.sendKeys(String.valueOf(ch));
            try {
                Thread.sleep(delay);  // delay in milliseconds
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    
    //Click WebElement
    public static void clickElement(WebElement element) {
        if (element != null) {
            try {
                element.click();
                System.out.println("Clicked on element: " + element);
            } catch (Exception e) {
                System.out.println("Unable to click on element. Error: " + e.getMessage());
            }
        } else {
            System.out.println("Element is null, cannot click!");
        }
    }
    
    //SendKeys
    public static void typeText(WebElement element, String text) {
        try {
            element.clear();
            element.sendKeys(text);
            System.out.println("✅ Typed text: " + text);
        } catch (Exception e) {
            System.out.println("❌ Failed to type into element: " + e.getMessage());
        }
    }
}
