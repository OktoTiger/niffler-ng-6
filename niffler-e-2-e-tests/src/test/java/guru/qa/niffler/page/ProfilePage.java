package guru.qa.niffler.page;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class ProfilePage {
    private final SelenideElement inputImageSelector = $(".image__input-label");
    private final SelenideElement usernameInputSelector = $("#username");
    private final SelenideElement nameInputSelector = $("#name");
    private final SelenideElement signUpButtonSelector = $("button[type='submit']");
    private final SelenideElement checkBoxSelector = $("input[type='checkbox']");
    private final SelenideElement categoryInputSelector = $("#category");
    private final SelenideElement editButtonSelector = $("button[aria-label='Edit category']");
    private final SelenideElement archiveButtonSelector = $("button[aria-label='Archive category']");
    private final ElementsCollection categoryList = $$(".MuiChip-root");


    @Step("Ввод username")
    public ProfilePage setUsername(String username) {
        usernameInputSelector.setValue(username);
        return this;
    }

    @Step("Ввод name")
    public ProfilePage setName(String name) {
        nameInputSelector.setValue(name);
        return this;
    }

    @Step("Открыть модальное окно для выбора картинки")
    public ProfilePage clickUploadImage() {
        inputImageSelector.click();
        return this;
    }

    @Step("Нажать кнопку сохранить")
    public ProfilePage clickSaveButton() {
        signUpButtonSelector.click();
        return this;
    }

    @Step("Переключить toggle")
    public ProfilePage clickCheckbox() {
        checkBoxSelector.click();
        return this;
    }

    @Step("Ввод категории")
    public ProfilePage setCategory(String categoryName) {
        categoryInputSelector.setValue(categoryName);
        return this;
    }

    @Step("Нажать кнопку редактирования")
    public ProfilePage clickEditButton() {
        editButtonSelector.click();
        return this;
    }

    @Step("Нажать кнопку архивирования")
    public ProfilePage clickArchiveButton() {
        archiveButtonSelector.click();
        return this;
    }

    /**
     * ----------------------------------Checks----------------------------------------------------------------------
     */

    @Step("Проверка отображения категории")
    public void checkCategoryShouldBeVisible(String category) {
        categoryList.findBy(text(category)).shouldBe(visible);
    }


}
