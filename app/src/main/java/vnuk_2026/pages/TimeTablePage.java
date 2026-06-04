package vnuk_2026.pages;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import vnuk_2026.models.Train;
import vnuk_2026.utils.WebDriverUtils;

public class TimeTablePage {

    private final By tableHeadersBy = By.xpath("//tr/th");
    private final By tableRowBy = By.xpath("//tbody/tr");
    private final static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("H:mm");

    private List<String> getTableHeaders() {
        return WebDriverUtils.get()
                .findElements(tableHeadersBy)
                .stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }
    
    public LocalTime getDepartureTime(String departStation, String arriveStation) {
        var headers = getTableHeaders();
        int departStationColumnIndex = headers.indexOf("Depart Station") + 1;
        int arriveStationColumnIndex = headers.indexOf("Arrive Station") + 1;
        int departTimeColumnIndex = headers.indexOf("Depart Time") + 1;

        By xpath = By.xpath(String.format("//tr[td[%d][text()='%s'] and td[%d][text()='%s']]/td[%d]", 
                departStationColumnIndex, departStation, arriveStationColumnIndex, arriveStation, departTimeColumnIndex));
        WebElement departureTimeCell = WebDriverUtils.get().findElement(xpath);

        return LocalTime.parse(departureTimeCell.getText(), formatter);
    }

    public void clickBookTicketForRoute(String departStation, String arriveStation) {
        var headers = getTableHeaders();
        // Lấy chính xác vị trí cột dựa trên Header của bảng
        int departStationColumnIndex = headers.indexOf("Depart Station") + 1;
        int arriveStationColumnIndex = headers.indexOf("Arrive Station") + 1;
        
        // Tạo XPath để tìm đúng link "book ticket" trên hàng tương ứng
        By xpath = By.xpath(String.format("//tr[td[%d][text()='%s'] and td[%d][text()='%s']]/td[last()]/a", 
                departStationColumnIndex, departStation, arriveStationColumnIndex, arriveStation));
        
        // Tìm phần tử link
        WebElement bookTicketLink = WebDriverUtils.get().findElement(xpath);

        // Sử dụng JavascriptExecutor để click, vượt qua thanh Footer che khuất
        JavascriptExecutor js = (JavascriptExecutor) WebDriverUtils.get();
        js.executeScript("arguments[0].click();", bookTicketLink);
    }
}