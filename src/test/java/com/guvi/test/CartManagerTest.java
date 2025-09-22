package com.guvi.test;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class CartManagerTest extends BaseTesting {

    @Test(priority = 1)
    public void testRemoveAllProductsFromCart() {
        try {
            driver.findElement(By.id("cartur")).click();
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2[contains(text(),'Products')]")));
            actionDelay();

            while (driver.findElements(By.xpath("//a[text()='Delete']")).size() > 0) {
                WebElement deleteBtn = driver.findElement(By.xpath("//a[text()='Delete']"));
                deleteBtn.click();
                actionDelay();
            }

            List<WebElement> remaining = driver.findElements(By.xpath("//tbody/tr"));
            Assert.assertEquals(remaining.size(), 0, "Cart is not empty after deletion");
            System.out.println("✅ Cart is empty after cleanup");

        } catch (Exception e) {
            System.out.println("❌ Error while removing products: " + e.getMessage());
            Assert.fail("Failed to clean cart", e);
        }
    }

    @Test(priority = 2)
    public void testRemoveFirstProduct() {
        try {
            driver.findElement(By.id("cartur")).click();
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2[contains(text(),'Products')]")));
            actionDelay();

            List<WebElement> itemsBefore = driver.findElements(By.xpath("//tbody/tr"));
            if (!itemsBefore.isEmpty()) {
                WebElement deleteBtn = driver.findElement(By.xpath("//a[text()='Delete']"));
                deleteBtn.click();
                actionDelay();

                List<WebElement> itemsAfter = driver.findElements(By.xpath("//tbody/tr"));
                Assert.assertTrue(itemsAfter.size() < itemsBefore.size(), "First item not removed");
                System.out.println("✅ First item removed from cart");
            } else {
                System.out.println("⚠️ No items to remove");
            }

        } catch (Exception e) {
            System.out.println("❌ Error removing first product: " + e.getMessage());
            Assert.fail("Failed to remove first product", e);
        }
    }
}
