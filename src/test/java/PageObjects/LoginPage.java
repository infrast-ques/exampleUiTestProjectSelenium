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

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.junit.MatcherAssert.assertThat;

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


    @FindBy(css = "div.ui-h2")
    private WebElement pageTitle;

    private By noticeMessage = By.cssSelector("div[class*=ng-trigger-noticesAnimation] div");

    @Step("Открыть страницу авторизации")
    public LoginPage openPage() {
        driver.get("https://www.tsum.ru/login/");
        wait.until(ExpectedConditions.visibilityOf(emailField));
        return this;
    }

    @Step("Авторизироваться с почтой {email} и паролем {password}")
    public ProfilePage login(String email, String password) {
        wait.until(ExpectedConditions.elementToBeClickable(emailField));
        emailField.sendKeys(email);
        passwordField.sendKeys(password);
        buttonSubmitLogin.click();
        return new ProfilePage(driver);
    }


    @Step("Переключиться на форму регистрации")
    public RegistrationPage switchToRegistrationForm(){
        wait.until(ExpectedConditions.visibilityOf(emailField));
        buttonTurnToRegistration.click();
        return new RegistrationPage(driver);
    }

    @Step("Получить текст уведомлений")
    public ArrayList<String> getNoticeText() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(noticeMessage));
        List<WebElement> webElements = driver.findElements(noticeMessage);
        ArrayList<String> noticeTextList = new ArrayList<>();
        for (WebElement element : webElements) {
            noticeTextList.add(element.getText());
        }
        return noticeTextList;
    }

    @Step("Проверить что открыта страница авторизации")
    public LoginPage checkIsLoginPagePage(){
        try {
            wait.until(ExpectedConditions.titleIs(title));
            assertThat(driver.getTitle(), equalTo(title));
        } catch (Exception e) {
            return null;
        }
        return this;
    }

}
