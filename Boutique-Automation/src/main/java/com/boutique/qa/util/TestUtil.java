package com.boutique.qa.util;

import java.awt.List;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;
import java.util.logging.Level;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.boutique.qa.base.TestBase;


public class TestUtil extends TestBase {

	WebDriver driver;
	WebDriverWait wait;
	public static long PAGE_LOAD_TIMEOUT = 40;
	public static long IMPLICIT_WAIT = 40;
	public static final String CHAR_LIST = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
	public static final int RANDOM_Int_LENGTH = 5;
	public String firstWinHandle;
	public String secondWinHandle;
	

	public TestUtil(WebDriver driver) {
		this.driver = driver;
		wait = new WebDriverWait(driver, 15);

	}
	public void customWait()
	{
		wait = new WebDriverWait(driver, 10);
	}
	public void flushDownload()
	{
			try {

				File file = new File(System.getProperty("user.dir") + "\\Downloads");
				if (file != null && file.isDirectory()) {
					FileUtils.cleanDirectory(file);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
	}
     public void staticWait(int time)
     {
    	 try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			
		}
     }
     public void handleWindow() throws Exception {
		Set<String> handles = driver.getWindowHandles();// To handle multiple
														// windows
		firstWinHandle = driver.getWindowHandle();
		handles.remove(firstWinHandle);
		String winHandle = handles.iterator().next();
		if (winHandle != firstWinHandle) {
			secondWinHandle = winHandle;

			driver.switchTo().window(secondWinHandle);
			Thread.sleep(2000);
		} // Switch to popup window
	}

	public void clickOn(WebElement element, int timeout) {
		new WebDriverWait(driver, timeout).until(ExpectedConditions.elementToBeClickable(element));
		element.click();
		
	}

	public void select_dropdown_withwait(WebElement element, String status, int timeout) {

		new WebDriverWait(driver, timeout).until(ExpectedConditions.visibilityOf(element));

		Select statusDropdown = new Select(element);
		statusDropdown.selectByVisibleText(status);
	}

	public void select_dropdown(WebElement element, String status) {

		Select statusDropdown = new Select(element);
		statusDropdown.selectByVisibleText(status);
	}
	public WebElement selectDropDownIndexList(WebElement element)
	{
		Select statusDropdown = new Select(element);
		ArrayList<WebElement> BatchList = (ArrayList) statusDropdown.getOptions();
		int count = BatchList.size();
		statusDropdown.selectByIndex(count - 1);
		return BatchList.get(count-1 ) ;

	}
	

	public void datePicker(WebElement element, String date) {
		JavascriptExecutor js = ((JavascriptExecutor) driver);
		js.executeScript("arguments[0].setAttribute('value','" + date + "');", element);
	}

	public static String generateRandomString() {

		StringBuffer randStr = new StringBuffer();
		for (int i = 0; i < RANDOM_Int_LENGTH; i++) {
			int number = getRandomNumber();
			char ch = CHAR_LIST.charAt(number);
			randStr.append(ch);

		}
		return randStr.toString();
	}

	public static int getRandomNumber() {
		int randomInt = 0;
		Random randomGenerator = new Random();
		randomInt = randomGenerator.nextInt(CHAR_LIST.length());
		if (randomInt - 1 == -1) {
			return randomInt;
		} else {
			return randomInt - 1;
		}
	}

	// Explicit Wait for Sending Data to any Element.
	public static void dosendKeys(WebDriver driver, WebElement element, int timeout, String value) {
		new WebDriverWait(driver, timeout).until(ExpectedConditions.visibilityOf(element));
		element.sendKeys(value);
	}

	public void flash(WebElement element) {
		JavascriptExecutor js = ((JavascriptExecutor) driver);
		String bgcolor = element.getCssValue("backgroundColor");
		for (int i = 0; i < 20; i++) {
			changeColor("rgb(0,200,0)", element);// 1
			changeColor(bgcolor, element);// 2
		}
	}

	private void changeColor(String color, WebElement element) {
		JavascriptExecutor js = ((JavascriptExecutor) driver);
		js.executeScript("arguments[0].style.backgroundColor = '" + color + "'", element);

		try {
			Thread.sleep(20);
		} catch (InterruptedException e) {
		}
	}

	public void drawBorder(WebElement element) {
		JavascriptExecutor js = ((JavascriptExecutor) driver);
		js.executeScript("arguments[0].style.border='3px solid red'", element);
	}

	public void generateAlert(String message) {
		JavascriptExecutor js = ((JavascriptExecutor) driver);
		js.executeScript("alert('" + message + "')");
	}

	public void clickElementByJSWithwait(WebElement element, int timeout) {

		new WebDriverWait(driver, timeout).until(ExpectedConditions.elementToBeClickable(element));
		JavascriptExecutor js = ((JavascriptExecutor) driver);
		js.executeScript("arguments[0].click();", element);

	}

	public void clickElementByJS(WebElement element) {

		JavascriptExecutor js = ((JavascriptExecutor) driver);
		js.executeScript("arguments[0].click();", element);

	}

	public void refreshBrowserByJS() {
		JavascriptExecutor js = ((JavascriptExecutor) driver);
		js.executeScript("history.go(0)");
	}

	public String getTitleByJS() {
		JavascriptExecutor js = ((JavascriptExecutor) driver);
		String title = js.executeScript("return document.title;").toString();
		return title;
	}

	public String getPageInnerText() {
		JavascriptExecutor js = ((JavascriptExecutor) driver);
		String pageText = js.executeScript("return document.documentElement.innerText;").toString();
		return pageText;
	}

	public void scrollPageDownBottom() {
		JavascriptExecutor js = ((JavascriptExecutor) driver);
		js.executeScript("window.scrollTo(0,document.body.scrollHeight)");
	}

	public void scrollIntoView(WebElement element) {
		JavascriptExecutor js = ((JavascriptExecutor) driver);
		//js.executeScript("arguments[0].scrollIntoView(true);", element);

          js.executeScript("arguments[0].scrollIntoView(true);",element);
	}

	public void scrollPageDown() {
		JavascriptExecutor js = ((JavascriptExecutor) driver);
		js.executeScript("window.scrollBy(0,250)", "");
	}
	public void scrollRightSide()
	{
		JavascriptExecutor js = ((JavascriptExecutor) driver);
		js.executeScript("window.scrollBy(1000,0)", "");
	}

	public String getBrowserInfo() {
		JavascriptExecutor js = ((JavascriptExecutor) driver);
		String uAgent = js.executeScript("return navigator.userAgent;").toString();
		return uAgent;
	}

	public void sendKeysUsingJSWithId(String id, String value) {
		JavascriptExecutor js = ((JavascriptExecutor) driver);
		js.executeScript("document.getElementById('" + id + "').value='" + value + "'");
	}

	public void sendKeysUsingJSWithName(String name, String value) {
		JavascriptExecutor js = ((JavascriptExecutor) driver);
		js.executeScript("document.getElementByName('" + name + "').value='" + value + "'");
	}

	public void javaScriptSendKeys(WebElement ele, String value) {
		 ele.clear();
		 ele.sendKeys(value);
		JavascriptExecutor js = ((JavascriptExecutor) driver);
		String val = "arguments[0].value='" + value + "';";
		js.executeScript(val, ele);
	}

	public void checkPageIsReady() {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		// Initially bellow given if condition will check ready state of page.
		if (js.executeScript("return document.readyState").toString().equals("complete")) {
			System.out.println("Page Is loaded.");
			return;
		}

		// This loop will rotate for 25 times to check If page Is ready after
		// every 1 second.
		// You can replace your value with 25 If you wants to Increase or
		// decrease wait time.
		for (int i = 0; i < 25; i++) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
			}
			// To check page ready state.
			if (js.executeScript("return document.readyState").toString().equals("complete")) {
				break;
			}
		}
	}
	
	public static String generateID(int totalDigitOfNo) {
		Random rnd = new Random();
		char[] digits = new char[totalDigitOfNo];
		digits[0] = (char) (rnd.nextInt(9) + '1');
		for (int i = 1; i < digits.length; i++) {
			digits[i] = (char) (rnd.nextInt(10) + '0');
		}
		Long x = Long.parseLong(new String(digits));
		return x != null ? x.toString() : "123456789";
	}
	public void doActionsClick(WebElement element) {
		try {
			Actions action = new Actions(driver);
			action.click(element).build().perform();
		} catch (Exception e) {
			System.out.println("some exception got occurred while clicking on the webelement : " +element);
			System.out.println(e.getMessage());

		}
	}
	public String getCurrentDate()
	{
		DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		String dateobj = formatter.format(new Date());
		System.out.println("kkckv"+ dateobj);
		return dateobj;
		
	}
	
	
}
