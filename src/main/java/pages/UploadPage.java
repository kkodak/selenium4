package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.io.File;

public class UploadPage extends Page {

    @FindBy(id = "file-upload")
    private WebElement uploadInp;

    @FindBy(id = "file-submit")
    private WebElement uploadBtn;

    @FindBy(css = ".example h3")
    private WebElement headerMsg;

    @FindBy(css="#uploaded-files")
    private WebElement uploadedFiles;

    public UploadPage(WebDriver webDriver) {
        super(webDriver);

    }

    @Override
    public void init() {
        PageFactory.initElements(webDriver, this);
    }


    public void uploadFile(String fileName) {
        uploadInp.sendKeys(fileName);
        uploadBtn.click();
    }

    public String getHeaderMsg() {
        return headerMsg.getText();
    }

    public String getUploadedFileName(){
      return  uploadedFiles.getText();
    }
}
