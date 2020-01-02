package PageObjects;

import Drivers.GetPropertiesForDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;

public class RegistrationPage {

    private WebDriver driver;
    private WebDriverWait wait;
    final public String title = "Регистрация";
    final public String approveRegistrationNotice = "Успешная регистрация";
    private By noticeMessage = By.cssSelector("div[class*=ng-trigger-noticesAnimation] div");

    public RegistrationPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        wait = new WebDriverWait(driver, new GetPropertiesForDriver().getWaitTime());
        System.out.println(driver.getTitle());
    }

    public ArrayList<String> getNoticeText() {
        ExpectedConditions.visibilityOfElementLocated(noticeMessage);
        List<WebElement> webElements = driver.findElements(noticeMessage);
        ArrayList<String> noticeTextList = new ArrayList<>();
        for (WebElement element : webElements) {
            noticeTextList.add(element.getText());
        }
        return noticeTextList;
    }

}
