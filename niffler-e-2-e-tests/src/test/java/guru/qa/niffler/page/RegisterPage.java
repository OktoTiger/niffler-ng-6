package guru.qa.niffler.page;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import lombok.Data;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Data
public class RegisterPage {
    private final SelenideElement usernameInputSelectorSelector = $("#username");
    private final SelenideElement passwordInputSelector = $("#password");
    private final SelenideElement passwordSubmitInputSelector = $("#passwordSubmit");
    private final SelenideElement signUpButtonSelector = $("button[type='submit']");
    private final SelenideElement titleSuccessSignUpSelector = $(byText("Congratulations! You've registered!"));
    private final SelenideElement ErrorMessageForExistedUser = $(".form__error");
    private final SelenideElement ErrorMassegeForNotEqualPassword = $(byText("Passwords should be equal"));
    private final SelenideElement ErrorMassegeForInvalidPassword = $(byText("Allowed password length should be from 3 to 12 characters"));
    private final SelenideElement ErrorMassegeForInvalidUsername = $(byText("Allowed username length should be from 3 to 50 characters"));

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
     * */

    @Step("Проверка успешной регистрации")
    public void checkSuccessRegistration() {
        assertEquals(titleSuccessSignUpSelector.text(), "Congratulations! You've registered!");
    }

    @Step("Проверка сообщения при не верной регистрации")
    public void checkUnSuccessfulErrorMessage(SelenideElement element, String errorMessage){
        assertEquals(element.getText(), errorMessage);
    }





}
