package vnuk_2026.pages;

import org.openqa.selenium.By;

import vnuk_2026.utils.WebDriverUtils;

public class HomePage {

    private final By loginMenuBy = By.linkText("Login"); // locator
    private final By greetingLblBy = By.cssSelector("div.account strong");

    /**
     * Navigate to Railways home page (/)
     */
    public void open() {
        WebDriverUtils.get().get("http://railwayb1.somee.com");
    }

    /**
     * Navigate to Login page from nav bar
     * 
     */
    public void navigateToLoginPage() {
        WebDriverUtils.get().findElement(loginMenuBy).click();
    }

    public String getGreetingText() {
        return WebDriverUtils.get().findElement(greetingLblBy).getText();
    }

    
}
