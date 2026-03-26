package base;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.ITestResult;
import utils.ScreenshotUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.InputStream;
import java.util.Properties;

public class BaseTest {

    protected static final Logger log =
            LogManager.getLogger(BaseTest.class);

    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    private static ThreadLocal<Properties> prop = new ThreadLocal<>();


    public static WebDriver getDriver() {
        WebDriver drv = driver.get();

        if (drv == null) {
            throw new RuntimeException("Driver is NULL for Thread: " + Thread.currentThread().getId());
        }
        return drv;
    }

    public static Properties getProp() {
        return prop.get();
    }

    @BeforeMethod(alwaysRun = true)
    public void setUp() {

        System.out.println(">>> BEFORE METHOD - Thread: " + Thread.currentThread().getId());

        try {
            // Load properties per thread
            Properties properties = new Properties();
            InputStream is = getClass().getClassLoader()
                    .getResourceAsStream("config.properties");

            properties.load(is);
            prop.set(properties);

            String browser = properties.getProperty("browser");

            log.info("Browser: " + browser);

            WebDriver localDriver;

            if (browser.equalsIgnoreCase("chrome")) {
                WebDriverManager.chromedriver().setup();
                localDriver = new ChromeDriver();
            } else {
                throw new RuntimeException("Unsupported browser: " + browser);
            }

            driver.set(localDriver);

            System.out.println("Driver SET: " + localDriver);

            getDriver().manage().window().maximize();
            getDriver().get(properties.getProperty("baseUrl"));

        } catch (Exception e) {
            throw new RuntimeException("Setup failed: " + e.getMessage());
        }
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown(ITestResult result) {

        try {
            if (ITestResult.FAILURE == result.getStatus()) {
                String testName = result.getName();
                ScreenshotUtils.captureScreenshot(getDriver(), testName);
                log.error("Screenshot captured for failed test: " + testName);
            }

        } catch (Exception e) {
            log.error("Error in teardown: " + e.getMessage());
        }

        if (driver.get() != null) {
            getDriver().quit();
            driver.remove();
        }

        prop.remove();
    }
}