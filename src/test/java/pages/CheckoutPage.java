package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class CheckoutPage {

    private WebDriver driver;

    public CheckoutPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(className = "address")
    List<WebElement> addresses;
    @FindBy(name = "confirm-addresses")
    WebElement confirmAddressBtn;
    @FindBy(id = "delivery_option_1")
    WebElement firstDeliveryOption;
    @FindBy(name = "confirmDeliveryOption")
    WebElement confirmDeliveryBtn;
    @FindBy(id = "payment-option-1")
    WebElement firstPaymentOption;
    @FindBy(id = "conditions_to_approve[terms-and-conditions]")
    WebElement termsAndConditionsOption;
    @FindBy(xpath = "//button[@class='btn btn-primary center-block']")
    WebElement confirmOrderBtn;

    public void pickFirstAddress() {
        if (!addresses.get(0).isSelected()) {
            addresses.get(0).click();
        } else throw new AssertionError("Couldn't select address");
    }

    public void confirmAddress() {
        if (confirmAddressBtn.isEnabled()) {
            confirmAddressBtn.click();
        } else throw new AssertionError("Couldn't click on confirm address button");
    }

    public void pickFirstDeliveryOption() {
        if (!firstDeliveryOption.isSelected()) {
            firstDeliveryOption.click();
        }
    }

    public void confirmDelivery() {
        if (confirmDeliveryBtn.isEnabled()) {
            confirmDeliveryBtn.click();
        } else throw new AssertionError("Couldn't click on confirm delivery button");
    }

    public void pickFirstPaymentOption() {
        if (!firstPaymentOption.isSelected()) {
            firstPaymentOption.click();
        } else throw new AssertionError("Couldn't select the payment option");
    }

    public void confirmTermsAndConditions() {
        if (!termsAndConditionsOption.isSelected()) {
            termsAndConditionsOption.click();
        } else throw new AssertionError("Couldn't confirm terms and conditions");
    }

    public void confirmTheOrder(){
        if (confirmOrderBtn.isEnabled()){
            confirmOrderBtn.click();
        }else throw new AssertionError("Couldn't confirm the order");
    }

}
