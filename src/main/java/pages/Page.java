package pages;

import lib.Timeout;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public abstract class Page {

    protected WebDriver webDriver;
    protected WebDriverWait wait;


    public Page(WebDriver webDriver) {
        this.webDriver = webDriver;
        wait = new WebDriverWait(webDriver, Duration.ofSeconds(Timeout.defaultTimeout));
        init();
    }

    abstract public void init();



}
