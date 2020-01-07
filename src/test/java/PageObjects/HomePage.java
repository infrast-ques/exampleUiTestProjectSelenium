package PageObjects;

import Drivers.GetPropertiesForDriver;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.junit.MatcherAssert.assertThat;

public class HomePage {

    private WebDriver driver;
    private WebDriverWait wait;
    final public String title = "ЦУМ - интернет-магазин одежды, обуви и аксессуаров ведущих мировых брендов";

    @FindBy(css = "div.header__private a")
    private WebElement buttonLogin;

    public HomePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        wait = new WebDriverWait(driver, new GetPropertiesForDriver().getWaitTime());

    }

    @Step("Открыть домашнюю страницу")
    public HomePage openPage() {
        driver.get(new GetDataForTest().getUrlHomePage());
        return this;
    }

    @Step("Нажать кнопку \"Личный кабинет\"")
    public LoginPage openLoginPage() {
        wait.until(ExpectedConditions.elementToBeClickable(buttonLogin));
        buttonLogin.click();
        return new LoginPage(driver);
    }

    @Step("Проверить что открыта домашняя страница")
    public HomePage checkIsHomePage() {
        try {
            wait.until(ExpectedConditions.titleIs(title));
            assertThat(driver.getTitle(), equalTo(title));
        } catch (Exception e) {
            return null;
        }
        return this;
    }


}
