package steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import org.junit.Assert;

import static hooks.Setup.driver;

public class HomePageStepsDefinition {

    CommonSteps steps = new steps.CommonSteps();
    private static final String PASSWORD_ASSISTANCE_PAGE_TITLE = "Amazon Password Assistance";
    @Given("An unauthorized user navigates to the home page")
    public void an_unauthorized_user_navigates_to_the_home_page() {
        driver.get("https://www.amazon.com/");
    }
    @When("Navigates the Amazon home page")
    public void navigates_the_link() {
        steps.moveToElement("//span[text()='Account & Lists']");
        steps.clickTo("//span[text()='Sign in']");
        steps.clickTo("//span[contains(text(), 'Need help?')]");
        steps.clickTo("//a[contains(text(), 'Forgot your password?')]");
    }
    @Then("A user should see the Password assistance page")
    public void a_user_should_see_the_password_assistance_page() {
        Assert.assertEquals(driver.getTitle(), PASSWORD_ASSISTANCE_PAGE_TITLE);
    }

}
