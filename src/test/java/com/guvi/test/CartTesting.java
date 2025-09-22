package com.guvi.test;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class CartTesting extends BaseTesting {

    @Test(priority = 1)
    public void addFirstProductToCart() {
        addProductToCart("Samsung galaxy s6");
    }

    @Test(priority = 2)
    public void addSecondProductToCart() {
        addProductToCart("Nokia lumia 1520");
    }

    @Test(priority = 3)
    public void viewCartAndVerifyItems() {
        try {
            driver.findElement(By.id("cartur")).click();
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2[contains(text(),'Products')]")));
            actionDelay();

            List<WebElement> items = driver.findElements(By.xpath("//tbody/tr"));
            System.out.println("🛒 Cart contains " + items.size() + " items");

            Assert.assertTrue(items.size() >= 2, "Cart does not contain expected number of items");

        } catch (Exception e) {
            System.out.println("❌ Cart verification failed: " + e.getMessage());
            Assert.fail("Cart verification failed", e);
        }
    }

    @Test(priority = 4)
    public void removeAllProductsFromCart() {
        try {
            driver.findElement(By.id("cartur")).click();
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2[contains(text(),'Products')]")));
            actionDelay();

            while (driver.findElements(By.xpath("//a[text()='Delete']")).size() > 0) {
                driver.findElement(By.xpath("//a[text()='Delete']")).click();
                actionDelay();
            }

            System.out.println("✅ All products removed from cart");

            List<WebElement> items = driver.findElements(By.xpath("//tbody/tr"));
            Assert.assertEquals(items.size(), 0, "Cart is not empty after deletion");

        } catch (Exception e) {
            System.out.println("❌ Failed to remove products: " + e.getMessage());
            Assert.fail("Product removal failed", e);
        }
    }

    // Helper method for reuse
    public void addProductToCart(String productName) {
        try {
            driver.findElement(By.xpath("//a[contains(text(),'" + productName + "')]")).click();
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2[contains(text(),'" + productName + "')]")));
            actionDelay();

            driver.findElement(By.xpath("//a[text()='Add to cart']")).click();
            actionDelay();
            handleAlert();

            System.out.println("✅ Added to cart: " + productName);

            // Go back to home page
            driver.findElement(By.xpath("//a[contains(text(),'Home')]")).click();
            actionDelay();

        } catch (Exception e) {
            System.out.println("❌ Failed to add product '" + productName + "': " + e.getMessage());
            Assert.fail("Add to cart failed for: " + productName, e);
        }
    }
}
