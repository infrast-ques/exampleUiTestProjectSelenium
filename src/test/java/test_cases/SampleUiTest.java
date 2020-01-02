package test_cases;

import Drivers.SetupDriver;
import Drivers.myChromeDriver;
import PageObjects.GetDataForTest;
import PageObjects.HomePage;
import PageObjects.LoginPage;
import PageObjects.ProfilePage;
import org.junit.Assert;
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
        assertThat(homePage.getTitle(), is("ЦУМ - интернет-магазин одежды, обуви и аксессуаров ведущих мировых брендов"));
    }

    @Test
    @DisplayName("Проверка авторизации и выхода")
    public void shouldLoginAndLogout() {
        profilePage = new HomePage(driver)
                .openPage()
                .clickButtonLoginPage()
                .login(new GetDataForTest().getEmail(), new GetDataForTest().getPassword());
        assertThat(profilePage.getTitle(), is("Личный кабинет"));

        homePage = profilePage.logout();
        assertThat(homePage.getTitle(), is("ЦУМ - интернет-магазин одежды, обуви и аксессуаров ведущих мировых брендов"));
    }

    @Test
    @DisplayName("Авторизация с неверным паролем")
    public void shouldNotLogin() {
        ArrayList<String> errorText = new LoginPage(driver)
                .openPage()
                .loginWhichFails(new GetDataForTest().getEmailUnvalid(), new GetDataForTest().getPasswordUnvalid())
                .getNoticeText();
        assertThat(errorText, hasItem(containsString("Неверный логин или пароль")));
    }

    @Test
    @DisplayName("Регистрация нового аккаунта")
    public void checkRegisterNewAccount() {
        ArrayList<String> approveRegistrationText = new LoginPage(driver)
                .openPage()
                .register(new GetDataForTest().getEmail(), new GetDataForTest().getPassword())
                .getNoticeText();
        assertEquals(1, approveRegistrationText.size());
        assertThat(approveRegistrationText, hasItem(containsString("Успешная регистрация")));

        String profilePageTitle = new ProfilePage(driver)
                .openPage()
                .getTitle();
        assertThat(profilePageTitle, is("Личный кабинет"));
    }

    @Test
    @DisplayName("Проверка ФЛК на форме регистрации нового аккаунта")
    public void checkFLCRegisterFields() {
        ArrayList<String> errorNoticeRegistrationText = new LoginPage(driver)
                .openPage()
                .registerWhichFails("mail.mail", "1234567")
                .getNoticeText();
        assertThat(errorNoticeRegistrationText, contains("Указан некорректный email", "Пароль должен быть не менее 8 символов длиной"));
    }

    @Test
    @DisplayName("Создание аккаунта на ранее зарегистрированную почту")
    public void checkFLCRegistrationAtAlreadyUsedEmail() {
        ArrayList<String> noticeRegistrationText = new LoginPage(driver)
                .openPage()
                .registerWhichFails(new GetDataForTest().getEmail(), new GetDataForTest().getPassword())
                .getNoticeText();
        assertEquals(1, noticeRegistrationText.size());
        assertThat(noticeRegistrationText, contains("Пользователь с таким email уже существует."));
    }

    @Disabled
    @Test
    @DisplayName("Test")
    public void test() {
        homePage = new HomePage(driver)
                .openPage();
        driver.findElement(By.cssSelector("div.header__private a")).click();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }





        /*
    @Test
    @Disabled("test not yet ready")
    // @DisplayName("Check google title, expect result is \"Google\"")
    public void testShouldCheckGoogleTitle() {
        System.out.println("huiu");
        Assert.assertFalse(true);
        //driver.get("https://www.google.ru/");
        //assertEquals("Google", driver.getTitle());


        //CustomMatcher2 customMatcher2 = new CustomMatcher2();
        //customMatcher2.title =
        //       assertThat(driver.getTitle(), customMatcher2.getTitle());

    }*/


}
