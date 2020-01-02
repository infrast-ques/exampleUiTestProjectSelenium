package PageObjects;

import Drivers.GetPropertiesForDriver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HomePage{

    private WebDriver driver;
    private WebDriverWait wait;
    final public String title = "ЦУМ - интернет-магазин одежды, обуви и аксессуаров ведущих мировых брендов";

    @FindBy (css = "div.header__private a")
    private WebElement buttonLogin;

    public HomePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        wait = new WebDriverWait(driver, new GetPropertiesForDriver().getWaitTime());

    }

    public String getTitle(){
        wait.until(ExpectedConditions.urlToBe("https://www.tsum.ru/"));
        return driver.getTitle();
    }

    public HomePage openPage(){
        driver.get(new GetDataForTest().getUrlHomePage());
        //wait.until(ExpectedConditions.titleIs("ЦУМ - интернет-магазин одежды, обуви и аксессуаров ведущих мировых брендов"));
        return new HomePage(driver);
    }

    public LoginPage openLoginPage(){
        buttonLogin.click();
        //wait.until(ExpectedConditions.titleIs("Персональные данные"));
        return new LoginPage(driver);
    }



}
