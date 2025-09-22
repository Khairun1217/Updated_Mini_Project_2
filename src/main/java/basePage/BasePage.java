package basePage;

import java.time.Duration;

import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BasePage {
	 	protected WebDriver driver;
	    protected WebDriverWait wait;
	    protected static final int DEFAULT_TIMEOUT = 10; // seconds
	    protected static final int ACTION_DELAY_MS = 3000;

	public BasePage(WebDriver driver) {
		// TODO Auto-generated constructor stub
		this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(DEFAULT_TIMEOUT));
	}

	public String handleAlert() {
        try {
            wait.until(ExpectedConditions.alertIsPresent());
            Alert alert = driver.switchTo().alert();
            String msg = alert.getText();
            alert.accept();
            actionDelay();
            return msg;
        } catch (Exception e) {
            return "⚠️ No alert present";
        }
    }

	
	protected void actionDelay() {
        try {
            Thread.sleep(ACTION_DELAY_MS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
