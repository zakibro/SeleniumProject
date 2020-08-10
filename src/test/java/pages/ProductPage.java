package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

public class ProductPage {

    private WebDriver driver;

    public ProductPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(id = "group_1")
    WebElement size;
    @FindBy(id = "quantity_wanted")
    WebElement quantity;
    @FindBy(xpath = "//button[@class='btn btn-primary add-to-cart']")
    WebElement addToCartBtn;
    @FindBy(xpath = "//span[@class='discount discount-percentage']")
    WebElement discount;
    @FindBy(xpath = "//span[@class='regular-price']")
    WebElement regularPrice;
    @FindBy(xpath = "//span[@itemprop='price']")
    WebElement price;
    @FindBy(xpath = "//p[@class='cart-products-count']")
    WebElement numberOfProductsInCart;
    @FindBy(xpath = "//div[@class='col-md-7']//p[2]")
    WebElement totalAmountToPay;
    @FindBy(id = "myModalLabel")
    WebElement productsAddedToCartMessage;
    @FindBy(xpath = "//a[@class='btn btn-primary']")
    WebElement proceedToCheckoutBtn;

    public String getTheDiscountLabel() {
        return discount.getText();
    }

    public double getPriceAfterDiscount() {
        String priceWithout€ = price.getText().replaceAll("€", "");
        return Double.parseDouble(priceWithout€);
    }

    public double getPriceBeforeDiscount() {
        String regularPriceWithout€ = regularPrice.getText().replaceAll("€", "");
        return Double.parseDouble(regularPriceWithout€);
    }

    public void selectSize(String size) {
        Select sizeDropdown = new Select(this.size);
        sizeDropdown.selectByVisibleText(size);
    }

    public void enterQuantity(String quantity) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            System.err.println("Something went wrong during entering the quantity");
        }
        if (this.quantity.isEnabled()) {
            this.quantity.clear();
            this.quantity.sendKeys(quantity);
        } else throw new AssertionError("Couldn't enter quantity");
    }

    public void addToCart() {
        if (addToCartBtn.isEnabled()) {
            addToCartBtn.click();
        } else throw new AssertionError("Couldn't click add to cart button");
    }

    public String getNumberOfProductsInCart() {
        String numberOfProductsInCartString = numberOfProductsInCart.getText();
        String numberOfProductsInCartTable[] = numberOfProductsInCartString.split(" ");
        return numberOfProductsInCartTable[2];
    }

    public void getProductAddedToCartConfirmation(String message) {
        String test = productsAddedToCartMessage.getText().replaceAll("\uE876", "");
        if (!test.equals(message)) {
            throw new AssertionError("The products added message is not correct");
        }
    }

    public double getPricePerUnit() {
        String pricePerUnitWithout€ = price.getText().replaceAll("€", "");
        return Double.parseDouble(pricePerUnitWithout€);
    }

    public double getTotalPrice() {
        String totalPrice = totalAmountToPay.getText().replaceAll("Total products: €", "");
        return Double.parseDouble(totalPrice);
    }

    public void proceedToCheckout() {
        if (proceedToCheckoutBtn.isEnabled()) {
            proceedToCheckoutBtn.click();
        } else throw new AssertionError("Couldn't click on proceed to checkout button");
    }

}
