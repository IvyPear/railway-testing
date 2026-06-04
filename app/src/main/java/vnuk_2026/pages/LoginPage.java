package vnuk_2026.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import vnuk_2026.utils.WebDriverUtils;

public class LoginPage {

    private final By usernameTxtBy = By.cssSelector("#username");
    private final By passwordTxtBy = By.id("password");
    private final By loginBtnBy = By.cssSelector("[title=Login]");
    private final By errorMsgBy = By.cssSelector(".message.error");

    public void login(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        clickLoginButton();
    }

    public void enterUsername(String username) {
        WebDriverUtils.get().findElement(usernameTxtBy).clear();
        WebDriverUtils.get().findElement(usernameTxtBy).sendKeys(username);
    }

    public void enterPassword(String password) {
        WebDriverUtils.get().findElement(passwordTxtBy).clear();
        WebDriverUtils.get().findElement(passwordTxtBy).sendKeys(password);
    }

    public void clickLoginButton() {
        // Tìm element nút Login
        WebElement loginBtn = WebDriverUtils.get().findElement(loginBtnBy);
        
        // Sử dụng JavascriptExecutor để ép click thẳng vào nút, xuyên qua mọi element đang che khuất nó
        JavascriptExecutor js = (JavascriptExecutor) WebDriverUtils.get();
        js.executeScript("arguments[0].click();", loginBtn);
    }

    public String getErrorMessage() {
        return WebDriverUtils.get().findElement(errorMsgBy).getText();
    }
}