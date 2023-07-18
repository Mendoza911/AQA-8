package org.example.test;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.example.data.DataHelper;
import org.example.data.SQLHelper;
import org.example.page.LoginPage;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.lang.annotation.Repeatable;
import java.time.Duration;

import static com.codeborne.selenide.Selenide.*;
import static org.example.data.SQLHelper.cleanDatabase;

public class DeadlineAppTest {

    @AfterAll
    static void teardown(){
        cleanDatabase();
    }

    @Test
    @DisplayName("Should successfully login to dashboard with exist login and password from sut test data")
    void shouldBeSuccess() {
        var loginPage = open("http://localhost:9999", LoginPage.class);
        var authInfo = DataHelper.getAuthInfoWithTestData();
        var verificationPage = loginPage.validLogin(authInfo);
        verificationPage.verifyVerificationPageVisibility();
        var verificationCode = SQLHelper.getVerificationCode();
        verificationPage.validVerify(verificationCode.getCode());
    }

    @Test
    @DisplayName("Should get error notification if user is not exist in base")
    void shouldBeErrorLoginNotInDatabase() {
        var loginPage = open("http://localhost:9999", LoginPage.class);
        var authInfo = DataHelper.generateRandomUser();
        loginPage.validLogin(authInfo);
        loginPage.verifyErrorNotificationVisibility();
    }

    @Test
    @DisplayName("Should get error notification if login with exist in base and active user and random verification code")
    void shouldBeErrorCodeNotInDatabase() {
        var loginPage = open("http://localhost:9999", LoginPage.class);
        var authInfo = DataHelper.getAuthInfoWithTestData();
        var verificationPage = loginPage.validLogin(authInfo);
        verificationPage.verifyVerificationPageVisibility();
        var verificationCode = DataHelper.generateRandomVerificationCode();
        verificationPage.verify(verificationCode.getCode());
        verificationPage.verifyErrorNotificationVisibility();
    }

    @Test
    @DisplayName("Should get block notification if more than three false authorizations")
    void shouldBeErrorMoreThenThreeFalseAuth() {
        var loginPage = open("http://localhost:9999", LoginPage.class);
        ;
        var authInfo = DataHelper.generateRandomUser();
        loginPage.validLogin(authInfo);
        loginPage.verifyErrorNotificationVisibility();
        loginPage.clear();
        loginPage.validLogin(authInfo);
        loginPage.verifyErrorNotificationVisibility();
        loginPage.clear();
        loginPage.validLogin(authInfo);
        loginPage.verifyErrorNotificationVisibility();
        loginPage.clear();
        loginPage.validLogin(authInfo);
        loginPage.getBlockInfo();
    }
}
