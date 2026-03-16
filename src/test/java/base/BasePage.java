package base;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class BasePage {

    protected WebDriver driver;

    public BasePage(WebDriver driver) {
        this.driver = driver;
    }

    protected WebElement getElement(By locator) {
        return driver.findElement(locator);
    }

    protected void click(By locator) {
        getElement(locator).click();
    }

    protected void type(By locator, String text) {
        getElement(locator).clear();
        getElement(locator).sendKeys(text);
    }

    protected String getText(By locator) {
        return getElement(locator).getText();
    }
}