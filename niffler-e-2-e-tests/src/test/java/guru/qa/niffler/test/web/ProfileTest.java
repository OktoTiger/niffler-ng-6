package guru.qa.niffler.test.web;

import com.codeborne.selenide.Selenide;
import guru.qa.niffler.config.Config;
import guru.qa.niffler.jupiter.annotation.Category;
import guru.qa.niffler.jupiter.annotation.meta.User;
import guru.qa.niffler.model.CategoryJson;
import guru.qa.niffler.page.LoginPage;
import guru.qa.niffler.page.MainPage;
import guru.qa.niffler.page.ProfilePage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Locale;

import static com.codeborne.selenide.Selenide.executeJavaScript;

public class ProfileTest {
    private static final Config CFG = Config.getInstance();
    LoginPage loginPage = new LoginPage();
    MainPage mainPage = new MainPage();
    ProfilePage profilePage = new ProfilePage();
    private final String existedUser = "severus";
    private final String existedPassword = "12345";

    @BeforeEach
    void setUp() {
        Selenide.open(CFG.frontUrl());
        executeJavaScript("return document.readyState").equals("complete");
    }

    @User(
            username = existedUser,
            categories = @Category(
                    acrhived = true
            )
    )
    @Test
    @DisplayName("[POS] Отображение архивной категории в профиле")
    void archiveCategoryShouldPresentInCategoriesListPositiveTest(CategoryJson category) {
        loginPage.setUsername(existedUser)
                .setPassword(existedPassword)
                .submitSignUpButton();
        mainPage.clickMenuButton();
        mainPage.clickProfileLink();
        profilePage.clickCheckbox().checkCategoryShouldBeVisible(category.name());
    }

    @User(
            username = existedUser,
            categories = @Category(
                    acrhived = false
            )
    )
    @Test
    @DisplayName("[POS] Отображение активной категории в профиле")
    void ActiveCategoryShouldPresentInCategoriesListPositiveTest(CategoryJson category) {
        loginPage.setUsername(existedUser)
                .setPassword(existedPassword)
                .submitSignUpButton();
        mainPage.clickMenuButton();
        mainPage.clickProfileLink();
        profilePage.checkCategoryShouldBeVisible(category.name());
    }

}
