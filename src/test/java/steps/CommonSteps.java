package steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import lib.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WindowType;

public class CommonSteps {

    @Given("^Open \"([^\"]*)\"?")
    public void openLink(String link) {
        WebDriverManager.getWebDriver().get(link);
    }

    @When("^Open \"([^\"]*)\" in new window?")
    public void openLinkNewWindow(String link) {
        WebDriver driver=WebDriverManager.getWebDriver();
        driver.switchTo().newWindow(WindowType.WINDOW);
        driver.get(link);
    }
}
