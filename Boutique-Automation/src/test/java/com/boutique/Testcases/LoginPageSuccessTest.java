package com.boutique.Testcases;

import java.io.IOException;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.boutique.qa.base.TestBase;
import com.boutique.qa.pages.LoginPageWithSuccess;

import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;

public class LoginPageSuccessTest extends TestBase {

	LoginPageWithSuccess loginPage;

	public LoginPageSuccessTest() {
		super();
	}

	@BeforeMethod
	public void setUp() {
		initialization(prop.getProperty("Browser"));
		loginPage = new LoginPageWithSuccess(driver);
	}

	@Severity(SeverityLevel.BLOCKER)
	@Description("Verify that user logging successfully and comes in Dasboard Page")
	@Story("Story Name: Login Page functionality")
	@Test(priority = 1, description = "Verify that user is able to login")
	public void loginTest() throws IOException {
		loginPage.clearcache();
		loginPage.doLoginWithSuccess(prop.getProperty("username"), prop.getProperty("password"));
		// loginPage.doLogout();
	}

	@AfterMethod
	public void tearDown() {

		// driver.quit();

	}
}
