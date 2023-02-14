package steps;

import io.cucumber.java.en.Given;
import lib.WebDriverManager;

public class CommonSteps {

    @Given("^Open \"([^\"]*)\"?")
    public void openLink(String link) {
        WebDriverManager.getWebDriver().get(link);
    }
}
