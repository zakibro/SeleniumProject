package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CartPage {

    private WebDriver driver;

    public CartPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//a[@class='btn btn-primary']")
    WebElement proceedToCheckoutBtn;

    public void proceedToCheckout() {
        if (proceedToCheckoutBtn.isEnabled()) {
            proceedToCheckoutBtn.click();
        } else throw new AssertionError("Couldn't click on proceed to checkout button on Cart Page");
    }

}
