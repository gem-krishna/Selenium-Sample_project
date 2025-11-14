package stepdefinitions.ui;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.And;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.time.Duration;

import static org.junit.Assert.*;

public class LoginStepDefinitions {

    private WebDriver driver;
    private WebDriverWait wait;

    @Before
    public void setUp() {
        // Setup ChromeDriver - make sure chromedriver is in your PATH or use WebDriverManager
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        options.addArguments("--disable-blink-features=AutomationControlled");
        driver = new ChromeDriver(options);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Given("I open the browser")
    public void iOpenTheBrowser() {
        // Browser is already opened in @Before hook
        assertNotNull("WebDriver should be initialized", driver);
    }

    @When("I navigate to {string}")
    public void iNavigateTo(String url) {
        driver.get(url);
    }

    @Then("the page title should be {string}")
    public void thePageTitleShouldBe(String expectedTitle) {
        wait.until(ExpectedConditions.titleIs(expectedTitle));
        String actualTitle = driver.getTitle();
        assertEquals("Page title should match", expectedTitle, actualTitle);
    }

    @And("I enter {string} on element having id {string}")
    public void iEnterOnElementHavingId(String text, String elementId) {
        WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(By.id(elementId)));
        element.clear();
        element.sendKeys(text);
    }

    @And("I click on submit button")
    public void iClickOnSubmitButton() {
        // Assuming submit button has type="submit" or button with specific text
        WebElement submitButton = wait.until(
            ExpectedConditions.elementToBeClickable(By.cssSelector("button[type='submit']"))
        );
        ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", submitButton);
        submitButton.click();
    }

    @Then("I should see a success message")
    public void iShouldSeeASuccessMessage() {
        // Wait for success message to appear - adjust selector based on actual page structure
        WebElement successMessage = wait.until(
            ExpectedConditions.presenceOfElementLocated(
                By.xpath("//*[contains(@class, 'success') or contains(@class, 'alert-success') or contains(text(), 'success')]")
            )
        );
        assertTrue("Success message should be displayed", successMessage.isDisplayed());
    }
}
