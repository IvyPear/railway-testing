package vnuk_2026.pages;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import vnuk_2026.utils.WebDriverUtils;

public class RegisterPage {

    private final By emailTxtBy = By.id("email");
    private final By passwordTxtBy = By.id("password");
    private final By confirmPasswordTxtBy = By.id("confirmPassword");
    private final By pidTxtBy = By.id("pid");
    
    private final By registerBtnBy = By.xpath("//input[@value='Register'] | //input[@type='submit']");
    
    // Locator bắt lỗi tổng quát (như lỗi trùng email ở kịch bản trước)
    private final By errorMessageBy = By.cssSelector("p.message.error");
    
    // BỔ SUNG: Locator bắt lỗi validation chữ đỏ nằm dưới ô Email (dựa theo class và for trong HTML của bạn)
    private final By emailValidationErrorBy = By.cssSelector("label[for='email'].validation-error");

    public void register(String email, String password, String confirmPassword, String pid) {
        WebDriverUtils.get().findElement(emailTxtBy).sendKeys(email);
        WebDriverUtils.get().findElement(passwordTxtBy).sendKeys(password);
        WebDriverUtils.get().findElement(confirmPasswordTxtBy).sendKeys(confirmPassword);
        WebDriverUtils.get().findElement(pidTxtBy).sendKeys(pid);

        WebElement registerBtn = WebDriverUtils.get().findElement(registerBtnBy);
        JavascriptExecutor js = (JavascriptExecutor) WebDriverUtils.get();
        js.executeScript("arguments[0].click();", registerBtn);
    }

    public String getErrorMessage() {
        WebDriverWait wait = new WebDriverWait(WebDriverUtils.get(), Duration.ofSeconds(10));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(errorMessageBy)).getText().trim();
    }

    // BỔ SUNG: Hàm lấy thông báo lỗi định dạng của ô Email
    public String getEmailValidationError() {
        WebDriverWait wait = new WebDriverWait(WebDriverUtils.get(), Duration.ofSeconds(10));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(emailValidationErrorBy)).getText().trim();
    }
}