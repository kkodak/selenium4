package steps;

import com.google.common.net.MediaType;
import io.cucumber.java.After;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lib.WebDriverManager;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.devtools.NetworkInterceptor;
import org.openqa.selenium.remote.http.HttpResponse;
import org.openqa.selenium.remote.http.Route;

import java.util.Optional;

import static org.junit.Assert.assertTrue;
import static org.openqa.selenium.remote.http.Contents.utf8String;

public class InterceptorTest {


    @Given("selenium intercepts {string} message")
    public void selenium_intercepts_message(String str) {
        var driver = WebDriverManager.getWebDriver();
        new NetworkInterceptor(
                driver,
                Route.matching(req -> true)
                        .to(() -> req -> new HttpResponse()
                                .setStatus(200)
                                .addHeader("Content-Type", MediaType.HTML_UTF_8.toString())
                                .setContent(utf8String(str))));

    }

    @When("user navigates to google.com")
    public void user_navigates_to_google_com() {
        WebDriverManager.getWebDriver().get("http://google.com");
    }

    @Then("user see {string} on page")
    public void user_see_on_page(String str) {
        String source = WebDriverManager.getWebDriver().getPageSource();

        assertTrue(source.contains(str));
    }


}
