package com.boutique.qa.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

import com.boutique.qa.util.TestUtil;
import com.boutique.qa.util.WebEventLisetner;

import io.github.bonigarcia.wdm.WebDriverManager;

public class TestBase {

	public static WebDriver driver;
	public static Properties prop;
	public static EventFiringWebDriver e_driver;
	public static WebEventLisetner eventListener;
	

	public TestBase() {
		try {
			prop = new Properties();
			FileInputStream ip = new FileInputStream(
					System.getProperty("user.dir") + "/src/main/java/com/dqa" + "/qa/config/config.properties");
			prop.load(ip);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("deprecation")
	@Parameters("browser")
	public static void initialization(String browserName) {

		if (browserName.equalsIgnoreCase("chrome")) {
			// WebDriverManager.chromedriver().setup();

			Map<String, Object> chromePrefs = new HashMap<String, Object>();
			chromePrefs.put("profile.default_content_setting_values.notifications", 1);
			chromePrefs.put("profile.default_content_settings.popups", 0);
			String downloadFilepath = System.getProperty("user.dir") + "\\Downloads";
			
			chromePrefs.put("download.default_directory", downloadFilepath);

			chromePrefs.put("download.prompt_for_download", false);

			chromePrefs.put("plugins.plugins_disabled", "Chrome PDF Viewer");

			ChromeOptions options = new ChromeOptions();

			// options.addArguments("--headless")
			// options.addArguments("--window-size=1920,1080")
			options.addArguments("--test-type");
			// options.addArguments("--disable-gpu")
			options.addArguments("--no-sandbox");
			// options.addArguments("--disable-dev-shm-usage")
			options.addArguments("--disable-notifications");
			options.addArguments("--disable-software-rasterizer");
			options.addArguments("--disable-popup-blocking");
			options.addArguments("--disable-extensions");
			options.setExperimentalOption("prefs", chromePrefs);

			DesiredCapabilities cap = DesiredCapabilities.chrome();
			cap.setCapability(ChromeOptions.CAPABILITY, options);
			cap.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
			System.setProperty("webdriver.chrome.driver",System.getProperty("user.dir") + "//Drivers//chromedriver.exe");

			driver = new ChromeDriver(cap);

		} else if (browserName.equalsIgnoreCase("firefox")) {
			WebDriverManager.firefoxdriver().setup();
			driver = new FirefoxDriver();
		}

		e_driver = new EventFiringWebDriver(driver);
		eventListener = new WebEventLisetner();
		e_driver.register(eventListener);
		driver = e_driver;

		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().pageLoadTimeout(TestUtil.PAGE_LOAD_TIMEOUT, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(TestUtil.IMPLICIT_WAIT, TimeUnit.SECONDS);

		driver.get(prop.getProperty("url"));

	}

	public static void takeScreenshotAtEndOfTest() throws IOException {
		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		String currentDir = System.getProperty("user.dir");
		FileUtils.copyFile(scrFile, new File(currentDir + "/screenshots/" + System.currentTimeMillis() + ".png"));
	}

}
