package com.guvi.test;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

public class OrderTesting extends BaseTesting {

    @Test(priority = 1)
    public void placeOrderWithValidDetails() {
        try {
            // Navigate to cart
            driver.findElement(By.id("cartur")).click();
            wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//h2[contains(text(),'Products')]")));
            actionDelay();

            // Click Place Order
            driver.findElement(By.xpath("//button[text()='Place Order']")).click();
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("orderModal")));
            actionDelay();

            // Fill order form
            fillOrderForm("Test User", "United States", "New York", "4111111111111111", "12", "2025");

            // Click Purchase
            driver.findElement(By.xpath("//button[text()='Purchase']")).click();

            // Handle confirmation, modal close, cart cleanup, logout
            handleOrderConfirmation();

        } catch (Exception e) {
            System.out.println("❌ Order placement failed: " + e.getMessage());
            Assert.fail("Order placement failed", e);
        }
    }

    private void fillOrderForm(String name, String country, String city,
                               String card, String month, String year) {
        driver.findElement(By.id("name")).sendKeys(name);
        actionDelay();
        driver.findElement(By.id("country")).sendKeys(country);
        actionDelay();
        driver.findElement(By.id("city")).sendKeys(city);
        actionDelay();
        driver.findElement(By.id("card")).sendKeys(card);
        actionDelay();
        driver.findElement(By.id("month")).sendKeys(month);
        actionDelay();
        driver.findElement(By.id("year")).sendKeys(year);
        actionDelay();
        System.out.println("✅ Order form filled");
    }

    private void handleOrderConfirmation() {
        try {
            WebElement confirmation = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//div[contains(@class,'sweet-alert')]")));

            String confirmationText = confirmation.getText();
            Assert.assertTrue(confirmationText.contains("Thank you"), "Confirmation message not as expected");

            System.out.println("✅ Order placed successfully");
            System.out.println("🧾 Order Details:\n" + confirmationText);
            actionDelay();

            // Click OK to confirm
            driver.findElement(By.xpath("//button[text()='OK']")).click();
            wait.until(ExpectedConditions.invisibilityOf(confirmation));
            actionDelay();
            System.out.println("✅ Confirmation modal closed");

            // Close modal if still visible
            try {
                WebElement closeBtn = driver.findElement(
                        By.xpath("//div[@id='orderModal']//button[text()='Close']"));
                closeBtn.click();
                actionDelay();
                System.out.println("✅ Order modal closed with 'Close' button");
            } catch (Exception e) {
                System.out.println("ℹ️ 'Close' button not found — already dismissed.");
            }

            // Go back to Cart
            driver.findElement(By.id("cartur")).click();
            wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//h2[contains(text(),'Products')]")));
            actionDelay();

            // Delete all items
            deleteAllCartItems();

            // Logout
            logout();

            // Confirm home redirection
            Assert.assertTrue(driver.getCurrentUrl().contains("index.html"),
                    "⚠️ Not redirected to homepage properly");
            System.out.println("🏠 Successfully redirected to homepage after logout");

        } catch (Exception e) {
            System.out.println("❌ Order confirmation or cleanup failed: " + e.getMessage());
            Assert.fail("Order confirmation or cleanup failed", e);
        }
    }

    private void deleteAllCartItems() {
        try {
            while (driver.findElements(By.xpath("//a[text()='Delete']")).size() > 0) {
                WebElement deleteBtn = driver.findElement(By.xpath("//a[text()='Delete']"));
                deleteBtn.click();
                actionDelay();
            }
            System.out.println("✅ All cart items deleted");
        } catch (Exception e) {
            System.out.println("✅ All cart items deleted or none found.");
        }
    }
}
