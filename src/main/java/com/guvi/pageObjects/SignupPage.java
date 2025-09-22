package com.guvi.pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.aventstack.extentreports.ExtentTest;

import appComponents.SeleniumUtils;

import java.util.UUID;

public class SignupPage extends BasePage{
    WebDriver driver;
    WebDriverWait wait;
    ExtentTest test;
    public SignupPage(WebDriver driver, WebDriverWait wait,ExtentTest test) {
        super(driver);
    	this.driver = driver;
        this.wait = wait;
        this.test = test;
        PageFactory.initElements(driver,this);
    }
    
    @FindBy(id="signin2")
    WebElement lnksignin;
    
    @FindBy(id="sign-username")
    WebElement txtusername;
    
    @FindBy(id="sign-password")
    WebElement txtpassword;
    
    @FindBy(xpath ="//button[text()='Sign up']")
    WebElement btnsignup;
    
    public void testSignup(String Username,String Password) {
        //BaseTest.newUsername = "user_" + UUID.randomUUID().toString().substring(0, 5);

        try {        
            SeleniumUtils.clickElement(lnksignin);
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("sign-username")));
            
            SeleniumUtils.typeText(txtusername, Username);
            SeleniumUtils.typeText(txtpassword, Password);
            SeleniumUtils.clickElement(btnsignup);

            // Wait for alert to appear
            Thread.sleep(1000); // Small delay to ensure alert appears
            
            String alertMsg = handleAlert();
            if (alertMsg.contains("successful")) {
                System.out.println("✅ Sign-up success: " + Username);
            } else {
                System.out.println("❌ Sign-up failed: " + alertMsg);
            }
        } catch (Exception e) {
            System.out.println("❌ Sign-up error: " + e.getMessage());
        }
    }
}