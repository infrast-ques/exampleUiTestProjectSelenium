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

    final public String title = "Онлайн каталог интернет магазина ЦУМ - Вход";
    final public String titleRegistration = "Регистрация";
    final public String noticeIncorrectPassword = "Неверный логин или пароль";
    final public String noticeIncorrectEmail = "Указан некорректный email";
    final public String noticeEmailIsAlreadyInUse = "Пользователь с таким email уже существует.";
    final public String noticePasswordMustBeHaveMoreThen8Symbols = "Пароль должен быть не менее 8 символов длиной";

    @FindBy(css = "div.ui-container p.first")
    private WebElement buttonTurnToAuthorization;

    @FindBy(css = "div.ui-container p.last")
    private WebElement buttonTurnToRegistration;

    @FindBy(css = "auth-layout input[formcontrolname=email]")
    private WebElement emailField;

    @FindBy(css = "input[formcontrolname=password]")
    private WebElement passwordField;

    @FindBy(css = "auth-login button[type=submit]")
    private WebElement buttonSubmitLogin;

    @FindBy(css = "auth-register button[type=submit]")
    private WebElement buttonSubmitRegistration;

    @FindBy(css = "div.ui-h2")
    private WebElement pageTitle;

    private By noticeMessage = By.cssSelector("div[class*=ng-trigger-noticesAnimation] div");


    public LoginPage openPage() {
        driver.get("https://www.tsum.ru/login/");
        wait.until(ExpectedConditions.visibilityOf(emailField));
        return this;
    }

    public ProfilePage login(String email, String password) {
        wait.until(ExpectedConditions.elementToBeClickable(emailField));
        emailField.sendKeys(email);
        passwordField.sendKeys(password);
        buttonSubmitLogin.click();
        return new ProfilePage(driver);
    }

    public RegistrationPage registrationNewAccount(String email, String password) {
        wait.until(ExpectedConditions.visibilityOf(emailField));
        String emailArr[] = email.split("@");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yy.hh.mm");
        emailField.sendKeys(emailArr[0] + simpleDateFormat.format(new Date()) + "@" + emailArr[1]);
        passwordField.sendKeys(password);
        buttonSubmitRegistration.click();
        return new RegistrationPage(driver);
    }

    public LoginPage switchToRegistrationForm(){
        wait.until(ExpectedConditions.visibilityOf(emailField));
        buttonTurnToRegistration.click();
        return this;
    }


    public LoginPage registrationAccount(String email, String password) {
        wait.until(ExpectedConditions.visibilityOf(emailField));
        emailField.sendKeys(email);
        passwordField.sendKeys(password);
        buttonSubmitRegistration.click();
        return this;
    }

    public ArrayList<String> getNoticeText() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(noticeMessage));
        List<WebElement> webElements = driver.findElements(noticeMessage);
        ArrayList<String> noticeTextList = new ArrayList<>();
        for (WebElement element : webElements) {
            noticeTextList.add(element.getText());
        }
        return noticeTextList;
    }

    public String getTitle() {
        return pageTitle.getText();
    }


}
