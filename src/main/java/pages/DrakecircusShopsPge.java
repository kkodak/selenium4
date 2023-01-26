package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.ArrayList;
import java.util.List;

public class DrakecircusShopsPge extends Page {

    @FindBy(id = "usercentrics-root")
    private WebElement shadowBlock;

    public DrakecircusShopsPge(WebDriver webDriver) {
        super(webDriver);
    }

    @Override
    public void init() {
        PageFactory.initElements(webDriver, this);
    }

    public void acceptAllCookies() {
        By acceptAllBtn = By.cssSelector("button[data-testid='uc-accept-all-button']");
        wait.until((WebDriver d) -> shadowBlock.getShadowRoot().findElements(acceptAllBtn).size() != 0);
        shadowBlock.getShadowRoot().findElement(acceptAllBtn).click();
    }

    public List<List<String>> getShopsInfo() {
        By articleBy = By.cssSelector("article[data-id]");
        List<List<String>> result = new ArrayList<>();
        for (int i = 0; i < webDriver.findElements(articleBy).size(); i++) {
            List<String> line = new ArrayList<>();
            line.add(webDriver.findElements(articleBy).get(i).findElement(By.tagName("h3")).getText());
            line.add(webDriver.findElements(articleBy).get(i).findElement(By.cssSelector("a")).getAttribute("href"));
            result.add(line);
            executeScript("arguments[0].scrollIntoView(true);", webDriver.findElements(articleBy).get(i));
        }
        return result;
    }
}
