package libs;

import com.google.common.net.MediaType;
import com.sun.net.httpserver.HttpServer;
import lib.webdriver.WebDriverCreator;
import org.junit.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.devtools.NetworkInterceptor;
import org.openqa.selenium.net.PortProber;
import org.openqa.selenium.remote.http.HttpResponse;
import org.openqa.selenium.remote.http.Route;

import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.openqa.selenium.remote.http.Contents.utf8String;

public class SmokeTest extends TestBase{

    @Test
    public void testInterceptor() throws InterruptedException {
        var driver=WebDriverCreator.getWebDriver();
        NetworkInterceptor interceptor = new NetworkInterceptor(
                driver,
                Route.matching(req -> true)
                        .to(() -> req -> new HttpResponse()
                                .setStatus(200)
                                .addHeader("Content-Type", MediaType.HTML_UTF_8.toString())
                                .setContent(utf8String("Creamy, delicious cheese!"))));

        driver.get("https://example-sausages-site.com");

        String source = driver.getPageSource();

        assertTrue(source.contains("delicious cheese!"));
        Thread.sleep(5000);
    }

    @Test
    public void testForm() throws InterruptedException {
        WebDriverCreator.getWebDriver().get(serverUrl);
        Thread.sleep(5000);
    }


}
