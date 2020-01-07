package test_cases;

import Drivers.SetupDriver;
import Drivers.myChromeDriver;
import PageObjects.*;
import org.junit.jupiter.api.*;
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
    static GetDataForTest getDataForTest;

    @BeforeAll
    public static void beforeMethod() {
        getDataForTest = new GetDataForTest();
    }

    @BeforeEach
    public void getDriver() {
        driver = webDriverInterface.getWebDriver();
    }

    @AfterEach
    public void dropDriver() {
        webDriverInterface.tearDownWebDriver(driver);
    }

    @Test
    @DisplayName("Проверка заголовка главной страницы")
    public void checkTsumTitle() {
       homePage = new HomePage(driver)
                .openPage()
                .checkIsHomePage();
       assertThat(homePage, notNullValue());
    }

    @Test
    @DisplayName("Проверка авторизации и выхода")
    public void shouldLoginAndLogout() {
        homePage = new HomePage(driver)
                .openPage()
                .openLoginPage()
                .login(getDataForTest.getEmail(), getDataForTest.getPassword())
                .checkIsProfilePage()
                .logout()
                .checkIsHomePage();
        assertThat(homePage, notNullValue());
    }

    @Test
    @DisplayName("Авторизация с неверным паролем")
    public void shouldNotLogin() {
        loginPage = new LoginPage(driver);
        profilePage = loginPage
                .openPage()
                .login(getDataForTest.getEmailUnvalid(), getDataForTest.getPasswordUnvalid())
                .checkIsProfilePage();
        assertThat(profilePage, nullValue());

        ArrayList<String> errorText = loginPage
                .getNoticeText();
        assertThat(errorText, hasItem(containsString(loginPage.noticeIncorrectPassword)));
    }

    @Test
    @DisplayName("Регистрация нового аккаунта")
    public void shouldRegisteredNewAccount() {
        loginPage = new LoginPage(driver);
        registrationPage = loginPage
                .openPage()
                .switchToRegistrationForm()
                .registrationNewAccount(getDataForTest.getEmail(), getDataForTest.getPassword())
                .checkNextPageAfterRegistrationNewAccount();
        assertThat(registrationPage, notNullValue());

        ArrayList<String> approveRegistrationNotices = registrationPage.getNoticeText();
        assertEquals(1, approveRegistrationNotices.size());
        assertThat(approveRegistrationNotices, hasItem(containsString(registrationPage.approveRegistrationNotice)));
    }

    @Test
    @DisplayName("Проверка ФЛК на форме регистрации нового аккаунта")
    public void shouldAppearFLCThatDataInRegistrationFieldsIsInvalid() {
        loginPage = new LoginPage(driver);
        registrationPage = loginPage
                .openPage()
                .switchToRegistrationForm()
                .registrationAccount("mail.mail", "1234567")
                .checkNextPageAfterRegistrationNewAccount();
        assertThat(registrationPage, nullValue());

        ArrayList<String> errorNoticeRegistrationText = loginPage.getNoticeText();
        assertThat(errorNoticeRegistrationText,
                containsInAnyOrder(loginPage.noticeIncorrectEmail, loginPage.noticePasswordMustBeHaveMoreThen8Symbols));

    }

    @Test
    @DisplayName("Создание аккаунта на ранее зарегистрированную почту")
    public void shouldAppearFLCThatEmailAlreadyUsed() {
        loginPage = new LoginPage(driver);
        registrationPage = loginPage
                .openPage()
                .switchToRegistrationForm()
                .registrationAccount(getDataForTest.getEmail(), getDataForTest.getPassword())
                .checkNextPageAfterRegistrationNewAccount();
        assertThat(registrationPage, nullValue());

        ArrayList<String> noticeRegistrationText = loginPage.getNoticeText();
        assertEquals(1, noticeRegistrationText.size());
        assertThat(noticeRegistrationText, contains(loginPage.noticeEmailIsAlreadyInUse));
    }

    @Disabled
    @Test
    @DisplayName("Test")
    public void test() {
        driver.get("https://www.tsum.ru/login/");
        System.out.println(driver.getTitle());
        driver.get("https://www.tsum.ru/registration/");
        System.out.println(driver.getTitle());
        registrationPage = new RegistrationPage(driver).registrationNewAccount(getDataForTest.getEmail(), getDataForTest.getPassword());
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(driver.getTitle());

    }

}
