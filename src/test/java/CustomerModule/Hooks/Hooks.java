package CustomerModule.Hooks;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import org.openqa.selenium.WebDriver;
import utilities.DriverManagers;
import utilities.PropertyFileSetup;

import java.io.IOException;

public class Hooks {
    public static WebDriver driver;

    @Before
    public static void setUp() throws IOException {
        DriverManagers.initDriver();
        //WebDriver driver = new RemoteWebDriver(new URL("http://192.168.1.106:4444/wd/hub"),capabilities);

        //ecplicit

        String environment = PropertyFileSetup.props.getProperty("environment");

        switch (environment) {
            case "pre-production":
                String preproductionURL = PropertyFileSetup.props.getProperty("PreproductionURL");
                if (preproductionURL == null) {
                    throw new RuntimeException("Preproduction URL is not set in the properties file.");
                }
                DriverManagers.getDriver().get(preproductionURL);
                break;

            case "staging":
                String stagingURL = PropertyFileSetup.props.getProperty("StagingURL");
                if (stagingURL == null) {
                    throw new RuntimeException("Staging URL is not set in the properties file.");
                }
                DriverManagers.getDriver().get(stagingURL);
                break;

            case "production":
                String productionURL = PropertyFileSetup.props.getProperty("ProductionURL");
                if (productionURL == null) {
                    throw new RuntimeException("Production URL is not set in the properties file.");
                }
                DriverManagers.getDriver().get(productionURL);
                break;

            default:
                throw new RuntimeException("Unknown environment: " + environment);
        }
    }

    @After
    public void tearDown() {
        if(driver != null)
        {
           // driver.close();
        DriverManagers.quitDriver();
            }
    }
//    @AfterTest
//    public void ScreenShot(ITestResult result) {
//        if (result.getStatus() == ITestResult.FAILURE) {
//            System.out.println("Failed!");
//            System.out.println("Taking Failed screenshot......");
//            Screenshot.CaptureScreenShotFromWeb(driver, result.getName());
//        }
//        driver.close();
//    }
}
