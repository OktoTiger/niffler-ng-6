package guru.qa.niffler.test.web;
import com.codeborne.selenide.Selenide;
import guru.qa.niffler.config.Config;
import guru.qa.niffler.jupiter.annotation.meta.WebTest;
import guru.qa.niffler.jupiter.extension.UsersQueueExtension.StaticUser;
import guru.qa.niffler.jupiter.extension.UsersQueueExtension.UserType;
import guru.qa.niffler.page.FriendsPage;
import guru.qa.niffler.page.LoginPage;
import guru.qa.niffler.page.PeoplePage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static guru.qa.niffler.jupiter.extension.UsersQueueExtension.UserType.Type.EMPTY;
import static guru.qa.niffler.jupiter.extension.UsersQueueExtension.UserType.Type.WITH_FRIEND;
import static guru.qa.niffler.jupiter.extension.UsersQueueExtension.UserType.Type.WITH_INCOME_REQUEST;
import static guru.qa.niffler.jupiter.extension.UsersQueueExtension.UserType.Type.WITH_OUTCOME_REQUEST;
@WebTest
public class FriendsWebTest {
    private static final Config CFG = Config.getInstance();
    LoginPage loginPage = new LoginPage();
    FriendsPage friendsPage = new FriendsPage();
    PeoplePage peoplePage = new PeoplePage();

    @BeforeEach
    void setUp() {
        Selenide.open(CFG.frontUrl());
    }

    @Test
    void friendShouldBePresentInFriendsTable(@UserType(WITH_FRIEND) StaticUser user) {
                loginPage.successLogin(user.username(), user.password())
                .checkSuccessLogIn("History of Spendings");
                friendsPage.openFriendsTab()
                .checkExistingFriends(user.friend());
    }

    @Test
    void friendsTableShouldBeEmptyForNewUser(@UserType(EMPTY) StaticUser user) {
        loginPage.successLogin(user.username(), user.password())
                .checkSuccessLogIn("History of Spendings");
        friendsPage.openFriendsTab()
                .checkNoExistingFriends();
    }

    @Test
    void incomeInvitationBePresentInFriendsTable(@UserType(WITH_INCOME_REQUEST) StaticUser user) {
        loginPage.successLogin(user.username(), user.password())
                .checkSuccessLogIn("History of Spendings");
        friendsPage.openFriendsTab()
                .checkExistingInvitations(user.income());
    }

    @Test
    void outcomeInvitationBePresentInAllPeoplesTable(@UserType(WITH_OUTCOME_REQUEST) StaticUser user) {
        loginPage.successLogin(user.username(), user.password());
        friendsPage.goToAllPeoplePage();
        peoplePage.checkInvitationSentToUser(user.outcome());
    }
}
