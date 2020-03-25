package pages;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

public class PetStoreMenuPage {

	private WebDriver driver;
	private Properties locators;
	private WebDriverWait waiter;

	public PetStoreMenuPage(WebDriver driver, Properties locators, WebDriverWait waiter) {
		this.driver = driver;
		this.locators = locators;
		this.waiter = waiter;
	}

	public List<WebElement> getSideBar() {
		return this.driver.findElements(By.xpath(this.locators.getProperty("sidebar_links")));
	}

	public List<WebElement> getMain() {
		return this.driver.findElements(By.xpath(this.locators.getProperty("main_links")));
	}

	public List<WebElement> getNav() {
		return this.driver.findElements(By.xpath(this.locators.getProperty("nav_links")));
	}

	public List<WebElement> getLinksByCategoryName(String name) {
		return this.driver.findElements(By.xpath(this.locators.getProperty(name.toLowerCase())));
	}

	public WebElement getSignIn() {
		return this.driver.findElement(By.xpath(this.locators.getProperty("sign_in_btn")));
	}

	public boolean sideBarValidation() {
		List<WebElement> sideBarLinks = this.getSideBar();
		boolean validation = true;
		for (int i = 0; i < sideBarLinks.size(); i++) {
			WebElement link1 = sideBarLinks.get(i);
			int status = PetStoreMenuPage.verifyURLStatus(link1.getAttribute("href"));
			if (status > 400) {
				validation = false;
			}
		}
		return validation;
	}

	public boolean mainValidation() {
		List<WebElement> mainLinks = this.getMain();
		boolean validation = true;
		for (int i = 0; i < mainLinks.size(); i++) {
			WebElement link2 = mainLinks.get(i);
			int status = PetStoreMenuPage.verifyURLStatus(link2.getAttribute("href"));
			if (status > 400) {
				validation = false;
			}
		}
		return validation;
	}

	public boolean navBarValidation() {
		List<WebElement> navLinks = this.getNav();
		boolean validation = true;
		for (int i = 0; i < navLinks.size(); i++) {
			WebElement link3 = navLinks.get(i);
			int status = PetStoreMenuPage.verifyURLStatus(link3.getAttribute("href"));
			if (status > 400) {
				validation = false;
			}
		}
		return validation;
	}

	public static int verifyURLStatus(String urlString) {
		int status = 404;
		try {
			URL link = new URL(urlString);
			HttpURLConnection hConn = null;
			hConn = (HttpURLConnection) link.openConnection();
			hConn.setRequestMethod("GET");
			hConn.connect();
			status = hConn.getResponseCode();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return status;
	}

	public boolean checkLinksByCategoryName(String name) throws InterruptedException {
		boolean isOK = true;
		for (int i = 0; i < 3; i++) {
			driver.navigate().to(this.locators.getProperty("pet_store_menu_url"));
			Thread.sleep(2000);
			List<WebElement> links = this.getLinksByCategoryName(name);
			links.get(i).click();
			if (!this.driver.getCurrentUrl().contains("categoryId=" + name)) {
				isOK = false;
			}
		}
		return isOK;
	}

	public void clickSignInBtn() {
		this.getSignIn().click();
	}

	public void signIn() {
		this.clickSignInBtn();

	}
}