package lib.rules;


import io.qameta.allure.Attachment;
import lib.webdriver.WebDriverCreator;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

public class ScreenshotOnTestFailedRule implements TestRule {
    private String screenshotName;

    public ScreenshotOnTestFailedRule(String screenshotName) {
        this.screenshotName = screenshotName;
    }

    public Statement apply(final Statement statement, final Description description) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                try {
                    statement.evaluate();
                } catch (Throwable t) {
                    makeScreenshot(screenshotName);
                    throw t;
                }

            }

        };
    }

    @Attachment(value = "{0}", type = "image/png")
    public static byte[] makeScreenshot(String screenshotName) {
        return WebDriverCreator.getScreen();
    }
}
