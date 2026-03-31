package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.LoginPage;
import utils.ExcelUtils;

public class LoginTest extends BaseTest {

    //LoginPage loginPage;   // declare object

    @Test(dataProvider="loginData", groups= {"smoke", "regression"}, retryAnalyzer = utils.RetryAnalyzer.class)
    @Test(dataProvider="loginData", groups= {"smoke", "regression"})//retryAnalyzer = utils.RetryAnalyzer.class)
    public void loginTest(String username, String password, String expectedResult) {


        System.out.println("Thread: " + Thread.currentThread().getId());
        System.out.println("Driver in test: " + getDriver());

        LoginPage loginPage = new LoginPage(getDriver());
        System.out.println("Running on Thread: " + Thread.currentThread().getId());


        loginPage.login(username, password);

        String message = loginPage.getMessage();

//        if (expectedResult.equals("success")) {
//            Assert.assertTrue(message.contains("You logged into a secure area"));
//        } else {
//            Assert.assertTrue(message.contains("Your username is invalid") ||
//                    message.contains("Your password is invalid"));
//        }
        // SoftAssert allows test to continue even if one validation fails
        SoftAssert softAssert = new SoftAssert();

        if (expectedResult.trim().equalsIgnoreCase("success")) {

            // Validate success message
            softAssert.assertTrue(
                    message.toLowerCase().contains("secure area"),
                    "Expected success message not found. Actual: " + message
            );

        } else {

            // Validate failure message
            softAssert.assertTrue(
                    message.toLowerCase().contains("invalid"),
                    "Expected error message not found. Actual: " + message
            );
        }

// Additional validation: Page title check
        softAssert.assertTrue(
                getDriver().getTitle().contains("The Internet"),
                "Page title mismatch"
        );

// VERY IMPORTANT → triggers all validations
        softAssert.assertAll();
    }
    @Test(groups = {"sanity"})
    public void basicPageLoadTest() {
        Assert.assertTrue(getDriver().getTitle().contains("The Internet"));
    }
    @Test(groups = {"sanity"})
    public void basicPageLoadTest() {
        Assert.assertTrue(getDriver().getTitle().contains("The Internet"));
    }

     @DataProvider(name = "loginData", parallel = false)
    public Object[][] getData() {

        String path = "src/test/resources/testdata/LoginData.xlsx";
        return ExcelUtils.getExcelData(path, "LoginData");
    }
}