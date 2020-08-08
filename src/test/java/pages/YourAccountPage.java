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

    @FindBy(xpath = "//a[@id='addresses-link']//span[1]")
    WebElement AddressesBtn;

    public void goToAddressespage(){
        if (AddressesBtn.isDisplayed()){
            AddressesBtn.click();
        }else throw new AssertionError("Couldn't click on Addresses Button");
    }
}
