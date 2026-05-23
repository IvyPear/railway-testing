package vnuk_2026.railway;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

import vnuk_2026.pages.HomePage;
import vnuk_2026.pages.LoginPage;

// @Test
public class LoginTest {

    HomePage homePage = new HomePage();
    LoginPage loginPage = new LoginPage();

    @Test
    public void tc001_verifyUsersCanLoginInSuccessfullyPOM()  {
        homePage.open();

        homePage.navigateToLoginPage();

        loginPage.login("example@udn.vn", "123456789");

        Assert.assertEquals(homePage.getGreetingText(), "Welcome example@udn.vn");
    }

    @Test
    public void tc001_verifyUsersCanLoginInSuccessfully() throws InterruptedException {
        WebDriver webDriver = new ChromeDriver();

        webDriver.get("http://railwayb1.somee.com");

        // Thread.sleep(2000); // pause
        By loginMenuBy = By.linkText("Login"); // locator

        WebElement loginMenu = webDriver.findElement(loginMenuBy);

        loginMenu.click();

        // Login form
        By usernameTxtBy = By.cssSelector("#username");
        By passwordTxtBy = By.id("password");

        webDriver.findElement(usernameTxtBy).sendKeys("example@udn.vn");
        webDriver.findElement(passwordTxtBy).sendKeys("123456789");

        By loginBtnBy = By.cssSelector("[title=Login]");

        webDriver.findElement(loginBtnBy).click();

        By greetingLblBy = By.cssSelector("div.account strong");
        // System.out.println(webDriver.findElement(greetingLblBy).getText());
        Assert.assertEquals(webDriver.findElement(greetingLblBy).getText(), "Welcome example1@udn.vn");

        // Thread.sleep(5000);

        webDriver.close();
    }

    @Test
    public void tc002_verifyUsersCantLoginInWithInvalidUsernameOrPassword() {
        WebDriver webDriver = new ChromeDriver();

        webDriver.get("http://railwayb1.somee.com");

        // Thread.sleep(2000); // pause
        By loginMenuBy = By.linkText("Login"); // locator

        WebElement loginMenu = webDriver.findElement(loginMenuBy);

        loginMenu.click();

        // Login form
        By usernameTxtBy = By.cssSelector("#username");
        By passwordTxtBy = By.id("password");

        webDriver.findElement(usernameTxtBy).sendKeys("example@udn.vn");
        webDriver.findElement(passwordTxtBy).sendKeys("12345");

        By loginBtnBy = By.cssSelector("[title=Login]");

        webDriver.findElement(loginBtnBy).click();

        By errorMessageBy = By.cssSelector("p.message.error.LoginForm");

        Assert.assertFalse(webDriver.findElement(errorMessageBy).isDisplayed(), "There is no error display");

        webDriver.close();
    }

}
