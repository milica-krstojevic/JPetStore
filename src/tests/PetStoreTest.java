package tests;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import pages.PetStoreMenuPage;

public class PetStoreTest {
	private WebDriver driver;
	private Properties locators;
	private WebDriverWait waiter;

	@BeforeClass
	public void setup() throws FileNotFoundException, IOException {
		System.setProperty("webdriver.chrome.driver", "driver-lib\\chromedriver.exe");
		this.driver = new ChromeDriver();
		this.locators = new Properties();
		locators.load(new FileInputStream("config/jpetstore.properties"));
		driver.navigate().to(this.locators.getProperty("pet_store_menu_url"));
		driver.manage().window().maximize();
		driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
		
	}

	@Test
	public void linksValidation() {

		PetStoreMenuPage mp = new PetStoreMenuPage(driver, locators, waiter);
		Assert.assertTrue(mp.sideBarValidation());
		Assert.assertTrue(mp.navBarValidation());
		Assert.assertTrue(mp.mainValidation());
	}

	@Test
	public void checkLinks() throws InterruptedException {

		PetStoreMenuPage cf = new PetStoreMenuPage(driver, locators, waiter);
		Assert.assertTrue(cf.checkLinksByCategoryName("FISH"));
		Assert.assertTrue(cf.checkLinksByCategoryName("DOGS"));
		Assert.assertTrue(cf.checkLinksByCategoryName("CATS"));
		Assert.assertTrue(cf.checkLinksByCategoryName("REPTILES"));
		Assert.assertTrue(cf.checkLinksByCategoryName("BIRDS"));
	}

	@Test
	public void signInBtnPageTest() throws InterruptedException {

		PetStoreMenuPage ps = new PetStoreMenuPage(driver, locators, waiter);
		ps.clickSignInBtn();

		boolean validSignInClick = this.driver.findElement(By.xpath(this.locators.getProperty("sign_in_text")))
				.isDisplayed();
		Assert.assertTrue(validSignInClick);

	}

	@AfterClass
	public void afterClass() {
		this.driver.close();
	}

}
