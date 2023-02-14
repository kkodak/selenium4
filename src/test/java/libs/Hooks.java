package libs;

import io.cucumber.java.After;
import io.cucumber.java.Scenario;
import lib.WebDriverManager;

public class Hooks {


    @After
    public void afterScenario(Scenario scenario) {
        if (scenario.isFailed()) {
            byte[] screenshot = WebDriverManager.makeScreen();
            scenario.attach(screenshot, "image/png", "name");
        }
        WebDriverManager.driverQuite();

    }

}
