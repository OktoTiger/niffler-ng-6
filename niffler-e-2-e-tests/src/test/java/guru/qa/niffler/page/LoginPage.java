package guru.qa.niffler.page;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

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

    public MainPage successLogin(String username, String password) {
        login(username, password);
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

    @Step("Проверка авторизации")
    public void checkAuthorization(String text) {
        errorMessage.shouldHave(text(text)).shouldBe(visible);
    }
}
