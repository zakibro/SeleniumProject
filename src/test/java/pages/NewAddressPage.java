package pages;

import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

public class NewAddressPage {

    private static WebDriver driver;

    public NewAddressPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(name = "alias")
    WebElement aliasInput;
    @FindBy(name = "company")
    WebElement companyInput;
    @FindBy(name = "address1")
    WebElement addressInput;
    @FindBy(name = "postcode")
    WebElement postcodeInput;
    @FindBy(name = "city")
    WebElement cityInput;
    @FindBy(name = "id_country")
    WebElement countrySelect;
    @FindBy(name = "phone")
    WebElement phoneInput;
    @FindBy(xpath = "//button[@class='btn btn-primary float-xs-right']")
    WebElement saveBtn;

    public void enterFields(String alias, String company, String address, String postcode, String city, String phone) {
        aliasInput.clear();
        aliasInput.sendKeys(alias);

        companyInput.clear();
        companyInput.sendKeys(company);

        addressInput.clear();
        addressInput.sendKeys(address);

        postcodeInput.clear();
        postcodeInput.sendKeys(postcode);

        cityInput.clear();
        cityInput.sendKeys(city);

        Select countryDropdown = new Select(countrySelect);
        countryDropdown.selectByValue("17");

        phoneInput.clear();
        phoneInput.sendKeys(phone);
    }

    public void saveNewAddress() {
        if (saveBtn.isDisplayed()) {
            saveBtn.click();
        } else Assert.fail();
    }

}
