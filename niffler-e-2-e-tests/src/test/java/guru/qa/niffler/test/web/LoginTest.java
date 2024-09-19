package guru.qa.niffler.test.web;

import com.codeborne.selenide.Selenide;
import guru.qa.niffler.config.Config;
import guru.qa.niffler.helper.DataGenerator;
import guru.qa.niffler.page.LoginPage;
import guru.qa.niffler.page.MainPage;
import io.qameta.allure.Description;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.executeJavaScript;

class LoginTest {
    private static final Config CFG = Config.getInstance();
    LoginPage loginPage = new LoginPage();
    MainPage mainPage = new MainPage();
    DataGenerator dt = new DataGenerator();
    private static final String existedUser = "severus";
    private static final String existedPassword = "12345";
    private static final String usernameInCorrect = "Us";
    private static final String FAILED_LOGIN_MESSAGE = "Bad credentials";
    private static final String SUCCESFUL_LOGIN_MESSAGE = "History of Spendings";


    @BeforeEach()
    void setUp() {
        Selenide.open(CFG.frontUrl());
        executeJavaScript("return document.readyState").equals("complete");
    }

    @Description("[POS] Отображение главной страницы при успешной авторизации")
    @Test
    void mainPageShouldBeDisplayedAfterSuccessfulLoginTest() {
        loginPage.setUsername(existedUser)
                .setPassword(existedPassword)
                .submitSignUpButton();
        mainPage.checkSuccessLogIn(SUCCESFUL_LOGIN_MESSAGE);
    }

    @Description("[NEG] При не успешной авторизации, пользователь остается на странице login page")
    @Test
    void userShouldStayOnLoginPageAfterLoginWithBadCredentialsNegativeTest() {
        loginPage.setUsername(usernameInCorrect)
                .setPassword(dt.validPassword())
                .submitSignUpButton()
                .checkAuthorization(FAILED_LOGIN_MESSAGE);
    }


}
