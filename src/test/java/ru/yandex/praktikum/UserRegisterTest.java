package ru.yandex.praktikum;

import org.junit.Test;
import org.junit.After;
import org.junit.Before;
import io.qameta.allure.Epic;
import io.qameta.allure.junit4.DisplayName;
import ru.yandex.praktikum.entity.User;
import ru.yandex.praktikum.pom.HomePage;
import ru.yandex.praktikum.utils.GenerateUser;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.Condition;
import static com.codeborne.selenide.Selenide.*;
import static org.junit.Assert.*;

@Epic("Register user")
public class UserRegisterTest {
    private User user;
    private HomePage homePage;

    @Before
    public void setUp() {
        user = GenerateUser.getRandomUser();
        homePage = open(HomePage.URL, HomePage.class);
    }

    @After
    public void clearState() {
        user = null;
        Selenide.clearBrowserLocalStorage();
    }

    @Test
    @DisplayName("Register user by valid credentials")
    public void registerUserByValidCredentials() {
        boolean isDisplayed = homePage.clickLoginButton()
                .clickRegisterLink()
                .fillRegisterForm(user.getName(), user.getEmail(), user.getPassword())
                .clickRegisterButton(Condition.hidden);

        assertFalse(isDisplayed);
    }

    @Test
    @DisplayName("Register user by invalid password")
    public void registerUserByInvalidPassword() {
        boolean isDisplayed = homePage.clickLoginButton()
                .clickRegisterLink()
                .fillRegisterForm(user.getName(), user.getEmail(), "888")
                .clickRegisterButton(Condition.visible);

        assertTrue(isDisplayed);
    }

    @Test
    @DisplayName("Register user is displayed password error")
    public void registerUserIsDisplayedPasswordError() {
        boolean isDisplayed = homePage.clickLoginButton()
                .clickRegisterLink()
                .fillRegisterForm(user.getName(), user.getEmail(), "888")
                .isDisplayedPasswordError();

        assertTrue(isDisplayed);
    }
}
