import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.util.concurrent.TimeUnit;

public class SampleTest2 {
    private static final ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    public static final String URL = "@hub-cloud.browserstack.com/wd/hub";

    @Test(dataProvider = "capabilities", dataProviderClass = DataProviderClass.class)
    public void login(String name, String os, String os_Version, String browser, String browserVersion) throws IOException {
        {

            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            DesiredCapabilities capability = new DesiredCapabilities();
            capability.setCapability("os", os);
            capability.setCapability("os_version", os_Version);
            capability.setCapability("browser", browser);
            capability.setCapability("browser_version", browserVersion);
            capability.setCapability("build", "cross_browser");
            capability.setCapability("name", "Thread 2 : " + timestamp);

            try {

                // driver = new RemoteWebDriver(new URL("https://" + username + ":" + auth_key + URL), capability);
                driver.set(new RemoteWebDriver(new URL("https://" + SampleTest.USERNAME + ":" + SampleTest.PASSWORD + URL), capability));
            } catch (Exception e) {

                System.out.println("Invalid grid URL" + e.getMessage());
            }

            try {
                driver.get().manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
                driver.get().get("https://www.google.com/");
                System.out.println("Thread 2 : I am at browserstack page 2");
                // driver.get().quit();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }


    @AfterMethod
    public void tearDown() throws Exception {
        if (driver.get() != null) {
            driver.get().quit();
        }
    }


}