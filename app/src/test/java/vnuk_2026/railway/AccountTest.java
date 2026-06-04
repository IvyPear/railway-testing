package vnuk_2026.railway;

import org.testng.Assert;
import org.testng.annotations.Test;
import vnuk_2026.pages.ChangePasswordPage;
import vnuk_2026.pages.HomePage;
import vnuk_2026.pages.LoginPage;

public class AccountTest extends RailwayTest {

    HomePage homePage = new HomePage();
    LoginPage loginPage = new LoginPage();
    ChangePasswordPage changePasswordPage = new ChangePasswordPage();

    @Test
    public void tc005_verifyChangePasswordAndReLoginFlow() {
        String email = "example1@udn.vn";
        String oldPass = "111111111";
        String newPass = "222222222";

        homePage.open();
        homePage.navigateToLoginPage();
        loginPage.login(email, oldPass);

        homePage.navigateToChangePasswordPage();
        changePasswordPage.changePassword(oldPass, newPass, newPass);
        Assert.assertEquals(changePasswordPage.getSuccessMessageText(), "Your password has been updated!");

        homePage.logout();

        homePage.navigateToLoginPage();
        loginPage.login(email, oldPass);
        Assert.assertTrue(loginPage.getErrorMessage().contains("Invalid username or password"), 
                "Hệ thống không chặn đăng nhập bằng mật khẩu cũ!");

        loginPage.login(email, newPass);
        Assert.assertEquals(homePage.getGreetingText(), "Welcome " + email);
    }
}