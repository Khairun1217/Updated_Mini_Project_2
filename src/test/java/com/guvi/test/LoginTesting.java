package com.guvi.test;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginTesting extends BaseTesting {

    @Test(priority = 1)
    public void testLogin() {
        try {
            driver.findElement(By.id("login2")).click();
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("loginusername")));

            driver.findElement(By.id("loginusername")).sendKeys(newUsername);
            driver.findElement(By.id("loginpassword")).sendKeys(password);
            driver.findElement(By.xpath("//button[text()='Log in']")).click();

            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nameofuser")));
            String welcomeText = driver.findElement(By.id("nameofuser")).getText();
            System.out.println("✅ Login success: " + newUsername);

            // Optional Assertion
            Assert.assertTrue(welcomeText.contains(newUsername), "Login username mismatch!");

        } catch (Exception e) {
            System.out.println("❌ Login failed: " + e.getMessage());
            Assert.fail("Login test failed", e);
        }
    }
}
