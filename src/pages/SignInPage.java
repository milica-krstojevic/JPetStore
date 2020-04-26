package pages;

import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SignInPage {

	private WebDriver driver;
	private Properties locators;
	private WebDriverWait waiter;

	public SignInPage(WebDriver driver, Properties locators, WebDriverWait waiter) {
		this.driver = driver;
		this.locators = locators;
		this.waiter = waiter;
	}

	public WebElement getUserName() {
		return this.driver.findElement(By.xpath(locators.getProperty("user_id")));
	}

	public void setUserName(String username) {
		WebElement userName = this.getUserName();
		userName.clear();
		userName.sendKeys(username);
	}

	public WebElement getPassword() {
		return this.driver.findElement(By.xpath(locators.getProperty("password")));
	}

	public void setPassword(String password) {
		WebElement passWord = this.getPassword();
		passWord.clear();
		passWord.sendKeys(password);
	}

	public WebElement getLoginButton() {
		return this.driver.findElement(By.xpath(locators.getProperty("login_btn")));
	}

	public void clickLoginButton() {
		this.getLoginButton().click();
	}

	public WebElement getRegLink() {
		return this.driver.findElement(By.xpath(locators.getProperty("register_form_url")));
	}

	public void registration() {
		this.getRegLink().click();
	}

	public void login(String username, String password) {
		this.setUserName(username);
		this.setPassword(password);
		this.getLoginButton().click();
	}

	public boolean isLoggedIn() {
		boolean login = false;
		WebElement e = this.driver.findElement(By.xpath(locators.getProperty("my_account")));
		if (e.isDisplayed()) {
			login = true;
		}
		return login;
	}

	public boolean isNotLoggedInNoPassword() {
		boolean login = false;
		WebElement e = this.driver.findElement(By.xpath(locators.getProperty("enter_u_and_p")));
		if (e.isDisplayed()) {
			login = true;
		}
		return login;
	}

	public boolean isNotLoggedInWrongPassword() {
		boolean login = false;
		WebElement e = this.driver.findElement(By.xpath(locators.getProperty("message")));
		if (e.isDisplayed()) {
			login = true;
		}
		return login;
	}
}
