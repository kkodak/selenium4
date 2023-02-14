package steps;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lib.WebDriverManager;
import pages.DownloadPage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.Assert.assertEquals;

public class DownloadFile {
    private DownloadPage downloadPage;
    private String file;
    private JsonObject json;


    @When("Download file {string}")
    public void downloadFile(String arg0) throws InterruptedException {
        downloadPage = new DownloadPage(WebDriverManager.getWebDriver());
        file = downloadPage.downloadFile(arg0);
    }

    @When("Read json from file")
    public void readJsonFromFile() throws IOException {
        String data = new String(Files.readAllBytes(Paths.get(file)));
        JsonElement jsonElement = JsonParser.parseString(data);
        json = jsonElement.getAsJsonObject();

    }

    @Then("Json contains {string} attribute with value {string}")
    public void jsonContainsAttributeWithValue(String key, String value) {
        assertEquals(value, json.get(key).getAsString());
    }
}
