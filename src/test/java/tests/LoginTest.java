package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.LoginPage;
import utils.ExcelUtils;

public class LoginTest extends BaseTest {

    //LoginPage loginPage;   // declare object

    @Test(dataProvider="loginData")
    public void loginTest(String username, String password, String expectedResult) {

        LoginPage loginPage = new LoginPage(getDriver());
        System.out.println("Running on Thread: " + Thread.currentThread().getId());


        loginPage.login(username, password);

        String message = loginPage.getMessage();

        if (expectedResult.equals("success")) {
            Assert.assertTrue(message.contains("You logged into a secure area"));
        } else {
            Assert.assertTrue(message.contains("Your username is invalid") ||
                    message.contains("Your password is invalid"));
        }
    }

     @DataProvider(name = "loginData", parallel = true)
    public Object[][] getData() {

        String path = "src/test/resources/testdata/LoginData.xlsx";
        return ExcelUtils.getExcelData(path, "LoginData");
    }
}