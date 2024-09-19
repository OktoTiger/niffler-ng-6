package guru.qa.niffler.test.web;

import com.codeborne.selenide.Selenide;
import guru.qa.niffler.helper.DataGenerator;
import guru.qa.niffler.page.RegisterPage;
import io.qameta.allure.Description;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import guru.qa.niffler.config.Config;

public class RegisterTest {
    private static final Config CFG = Config.getInstance();
    DataGenerator dt = new DataGenerator();

    RegisterPage registerPage = new RegisterPage();
    private static final String USERNAME_INCORRECT = "Us";
    private static final String EXISTED_USER = "severus";
    private static final String EXISTED_PASSWORD = "12345";
    private static final String PASSWORD_IN_CORRECT_LONG = "1234567891011";
    private static final String SUCCESSFUL_MESSAGE = "Congratulations! You've registered";
    private static final String NOTEQUAL_PASSWORD_MESSAGE = "Passwords should be equal";
    private static final String INVALID_PASSWORD_MESSAGE = "Allowed password length should be from 3 to 12 characters";
    private static final String INVALID_USER_MESSAGE = "Allowed username length should be from 3 to 50 characters";


    @BeforeEach()
    void setUp() {
        Selenide.open(CFG.registerUrl());
    }

    @Description("[POS] Успешная регистрация нового пользователя")
    @Test
    void shouldRegisterNewUserPositiveTest() {
        registerPage
                .setUsername(dt.validUsername())
                .setPassword(dt.validPassword())
                .setSubmitPassword(dt.validPassword())
                .submitSignUpButton()
                .checkSuccessRegistration(SUCCESSFUL_MESSAGE);
    }

    @Description("[NEG] Регистрация пользователя с существующим логином")
    @Test
    void shouldNotRegisterNewUserNegativeTest() {
        registerPage
                .setUsername(EXISTED_USER)
                .setPassword(EXISTED_PASSWORD)
                .setSubmitPassword(EXISTED_PASSWORD)
                .submitSignUpButton()
                .checkRegistrationWithExistedUser("Username " + "`" + EXISTED_USER + "`" + " already exists");
    }

    @Description("[NEG] Ошибка при вводе разных паролей в поле пароль и подтвердить пароль")
    @Test
    void shouldShowErrorIfPasswordAndConfirmPasswordAreNotEqualsNegativeTest() {
        registerPage
                .setUsername(dt.validUsername())
                .setPassword(dt.validPassword())
                .setSubmitPassword("7" + dt.validPassword())
                .submitSignUpButton()
                .checkRegistrationNotEqualPassword(NOTEQUAL_PASSWORD_MESSAGE);
    }

    @Description("[NEG] Ошибка при вводе пароля меньше 3 символов")
    @Test
    void shouldShowErrorIfPasswordShorterThanThreeSymbolsNegativeTest() {
        registerPage
                .setUsername(dt.validUsername())
                .setPassword(dt.invalidPassword())
                .setSubmitPassword(dt.invalidPassword())
                .submitSignUpButton()
                .checkRegistrationInvalidPassword(INVALID_PASSWORD_MESSAGE);
    }

    @Description("[NEG] Ошибка при вводе пароля больше 12 символов")
    @Test
    void shouldShowErrorIfPasswordLargerThanTwelveSymbolsNegativeTest() {
        registerPage
                .setUsername(dt.validUsername())
                .setPassword(PASSWORD_IN_CORRECT_LONG)
                .setSubmitPassword(PASSWORD_IN_CORRECT_LONG)
                .submitSignUpButton()
                .checkRegistrationInvalidPassword(INVALID_PASSWORD_MESSAGE);
    }

    @Description("[NEG] Ошибка при вводе username меньше 3 символов")
    @Test
    void shouldShowErrorIfUserNameShorterThanThreeSymbolsNegativeTest() {
        registerPage
                .setUsername(USERNAME_INCORRECT)
                .setPassword(dt.validPassword())
                .setSubmitPassword(dt.validPassword())
                .submitSignUpButton()
                .checkRegistrationInvalidUsername(INVALID_USER_MESSAGE);
    }


}
