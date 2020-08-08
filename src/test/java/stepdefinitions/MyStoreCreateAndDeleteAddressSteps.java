package stepdefinitions;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import pages.AddressesPage;
import pages.LoginPage;
import pages.NewAddressPage;
import pages.YourAccountPage;

import java.util.concurrent.TimeUnit;

public class MyStoreCreateAndDeleteAddressSteps {

    private WebDriver driver;
    private LoginPage loginPage;
    private YourAccountPage yourAccountPage;
    private AddressesPage addressesPage;
    private NewAddressPage newAddressPage;
    private int numberOfAddressesOnAccount;
    private static final String EMAIL = "paweltestuje@gmail.com";
    private static final String PASSWORD = "password123";
    private static final String LOGGED_USER = "Paweł Mazur";

    @Given("^user is logged in my store$")
    public void userIsLoggedInMyStore() {

        //initiate new Chrome Browser and setup global wait
        System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver");
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

        // Maximize the browser window
        driver.manage().window().maximize();

        //login correctly to my store
        driver.get("https://prod-kurs.coderslab.pl/index.php?controller=authentication&back=my-account");
        loginPage = new LoginPage(driver);
        loginPage.loginAs(EMAIL, PASSWORD);
        Assert.assertEquals(LOGGED_USER, loginPage.getLoggedUsername());

    }

    @And("^user is on Addresses page$")
    public void userIsOnAddressesPage() {
        yourAccountPage = new YourAccountPage(driver);
        //goes to Your Address page
        yourAccountPage.goToAddressespage();

    }

    @When("^user clicks on Create new address button$")
    public void userClicksOnCreateNewAddressButton() {
        addressesPage = new AddressesPage(driver);
        //get the number of added addresses on the account
        numberOfAddressesOnAccount = addressesPage.getNumberOfAddressesOnTheAccount();
        //goes to Create New Address Form
        addressesPage.goToCreateNewAddressForm();
    }

    @And("^user enters fields with \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\"," +
            " \"([^\"]*)\", \"([^\"]*)\" and \"([^\"]*)\"$")
    public void userEntersFields(String alias, String company, String address,
                                 String postcode, String city, String phone) {
        //enter New address form fields
        newAddressPage = new NewAddressPage(driver);
        newAddressPage.enterFields(alias, company, address, postcode, city, phone);
    }

    @And("^user saves information$")
    public void userSavesInformation() {
        //save information entered
        newAddressPage.saveNewAddress();
    }

    @Then("^User sees \"([^\"]*)\"$")
    public void userSeesAddressCreationConfirmation(String message) {
        //assert success create message appeared
        Assert.assertEquals(message, addressesPage.getSuccessMessage());
    }

    @And("^the address has been correctly added with provided" +
            " \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\" and \"([^\"]*)\"$")
    public void theAddressHasBeenCorrectlyAdded(String alias, String company, String address,
                                                String postcode, String city, String phone) {
        //assert the details of added address are correct
        Assert.assertTrue(addressesPage.isAddressCorrectlyAdded(alias, company, address, city, postcode, phone));
        Assert.assertEquals(numberOfAddressesOnAccount + 1, addressesPage.getNumberOfAddressesOnTheAccount());
    }

    @When("^user deletes new address$")
    public void userDeletesNewAddress() {
        addressesPage.deleteAddedAddress();
    }

    @Then("^users sees \"([^\"]*)\"$")
    public void usersSeesAddressDeletedConfirmation(String message) {
        //assert success delete message appeared
        Assert.assertEquals(message, addressesPage.getSuccessMessage());
    }

    @And("^address has been deleted$")
    public void addressHasBeenDeleted() {
        //assert the address has been deleted
        Assert.assertEquals(numberOfAddressesOnAccount, addressesPage.getNumberOfAddressesOnTheAccount());
    }

    @And("^quit the browser$")
    public void quitTheBrowser() {
        driver.quit();
    }
}
