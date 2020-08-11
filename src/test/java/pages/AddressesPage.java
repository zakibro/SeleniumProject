package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class AddressesPage {

    private WebDriver driver;

    public AddressesPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//article[@class='alert alert-success']//ul")
    WebElement successMessage;

    @FindBy(xpath = "//span[contains(text(),'Create new address')]")
    WebElement createNewAddressBtn;
    @FindBy(className = "address")
    List<WebElement> addresses;
    @FindBy(xpath = "//span[contains(text(),'Delete')]")
    List<WebElement> deleteAddressBtn;

    public String getSuccessMessage() {
        return successMessage.getText();
    }

    public void goToCreateNewAddressForm() {
        if (createNewAddressBtn.isDisplayed()) {
            createNewAddressBtn.click();
        } else throw new AssertionError("Couldn't click on Create New Address Button");
    }

    public int getNumberOfAddressesOnTheAccount() {
        return addresses.size();
    }

    public String getAddressDetails() {
        String addressDetailsWithoutNewLines = addresses.get(addresses.size() - 1).getText().replaceAll("\n", " ");
        return addressDetailsWithoutNewLines.replaceAll(" \uE254 Update \uE872 Delete", "");
    }

    public void deleteAddedAddress() {
        WebElement lastAddedAddress = deleteAddressBtn.get(deleteAddressBtn.size() - 1);
        if (lastAddedAddress.isEnabled()) {
            lastAddedAddress.click();
        } else throw new AssertionError("Couldn't click on Delete Address Button");
    }


}
