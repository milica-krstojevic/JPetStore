package tests;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import pages.CartPage;
import pages.StoreItemPage;
import utils.ExcelUtils;

public class CartTest {

	private WebDriver driver;
	private Properties locators;
	private WebDriverWait waiter;

	@BeforeClass
	public void setup() throws FileNotFoundException, IOException {
		System.setProperty("webdriver.chrome.driver", "driver-lib\\chromedriver.exe");
		this.driver = new ChromeDriver();
		this.locators = new Properties();
		locators.load(new FileInputStream("config/jpetstore.properties"));
		driver.manage().window().maximize();
		driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		this.driver.navigate().to(locators.getProperty("cart_url"));
	}

	@Test(priority = 1)
	public void addCartItems() {
		StoreItemPage ip = new StoreItemPage(driver, locators, waiter);
		CartPage cp = new CartPage(driver, locators, waiter);
		SoftAssert sa = new SoftAssert();

		ExcelUtils.setExcell("data/pet-store-data.xlsx");
		ExcelUtils.setWorkSheet(0);
		for (int i = 1; i < ExcelUtils.getRowNumber(); i++) {
			String itemId = ExcelUtils.getDataAt(i, 0);
			String itemLink = ExcelUtils.getDataAt(i, 1);
			this.driver.navigate().to(itemLink);
			ip.clickAddToCart();
			sa.assertTrue(cp.isItemAddedToCart(itemId));
		}
		sa.assertAll();
	}

	@Test(priority = 2)
	public void sumEqual() {
		CartPage cart = new CartPage(driver, locators, waiter);
		Assert.assertTrue(cart.verifyPriceCalculation());
	}

	@Test(priority = 3)
	public void deletedCookies() {
		this.driver.navigate().to(locators.getProperty("cart_url"));
		CartPage ct = new CartPage(driver, locators, waiter);
		ct.deleteAllCookies();
		this.driver.navigate().refresh();
		Assert.assertTrue(ct.isCartEmpty());
	}

	@AfterClass
	public void afterClass() {
		ExcelUtils.closeExcell();
		this.driver.close();
	}
}