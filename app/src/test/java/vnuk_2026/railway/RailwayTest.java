package vnuk_2026.railway;

import org.testng.annotations.AfterMethod;
import vnuk_2026.utils.WebDriverUtils;

public class RailwayTest {
    
    @AfterMethod
    public void quitDriver() {
        WebDriverUtils.quit();
    }
}