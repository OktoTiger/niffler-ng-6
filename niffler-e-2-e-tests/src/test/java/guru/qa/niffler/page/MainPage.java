package guru.qa.niffler.page;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;

public class MainPage {
    private final ElementsCollection tableRows = $("#spendings tbody").$$("tr");
    private final SelenideElement titleSelector = $(byText("History of Spendings"));
    private final SelenideElement menuSelector = $("button[aria-label='Menu']");
    private final SelenideElement profileSelector = $(byText("Profile"));

    public EditSpendingPage editSpending(String spendingDescription) {
        tableRows.find(text(spendingDescription)).$$("td").get(5).click();
        return new EditSpendingPage();
    }

    @Step("Проверка успешной регистрации")
    public void checkThatTableContainsSpending(String spendingDescription) {
        tableRows.find(text(spendingDescription)).should(visible);
    }

    @Step("Проверка успешной авторизации")
    public void checkSuccessLogIn(String text) {
        titleSelector.shouldHave(text(text)).shouldBe(visible);
    }

    @Step("Нажать на кнопку Меню")
    public void clickMenuButton() {
        menuSelector.click();
    }

    @Step("Нажать на ссылку profile")
    public void clickProfileLink() {
        profileSelector.click();
    }


}
