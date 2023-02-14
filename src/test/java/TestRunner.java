import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = {
                "src/test/resources/features/interceptor.feature",
                "src/test/resources/features/upload.feature",
                "src/test/resources/features/download.feature"
        },
        plugin = {"pretty", "html:target/cucumber-reports.html"}

)

public class TestRunner {
}

