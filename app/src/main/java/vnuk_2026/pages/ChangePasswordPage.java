package vnuk_2026.pages;

import org.openqa.selenium.By;
import vnuk_2026.utils.WebDriverUtils;

public class ChangePasswordPage {

    private final By currentPasswordTxtBy = By.id("currentPassword");
    private final By newPasswordTxtBy = By.id("newPassword");
    private final By confirmPasswordTxtBy = By.id("confirmPassword");
    private final By changePasswordBtnBy = By.cssSelector("input[type=submit][value='Change Password']");
    private final By successMsgBy = By.cssSelector(".message.success");

    public void changePassword(String currentPwd, String newPwd, String confirmPwd) {
        WebDriverUtils.get().findElement(currentPasswordTxtBy).sendKeys(currentPwd);
        WebDriverUtils.get().findElement(newPasswordTxtBy).sendKeys(newPwd);
        WebDriverUtils.get().findElement(confirmPasswordTxtBy).sendKeys(confirmPwd);
        WebDriverUtils.get().findElement(changePasswordBtnBy).click();
    }

    public String getSuccessMessageText() {
        return WebDriverUtils.get().findElement(successMsgBy).getText();
    }
}