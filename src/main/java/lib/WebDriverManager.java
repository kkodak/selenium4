package lib;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.LocalFileDetector;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;


/**
 * @author Kateryna
 * Class creates instances of webdrivers, manage access to them from threads and close them eventually
 */
public class WebDriverManager {

    private static final Logger LOGGER =
            LoggerFactory.getLogger(WebDriverManager.class);

    final private static String CONFIG_FILE = "config.properties";


    public static String getDownloadDirPath() {
        return downloadDirPath;
    }

    /**
     * Project directory used for WebDriver download storing
     */
    final private static String downloadDirPath = new File("").getAbsolutePath() + File.separator + "files"
            + File.separator + "download";
    private static final ThreadLocal<WebDriver> webDriver = new InheritableThreadLocal<>();
    /**
     * Selenium Grid URL
     */
    private static String gridHost;
    /**
     * Screen which is made after test, will be attached to test if it fails
     */
    private static byte[] screen;

    /**
     * Gets instance of webdriver for current thread. Starts new if it doesn't
     * exist.
     *
     * @return Webdriver instance
     */
    public static WebDriver getWebDriver() {
        if (webDriver.get() == null || ((RemoteWebDriver) webDriver.get()).getSessionId() == null) {
            setProperties();
            if (gridHost == null || gridHost.equals("none")) {
                webDriver.set(WebDriverManager.getChromeDriver());
            } else {
                try {
                    webDriver.set(WebDriverManager.getGridChromeDriver());
                } catch (MalformedURLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
        return webDriver.get();
    }

    /**
     * Creates new browser session in same test. It can be used for scenarios with concurrent sessions.
     *
     * @return Webdriver instance
     */
    public static WebDriver getNewSession() {
        if (gridHost == null || gridHost.equals("none")) {
            return WebDriverManager.getChromeDriver();
        } else {
            try {
                return WebDriverManager.getGridChromeDriver();
            } catch (MalformedURLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * Returns list of cookies of browser session
     *
     * @return list of Cookies
     */
    public static List<Cookie> getCookies() {
        List<Cookie> appachCookies = new ArrayList<>();
        getWebDriver().manage().getCookies().forEach(cookie ->
                appachCookies.add(new Cookie(cookie.getName(), cookie.getValue()))
        );
        return appachCookies;
    }

    /**
     * Closes browser windows and kills browser process.
     */
    public static void driverQuite() {
        getWebDriver().quit();
        WebDriverManager.webDriver.remove();
        LOGGER.info("Webdriver is closed");

    }

    private static ChromeOptions getChromeOptions() {
        HashMap<String, Object> chromePrefs = new HashMap<>();
        chromePrefs.put("download.prompt_for_download", false);
        chromePrefs.put("download.default_directory", downloadDirPath);
        chromePrefs.put("profile.default_content_setting_values.dialogs", 1);
        chromePrefs.put("profile.default_content_settings.dialogs", 1);
        ChromeOptions options = new ChromeOptions();
        options.setExperimentalOption("prefs", chromePrefs);
        options.addArguments("--test-type");
        options.setAcceptInsecureCerts(true);


        return options;
    }

    private static WebDriver getChromeDriver() {
        io.github.bonigarcia.wdm.WebDriverManager.chromedriver().setup();

        ChromeOptions capabilities = getChromeOptions();
        LoggingPreferences prefs = new LoggingPreferences();

        prefs.enable(LogType.BROWSER, Level.SEVERE);
        WebDriver driver = new ChromeDriver(capabilities);
        LOGGER.info("Chrome driver is started");
        return driver;
    }

    private static WebDriver getGridChromeDriver() throws MalformedURLException {
        RemoteWebDriver driver = null;
        ChromeOptions capabilities = getChromeOptions();
        LoggingPreferences prefs = new LoggingPreferences();
        prefs.enable(LogType.BROWSER, Level.OFF);
        LOGGER.info(String.format("Grid URL: [%s]", gridHost));
        for (int i = 0; i < 10 && driver == null; i++) {
            try {
                driver = new RemoteWebDriver(new URL(gridHost), capabilities);
                LOGGER.info("Remote driver defined");
            } catch (WebDriverException e) {
                if (i == 9) {
                    throw e;
                }
                LOGGER.info("Selenium node is not reachable.");
            }
        }
        driver.setFileDetector(new LocalFileDetector());
        return driver;
    }

    public static synchronized void setProperties() {
        /* Set logger */
        System.setProperty("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.Jdk14Logger");

        Properties properties = new Properties();
        try {
            properties.load(WebDriverManager.class.getClassLoader().getResourceAsStream(CONFIG_FILE));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        gridHost = properties.getProperty("grid");
    }

    public synchronized static void makeScreen() {
        TakesScreenshot takesScreenshot = (TakesScreenshot) WebDriverManager.getWebDriver();
        screen = takesScreenshot.getScreenshotAs(OutputType.BYTES);
    }

    public static byte[] getScreen() {
        return screen;
    }

}
