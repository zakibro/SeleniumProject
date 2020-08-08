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

    public void isTheDiscountCorrect(int discountAmount) {
        if (!discount.getText().equals("SAVE " + discountAmount + "%")) {
            throw new AssertionError("The discount label is not correct");
        }
        //parse int `discountAmount` to double
        double discountAmountDouble = (double) discountAmount;
        //strip `€` from the prices
        String regularPriceWithout€ = regularPrice.getText().replaceAll("€", "");
        String priceWithout€ = price.getText().replaceAll("€", "");

        //prase strings to doubles
        double regularPriceWithout€Double = Double.parseDouble(regularPriceWithout€);
        double priceWithout€Double = Double.parseDouble(priceWithout€);

        //assert the discount is correctly calculated
        if (!((priceWithout€Double / regularPriceWithout€Double * 100) == (100 - discountAmountDouble))) {
            throw new AssertionError("The discount is not correctly calculated!");
        }
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

    public void isTheProductAddedToCart(String quantity) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            System.err.println("Something went wrong during checking if the quantities are correct");
        }
        //strips the string to table and checks if the number of prdocuts is correct
        String numberOfProductsInCartString = numberOfProductsInCart.getText();
        String numberOfProductsInCartTable[] = numberOfProductsInCartString.split(" ");
        if (!numberOfProductsInCartTable[2].equals(quantity)) {
            throw new AssertionError("The quantity is not correct");
        }
    }

    public void getProductAddedToCartConfirmation(String message) {
        String test = productsAddedToCartMessage.getText().replaceAll("\uE876", "");
        if (!test.equals(message)) {
            throw new AssertionError("The products added message is not correct");
        }
    }

    public void isTotalPriceCorrect(int quantity) {
        //get total amount to pay from the website and get price per unit
        String totalPrice = totalAmountToPay.getText().replaceAll("Total products: €", "");
        String priceWithout€ = price.getText().replaceAll("€", "");

        //parse to double
        double totalPriceDouble = Double.parseDouble(totalPrice);
        double pricePerUnit = Double.parseDouble(priceWithout€);

        if (!((pricePerUnit * quantity) == totalPriceDouble)) {
            throw new AssertionError("The total price is wrong");
        }
    }

    public void proceedToCheckout() {
        if (proceedToCheckoutBtn.isEnabled()) {
            proceedToCheckoutBtn.click();
        } else throw new AssertionError("Couldn't click on proceed to checkout button");
    }

}
