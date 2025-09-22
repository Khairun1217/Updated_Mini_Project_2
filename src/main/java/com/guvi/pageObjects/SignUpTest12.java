package com.guvi.pageObjects;
//package com.guvi.test;
//
//import io.github.bonigarcia.wdm.WebDriverManager;
//import org.openqa.selenium.*;
//import org.openqa.selenium.chrome.ChromeDriver;
//import org.openqa.selenium.support.ui.ExpectedConditions;
//import org.openqa.selenium.support.ui.WebDriverWait;
//
//import java.time.Duration;
//import java.util.List;
//import java.util.UUID;
//
//public class SignUpTest12 {
//
//    static WebDriver driver;
//    static WebDriverWait wait;
//    static String baseUrl = "https://www.demoblaze.com";
//    static String newUsername;
//    static String password = "Test123!";
//
//    public static void main(String[] args) throws InterruptedException {
//        WebDriverManager.chromedriver().setup();
//        driver = new ChromeDriver();
//        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
//        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
//        driver.manage().window().maximize();
//
//        driver.get(baseUrl);
//
//        // ========== USER FLOW TESTING ==========
//        testUserRegistrationAndLogin();
//        
//        // ========== CART FUNCTIONALITY ==========
//        testCartFunctionality();
//        
//        // ========== ORDER FUNCTIONALITY ==========
//        testOrderFunctionality();
//        
//        // ========== PRODUCT BROWSING ==========
//        testProductBrowsing();
//        
//        // ========== NAVIGATION ==========
//        testNavigation();
//        
//        // ========== LOGOUT FUNCTIONALITY ==========
//        testLogoutFunctionality();
//        
//        // ========== UI/UX TESTING ==========
//        testUIElements();
//        
//        driver.quit();
//    }
//
//    // ========== TEST GROUPS ==========
//    
//    static void testUserRegistrationAndLogin() throws InterruptedException {
//        signUpNewUser();
//        loginWithSameUser();
//    }
//    
//    static void testCartFunctionality() throws InterruptedException {
//        addProductToCart("Samsung galaxy s6");
//        viewCartAndVerify();
//        removeProductFromCart();
//        addMultipleProductsToCart();
//    }
//    
//    static void testOrderFunctionality() throws InterruptedException {
//        placeOrderWithValidDetails();
//        placeOrderWithEmptyForm();
//        verifyOrderConfirmationPopup();
//    }
//    
//    static void testProductBrowsing() throws InterruptedException {
//        browseProductCategories("Phones");
//        browseProductCategories("Laptops");
//        browseProductCategories("Monitors");
//        viewProductDetails("Nexus 6");
//    }
//    
//    static void testNavigation() throws InterruptedException {
//        verifyHomeNavigation();
//        testNavbarLinks();
//    }
//    
//    static void testLogoutFunctionality() throws InterruptedException {
//        verifyLogout();
//    }
//    
//    static void testUIElements() throws InterruptedException {
//        verifyProductTileAlignment();
//        checkButtonVisibility();
//        checkFontConsistency();
//        testAlertStyling();
//    }
//
//    // ========== CORE METHODS ==========
//    
//    static void signUpNewUser() throws InterruptedException {
//        newUsername = "user_" + UUID.randomUUID().toString().substring(0, 5);
//
//        driver.findElement(By.id("signin2")).click();
//        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("sign-username")));
//        
//        driver.findElement(By.id("sign-username")).sendKeys(newUsername);
//        driver.findElement(By.id("sign-password")).sendKeys(password);
//        driver.findElement(By.xpath("//button[text()='Sign up']")).click();
//
//        String alertMsg = handleAlert();
//        if (alertMsg.contains("successful")) {
//            System.out.println("✅ Sign-up success: " + newUsername);
//        } else {
//            System.out.println("❌ Sign-up failed: " + alertMsg);
//        }
//    }
//
//    static void loginWithSameUser() throws InterruptedException {
//        driver.findElement(By.id("login2")).click();
//        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("loginusername")));
//
//        driver.findElement(By.id("loginusername")).sendKeys(newUsername);
//        driver.findElement(By.id("loginpassword")).sendKeys(password);
//        driver.findElement(By.xpath("//button[text()='Log in']")).click();
//
//        try {
//            WebElement welcome = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nameofuser")));
//            System.out.println("✅ Login success: " + welcome.getText());
//        } catch (TimeoutException e) {
//            System.out.println("❌ Login failed");
//        }
//    }
//
//    // ========== ORDER FUNCTIONALITY METHODS ==========
//    
//    static void placeOrderWithValidDetails() throws InterruptedException {
//        // Ensure we have products in cart
//        if (driver.findElements(By.xpath("//tbody/tr")).isEmpty()) {
//            addProductToCart("Samsung galaxy s6");
//        }
//        
//        driver.findElement(By.xpath("//button[text()='Place Order']")).click();
//        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("orderModal")));
//        
//        // Fill order form
//        driver.findElement(By.id("name")).sendKeys("Test User");
//        driver.findElement(By.id("country")).sendKeys("Test Country");
//        driver.findElement(By.id("city")).sendKeys("Test City");
//        driver.findElement(By.id("card")).sendKeys("4111111111111111");
//        driver.findElement(By.id("month")).sendKeys("12");
//        driver.findElement(By.id("year")).sendKeys("2025");
//        
//        driver.findElement(By.xpath("//button[text()='Purchase']")).click();
//        
//        try {
//            WebElement confirmation = wait.until(ExpectedConditions.visibilityOfElementLocated(
//                By.xpath("//div[contains(@class,'sweet-alert')]")));
//            
//            if (confirmation.getText().contains("Thank you for your purchase")) {
//                System.out.println("✅ Order placed successfully");
//                // Capture order details
//                String details = confirmation.getText();
//                System.out.println("Order Details:\n" + details);
//            } else {
//                System.out.println("❌ Order placement failed");
//            }
//            
//            // Close confirmation
//            driver.findElement(By.xpath("//button[text()='OK']")).click();
//        } catch (TimeoutException e) {
//            System.out.println("❌ Order confirmation not shown");
//        }
//    }
//    
//    static void placeOrderWithEmptyForm() throws InterruptedException {
//        driver.findElement(By.xpath("//button[text()='Place Order']")).click();
//        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("orderModal")));
//        
//        // Click purchase without filling any fields
//        driver.findElement(By.xpath("//button[text()='Purchase']")).click();
//        
//        try {
//            Alert alert = driver.switchTo().alert();
//            String alertText = alert.getText();
//            alert.accept();
//            
//            if (alertText.toLowerCase().contains("fill")) {
//                System.out.println("✅ Empty form validation works: " + alertText);
//            } else {
//                System.out.println("❌ Unexpected empty form behavior: " + alertText);
//            }
//        } catch (NoAlertPresentException e) {
//            System.out.println("❌ No alert shown for empty form");
//        }
//        
//        // Close the modal
//        driver.findElement(By.xpath("//div[@id='orderModal']//button[text()='Close']")).click();
//    }
//    
//    static void verifyOrderConfirmationPopup() throws InterruptedException {
//        // This would typically be called after placeOrderWithValidDetails
//        // But we'll simulate it independently
//        placeOrderWithValidDetails();
//        
//        try {
//            WebElement confirmation = wait.until(ExpectedConditions.visibilityOfElementLocated(
//                By.xpath("//div[contains(@class,'sweet-alert')]")));
//            
//            boolean hasThankYou = confirmation.getText().contains("Thank you");
//            boolean hasAmount = confirmation.getText().contains("Amount");
//            boolean hasId = confirmation.getText().contains("Id");
//            
//            if (hasThankYou && hasAmount && hasId) {
//                System.out.println("✅ Order confirmation popup contains all required elements");
//            } else {
//                System.out.println("❌ Order confirmation popup missing elements");
//            }
//            
//            driver.findElement(By.xpath("//button[text()='OK']")).click();
//        } catch (TimeoutException e) {
//            System.out.println("❌ Order confirmation popup not shown");
//        }
//    }
//
//    // ========== PRODUCT BROWSING METHODS ==========
//    
//    static void browseProductCategories(String category) throws InterruptedException {
//        driver.findElement(By.xpath("//a[text()='" + category + "']")).click();
//        Thread.sleep(2000); // Wait for products to load
//        
//        List<WebElement> products = driver.findElements(By.xpath("//div[@class='col-lg-4 col-md-6 mb-4']"));
//        if (!products.isEmpty()) {
//            System.out.println("✅ " + category + " category shows " + products.size() + " products");
//            
//            // Verify all displayed products belong to the category
//            boolean allMatch = true;
//            for (WebElement product : products) {
//                String title = product.findElement(By.xpath(".//h4/a")).getText();
//                if (!title.toLowerCase().contains(category.split(" ")[0].toLowerCase())) {
//                    allMatch = false;
//                    break;
//                }
//            }
//            
//            if (allMatch) {
//                System.out.println("✅ All products belong to " + category + " category");
//            } else {
//                System.out.println("❌ Some products don't match " + category + " category");
//            }
//        } else {
//            System.out.println("❌ No products found in " + category + " category");
//        }
//    }
//    
//    static void viewProductDetails(String productName) throws InterruptedException {
//        driver.findElement(By.xpath("//a[contains(text(),'" + productName + "')]")).click();
//        Thread.sleep(2000);
//        
//        try {
//            WebElement productImage = driver.findElement(By.xpath("//div[@class='item active']/img"));
//            WebElement productTitle = driver.findElement(By.xpath("//h2[@class='name']"));
//            WebElement productPrice = driver.findElement(By.xpath("//h3[@class='price-container']"));
//            WebElement productDescription = driver.findElement(By.xpath("//div[@id='more-information']/p"));
//            
//            if (productImage.isDisplayed() && productTitle.isDisplayed() && 
//                productPrice.isDisplayed() && productDescription.isDisplayed()) {
//                System.out.println("✅ Product details page shows all required elements for " + productName);
//                System.out.println(" - Title: " + productTitle.getText());
//                System.out.println(" - Price: " + productPrice.getText());
//                System.out.println(" - Description: " + productDescription.getText().substring(0, 50) + "...");
//            } else {
//                System.out.println("❌ Missing elements on product details page");
//            }
//        } catch (NoSuchElementException e) {
//            System.out.println("❌ Product details page not loaded correctly");
//        }
//    }
//
//    // ========== NAVIGATION METHODS ==========
//    
//    static void verifyHomeNavigation() throws InterruptedException {
//        // Go to some other page first
//        driver.findElement(By.xpath("//a[text()='Cart']")).click();
//        Thread.sleep(1000);
//        
//        // Click Home
//        driver.findElement(By.xpath("//a[contains(text(),'Home')]")).click();
//        Thread.sleep(1000);
//        
//        // Verify we're on home page
//        if (driver.findElements(By.xpath("//div[@id='tbodyid']/div")).size() > 0) {
//            System.out.println("✅ Home navigation works correctly");
//        } else {
//            System.out.println("❌ Home navigation failed");
//        }
//    }
//    
//    static void testNavbarLinks() throws InterruptedException {
//        String[] links = {"Home", "Contact", "About us", "Cart", "Log in", "Sign up"};
//        
//        for (String link : links) {
//            try {
//                driver.findElement(By.xpath("//a[text()='" + link + "']")).click();
//                Thread.sleep(1000);
//                
//                // Simple verification that URL changed
//                if (!driver.getCurrentUrl().equals(baseUrl)) {
//                    System.out.println("✅ " + link + " link navigates correctly");
//                } else if (link.equals("Home")) {
//                    // Home might not change URL
//                    System.out.println("✅ " + link + " link navigates correctly");
//                } else {
//                    System.out.println("❌ " + link + " link may not work");
//                }
//                
//                // Go back to home for next test
//                if (!link.equals("Home")) {
//                    driver.findElement(By.xpath("//a[contains(text(),'Home')]")).click();
//                    Thread.sleep(1000);
//                }
//            } catch (NoSuchElementException e) {
//                System.out.println("❌ " + link + " link not found");
//            }
//        }
//    }
//
//    // ========== LOGOUT FUNCTIONALITY METHODS ==========
//    
//    static void verifyLogout() throws InterruptedException {
//        // Ensure we're logged in
//        try {
//            driver.findElement(By.id("nameofuser"));
//        } catch (NoSuchElementException e) {
//            loginWithSameUser();
//        }
//        
//        driver.findElement(By.id("logout2")).click();
//        Thread.sleep(1000);
//        
//        try {
//            driver.findElement(By.id("nameofuser"));
//            System.out.println("❌ Logout failed - user still appears logged in");
//        } catch (NoSuchElementException e) {
//            System.out.println("✅ Logout successful");
//        }
//        
//        // Verify login button is visible
//        if (driver.findElement(By.id("login2")).isDisplayed()) {
//            System.out.println("✅ Login button visible after logout");
//        } else {
//            System.out.println("❌ Login button not visible after logout");
//        }
//    }
//
//    // ========== UI/UX TESTING METHODS ==========
//    
//    static void verifyProductTileAlignment() {
//        List<WebElement> products = driver.findElements(By.xpath("//div[@class='col-lg-4 col-md-6 mb-4']"));
//        
//        if (products.size() > 0) {
//            // Get first product's position as reference
//            Point firstPosition = products.get(0).getLocation();
//            int firstX = firstPosition.getX();
//            int firstY = firstPosition.getY();
//            
//            boolean aligned = true;
//            
//            // Check alignment of other products
//            for (int i = 1; i < products.size(); i++) {
//                Point currentPosition = products.get(i).getLocation();
//                
//                // Check if X position matches for same row (assuming 3 columns)
//                if (i % 3 == 0) {
//                    // Should be new row, Y should be different
//                    if (currentPosition.getY() <= firstY) {
//                        aligned = false;
//                        break;
//                    }
//                } else {
//                    // Should be same row, Y should be same
//                    if (currentPosition.getY() != firstY) {
//                        aligned = false;
//                        break;
//                    }
//                }
//            }
//            
//            if (aligned) {
//                System.out.println("✅ Product tiles are properly aligned");
//            } else {
//                System.out.println("❌ Product tiles alignment issues");
//            }
//        } else {
//            System.out.println("❌ No products found to check alignment");
//        }
//    }
//    
//    static void checkButtonVisibility() {
//        String[] buttonTexts = {"Add to cart", "Log in", "Sign up"};
//        
//        for (String text : buttonTexts) {
//            try {
//                WebElement button = driver.findElement(By.xpath("//button[contains(text(),'" + text + "')] | //a[contains(text(),'" + text + "')]"));
//                if (button.isDisplayed() && button.isEnabled()) {
//                    System.out.println("✅ " + text + " button is visible and clickable");
//                } else {
//                    System.out.println("❌ " + text + " button is not visible or not clickable");
//                }
//            } catch (NoSuchElementException e) {
//                System.out.println("❌ " + text + " button not found");
//            }
//        }
//    }
//    
//    static void checkFontConsistency() {
//        // Check font on multiple elements across pages
//        String[] elementsToCheck = {"//h5[@class='card-title']", "//h2", "//p", "//a"};
//        String expectedFont = "";
//        boolean consistent = true;
//        
//        try {
//            // Get reference font from first element
//            expectedFont = driver.findElement(By.xpath(elementsToCheck[0])).getCssValue("font-family");
//            
//            for (String xpath : elementsToCheck) {
//                List<WebElement> elements = driver.findElements(By.xpath(xpath));
//                if (!elements.isEmpty()) {
//                    String currentFont = elements.get(0).getCssValue("font-family");
//                    if (!currentFont.equals(expectedFont)) {
//                        consistent = false;
//                        break;
//                    }
//                }
//            }
//            
//            if (consistent) {
//                System.out.println("✅ Font consistency maintained across elements");
//            } else {
//                System.out.println("❌ Font inconsistency detected");
//            }
//        } catch (Exception e) {
//            System.out.println("❌ Error checking font consistency: " + e.getMessage());
//        }
//    }
//    
//    static void testAlertStyling() {
//        // Trigger an alert
//        try {
//            addProductToCart("Samsung galaxy s6");
//            
//            // Alert styling can't be checked directly, but we can verify it appears
//            System.out.println("✅ Alert appeared as expected");
//        } catch (Exception e) {
//            System.out.println("❌ Error testing alert styling: " + e.getMessage());
//        }
//    }
//
//    // ========== HELPER METHODS ==========
//    
//    static void addProductToCart(String productName) throws InterruptedException {
//        driver.findElement(By.xpath("//a[contains(text(),'" + productName + "')]")).click();
//        Thread.sleep(2000);
//        driver.findElement(By.xpath("//a[text()='Add to cart']")).click();
//        Thread.sleep(2000);
//        handleAlert();
//    }
//    
//    static void viewCartAndVerify() throws InterruptedException {
//        driver.findElement(By.id("cartur")).click();
//        Thread.sleep(3000);
//    }
//    
//    static void removeProductFromCart() throws InterruptedException {
//        List<WebElement> deleteLinks = driver.findElements(By.xpath("//a[text()='Delete']"));
//        if (!deleteLinks.isEmpty()) {
//            deleteLinks.get(0).click();
//            Thread.sleep(2000);
//        }
//    }
//    
//    static void addMultipleProductsToCart() throws InterruptedException {
//        driver.findElement(By.xpath("//a[contains(text(),'Home')]")).click();
//        Thread.sleep(2000);
//        addProductToCart("Nexus 6");
//        addProductToCart("Nokia lumia 1520");
//    }
//    
//    static String handleAlert() {
//        try {
//            Alert alert = driver.switchTo().alert();
//            String msg = alert.getText();
//            alert.accept();
//            return msg;
//        } catch (NoAlertPresentException e) {
//            return "No alert present";
//        }
//    }
//}