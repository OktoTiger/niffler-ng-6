package guru.qa.niffler.test.web;

import com.codeborne.selenide.Selenide;
import guru.qa.niffler.page.RegisterPage;
import io.qameta.allure.Description;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static guru.qa.niffler.helper.DataGenerator.*;

import guru.qa.niffler.config.Config;

public class RegisterTest {
    private static final Config CFG = Config.getInstance();

    RegisterPage registerPage = new RegisterPage();


    @BeforeEach()
    void setUp() {
        Selenide.open(CFG.registerUrl());
    }

    @Description("[POS] Успешная регистрация нового пользователя")
    @Test
    void shouldRegisterNewUserPositiveTest() {
        registerPage
                .setUsername(usernameCorrect)
                .setPassword(passwordCorrect)
                .setSubmitPassword(passwordCorrect)
                .submitSignUpButton()
                .checkSuccessRegistration();
    }

    @Description("[NEG] Регистрация пользователя с существующим логином")
    @Test
    void shouldNotRegisterNewUserNegativeTest() {
        registerPage
                .setUsername(existedUser)
                .setPassword(existedPassword)
                .setSubmitPassword(existedPassword)
                .submitSignUpButton()
                .checkUnSuccessfulErrorMessage(registerPage.getErrorMessageForExistedUser(), "Username " + "`" + existedUser + "`" + " already exists");
    }

    @Description("[NEG] Ошибка при вводе разных паролей в поле пароль и подтвердить пароль")
    @Test
    void shouldShowErrorIfPasswordAndConfirmPasswordAreNotEqualsNegativeTest() {
        registerPage
                .setUsername(usernameCorrect)
                .setPassword(passwordCorrect)
                .setSubmitPassword("7" + passwordCorrect)
                .submitSignUpButton()
                .checkUnSuccessfulErrorMessage(registerPage.getErrorMassegeForNotEqualPassword(), "Passwords should be equal");
    }

    @Description("[NEG] Ошибка при вводе пароля меньше 3 символов")
    @Test
    void shouldShowErrorIfPasswordShorterThanThreeSymbolsNegativeTest() {
        registerPage
                .setUsername(usernameCorrect)
                .setPassword(passwordInCorrectShort)
                .setSubmitPassword(passwordInCorrectShort)
                .submitSignUpButton()
                .checkUnSuccessfulErrorMessage(registerPage.getErrorMassegeForInvalidPassword(), "Allowed password length should be from 3 to 12 characters");
    }

    @Description("[NEG] Ошибка при вводе пароля больше 12 символов")
    @Test
    void shouldShowErrorIfPasswordLargerThanTwelveSymbolsNegativeTest() {
        registerPage
                .setUsername(usernameCorrect)
                .setPassword(passwordInCorrectLong)
                .setSubmitPassword(passwordInCorrectLong)
                .submitSignUpButton()
                .checkUnSuccessfulErrorMessage(registerPage.getErrorMassegeForInvalidPassword(), "Allowed password length should be from 3 to 12 characters");
    }

    @Description("[NEG] Ошибка при вводе username меньше 3 символов")
    @Test
    void shouldShowErrorIfUserNameShorterThanThreeSymbolsNegativeTest() {
        registerPage
                .setUsername(usernameInCorrect)
                .setPassword(passwordCorrect)
                .setSubmitPassword(passwordCorrect)
                .submitSignUpButton()
                .checkUnSuccessfulErrorMessage(registerPage.getErrorMassegeForInvalidUsername(), "Allowed username length should be from 3 to 50 characters");
    }


}
