package vnuk_2026.utils;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class WebDriverUtils {
    
    private static final ThreadLocal<WebDriver> drivers = new ThreadLocal<>();

    public static WebDriver get() {
        if (drivers.get() == null) {
            ChromeOptions options = new ChromeOptions();
            
            // Chỉ giữ lại --guest, xóa bỏ --headless=new để trình duyệt hiển thị lên màn hình
            options.addArguments("--guest");
            
            var driver = new ChromeDriver(options);
            driver.manage().window().setSize(new Dimension(1920, 1080));
            drivers.set(driver);
        }
        return drivers.get();
    }

    public static void quit() {
        WebDriver driver = drivers.get();
        if (driver != null) {
            driver.quit();
            drivers.remove();
        }
    }
}