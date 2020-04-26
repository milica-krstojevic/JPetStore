package tests;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import pages.SignInPage;
import utils.ExcelUtils;

public class SignInTest {

	private WebDriver driver;
	private Properties locators;
	private WebDriverWait waiter;

	@BeforeClass
	public void setup() throws FileNotFoundException, IOException {
		System.setProperty("webdriver.chrome.driver", "driver-lib\\chromedriver.exe");
		this.driver = new ChromeDriver();
		this.locators = new Properties();
		this.locators.load(new FileInputStream("config/jpetstore.properties"));
		this.driver.manage().window().maximize();
		this.driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
		this.driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}

	@Test
	public void signInTest() {
		ExcelUtils.setExcell("data/pet-store-data.xlsx");
		ExcelUtils.setWorkSheet(1);
		SoftAssert sa = new SoftAssert();
		for (int i = 1; i < ExcelUtils.getRowNumber(); i++) {
			driver.navigate().to(this.locators.getProperty("sign_in_form"));

			SignInPage user = new SignInPage(driver, locators, waiter);
			String username = ExcelUtils.getDataAt(i, 0);
			String password = ExcelUtils.getDataAt(i, 1);

			user.login(username, password);

			sa.assertTrue(user.isLoggedIn());

		}
		sa.assertAll();
	}

	@Test
	public void signInTest2() throws InterruptedException {
		ExcelUtils.setExcell("data/pet-store-data.xlsx");
		ExcelUtils.setWorkSheet(1);
		SoftAssert sa = new SoftAssert();
		{
			driver.navigate().to(this.locators.getProperty("sign_in_form"));

			SignInPage user = new SignInPage(driver, locators, waiter);
			String username = ExcelUtils.getDataAt(1, 0);
			String password = ExcelUtils.getDataAt(1, 1);

			user.login(username, "");

			Thread.sleep(2000);
			sa.assertTrue(user.isNotLoggedInNoPassword());

			driver.navigate().to(this.locators.getProperty("sign_in_form"));

			user.login(username, password + "1254");

			Thread.sleep(2000);
			sa.assertTrue(user.isNotLoggedInWrongPassword());

			sa.assertAll();
		}
	}

	@AfterClass
	public void afterClass() {
		ExcelUtils.closeExcell();
		this.driver.close();
	}
}