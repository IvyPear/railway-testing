package vnuk_2026.pages;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import vnuk_2026.utils.WebDriverUtils;

public class RegisterPage {

    // Khai báo locator dựa trên ID của HTML
    private final By emailTxtBy = By.id("email");
    private final By passwordTxtBy = By.id("password");
    private final By confirmPasswordTxtBy = By.id("confirmPassword");
    private final By pidTxtBy = By.id("pid");
    
    // Nút Register (Giả định dùng input type='submit' hoặc value='Register')
    private final By registerBtnBy = By.xpath("//input[@value='Register'] | //input[@type='submit']");
    
    // Locator bắt thẻ p chứa lỗi
    private final By errorMessageBy = By.cssSelector("p.message.error");

    public void register(String email, String password, String confirmPassword, String pid) {
        WebDriverUtils.get().findElement(emailTxtBy).sendKeys(email);
        WebDriverUtils.get().findElement(passwordTxtBy).sendKeys(password);
        WebDriverUtils.get().findElement(confirmPasswordTxtBy).sendKeys(confirmPassword);
        WebDriverUtils.get().findElement(pidTxtBy).sendKeys(pid);

        // Dùng JS click để tránh lỗi Footer che khuất như các trang trước
        WebElement registerBtn = WebDriverUtils.get().findElement(registerBtnBy);
        JavascriptExecutor js = (JavascriptExecutor) WebDriverUtils.get();
        js.executeScript("arguments[0].click();", registerBtn);
    }

    public String getErrorMessage() {
        WebDriverWait wait = new WebDriverWait(WebDriverUtils.get(), Duration.ofSeconds(10));
        // Lấy text và dùng trim() để cắt các khoảng trắng dư thừa ở đầu/cuối
        return wait.until(ExpectedConditions.visibilityOfElementLocated(errorMessageBy)).getText().trim();
    }
}