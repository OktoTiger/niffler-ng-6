package guru.qa.niffler.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.Assertions;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class RegisterPage {
    private final SelenideElement usernameInputSelectorSelector = $("#username");
    private final SelenideElement passwordInputSelector = $("#password");
    private final SelenideElement passwordSubmitInputSelector = $("#passwordSubmit");
    private final SelenideElement signUpButtonSelector = $("a").shouldHave(Condition.text("Create new account"));
    private final SelenideElement titleSuccessSignUpSelector = $(byText("Congratulations! You've registered!"));
    private final SelenideElement testErrorForExistedUser = $("span.form__error");
    private final SelenideElement testErrorForNotEqualPassword = $(byText("Passwords should be equal"));


    private MainPage mainPage = new MainPage();

    public RegisterPage setUsername(String username) {
        usernameInputSelectorSelector.setValue(username);
        return this;
    }
    public RegisterPage setPassword(String password) {
        passwordInputSelector.setValue(password);
        return this;
    }

    public RegisterPage setSubmitPassword(String password) {
        passwordSubmitInputSelector.setValue(password);
        return this;
    }

    public RegisterPage submitSignUpButton() {
        signUpButtonSelector.click();
        return this;
    }



    /**
     * ---------------------------Checks----------------------------------------------------
     * */

    public void checkSuccessRegistration() {
        assertEquals(titleSuccessSignUpSelector.text(), "Congratulations! You've registered!");
    }

    public void checkUnSuccessLogin(String username){
        assertEquals(testErrorForExistedUser, "Username "+ username + "already exists");
    }

    public void checkUnSuccessfullEnteringPassword(){
        assertEquals(testErrorForNotEqualPassword, "Passwords should be equal");
    }




}
