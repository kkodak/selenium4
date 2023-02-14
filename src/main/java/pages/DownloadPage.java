package pages;

import lib.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class DownloadPage extends Page {


    /**
     * Downloads file clicking at <linkName>
     *
     * @return file path
     */
    public String downloadFile(String linkName) throws InterruptedException {
        String path = WebDriverManager.getDownloadDirPath();
        File dir = new File(path);
        final List<String> entry = Arrays.asList(dir.list());

        //click at link to start downloading
        webDriver.findElement(By.linkText(linkName)).click();
        //wait till new file appears
        wait.until((WebDriver w) -> dir.list().length > entry.size());

        //wait new file finish downloading
        String fileName = Arrays.stream(dir.list()).sequential()
                .filter(it -> !entry.contains(it)).collect(Collectors.toList()).get(0);
        while (fileName.contains(".tmp") || fileName.contains(".crdownload")) //wait file download is finished
        {
            for (int i = 0; i < 5 && !new File(path + fileName).exists(); i++)
                Thread.sleep(500);
            //get correct file Name
            fileName = Arrays.stream(dir.list()).sequential()
                    .filter(it -> !entry.contains(it)).collect(Collectors.toList()).get(0);
        }

        for (int i = 0; i < 5 && !
                new File(path + fileName).exists() &&
                new File(path + fileName).getTotalSpace() > 0;
             i++)
            Thread.sleep(500);

        return (path + File.separator + fileName);
    }

    public DownloadPage(WebDriver webDriver) {
        super(webDriver);
    }

    @Override
    public void init() {
        PageFactory.initElements(webDriver, this);
    }
}
