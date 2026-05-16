package vnuk_2026.railway;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

// @Test
public class LoginTest {

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

        webDriver.findElement(usernameTxtBy).sendKeys("admin");
        webDriver.findElement(passwordTxtBy).sendKeys("123456");

        By loginBtnBy = By.cssSelector("[title=Login]");

        webDriver.findElement(loginBtnBy).click();

        // Thread.sleep(5000);

        webDriver.close();
    }

}
