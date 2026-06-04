package vnuk_2026.pages;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import vnuk_2026.utils.WebDriverUtils;

public class TicketPricePage {

    // Locator bắt chính xác thẻ <th> chứa tiêu đề bảng giá
    private final By pricePageHeaderBy = By.xpath("//tr[@class='TableSmallHeader']/th");

    public void clickCheckPriceForRoute(String routeName) {
        // XPath bám sát 100% HTML của bạn: Tìm thẻ tr chứa thẻ li có tên tuyến, sau đó trỏ tới thẻ a nằm cùng trong tr đó
        String xpath = String.format("//tr[.//li[contains(normalize-space(), '%s')]]//a", routeName);
        
        WebDriverWait wait = new WebDriverWait(WebDriverUtils.get(), Duration.ofSeconds(10));
        WebElement checkPriceBtn = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpath)));
        
        // Dùng JavascriptExecutor để click xuyên qua thanh Footer
        JavascriptExecutor js = (JavascriptExecutor) WebDriverUtils.get();
        js.executeScript("arguments[0].click();", checkPriceBtn);
    }

    public String getPricePageHeader() {
        WebDriverWait wait = new WebDriverWait(WebDriverUtils.get(), Duration.ofSeconds(10));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(pricePageHeaderBy)).getText().trim();
    }
}