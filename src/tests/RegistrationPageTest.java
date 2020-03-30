
package tests;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import pages.RegistrationPage;
import utils.ExcelUtils;

public class RegistrationPageTest {

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
	public void registrationTest() {
		ExcelUtils.setExcell("data/pet-store-data.xlsx");
		ExcelUtils.setWorkSheet(1);
		SoftAssert sa = new SoftAssert();
		for (int i = 1; i < ExcelUtils.getRowNumber(); i++) {
			driver.navigate().to(this.locators.getProperty("register_form_url"));
			RegistrationPage rp = new RegistrationPage(driver, locators, waiter);
			String uniqueID = UUID.randomUUID().toString();
			String userID = uniqueID.substring(0, 7);
			ExcelUtils.setDataAt(i, 0, userID);
			String password = ExcelUtils.getDataAt(i, 1);
			String firstname = ExcelUtils.getDataAt(i, 2);
			String lastname = ExcelUtils.getDataAt(i, 3);
			String email = ExcelUtils.getDataAt(i, 4);
			String phone = ExcelUtils.getDataAt(i, 5);
			String address1 = ExcelUtils.getDataAt(i, 6);
			String address2 = ExcelUtils.getDataAt(i, 7);
			String city = ExcelUtils.getDataAt(i, 8);
			String state = ExcelUtils.getDataAt(i, 9);
			String zipcode = ExcelUtils.getDataAtNum(i, 10);
			String country = ExcelUtils.getDataAt(i, 11);

			rp.submitForm(userID, password, password, firstname, lastname, email, phone, address1, address2, city,
					state, zipcode, country, "english", "FISH", false, false);

			sa.assertTrue(rp.isRegisteredUser());
		}
		sa.assertAll();

	}

	@Test
	public void registrationTest2() {
		ExcelUtils.setExcell("data/pet-store-data.xlsx");
		ExcelUtils.setWorkSheet(1);
		SoftAssert sa = new SoftAssert();
		driver.navigate().to(this.locators.getProperty("register_form_url"));
		RegistrationPage rp = new RegistrationPage(driver, locators, waiter);
		String uniqueID = UUID.randomUUID().toString();
		String userID = uniqueID.substring(0, 7);
		String password = ExcelUtils.getDataAt(1, 1);
		String firstname = ExcelUtils.getDataAt(1, 2);
		String lastname = ExcelUtils.getDataAt(1, 3);
		String email = ExcelUtils.getDataAt(1, 4);
		String phone = ExcelUtils.getDataAt(1, 5);
		String address1 = ExcelUtils.getDataAt(1, 6);
		String address2 = ExcelUtils.getDataAt(1, 7);
		String city = ExcelUtils.getDataAt(1, 8);
		String state = ExcelUtils.getDataAt(1, 9);
		String zipcode = ExcelUtils.getDataAtNum(1, 10);
		String country = ExcelUtils.getDataAt(1, 11);

		rp.submitForm(userID, password, password, firstname, "", email, phone, address1, address2, city, state, zipcode,
				country, "english", "FISH", false, false);

		sa.assertFalse(rp.isRegisteredUser());

		driver.navigate().to(this.locators.getProperty("register_form_url"));

		rp.submitForm(ExcelUtils.getDataAt(1, 0), password, password, firstname, lastname, email, phone, address1,
				address2, city, state, zipcode, country, "english", "FISH", true, false);

		sa.assertFalse(rp.isRegisteredUser());

		driver.navigate().to(this.locators.getProperty("register_form_url"));

		rp.submitForm(userID, password, password + "555", firstname, lastname, email, phone, address1, address2, city,
				state, zipcode, country, "japanese", "FISH", false, true);

		sa.assertTrue(rp.isRegisteredUser());

		sa.assertAll();
	}

	@AfterClass
	public void afterClass() {
		ExcelUtils.closeExcell();
		this.driver.close();
	}

}
