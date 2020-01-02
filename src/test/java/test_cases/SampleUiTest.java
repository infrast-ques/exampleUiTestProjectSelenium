package test_cases;

import Drivers.SetupDriver;
import Drivers.myChromeDriver;
import PageObjects.*;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;


import java.util.ArrayList;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.junit.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SampleUiTest {
    SetupDriver webDriverInterface = new myChromeDriver();
    WebDriver driver;
    HomePage homePage;
    LoginPage loginPage;
    ProfilePage profilePage;
    RegistrationPage registrationPage;


    @BeforeEach
    public void getDriver() {
        driver = webDriverInterface.getWebDriver();
    }

    @AfterEach
    public void dropDriver() {
        webDriverInterface.tearDownWebDriver(driver);
    }

    @Test
    @DisplayName("Проверка заголовка")
    public void checkTsumTitle() {
        homePage = new HomePage(driver)
                .openPage();
        assertThat(homePage.getTitle(), is(homePage.title));
    }

    @Test
    @DisplayName("Проверка авторизации и выхода")
    public void shouldLoginAndLogout() {
        profilePage = new HomePage(driver)
                .openPage()
                .openLoginPage()
                .login(new GetDataForTest().getEmail(), new GetDataForTest().getPassword());
        assertThat(driver.getTitle(), is(profilePage.title));

        homePage = profilePage.logout();
        assertThat(driver.getTitle(), is(homePage.title));
    }

    @Test
    @DisplayName("Авторизация с неверным паролем")
    public void shouldNotLogin() {
        loginPage = new LoginPage(driver);
        profilePage = loginPage
                .openPage()
                .login(new GetDataForTest().getEmailUnvalid(), new GetDataForTest().getPasswordUnvalid());
        assertThat(driver.getTitle(), equalTo(loginPage.title));

        ArrayList<String> errorText = loginPage
                .getNoticeText();
        assertThat(errorText, hasItem(containsString(loginPage.noticeIncorrectPassword)));
    }

    @Test
    @DisplayName("Регистрация нового аккаунта")
    public void checkRegisterNewAccount() {
        loginPage = new LoginPage(driver);
        registrationPage = loginPage
                .openPage()
                .registerNewAccount(new GetDataForTest().getEmail(), new GetDataForTest().getPassword());
        assertThat(driver.getTitle(), containsString(registrationPage.title));

        ArrayList<String> approveRegistrationNotices = registrationPage.getNoticeText();
        assertEquals(1, approveRegistrationNotices.size());
        assertThat(approveRegistrationNotices, hasItem(containsString(registrationPage.approveRegistrationNotice)));
    }

    @Test
    @DisplayName("Проверка ФЛК на форме регистрации нового аккаунта")
    public void checkFLCRegisterFields() {
        loginPage = new LoginPage(driver);
        ArrayList<String> errorNoticeRegistrationText = loginPage
                .openPage()
                .registerWhichFails("mail.mail", "1234567")
                .getNoticeText();
               assertThat(errorNoticeRegistrationText, containsInAnyOrder(loginPage.noticeIncorrectEmail, loginPage.noticePasswordMustBeHaveMoreThen8Symbols));

    }

    @Test
    @DisplayName("Создание аккаунта на ранее зарегистрированную почту")
    public void checkFLCRegistrationAtAlreadyUsedEmail() {
        loginPage = new LoginPage(driver);
        ArrayList<String> noticeRegistrationText = loginPage
                .openPage()
                .registerWhichFails(new GetDataForTest().getEmail(), new GetDataForTest().getPassword())
                .getNoticeText();
        assertEquals(1, noticeRegistrationText.size());
        assertThat(noticeRegistrationText, contains("Пользователь с таким email уже существует."));
        assertThat(loginPage.getTitle(), is("Личный кабинет"));
    }

    @Disabled
    @Test
    @DisplayName("Test")
    public void test() {
        driver.get("https://www.tsum.ru/login/");
        System.out.println(driver.getTitle());
        driver.get("https://www.tsum.ru/registration/");
        System.out.println(driver.getTitle());
    }

}
