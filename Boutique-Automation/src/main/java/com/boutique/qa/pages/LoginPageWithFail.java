package com.boutique.qa.pages;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.boutique.qa.base.TestBase;
import com.boutique.qa.util.TestUtil;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.Step;

public class LoginPageWithFail extends TestBase {

	TestUtil testUtil;

	@FindBy(xpath = "//i[@class='icon navigation-icon-user']")
	WebElement LoginClick;
	@FindBy(xpath = "//input[@name='email']")
	WebElement emailID;
	@FindBy(xpath = "//input[@name='password']")
	WebElement password;
	@FindBy(xpath = "//a[@id='loginSubmit']")
	WebElement loginButton;

	@FindBy(xpath = "//li[@id='accountBtn']//div[@class='icon-container']//a")
	WebElement logoutDrpdwn;
	@FindBy(xpath = "//font[contains(text(),'Sign out')]")
	WebElement logoutBtn;

	@FindBy(xpath = "//div[@class='fancybox-skin']/a")
	WebElement fancybox;

	public LoginPageWithFail(WebDriver driver) {
		PageFactory.initElements(driver, this);
		testUtil = new TestUtil(driver);
	}

	@Step("login with username: {0} and password: {1} step...")

	public void doLoginPageWithFail(String property, String property2) {
		testUtil.clickElementByJS(LoginClick);
		emailID.sendKeys("dummy@gmail.com");
		password.sendKeys("dummy@12345");
		testUtil.clickElementByJS(loginButton);

	}

	public void doLogout() {

		logoutDrpdwn.click();
		logoutBtn.click();
	}

	public void clearcache() {
		Map<String, Object> chromePrefs = new HashMap<String, Object>();
		chromePrefs.put("profile.default_content_setting_values.notifications", 1);
		chromePrefs.put("profile.default_content_settings.popups", 0);
		chromePrefs.put("download.prompt_for_download", false);
		chromePrefs.put("plugins.plugins_disabled", "Chrome PDF Viewer");
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--test-type");
		options.addArguments("--no-sandbox");
		options.addArguments("--disable-notifications");
		options.addArguments("--disable-software-rasterizer");
		options.addArguments("--disable-popup-blocking");
		options.addArguments("--disable-extensions");
		options.setExperimentalOption("prefs", chromePrefs);
		DesiredCapabilities cap = DesiredCapabilities.chrome();
		cap.setCapability(ChromeOptions.CAPABILITY, options);
		cap.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
		WebDriverManager.chromedriver().setup();
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		// WebElement fancybox =
		// driver.findElement(By.xpath("//div[@class='fancybox-skin']/a"));
		fancybox.click();

	}

}
