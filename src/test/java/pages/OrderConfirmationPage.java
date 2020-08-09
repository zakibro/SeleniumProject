package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class OrderConfirmationPage {

    private WebDriver driver;

    public OrderConfirmationPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//h3[@class='h1 card-title']")
    WebElement confirmationMessage;
    @FindBy(xpath = "//li[contains(text(),'Order reference:')]")
    WebElement orderId;


    public String getConfirmationMessage() {
        return confirmationMessage.getText().replaceAll("\uE876", "");

    }

    public String getOrderId(){
        return orderId.getText().replaceAll("Order reference: ", "");
    }

}
