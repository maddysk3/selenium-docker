package com.maddysk.tests;


import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;

import com.maddysk.util.Config;
import com.maddysk.util.Constants;

public abstract class AbstractTest {

	protected WebDriver driver;
	private static final Logger log = LoggerFactory.getLogger(AbstractTest.class);

	@BeforeSuite
	public void setupConfig() {
		Config.initilize();
	}
	
	@BeforeTest
	public void setDriver() throws MalformedURLException {

		this.driver = Boolean.parseBoolean(Config.get(Constants.GRID_ENABLED)) ? getRemoteDriver() : getLocalDriver();

	}

	private WebDriver getRemoteDriver() throws MalformedURLException {
		Capabilities capabilities = new ChromeOptions();
		if (Constants.FIREFOX.equalsIgnoreCase(Config.get(Constants.BROWSER))) {
			capabilities = new FirefoxOptions();
		}
		String urlFormat = Config.get(Constants.GRID_URL_FORMAT);
		String hubHost = Config.get(Constants.GRID_HUB_HOST);
		String url = String.format(urlFormat, hubHost);
		log.info("grid url:{}", url);
		return new RemoteWebDriver(new URL(url), capabilities);
	}

	private WebDriver getLocalDriver() {
		System.setProperty("webdriver.chrome.driver", "/Users/maddysk/stack/drv/chromedriver");
		return new ChromeDriver();
	}

	@AfterTest
	public void quitDriver() {
		this.driver.quit();
	}

}
