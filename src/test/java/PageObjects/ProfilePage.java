package PageObjects;

import Drivers.GetPropertiesForDriver;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.junit.MatcherAssert.assertThat;


public class ProfilePage {

    private WebDriver driver;
    private WebDriverWait wait;
    final public String title = "Персональные данные";

    @FindBy(xpath = "//div[@class='personal__menu']//li[last()]")
    private WebElement buttonExit;
    private By profilePageTitle = By.cssSelector("personal.ng-star-inserted div.ui-h1");


    public ProfilePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        wait = new WebDriverWait(driver, new GetPropertiesForDriver().getWaitTime());
    }

    public String getTitle() {
        return driver.findElement(profilePageTitle).getText();
    }

    @Step("Открыть страницу личного кабинета")
    public ProfilePage openPage(){
        driver.get("https://www.tsum.ru/personal/profile/");
        return this;
    }

    @Step("Выйти из личного кабинета")
    public HomePage logout(){
        buttonExit.click();
        HomePage homePage = new HomePage(driver);
        wait.until(ExpectedConditions.titleIs(homePage.title));
        return homePage;
    }

    @Step("Проверить что открыта страница личного кабинета")
    public ProfilePage checkIsProfilePage(){
        try {
            wait.until(ExpectedConditions.titleIs(title));
            assertThat(driver.getTitle(), equalTo(title));
        } catch (Exception e) {
            return null;
        }
        return this;
    }
}
