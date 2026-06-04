package vnuk_2026.railway;

import org.testng.Assert;
import org.testng.annotations.Test;
import vnuk_2026.pages.HomePage;
import vnuk_2026.pages.LoginPage;

public class LoginTest extends RailwayTest {

    HomePage homePage = new HomePage();
    LoginPage loginPage = new LoginPage();

    @Test
    public void tc001_verifyUsersCanLoginInSuccessfullyPOM() {
        homePage.open();
        homePage.navigateToLoginPage();
        
        loginPage.login("example1@udn.vn", "111111111");

        Assert.assertEquals(homePage.getGreetingText(), "Welcome example1@udn.vn");
    }
}