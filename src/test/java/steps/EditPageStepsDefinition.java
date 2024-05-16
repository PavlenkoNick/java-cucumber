package steps;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class EditPageStepsDefinition {
    private static final String CHECKBOX_INPUT_BY_NAME_LABEL = "//label[text()='Enter your full Name']/..//input";

    CommonSteps steps = new steps.CommonSteps();

    @When("Enters {string} into the {string} input field")
    public void enters_into_the_input_field(String text, String input_field_name) {
        String locator = steps.locatorByText(CHECKBOX_INPUT_BY_NAME_LABEL, input_field_name);
        steps.clearField(locator);
        steps.sendKeyTo(text, locator);
    }
    @When("Appends a text {string} into the {string} input field")
    public void appends_a_text_into_the_append_a_text_and_press_keyboard_tab_input_field(String text, String input_field_name) {
        String locator = steps.locatorByText(CHECKBOX_INPUT_BY_NAME_LABEL, input_field_name);
        steps.sendKeyTo(text, locator);
    }
    @Then("{string} is in the {string} input field")
    public void is_in_the_input_field(String text, String input_field_name) {
        String locator = steps.locatorByText(CHECKBOX_INPUT_BY_NAME_LABEL, input_field_name);
        steps.assertTextIsPresentedIn(text, locator);
    }
    @Then("{string} is presented in the {string} input field")
    public void is_presented_in_the_input_field(String text, String input_field_name) {
        String locator = steps.locatorByText(CHECKBOX_INPUT_BY_NAME_LABEL, input_field_name);
        steps.assertTextIsPresentedIn(text, locator);
    }
}
