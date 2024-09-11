package guru.qa.niffler.test.web;

import guru.qa.niffler.page.RegisterPage;
import org.junit.jupiter.api.Test;

import static io.qameta.allure.Allure.step;

public class RegisterTest {

    RegisterPage registerPage = new RegisterPage();

    @Test
    void shouldRegisterNewUser(){
        registerPage.submitSignUpButton()
                .setUsername("Петрович")
                .setPassword("123")
                .setSubmitPassword("123")
                .submitSignUpButton()
}
