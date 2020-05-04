package com.boutique.qa.pages;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HttpsURLConnection;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.boutique.qa.base.TestBase;
import com.boutique.qa.util.TestUtil;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.Step;

public class GetLinksWithResponse extends TestBase {

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

	public GetLinksWithResponse(WebDriver driver) {
		PageFactory.initElements(driver, this);
		testUtil = new TestUtil(driver);
	}

	@Step("login with username: {0} and password: {1} step...")
	public void getLinksWithResponse() throws MalformedURLException, IOException {

		Map<String, Object> chromePrefs = new HashMap<String, Object>();
		chromePrefs.put("profile.default_content_setting_values.notifications", 1);
		chromePrefs.put("profile.default_content_settings.popups", 0);
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
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver(cap);
		driver.get("https://www.trendyol.com/");
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		WebElement fancybox = driver.findElement(By.xpath("//div[@class='fancybox-skin']/a"));
		fancybox.click();

		java.util.List<WebElement> linklist = driver.findElements(By.tagName("a"));
		linklist.addAll(driver.findElements(By.tagName("img")));
		System.out.println("Total links or images in page" + linklist.size());
		java.util.List<WebElement> activelinks = new ArrayList<WebElement>();

		// not found
		for (int i = 0; i <= linklist.size() - 1; i++) {
			System.out.println(linklist.get(i).getAttribute("href"));
			if (linklist.get(i).getAttribute("href") != null
					&& (!linklist.get(i).getAttribute("href").contains("javascript"))) {
				activelinks.add(linklist.get(i));
			}
		}

		System.out.println("list of active link" + activelinks.size());
		int url_cell = 2;
		int responsemsg_cell = 2;
		int responseCode_cell = 2;
		int resposetime_cell = 2;

		for (int j = 0; j <= activelinks.size(); j++) {
			HttpsURLConnection con = (HttpsURLConnection) new URL(activelinks.get(j).getAttribute("href"))
					.openConnection();
			con.connect();
			String responsemsg = con.getResponseMessage();
			int resopnsecode = con.getResponseCode();
			long resposetime = con.getConnectTimeout();

			con.disconnect();

			// System.out.println(activelinks.get(j).getAttribute("href") +" --> " +
			// responsemsg +" "+ resopnsecode +" " + resposetime);
			String url = activelinks.get(j).getAttribute("href");
			System.out.println(url + " --> " + responsemsg + " " + resopnsecode + " " + resposetime);

			// !IMPORTANT Need to update directory to response codes file!
			File src = new File("C:\\Users\\atill\\.eclipse\\javatutorial\\Boutique-Automation");
			FileInputStream fis = new FileInputStream(src);
			XSSFWorkbook workbook = new XSSFWorkbook(fis);
			XSSFSheet sheet = workbook.getSheetAt(0);
			sheet.getRow(url_cell).createCell(1).setCellValue(url);
			sheet.getRow(responsemsg_cell).createCell(2).setCellValue(responsemsg);
			sheet.getRow(responseCode_cell).createCell(3).setCellValue(resopnsecode);
			sheet.getRow(resposetime_cell).createCell(4).setCellValue(resposetime);
			FileOutputStream fout = new FileOutputStream(src);
			workbook.write(fout);
			workbook.close();
			url_cell++;
			responsemsg_cell++;
			responseCode_cell++;
			resposetime_cell++;
		}
	}

	public void doLogout() {

		logoutDrpdwn.click();
		logoutBtn.click();
	}
}
