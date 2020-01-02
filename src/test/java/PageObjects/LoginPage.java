package PageObjects;

import Drivers.GetPropertiesForDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class LoginPage {

    private WebDriver driver;
    private WebDriverWait wait;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        wait = new WebDriverWait(driver, new GetPropertiesForDriver().getWaitTime());
    }

    @FindBy(css = "div.ui-container p.first")
    private WebElement buttonTurnToAuthorization;

    @FindBy(css = "div.ui-container p.last")
    private WebElement buttonTurnToRegistration;

    @FindBy(css = "input[formcontrolname=email]")
    private WebElement emailField;

    @FindBy(css = "input[formcontrolname=password]")
    private WebElement passwordField;

    @FindBy(css = "auth-login button[type=submit]")
    private WebElement buttonSubmitLogin;

    @FindBy(css = "auth-register button[type=submit]")
    private WebElement buttonSubmitRegistration;

    private By noticeMessage = By.cssSelector("div[class*=ng-trigger-noticesAnimation] div");


    public LoginPage openPage() {
        driver.get("https://www.tsum.ru/login/");
        return new LoginPage(driver);
    }

    public ProfilePage login(String email, String password) {
        wait.until(ExpectedConditions.visibilityOf(emailField));
        emailField.sendKeys(email);
        passwordField.sendKeys(password);
        buttonSubmitLogin.click();
        return new ProfilePage(driver);
    }

    public LoginPage loginWhichFails(String email, String password) {
        wait.until(ExpectedConditions.visibilityOf(emailField));
        emailField.sendKeys(email);
        passwordField.sendKeys(password);
        buttonSubmitLogin.click();
        return this;
    }

    public LoginPage register(String email, String password) {
        wait.until(ExpectedConditions.visibilityOf(emailField));
        buttonTurnToRegistration.click();
        String emailArr[] = email.split("@");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yy.hh.mm");
        emailField.sendKeys(emailArr[0] + simpleDateFormat.format(new Date()) + "@" + emailArr[1]);
        passwordField.sendKeys(password);
        buttonSubmitRegistration.click();
        return new LoginPage(driver);
    }
    public LoginPage registerWhichFails(String email, String password) {
        wait.until(ExpectedConditions.visibilityOf(emailField));
        buttonTurnToRegistration.click();
        emailField.sendKeys(email);
        passwordField.sendKeys(password);
        buttonSubmitRegistration.click();
        return new LoginPage(driver);
    }

    public ArrayList<String> getNoticeText() {
        ExpectedConditions.visibilityOfElementLocated(noticeMessage);
        List<WebElement> webElements = driver.findElements(noticeMessage);
        ArrayList<String> noticeTextList = new ArrayList<>();
        for(WebElement element : webElements){
            noticeTextList.add(element.getText());
            System.out.println(element.getText());
        }
        return noticeTextList;
    }


}
