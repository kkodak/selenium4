package steps;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lib.WebDriverManager;
import pages.DrakecircusShopsPge;

import static org.junit.Assert.assertFalse;

public class DrakecircusSteps {
    private DrakecircusShopsPge drakecircusShopsPge;

    @When("Close cookie popup")
    public void closeCookiePopup() {
        drakecircusShopsPge = new DrakecircusShopsPge(WebDriverManager.getWebDriver());
        drakecircusShopsPge.acceptAllCookies();
    }


    @Then("Cookie popup is not shown")
    public void cookiePopupIsStillShown() {
        assertFalse(drakecircusShopsPge.isCookiesPopupShown());
    }
}
