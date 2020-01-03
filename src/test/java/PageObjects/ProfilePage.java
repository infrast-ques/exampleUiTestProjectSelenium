package PageObjects;

import Drivers.GetPropertiesForDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

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

    public ProfilePage openPage(){
        driver.get("https://www.tsum.ru/personal/profile/");
        return this;
    }

    public HomePage logout(){
        buttonExit.click();
        HomePage homePage = new HomePage(driver);
        wait.until(ExpectedConditions.titleIs(homePage.title));
        return homePage;
    }

}
