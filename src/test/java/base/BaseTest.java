package base;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import org.testng.ITestResult;
import utils.ScreenshotUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class BaseTest {

    protected static final Logger log =
            LogManager.getLogger(BaseTest.class);

    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    protected Properties prop;


    public static WebDriver getDriver() {
        return driver.get();
    }

    @BeforeMethod
    public void setUp() throws IOException {

        log.info("Loading config.properties file");

        prop = new Properties();

        FileInputStream fis =
                new FileInputStream("src/main/resources/config.properties");

        prop.load(fis);

        String browser = prop.getProperty("browser");

        log.info("Browser selected: " + browser);

        if (browser.equalsIgnoreCase("chrome")) {

            log.info("Setting up ChromeDriver");

            WebDriverManager.chromedriver().setup();

            WebDriver localDriver = new ChromeDriver();

            driver.set(localDriver);

        } else {
            log.error("Unsupported browser: " + browser);
            throw new RuntimeException("Browser not supported: " + browser);
        }

        log.info("Maximizing browser window");

        getDriver().manage().window().maximize();

        log.info("Opening URL: " + prop.getProperty("baseUrl"));

        getDriver().get(prop.getProperty("baseUrl"));
    }

    @AfterMethod
    public void tearDown(ITestResult result) {

        if (ITestResult.FAILURE == result.getStatus()) {
            String testName = result.getName();
            ScreenshotUtils.captureScreenshot(getDriver(), testName);
            log.error("Test failed. Screenshot captured: " + testName);
        }

        log.info("Closing browser");
        getDriver().quit();
        driver.remove();
    }
}