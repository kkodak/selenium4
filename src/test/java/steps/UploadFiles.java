package steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lib.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import pages.UploadPage;

import java.io.File;

import static org.junit.Assert.assertEquals;

public class UploadFiles {
    private UploadPage uploadPage;

    @Given("^Open \"([^\"]*)\"?")
    public void openLink(String link) {
        WebDriverManager.getWebDriver().get(link);
    }

    @When("Upload file {string}")
    public void upload_file(String fileName) {
        String filePath = new File("").getAbsolutePath() + File.separator +
                "files" + File.separator + fileName;
        uploadPage = new UploadPage(WebDriverManager.getWebDriver());
        uploadPage.uploadFile(filePath);
    }

    @Then("Message that {string} file has been upload appears")
    public void messageUploaded(String fileName) {
        assertEquals( "File Uploaded!",uploadPage.getHeaderMsg());
        assertEquals(fileName, uploadPage.getUploadedFileName());
    }
}
