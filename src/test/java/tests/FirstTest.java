package tests;

import base.BaseTest;
import org.testng.annotations.Test;
import pages.LoginPage;
import org.testng.Assert;

public class FirstTest extends BaseTest {

    @Test
    public void loginTest() {

        LoginPage loginPage = new LoginPage(getDriver());
        loginPage.login("tomsmith", "SuperSecretPassword!");
        String message = loginPage.getFlashMessage();
        Assert.assertTrue(message.contains("You logged into a secure are"));

    }

    @Test
    public void invalidLoginTest() {

        getDriver().get("https://the-internet.herokuapp.com/login");

        LoginPage loginPage = new LoginPage(getDriver());
        loginPage.login("tomsmith", "wrongPassword");

        String message = loginPage.getFlashMessage();

        Assert.assertTrue(message.contains("Your password is invalid!"));

    }
}