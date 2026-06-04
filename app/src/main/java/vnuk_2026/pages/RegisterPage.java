package vnuk_2026.pages;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import vnuk_2026.utils.WebDriverUtils;

public class RegisterPage {

    // 1. Khai báo locator cho các ô nhập liệu
    private final By emailTxtBy = By.id("email");
    private final By passwordTxtBy = By.id("password");
    private final By confirmPasswordTxtBy = By.id("confirmPassword");
    private final By pidTxtBy = By.id("pid");
    
    // 2. Nút bấm Register
    private final By registerBtnBy = By.xpath("//input[@value='Register'] | //input[@type='submit']");
    
    // 3. Locator bắt lỗi tổng quát (Ví dụ: Email đã tồn tại)
    private final By errorMessageBy = By.cssSelector("p.message.error");
    
    // 4. Locator bắt lỗi validation chữ đỏ nằm dưới ô Email (Thiếu @)
    private final By emailValidationErrorBy = By.cssSelector("label[for='email'].validation-error");

    // 5. Locator bắt lỗi validation chữ đỏ nằm dưới ô Password (Độ dài không hợp lệ)
    private final By passwordValidationErrorBy = By.cssSelector("label[for='password'].validation-error");


    // Hàm thực hiện điền form và bấm Đăng ký
    public void register(String email, String password, String confirmPassword, String pid) {
        WebDriverUtils.get().findElement(emailTxtBy).sendKeys(email);
        WebDriverUtils.get().findElement(passwordTxtBy).sendKeys(password);
        WebDriverUtils.get().findElement(confirmPasswordTxtBy).sendKeys(confirmPassword);
        WebDriverUtils.get().findElement(pidTxtBy).sendKeys(pid);

        // Dùng JS click để tránh bị Footer che khuất nút bấm
        WebElement registerBtn = WebDriverUtils.get().findElement(registerBtnBy);
        JavascriptExecutor js = (JavascriptExecutor) WebDriverUtils.get();
        js.executeScript("arguments[0].click();", registerBtn);
    }

    // Hàm lấy thông báo lỗi tổng quát
    public String getErrorMessage() {
        WebDriverWait wait = new WebDriverWait(WebDriverUtils.get(), Duration.ofSeconds(10));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(errorMessageBy)).getText().trim();
    }

    // Hàm lấy thông báo lỗi định dạng Email
    public String getEmailValidationError() {
        WebDriverWait wait = new WebDriverWait(WebDriverUtils.get(), Duration.ofSeconds(10));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(emailValidationErrorBy)).getText().trim();
    }

    // Hàm lấy thông báo lỗi độ dài Password
    public String getPasswordValidationError() {
        WebDriverWait wait = new WebDriverWait(WebDriverUtils.get(), Duration.ofSeconds(10));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(passwordValidationErrorBy)).getText().trim();
    }
}