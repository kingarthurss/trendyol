package com.boutique.Testcases;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.boutique.qa.base.TestBase;
import com.boutique.qa.pages.LoginPageWithFail;

import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;

public class LoginPageFailTest extends TestBase {

	LoginPageWithFail loginPageFail;

	public LoginPageFailTest() {
		super();
	}

	@BeforeMethod
	public void setUp() {
		initialization(prop.getProperty("Browser"));
		loginPageFail = new LoginPageWithFail(driver);
	}

	@Severity(SeverityLevel.BLOCKER)
	@Test(priority = 1, description = "To Verify get all links")
	public void loginTest() {
		loginPageFail.doLoginPageWithFail(prop.getProperty("username"), prop.getProperty("password"));
	}

	@AfterMethod
	public void tearDown() {

		// driver.quit();
	}
}
