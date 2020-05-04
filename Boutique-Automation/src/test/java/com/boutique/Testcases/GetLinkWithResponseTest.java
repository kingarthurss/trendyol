package com.boutique.Testcases;

import java.io.IOException;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.boutique.qa.base.TestBase;
import com.boutique.qa.pages.GetLinksWithResponse;
import com.boutique.qa.pages.LoginPageWithSuccess;


import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;

public class GetLinkWithResponseTest extends TestBase {

	LoginPageWithSuccess loginPage;
	GetLinksWithResponse getLinksWithResponse;
	

	public GetLinkWithResponseTest() {
		super();
	}

	@BeforeMethod
	public void setUp() {
		initialization(prop.getProperty("Browser1"));
		loginPage = new LoginPageWithSuccess(driver);
	}
	  
	    @Severity(SeverityLevel.BLOCKER)    
	    @Test(priority = 1,description = "To Verify to get all links & response code")  
		public void loginTest() throws IOException {
			getLinksWithResponse=new GetLinksWithResponse(driver);
			getLinksWithResponse.getLinksWithResponse();
		}
	    
	@AfterMethod
	public void tearDown() {
		
		//driver.quit();  
	}
}

	


