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
    
        String pass1 = "111111111";
        String pass2 = "222222222";

        homePage.open();
        
        
        homePage.navigateToLoginPage();
        loginPage.login(email, pass1);

        homePage.navigateToChangePasswordPage();
        changePasswordPage.changePassword(pass1, pass2, pass2);
        Assert.assertEquals(changePasswordPage.getSuccessMessageText(), "Your password has been updated!");

        homePage.logout();

        homePage.navigateToLoginPage();
        loginPage.login(email, pass1);
        Assert.assertTrue(loginPage.getErrorMessage().contains("Invalid username or password"), 
                "Hệ thống không chặn đăng nhập bằng mật khẩu cũ!");

        homePage.navigateToLoginPage(); 
        loginPage.login(email, pass2);
        Assert.assertEquals(homePage.getGreetingText(), "Welcome " + email);
        
    
        homePage.navigateToChangePasswordPage();
        changePasswordPage.changePassword(pass2, pass1, pass1);
    }
}