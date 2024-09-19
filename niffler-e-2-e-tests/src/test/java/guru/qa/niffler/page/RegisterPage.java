package guru.qa.niffler.page;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class RegisterPage {
    private final SelenideElement usernameInputSelectorSelector = $("#username");
    private final SelenideElement passwordInputSelector = $("#password");
    private final SelenideElement passwordSubmitInputSelector = $("#passwordSubmit");
    private final SelenideElement signUpButtonSelector = $("button[type='submit']");
    private final SelenideElement titleSuccessSignUpSelector = $(byText("Congratulations! You've registered!"));
    private final SelenideElement errorMessageForExistedUser = $(".form__error");
    private final SelenideElement errorMessageForNotEqualPassword = $(byText("Passwords should be equal"));
    private final SelenideElement errorMessageForInvalidPassword = $(byText("Allowed password length should be from 3 to 12 characters"));
    private final SelenideElement errorMessageForInvalidUsername = $(byText("Allowed username length should be from 3 to 50 characters"));

    @Step("Ввод username")
    public RegisterPage setUsername(String username) {
        usernameInputSelectorSelector.setValue(username);
        return this;
    }

    @Step("Ввод пароля")
    public RegisterPage setPassword(String password) {
        passwordInputSelector.setValue(password);
        return this;
    }

    @Step("Повторный ввод пароля")
    public RegisterPage setSubmitPassword(String password) {
        passwordSubmitInputSelector.setValue(password);
        return this;
    }

    @Step("Нажать кнопку регистрации")
    public RegisterPage submitSignUpButton() {
        signUpButtonSelector.click();
        return this;
    }


    /**
     * ---------------------------Checks----------------------------------------------------
     */

    @Step("Проверка успешной регистрации")
    public void checkSuccessRegistration(String text) {
        titleSuccessSignUpSelector.shouldHave(text(text)).shouldBe(visible);
    }

    @Step("Проверка сообщения об ошибке при не успешной регистрации")
    public void checkUnSuccessfulErrorMessage(SelenideElement element, String errorMessage) {
        assertEquals(element.getText(), errorMessage);
    }

    @Step("Проверка сообщения об ошибке при регистрации с username существующего пользователя")
    public void checkRegistrationWithExistedUser(String text) {
        errorMessageForExistedUser.shouldHave(text(text)).shouldBe(visible);
    }

    @Step("Проверка сообщения об ошибке при регистрации, при вводе разных паролей")
    public void checkRegistrationNotEqualPassword(String text) {
        errorMessageForNotEqualPassword.shouldHave(text(text)).shouldBe(visible);
    }

    @Step("Проверка сообщения об ошибке при регистрации с невалидным паролем")
    public void checkRegistrationInvalidPassword(String text) {
        errorMessageForInvalidPassword.shouldHave(text(text)).shouldBe(visible);
    }

    @Step("Проверка сообщения об ошибке при регистрации с невалидным username")
    public void checkRegistrationInvalidUsername(String text) {
        errorMessageForInvalidUsername.shouldHave(text(text)).shouldBe(visible);
    }


}
