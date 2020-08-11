package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class OrderHistoryPage {

    private WebDriver driver;

    public OrderHistoryPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//tbody//tr[1]//th[1]")
    WebElement orderId;

    @FindBy(xpath = "//tr[1]//td[4]//span[1]")
    WebElement orderStatus;

    @FindBy(xpath = "//tr[1]//td[2]")
    WebElement totalPrice;

    public String getOrderId() {
        return orderId.getText();
    }

    public String getOrderStatus() {
        return orderStatus.getText();
    }

    public double getTotalPrice() {
        String totalPriceWithout€ = totalPrice.getText().replaceAll("€", "");
        return Double.parseDouble(totalPriceWithout€);
    }
}
