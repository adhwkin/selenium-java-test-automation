package pages;

import base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LoginPage extends BasePage {


    public LoginPage(WebDriver driver) {
        super(driver);
    }
    private static final Logger logger =
            LogManager.getLogger(LoginPage.class);

    By username = By.id("username");
    By password = By.id("password");
    By loginBtn = By.cssSelector("button[type='submit']");
    By flashMessage = By.id("flash");

    public void login(String user, String pass) {

        logger.info("Entering username");
        type(username, user);

        logger.info("Entering password");
        type(password, pass);

        logger.info("Clicking login button");
        click(loginBtn);
    }

    public String getFlashMessage() {
        return getText(flashMessage);
    }

    public String getMessage() {
        String message = driver.findElement(flashMessage).getText();

        if (message.contains("success")) {
            System.out.println("Login successful");
        }
        return message;
    }
}