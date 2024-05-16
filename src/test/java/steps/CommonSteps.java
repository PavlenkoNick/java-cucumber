package steps;

import hooks.Setup;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.apache.log4j.Logger;

import java.time.Duration;

public class CommonSteps {

    public Integer DEFAULT_WAIT_TIMEOUT = 15;
    public Integer LONG_WAIT_TIMEOUT = 30;
    public Integer SHORT_WAIT_TIMEOUT = 10;
    public Logger logger = Logger.getLogger(CommonSteps.class);

    public WebDriverWait wait;
    WebDriver driver;

    public CommonSteps() {
        driver = Setup.driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(DEFAULT_WAIT_TIMEOUT));
    }

    // ========================================================   WAIT (RETURN WEB ELEMENT  ) ======================================================== //

    // waits and returns an element, found by a locator, that is presented
    @And("wait for {string} is presented")
    public WebElement waitForIsPresented(String locator) {
        return waitForIsPresentedAndVisibleClickableForSec_base(locator, DEFAULT_WAIT_TIMEOUT, true, false, false, false);
    }

    @And("wait for {string} is presented for {string} seconds")
    // waits a number of seconds and returns an element, found by a locator, that is presented
    public WebElement waitForIsPresentedForSec(String locator, Integer amountOfSeconds) {
        return waitForIsPresentedAndVisibleClickableForSec_base(locator, amountOfSeconds, true, false, false, false);
    }

    @And("wait for {string} is visible")
    // waits and returns an element, found by a locator, that is visible
    public WebElement waitForIsVisible(String locator) {
        return waitForIsPresentedAndVisibleClickableForSec_base(locator, DEFAULT_WAIT_TIMEOUT, true, true, false, false);
    }

    // waits a number of seconds and returns an element, found by a locator, that is visible
    @And("wait for {string} is visible for {string} seconds")
    public WebElement waitForIsVisibleForSec(String locator, Integer amountOfSeconds) {
        return waitForIsPresentedAndVisibleClickableForSec_base(locator, amountOfSeconds, true, true, false, false);
    }

    @And("wait for {string} is clickable")
    // waits and returns an element, found by a locator, that is clickable
    public WebElement waitForIsClickable(String locator) {
        return waitForIsPresentedAndVisibleClickableForSec_base(locator, DEFAULT_WAIT_TIMEOUT, true, true, true, false);
    }

    @And("wait for {string} is clickable for {string} seconds")
    // waits for a number of seconds and returns an element, found by a locator, that is clickable
    public WebElement waitForIsClickableForSec(String locator, Integer amountOfSeconds) {
        return waitForIsPresentedAndVisibleClickableForSec_base(locator, amountOfSeconds, true, true, true, false);
    }

    // waits and returns an element, found by a locator, that is not clickable
    public WebElement waitForIsNotClickable(String locator) {
        return waitForIsPresentedAndVisibleClickableForSec_base(locator, DEFAULT_WAIT_TIMEOUT, true, true, false, true);
    }

    // waits a number of seconds and returns an element, found by a locator, that is not clickable
    public void waitForIsNotClickableForSec(String locator, Integer seconds) {
        waitForIsPresentedAndVisibleClickableForSec_base(locator, seconds, true, true, false, true);
    }

    // ===========================================================   WAIT (BASE METHODS)   =========================================================== //

    private WebElement waitForIsPresentedAndVisibleClickableForSec_base(String locator, Integer amountOfSeconds, boolean is_presented, boolean is_visible, boolean is_clickable, boolean is_not_clickable) {
        try {
            if (is_presented) waitUntilForSec(locator, "presence", amountOfSeconds);
            else waitUntilForSec(locator, "notPresence", amountOfSeconds);
            if (is_visible) waitUntilForSec(locator, "visibility", amountOfSeconds);
            if (is_clickable) waitUntilForSec(locator, "clickability", amountOfSeconds);
            if (is_not_clickable) waitUntilForSec(locator, "notClickability", amountOfSeconds);
            return driver.findElement(getByObject(locator));
        } catch (NoSuchElementException e) {
            logger.info("NoSuchElementException detected for an element: " + locator);
        }
        throw new NoSuchElementException("Element " + locator + " was not found");
    }

    private void waitUntil(String locator, String what_to_wait_for) {
        waitUntilForSec(locator, what_to_wait_for, DEFAULT_WAIT_TIMEOUT);
    }

    private void waitUntilForSec(String locator, String what_to_wait_for, Integer amountOfSeconds) {
        WebDriverWait localWait = new WebDriverWait(driver, Duration.ofSeconds(amountOfSeconds));
        try {
            switch (what_to_wait_for) {
                case "presence":
                    logger.info("waiting an element for being presented: " + locator);
                    localWait.until(ExpectedConditions.presenceOfElementLocated(getByObject(locator)));
                    break;
                case "notPresence":
                    logger.info("waiting an element for being not presented: " + locator);
                    localWait.until(ExpectedConditions.not(ExpectedConditions.presenceOfAllElementsLocatedBy(getByObject(locator))));
                    break;
                case "visibility":
                    logger.info("waiting an element for being visible: " + locator);
                    localWait.until(ExpectedConditions.visibilityOfElementLocated(getByObject(locator)));
                    break;
                case "notVisibility":
                    logger.info("waiting an element for being invisible: " + locator);
                    localWait.until(ExpectedConditions.invisibilityOfElementLocated(getByObject(locator)));
                    break;
                case "clickability":
                    logger.info("waiting an element for being clickable: " + locator);
                    localWait.until(ExpectedConditions.elementToBeClickable(getByObject(locator)));
                    break;
                case "notClickability":
                    logger.info("waiting an element for being not clickable: " + locator);
                    localWait.until(ExpectedConditions.attributeToBe(getByObject(locator), "disabled", "true"));
                    break;
                default:
                    throw new RuntimeException("There is no case '" + what_to_wait_for + "' in the waitUntil() method");
            }
            logger.info("waiting succeed");
        } catch (Exception e) {
            logger.info("waiting failed");
            throw e;
        }
    }


    private boolean isPresentedAndVisibleClickableForSec_base(String locator, Integer amountOfSeconds, boolean is_presented, boolean is_visible, boolean is_clickable, boolean is_not_clickable) {
        try {
            waitForIsPresentedAndVisibleClickableForSec_base(locator, amountOfSeconds, is_presented, is_visible, is_clickable, is_not_clickable);
        } catch (TimeoutException | NoSuchElementException e) {
            return false;
        }
        return true;
    }

    private void waitForElementIsNotVisibleWithText_base(String locator, String text, Integer amountOfSeconds, boolean is_with_text) {
        logger.info("waiting for invisibility of " + locator);
        WebDriverWait localWait = new WebDriverWait(driver, Duration.ofSeconds(amountOfSeconds));
        if (is_with_text) localWait.until(ExpectedConditions.invisibilityOfElementWithText(getByObject(locator), text));
        else waitUntilForSec(locator, "notVisibility", amountOfSeconds);
    }

    private boolean isNotVisibleWithText_base(String locator, String text, Integer amountOfSeconds, boolean is_with_text) {
        try {
            waitForElementIsNotVisibleWithText_base(locator, text, amountOfSeconds, is_with_text);
        } catch (TimeoutException | NoSuchElementException e) {
            return false;
        }
        return true;
    }

    // ==========================================================   WAIT (RETURN BOOLEAN)   ========================================================== //

    // returns true if an element, found by a locator, is presented
    public boolean isPresented(String locator) {
        return isPresentedAndVisibleClickableForSec_base(locator, DEFAULT_WAIT_TIMEOUT, true, false, false, false);
    }

    // waits a number of seconds and returns true if an element, found by a locator, is presented
    public boolean isPresentedForSec(String locator, Integer amountOfSeconds) {
        return isPresentedAndVisibleClickableForSec_base(locator, amountOfSeconds, true, false, false, false);
    }

    // waits a number of seconds and returns true if an element, found by a locator, is not presented
    public boolean isNotPresentedForSec(String locator, Integer amountOfSeconds) {
        return isPresentedAndVisibleClickableForSec_base(locator, amountOfSeconds, false, false, false, false);
    }

    // waits and returns true if an element, found by a locator, is visible
    public boolean isVisible(String locator) {
        return isPresentedAndVisibleClickableForSec_base(locator, DEFAULT_WAIT_TIMEOUT, true, true, false, false);
    }

    // waits a number of seconds and returns true if an element, found by a locator, is visible
    public boolean isVisibleForSec(String locator, Integer amountOfSeconds) {
        return isPresentedAndVisibleClickableForSec_base(locator, amountOfSeconds, true, true, false, false);
    }

    // waits and returns true if an element, found by a locator, is clickable
    public boolean isClickable(String locator) {
        return isPresentedAndVisibleClickableForSec_base(locator, DEFAULT_WAIT_TIMEOUT, true, true, true, false);
    }

    // waits for a number of seconds and returns true if an element, found by a locator, is clickable
    public boolean isClickableForSec(String locator, Integer amountOfSeconds) {
        return isPresentedAndVisibleClickableForSec_base(locator, amountOfSeconds, true, true, true, false);
    }

    // waits and returns true if an element, found by a locator, is clickable
    public boolean isNotClickable(String locator) {
        return isPresentedAndVisibleClickableForSec_base(locator, DEFAULT_WAIT_TIMEOUT, true, true, false, true);
    }

    // waits for a number of seconds and returns true if an element, found by a locator, is clickable
    public boolean isNotClickableForSec(String locator, Integer amountOfSeconds) {
        return isPresentedAndVisibleClickableForSec_base(locator, amountOfSeconds, true, true, false, true);
    }

    // waits for an element, found by a locator, that contains text, to be invisible. Returns true if found
    public boolean isInvisibleWithText(String locator, String text) {
        return isNotVisibleWithText_base(locator, text, DEFAULT_WAIT_TIMEOUT, true);
    }

    // waits for an element, found by a locator, to be invisible. Returns true if found
    public boolean isInvisible(String locator) {
        return isNotVisibleWithText_base(locator, "", DEFAULT_WAIT_TIMEOUT, false);
    }

    // waits a number of seconds for an element, found by a locator, to be invisible. Returns true if found
    public boolean isInvisibleForSec(String locator, Integer amountOfSeconds) {
        return isNotVisibleWithText_base(locator, "", amountOfSeconds, false);
    }

    // =========================================================   ACTIONS WITH AN ELEMENT   ========================================================= //

    @When("click to {string}")
    // clicks to an element, found by a locator
    public void clickTo(String locator) {
        try {
            // If an element is not presented on the page, wait for it
            WebElement element = waitForIsPresented(locator);
            if (!isVisibleForSec(locator, 1)) scrollToElementIfItExists(locator);
            if (element.getTagName().equalsIgnoreCase("button")) {
//                    scrollToElement(locator);
                waitForIsClickable(locator);
            }
            element.click();
        } catch (ElementClickInterceptedException e) {
            logger.info(e + " in clickTo(): " + locator);
            WebElement element = scrollToElement(locator);
            if (element.getTagName().equalsIgnoreCase("button")) {
                waitForIsClickable(locator);
            }
            element.click();
        } catch (StaleElementReferenceException | NoSuchElementException e) {
            //to deal with StaleElementReferenceException or sometimes with a floating NoSuchElementException: 'no such element: No node with given id found'
            // try some more attempts to get element again
            if (e instanceof NoSuchElementException || e.toString().toLowerCase().contains("No node with given id found".toLowerCase())) {
                logger.info(e + " in clickTo(): " + locator);
                throw e;
            }
        }
    }


    @Given("scroll to element {string}")
    // scrolls to an element, found by a locator
    public WebElement scrollToElement(String locator) {
        WebElement element = waitForIsPresented(locator);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView();", element);
        logger.info("scrolled to the element: " + locator);
        return element;
    }

    @Given("move to element {string}")
    // moves to an element, found by a locator
    public WebElement moveToElement(String locator) {
        WebElement element = waitForIsPresented(locator);
        Actions action = new Actions(driver);
        action.moveToElement(element).perform();
        return element;
    }

    // scrolls to an element, found by a locator, if such element exists
    public void scrollToElementIfItExists(String locator) {
        try {
            scrollToElement(locator);
        } catch (Exception e) {
            logger.info(e + " in scrollToElementIfItExists(). Locator: " + locator);
        }
    }

    // =========================================================   OTHER METHODS   ========================================================= //


    public static By getByObject(String target) {
        if (target.startsWith("name")) {
            String locatorValue = target.split("=")[1];
            return By.name(locatorValue);
        } else if (target.startsWith("xpath")) {
            String locatorValue = target.substring(6);
            return By.xpath(locatorValue);
        } else if (target.startsWith("//")) {
            return By.xpath(target);
        } else if (target.startsWith("id")) {
            String locatorValue = target.split("=")[1];
            return By.id(locatorValue);
        } else if (target.startsWith("linkText")) {
            String locatorValue = target.split("=")[1];
            return By.linkText(locatorValue);
        } else if (target.startsWith("css")) {
            String locatorValue = target.split("=")[1];
            return By.cssSelector(locatorValue);
        } else {
            throw new RuntimeException("Selector must start with 'xpath', 'css', 'name', 'id', 'linkText'. Actual value is: " + target);
        }
    }


}
