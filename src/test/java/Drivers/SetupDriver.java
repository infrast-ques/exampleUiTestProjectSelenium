package Drivers;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;


public interface SetupDriver {

    WebDriver getWebDriver();

    void tearDownWebDriver(WebDriver driver);

    Capabilities getCapabilities();


}

