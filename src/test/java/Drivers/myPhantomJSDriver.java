package Drivers;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.concurrent.TimeUnit;

public class myPhantomJSDriver implements SetupDriver {
GetPropertiesForDriver getPropertiesForDriver = new GetPropertiesForDriver();

public WebDriver getWebDriver() {
    return setConfigPhantomJSDriver();
}

    public Capabilities getCapabilities() {
        System.out.println("не установлен Capabilities для PhantomJS");
        return null;
    }

    private WebDriver setConfigPhantomJSDriver() {
    WebDriver driver;
    System.out.println(getClass().getResource(getPropertiesForDriver.getDirPhantomJSDriver()).getPath());
    System.setProperty("phantomjs.binary.path", getClass().getResource(getPropertiesForDriver.getDirPhantomJSDriver()).getPath());
    driver = new ChromeDriver();
    driver.manage().timeouts().implicitlyWait(getPropertiesForDriver.getImplicitlyWaitTime(), TimeUnit.SECONDS);
    //driver.manage().window().maximize();
    return driver;
}

public void tearDownWebDriver(WebDriver driver) {
    driver.close();
    driver.quit();
}
}
