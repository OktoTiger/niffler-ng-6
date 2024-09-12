package guru.qa.niffler.page;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.$;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class LoginPage {
    private final SelenideElement usernameInputSelector = $("input[name='username']");
    private final SelenideElement passwordInput = $("input[name='password']");
    private final SelenideElement submitButton = $("button[type='submit']");
    private final SelenideElement errorMessage = $(".form__error");

    public MainPage login(String username, String password) {
        usernameInputSelector.setValue(username);
        passwordInput.setValue(password);
        submitButton.click();
        return new MainPage();
    }

    @Step("Ввод username")
    public LoginPage setUsername(String username) {
        usernameInputSelector.setValue(username);
        return this;
    }

    @Step("Ввод пароля")
    public LoginPage setPassword(String password) {
        passwordInput.setValue(password);
        return this;
    }

    @Step("Нажать кнопку регистрации")
    public LoginPage submitSignUpButton() {
        submitButton.click();
        return this;
    }

    @Step("Проверка успешной не успешной авторизации")
    public void checkSuccessRegistration() {
        assertEquals(errorMessage.text(), "Bad credentials");
    }
}
