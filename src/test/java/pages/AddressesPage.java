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

    public int getNumberOfAddressesOnTheAccount(){
        return addresses.size();
    }

    public boolean isAddressCorrectlyAdded(String alias, String company, String address,
                                           String city, String postcode, String phone) {
        boolean isAddressCorrectlyAdded = false;
        String addressDetails[] = addresses.get(addresses.size() - 1).getText().split("\n");
        if (
                addressDetails[0].equals(alias)
                        && addressDetails[2].equals(company)
                        && addressDetails[3].equals(address)
                        && addressDetails[4].equals(city)
                        && addressDetails[5].equals(postcode)
                        && addressDetails[7].equals(phone)) {
            isAddressCorrectlyAdded = true;
        }
        return isAddressCorrectlyAdded;
    }

    public void deleteAddedAddress() {
        WebElement lastAddedAddress = deleteAddressBtn.get(deleteAddressBtn.size() - 1);
        if (lastAddedAddress.isEnabled()) {
            lastAddedAddress.click();
        } else throw new AssertionError("Couldn't click on Delete Address Button");
    }


}
