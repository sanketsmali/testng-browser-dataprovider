import capsUtils.Caps;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.annotations.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Timestamp;
import java.util.concurrent.TimeUnit;

public class SampleTest {
    public static final String  USERNAME= "<username>";
    public static final String PASSWORD = "<password>";
    private static final ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    public static final String URL= "@hub-cloud.browserstack.com/wd/hub";



    @Test(dataProvider= "capabilities" , dataProviderClass=DataProviderClass.class)
    public void login(String name,String os,String os_Version,String browser,String browserVersion) throws IOException {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        DesiredCapabilities capability= new DesiredCapabilities();
        capability.setCapability("os", os);
        capability.setCapability("os_version", os_Version);
        capability.setCapability("browser", browser);
        capability.setCapability("browser_version", browserVersion);
        capability.setCapability("build", "cross_browser");
        capability.setCapability("name", "Thread 1 : "+timestamp);

        try {
            driver.set(new RemoteWebDriver(new URL("https://" + USERNAME + ":" + PASSWORD + URL), capability));
        }

        catch (Exception e) {

            System.out.println("Invalid grid URL" + e.getMessage());
        }

        try
        {
            driver.get().manage().timeouts().implicitlyWait(30,TimeUnit.SECONDS);
            driver.get().get("https://www.google.com/");
            System.out.println("Thread 1 : I am at browserstack page 1");

        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }


    @AfterMethod
    public void tearDown() throws Exception {
        if ( driver.get() != null) {
            driver.get().quit();
        }
    }




}