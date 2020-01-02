package Drivers;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.stqa.selenium.factory.ThreadLocalSingleWebDriverPool;
import ru.stqa.selenium.factory.WebDriverPool;

import java.util.concurrent.TimeUnit;

public class myChromeDriver implements SetupDriver {
    GetPropertiesForDriver getPropertiesForDriver = new GetPropertiesForDriver();

    public WebDriver getWebDriver() {
        return getDriver();
    }

    public WebDriver getDriver(){
        WebDriver driver = WebDriverPool.DEFAULT.getDriver(getCapabilities()); //webDriver.getCapabilities()
        driver.manage().timeouts().implicitlyWait(getPropertiesForDriver.getImplicitlyWaitTime(), TimeUnit.SECONDS);
        driver.manage().window().maximize();
        return driver;
    }

    private WebDriver setConfigChromeDriver() {
        WebDriver driver;
        System.setProperty("webdriver.chrome.driver", getPropertiesForDriver.getDirChromeDriver());
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(getPropertiesForDriver.getImplicitlyWaitTime(), TimeUnit.SECONDS);
        //driver.manage().window().maximize();
        return driver;
    }
    public Capabilities getCapabilities(){
        System.setProperty("webdriver.chrome.driver", getPropertiesForDriver.getDirChromeDriver());
        DesiredCapabilities capabilities = DesiredCapabilities.chrome();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("test-type");
        capabilities.setCapability("chrome.binary", getPropertiesForDriver.getDirChromeDriver());
        capabilities.setCapability(ChromeOptions.CAPABILITY, options);
        return capabilities;
    }

    public void tearDownWebDriver(WebDriver driver) {
        WebDriverPool.DEFAULT.dismissDriver(driver);
//        driver.close();
//        driver.quit();
    }
}
