package vnuk_2026.pages;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import vnuk_2026.utils.WebDriverUtils;

public class HomePage {

    private final By loginMenuBy = By.linkText("Login");
    private final By bookTicketMenuBy = By.linkText("Book ticket");
    private final By timetableMenuBy = By.linkText("Timetable");
    private final By myTicketMenuBy = By.linkText("My ticket");
    private final By ticketPriceMenuBy = By.linkText("Ticket price");
    private final By changePasswordMenuBy = By.linkText("Change password");
    private final By greetingLblBy = By.cssSelector("div.account strong");
    private final By logoutMenuBy = By.linkText("Log out");
    private final By registerMenuBy = By.linkText("Register");

    public void open() {
        String aut = System.getProperty("autEnvironment", "B2").toLowerCase();
        String baseUrl = String.format("http://railwayb%s.somee.com", aut.replace("b", ""));
        WebDriverUtils.get().get(baseUrl);
    }

    public void navigateToLoginPage() {
        WebDriverUtils.get().findElement(loginMenuBy).click();
    }

    public void navigateToBookTicketPage() {
        WebDriverUtils.get().findElement(bookTicketMenuBy).click();
    }

    public void navigateToTimeTablePage() {
        WebDriverUtils.get().findElement(timetableMenuBy).click();
    }

    public void navigateToMyTicketPage() {
        WebDriverUtils.get().findElement(myTicketMenuBy).click();
    }

    public void navigateToTicketPricePage() {
        WebDriverUtils.get().findElement(ticketPriceMenuBy).click();
    }

    public void navigateToChangePasswordPage() {
        WebDriverUtils.get().findElement(changePasswordMenuBy).click();
    }

    public String getGreetingText() {
        try {
            // Khởi tạo WebDriverWait với thời gian chờ tối đa 10 giây
            WebDriverWait wait = new WebDriverWait(WebDriverUtils.get(), Duration.ofSeconds(10));

            // Chờ cho đến khi element lời chào xuất hiện (Visible) trên màn hình
            wait.until(ExpectedConditions.visibilityOfElementLocated(greetingLblBy));

            // Sau khi xuất hiện thì mới lấy text
            return WebDriverUtils.get().findElement(greetingLblBy).getText();
        } catch (Exception e) {
            System.out.println("Không tìm thấy lời chào: " + e.getMessage());
            return "";
        }
    }

    public void logout() {
        WebDriverUtils.get().findElement(logoutMenuBy).click();
    }
    public void navigateToRegisterPage() {
    WebDriverUtils.get().findElement(registerMenuBy).click();
    }
}