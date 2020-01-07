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

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.junit.MatcherAssert.assertThat;

public class RegistrationPage {

    private WebDriver driver;
    private WebDriverWait wait;
    final public String title = "Регистрация";
    final public String titleBody = "Ваши предпочтения";
    final public String approveRegistrationNotice = "Успешная регистрация";
    private By noticeMessage = By.cssSelector("div[class*=ng-trigger-noticesAnimation] div");

    public RegistrationPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        wait = new WebDriverWait(driver, new GetPropertiesForDriver().getWaitTime());
    }

    @FindBy(css = "auth-register button[type=submit]")
    private WebElement buttonSubmitRegistration;

    @FindBy(css = "auth-layout input[formcontrolname=email]")
    private WebElement emailField;

    @FindBy(css = "input[formcontrolname=password]")
    private WebElement passwordField;

    By titleBodyLocator = By.cssSelector("div.ui-container div.ui-h2");

    public RegistrationPage registrationNewAccount(String email, String password) {
        wait.until(ExpectedConditions.visibilityOf(emailField));
        String emailArr[] = email.split("@");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yy.hh.mm");
        emailField.sendKeys(emailArr[0] + simpleDateFormat.format(new Date()) + "@" + emailArr[1]);
        passwordField.sendKeys(password);
        buttonSubmitRegistration.click();
        return this;
    }

    public RegistrationPage registrationAccount(String email, String password) {
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

    public RegistrationPage checkIsRegistrationPage(){
        try {
            wait.until(ExpectedConditions.titleIs(title));
            assertThat(driver.getTitle(), equalTo(title));
        } catch (Exception e) {
            return null;
        }
        return this;
    }
    public RegistrationPage checkNextPageAfterRegistrationNewAccount(){
        try {
            wait.until(ExpectedConditions.textToBe(titleBodyLocator, titleBody));
            assertThat(driver.findElement(titleBodyLocator).getText(), equalTo(titleBody));
        } catch (Exception e) {
            return null;
        }
        return this;
    }

}
