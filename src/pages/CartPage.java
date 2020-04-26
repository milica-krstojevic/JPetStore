package pages;

import java.util.List;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CartPage {

	private WebDriver driver;
	private Properties locators;
	private WebDriverWait waiter;

	public CartPage(WebDriver driver, Properties locators, WebDriverWait waiter) {
		this.driver = driver;
		this.locators = locators;
		this.waiter = waiter;
	}

	public List<WebElement> getItemsId() {
		return this.driver.findElements(By.xpath(locators.getProperty("items_id")));
	}

	public WebElement getTotalSum() {
		return this.driver.findElement(By.xpath(locators.getProperty("total_sum")));
	}

	
	public int getSumTotal() {
		String subTotal = this.getTotalSum().getText().substring(12);
		double total = Double.parseDouble(subTotal);
                return (int) (total * 100);
	}

	public List<WebElement> getAllItemsTotalPrice() {
		return this.driver.findElements(By.xpath(locators.getProperty("total_price")));
	}

	public boolean isItemAddedToCart(String id) {
		List<WebElement> itemsId = this.getItemsId();
		for (int i = 0; i < itemsId.size(); i++) {
			String itemId = itemsId.get(i).getText();
			if (itemId.contentEquals(id)) {
				return true;
			}
		}
		return false;
	}

	public void deleteAllCookies() {
		this.driver.manage().deleteAllCookies();
	}

	public boolean isCartEmpty() {
		try {
			this.driver.findElement(By.xpath(locators.getProperty("empty_cart")));
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public int getTotalItemsSum() {
		List<WebElement> allItemsPrice = this.getAllItemsTotalPrice();
		int sum = 0;
		for (int i = 0; i < allItemsPrice.size(); i++) {
			String itemPrice = allItemsPrice.get(i).getText().substring(1,5);
			double price = Double.parseDouble(itemPrice);
			sum += (int) (price * 100);
		}
		return sum;
	}

	public boolean verifyPriceCalculation() {
		return this.getSumTotal() == this.getTotalItemsSum();
	}

}
