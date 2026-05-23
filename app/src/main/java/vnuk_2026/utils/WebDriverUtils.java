package vnuk_2026.utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class WebDriverUtils {
    
    private static final ThreadLocal<WebDriver> drivers = new ThreadLocal<>();

    public static WebDriver get() {
        if (drivers.get() == null) {
            // if there is no driver, init ChromeDriver by default
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--guest");
            drivers.set(
                new ChromeDriver(options)
            );
        }
        return drivers.get();
    }

}
