package libs;


import com.sun.net.httpserver.HttpServer;
import lib.rules.ScreenshotOnTestFailedRule;
import lib.webdriver.WebDriverCreator;
import org.junit.*;
import org.junit.rules.TestName;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;
import org.openqa.selenium.*;
import org.openqa.selenium.net.PortProber;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import static org.junit.Assert.assertEquals;

/**
 * base class from which all test classes to extend
 *
 * @author Kateryna
 */
public abstract class TestBase {

    protected static HttpServer server;
    protected static String serverUrl;

    public static String downloadDirPath;

    public static org.slf4j.Logger logger = LoggerFactory.getLogger(TestBase.class);

    @Rule
    public ScreenshotOnTestFailedRule screenshotOnTestFailedRule = new ScreenshotOnTestFailedRule(getTestName());

    @Rule
    public TestName name = new TestName();

    @ClassRule
    public static ScreenshotOnTestFailedRule screenshotOnClassFailedRule =
            new ScreenshotOnTestFailedRule("class" + System.currentTimeMillis());

    @ClassRule
    public static TestRule classWatcher = new TestRule() {
        public Statement apply(final Statement base, final Description description) {
            return new Statement() {
                @Override
                public void evaluate() throws Throwable {
                    logger.info(description.getDisplayName() + " class has started");
                    base.evaluate();
                    logger.info(description.getDisplayName() + " class has finished");
                }
            };
        }

    };


    @BeforeClass
    public static void setUp() throws IOException {
        WebDriver driver = WebDriverCreator.getWebDriver();
        downloadDirPath = WebDriverCreator.getDownloadDirPath();
        driver.manage().window().setSize(new Dimension(3840, 2160));
        startHttpServer();
    }

    @After
    public void afterMethod() {
        logger.info(String.format(" ~~~ Test '%s' finished", name.getMethodName()));
        WebDriverCreator.makeScreen();
    }


    @AfterClass
    public static void tearDown() {
        WebDriverCreator.makeScreen();
        WebDriverCreator.driverQuite();
        server.stop(0);
    }

    protected String getTestName() {
        return this.getClass().getSimpleName() + System.currentTimeMillis();
    }

    protected static synchronized String generateName(String prefix) {
        return prefix + System.currentTimeMillis();
    }

    protected static String generateName(String prefix, int numberOfDigits) {
        Random p = new Random();
        int digets = p.ints((int) (Math.pow(10, numberOfDigits - 1)),
                (int) (Math.pow(10, numberOfDigits) - 1)).iterator().nextInt();
        return prefix + digets;
    }

    protected static void startHttpServer() throws IOException {
        Path path = Paths.get("src/test/resources/login.html");
        byte[] bytes = Files.readAllBytes(path);

        var port = PortProber.findFreePort();
        server = HttpServer.create(new InetSocketAddress(port), 0);
        server.setExecutor(null);
        server.createContext("/", httpExchange -> {
            httpExchange.sendResponseHeaders(200, 0);
            try (OutputStream out = httpExchange.getResponseBody()) {
                out.write(bytes);
            }
        });
        server.start();
        serverUrl = String.format("http://localhost:%d", port);

    }

}
