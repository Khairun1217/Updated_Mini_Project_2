package com.guvi.pageObjects;

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

public class LoginPage extends BasePage{
    WebDriver driver;
    WebDriverWait wait;
    ExtentTest test;
    public LoginPage(WebDriver driver, WebDriverWait wait,ExtentTest test) {
        super(driver);
    	this.driver = driver;
        this.wait = wait;
        this.test = test;
        PageFactory.initElements(driver,this);
    }
    
    @FindBy(id="login2")
    WebElement login;
    
    @FindBy(id="loginusername")
    WebElement loginusername;
    
    @FindBy(id="loginpassword")
    WebElement loginpassword;
    
    @FindBy(xpath="//button[text()='Log in']")
    WebElement btnlogIn;
    
    public void testLogin(String Username,String Password) {
    	try {
    	SeleniumUtils.clickElement(login);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("loginusername")));
        SeleniumUtils.typeText(loginusername,Username);
        SeleniumUtils.typeText(loginpassword, Password);
        SeleniumUtils.clickElement(btnlogIn);
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nameofuser")));
            System.out.println("✅ Login success: " + Username);
        } catch (Exception e) {
            System.out.println("❌ Login failed");
        }
        } catch (Exception e) {
            System.out.println("❌ Login error: " + e.getMessage());
        }
    }
}