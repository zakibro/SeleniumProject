package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class YourAccountPage {

    private WebDriver driver;

    public YourAccountPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(name = "s")
    WebElement searchProcut;
    @FindBy(xpath = "//a[@id='addresses-link']//span[1]")
    WebElement AddressesBtn;
    @FindBy(id = "history-link")
    WebElement orderHistoryBtn;

    public void goToAddressespage() {
        if (AddressesBtn.isDisplayed()) {
            AddressesBtn.click();
        } else throw new AssertionError("Couldn't click on Addresses Button");
    }

    public void searchProduct(String product) {
        searchProcut.clear();
        searchProcut.sendKeys(product);
        searchProcut.submit();
    }

    public void goToOrderHistoryPage(){
        if (orderHistoryBtn.isEnabled()){
            orderHistoryBtn.click();
        }
        else throw new AssertionError("Couldn't go to order history page");
    }

}
