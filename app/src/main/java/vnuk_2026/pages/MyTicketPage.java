package vnuk_2026.pages;

import java.time.Duration;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import vnuk_2026.utils.WebDriverUtils;

public class MyTicketPage {

    // Lấy tất cả các thẻ tr (dòng) có chứa td (ô dữ liệu) trong các bảng hiển thị
    private final By tableRowsBy = By.xpath("//table//tr[td]");

    public boolean isTicketDisplayed(String departStation, String arriveStation, String seatType, int amount) {
        try {
            // Chờ tối đa 10 giây cho bảng dữ liệu hiển thị trên màn hình
            WebDriverWait wait = new WebDriverWait(WebDriverUtils.get(), Duration.ofSeconds(10));
            wait.until(ExpectedConditions.visibilityOfElementLocated(tableRowsBy));
        } catch (Exception e) {
            // Nếu không có dòng dữ liệu nào, bảng trống -> trả về false
            return false;
        }

        // Lấy danh sách toàn bộ các dòng hiện có
        List<WebElement> rows = WebDriverUtils.get().findElements(tableRowsBy);

        for (WebElement row : rows) {
            List<WebElement> cells = row.findElements(By.tagName("td"));

            // Đảm bảo dòng đó có đủ số lượng ô dữ liệu (dựa theo HTML bạn cung cấp có 11
            // thẻ td)
            if (cells.size() >= 10) {
                // Trích xuất text chính xác theo thứ tự Index của HTML
                String rowDepart = cells.get(1).getText().trim();
                String rowArrive = cells.get(2).getText().trim();
                String rowSeatType = cells.get(3).getText().trim();
                String rowAmount = cells.get(8).getText().trim();

                // So sánh tuyệt đối (bằng chữ 100%)
                if (rowDepart.equals(departStation) &&
                        rowArrive.equals(arriveStation) &&
                        rowSeatType.equals(seatType) &&
                        rowAmount.equals(String.valueOf(amount))) {
                    return true; // Tìm thấy dòng trùng khớp hoàn toàn!
                }
            }
        }
        return false; // Duyệt hết bảng nhưng không tìm thấy vé
    }

    public void cancelTicket(String departStation, String arriveStation, String seatType, int amount) {
        List<WebElement> rows = WebDriverUtils.get().findElements(tableRowsBy);

        for (WebElement row : rows) {
            List<WebElement> cells = row.findElements(By.tagName("td"));
            if (cells.size() >= 10) {
                String rowDepart = cells.get(1).getText().trim();
                String rowArrive = cells.get(2).getText().trim();
                String rowSeatType = cells.get(3).getText().trim();
                String rowAmount = cells.get(8).getText().trim();

                // Nếu tìm thấy đúng vé cần hủy
                if (rowDepart.equals(departStation) &&
                        rowArrive.equals(arriveStation) &&
                        rowSeatType.equals(seatType) &&
                        rowAmount.equals(String.valueOf(amount))) {

                    // Lấy nút Cancel bên trong dòng đó
                    WebElement cancelBtn = row.findElement(By.cssSelector("input[value='Cancel']"));

                    // Click nút Cancel
                    JavascriptExecutor js = (JavascriptExecutor) WebDriverUtils.get();
                    js.executeScript("arguments[0].click();", cancelBtn);

                    // Xử lý Popup xác nhận hủy của trình duyệt (Nhấn OK)
                    WebDriverWait wait = new WebDriverWait(WebDriverUtils.get(), Duration.ofSeconds(5));
                    wait.until(ExpectedConditions.alertIsPresent()).accept();

                    return; // Thoát hàm sau khi hủy xong
                }
            }
        }
    }
}