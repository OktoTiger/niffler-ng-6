package guru.qa.niffler.test.web;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import guru.qa.niffler.config.Config;
import guru.qa.niffler.page.LoginPage;
import guru.qa.niffler.page.MainPage;
import io.qameta.allure.Description;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.executeJavaScript;
import static guru.qa.niffler.helper.UserData.*;

class LoginTest {
    private static final Config CFG = Config.getInstance();

    LoginPage loginPage = new LoginPage();
    MainPage mainPage = new MainPage();

    @BeforeEach()
    void setUp() {
        Selenide.open(CFG.frontUrl());
        executeJavaScript("return document.readyState").equals("complete");
    }

    @Description("[POS] Отображение главной страницы при успешной авторизации")
    @Test
    void mainPageShouldBeDisplayedAfterSuccessfulLoginTest() {
        Configuration.holdBrowserOpen = true;
        loginPage.setUsername(existedUser)
                .setPassword(existedPassword)
                .submitSignUpButton();
        mainPage.checkSuccessLogIn();
    }

    @Description("[NEG] При не успешной авторизации, пользователь остается на странице login page")
    @Test
    void userShouldStayOnLoginPageAfterLoginWithBadCredentialsNegativeTest() {
        loginPage.setUsername(usernameInCorrect)
                .setPassword(passwordCorrect)
                .submitSignUpButton()
                .checkSuccessRegistration();
    }


}
