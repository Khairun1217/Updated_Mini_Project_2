package com.guvi.testsuite;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import java.time.Duration;

public class BaseTesting {

    protected static WebDriver driver;
    protected static WebDriverWait wait;
    protected static String baseUrl = "https://www.demoblaze.com";
    public static String newUsername;
    protected static String password = "Test123!";
    protected static final int ACTION_DELAY_MS = 3000;

    @BeforeClass
    public static void setup() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.setUnhandledPromptBehaviour(UnexpectedAlertBehaviour.ACCEPT);
        driver = new ChromeDriver(options);

        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();
        driver.get(baseUrl);
    }

    @AfterClass
    public void teardown() {
        try {
            logout();
        } finally {
            if (driver != null) {
                driver.quit();
            }
        }
    }

    public static void logout() {
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

    public static void actionDelay() {
        try {
            Thread.sleep(ACTION_DELAY_MS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public static String handleAlert() {
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
}
