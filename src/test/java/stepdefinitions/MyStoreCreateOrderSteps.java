package stepdefinitions;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import pages.*;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class MyStoreCreateOrderSteps {

    private WebDriver driver;
    private LoginPage loginPage;
    private YourAccountPage yourAccountPage;
    private ProductPage productPage;
    private CartPage cartPage;
    private CheckoutPage checkoutPage;
    private OrderConfirmationPage orderConfirmationPage;
    private OrderHistoryPage orderHistoryPage;
    private double totalPrice;
    private String orderId;
    private static final String EMAIL = "paweltestuje@gmail.com";
    private static final String PASSWORD = "password123";
    private static final String LOGGED_USER = "Paweł Mazur";

    @Given("^user is logged in My Store$")
    public void userIsLoggedInMyStore() {

        //initiate new Chrome Browser and setup global wait
        System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver");
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        // Maximize the browser window
        driver.manage().window().maximize();

        //login correctly to my store
        driver.get("https://prod-kurs.coderslab.pl/index.php?controller=authentication&back=my-account");
        loginPage = new LoginPage(driver);
        loginPage.loginAs(EMAIL, PASSWORD);
        Assert.assertEquals(LOGGED_USER, loginPage.getLoggedUsername());
    }

    @And("^user is on \"([^\"]*)\" product page$")
    public void userIsOnProductPage(String product) {
        yourAccountPage = new YourAccountPage(driver);
        yourAccountPage.searchProduct(product);
        WebElement goToProductPageLink = driver.findElement(By.
                xpath("//a[contains(text(),'Hummingbird printed sweater')]"));
        if (goToProductPageLink.isDisplayed()) {
            goToProductPageLink.click();
        } else throw new AssertionError("Couldn't go to the product page");
    }

    @And("^discount for the product is (\\d+)%$")
    public void discountForTheProductIsCorrect(int discountAmount) {
        productPage = new ProductPage(driver);
        Assert.assertEquals("SAVE " + discountAmount + "%", productPage.getTheDiscountLabel());
        if (!((productPage.getPriceAfterDiscount() / productPage.getPriceBeforeDiscount() * 100) == (100 - discountAmount))) {
            throw new AssertionError("The discount is not correctly calculated!");
        }
    }

    @When("^user selects size \"([^\"]*)\"$")
    public void userSelectsSize(String size) {
        productPage.selectSize(size);
    }

    @And("^user selects quantity \"([^\"]*)\"$")
    public void userSelectsQuantity(String quantity) {
        productPage.enterQuantity(quantity);
    }


    @And("^user adds the product to cart$")
    public void userAddsTheProductToCart() {
        productPage.addToCart();
    }

    @Then("^product with quantity \"([^\"]*)\" have been successfully added to cart$")
    public void productAddedToCart(String quantity) throws InterruptedException {
        Thread.sleep(1000);
        Assert.assertEquals(quantity, productPage.getNumberOfProductsInCart());
    }

    @And("^user sees products has been added message \"([^\"]*)\"$")
    public void userSeesProductsHasBeenAddedMessage(String message) {
        productPage.getProductAddedToCartConfirmation(message);
    }

    @And("^price is correctly calculated based on quantity \"([^\"]*)\"$")
    public void priceIsCorrectlyCalculated(int quantity) {
        //save total price for the order if correct, for future assertions
        totalPrice = productPage.getTotalPrice();
        Assert.assertTrue(productPage.getPricePerUnit() * quantity == productPage.getTotalPrice());
    }

    @When("^user proceeds to checkout$")
    public void userProceedsToCheckout() {
        cartPage = new CartPage(driver);
        productPage.proceedToCheckout();
        cartPage.proceedToCheckout();
    }

    @And("^confirms the address$")
    public void confirmsTheAddress() {
        checkoutPage = new CheckoutPage(driver);
        checkoutPage.pickFirstAddress();
        checkoutPage.confirmAddress();
    }

    @And("^confirms the shipping method$")
    public void confirmsTheShippingMethod() {
        checkoutPage.pickFirstDeliveryOption();
        checkoutPage.confirmDelivery();
    }

    @And("^selects the payment methods$")
    public void selectsThePaymentMethods() {
        checkoutPage.pickFirstPaymentOption();
    }

    @And("^confirms the order$")
    public void confirmsTheOrder() {
        checkoutPage.confirmTermsAndConditions();
        checkoutPage.confirmTheOrder();
    }

    @Then("^user sees order confirmation message \"([^\"]*)\"$")
    public void userSeesOrderConfirmationMessage(String message) {
        orderConfirmationPage = new OrderConfirmationPage(driver);
        Assert.assertEquals(message, orderConfirmationPage.getConfirmationMessage());
        //get orderId from confirmation page for future assertions -> order history page
        orderId = orderConfirmationPage.getOrderId();

        takeScreenShot(driver, "src/test/java/stepdefinitions/screenshot.png");
    }

    @When("^user goes to order history and details page$")
    public void userGoesToOrderHistoryAndDetailsPage() {
        loginPage.goToYourAccountPage();
        yourAccountPage.goToOrderHistoryPage();
    }

    @Then("^the created order is on top of the list with status \"([^\"]*)\"$")
    public void theCreatedOrderIsCorrectlyAddedToHistory(String status) {
        orderHistoryPage = new OrderHistoryPage(driver);
        Assert.assertEquals(status, orderHistoryPage.getOrderStatus());
        Assert.assertEquals(totalPrice, orderHistoryPage.getTotalPrice(), 0.001);
        Assert.assertEquals(orderId, orderHistoryPage.getOrderId());
    }

    private void takeScreenShot(WebDriver webdriver, String fileWithPath) {
        //Convert web driver object to TakeScreenshot
        TakesScreenshot scrShot = ((TakesScreenshot) webdriver);
        //Call getScreenshotAs method to create image file
        File SrcFile = scrShot.getScreenshotAs(OutputType.FILE);
        //Move image file to new destination
        File DestFile = new File(fileWithPath);
        //Copy file at destination
        try {
            FileUtils.copyFile(SrcFile, DestFile);
        } catch (IOException e) {
            System.err.println("Something went wrong during copying");
        }
    }
}
