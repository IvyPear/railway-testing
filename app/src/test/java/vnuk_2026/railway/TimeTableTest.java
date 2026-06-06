package vnuk_2026.railway;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import vnuk_2026.pages.HomePage;
import vnuk_2026.pages.LoginPage;
import vnuk_2026.utils.WebDriverUtils;

public class TimeTableTest extends RailwayTest {

    HomePage homePage = new HomePage();
    LoginPage loginPage = new LoginPage();

    @Test
    public void tc015_verifyTicketPriceFlowFromTimetable() {
        // 1. Khai báo dữ liệu kiểm thử (Test Data)
        String email = "thuylam@gmail.com";
        String password = "123456789";
        String departStation = "Sài Gòn";
        String arriveStation = "Phan Thiết";
        
        // Chuỗi mong đợi hiển thị tại tiêu đề bảng giá vé
        String expectedHeaderTitle = "Ticket price from " + departStation + " to " + arriveStation;

        // 2. Các bước thực hiện (Test Steps)
        System.out.println("--- Chạy TC015: Kiểm tra luồng hiển thị Giá vé từ trang Lịch trình ---");
        System.out.println("Bước 1: Truy cập trang chủ Railway");
        homePage.open();

        System.out.println("Bước 2: Di chuyển đến giao diện Đăng nhập và thực hiện đăng nhập");
        homePage.navigateToLoginPage();
        loginPage.login(email, password);
        
        // Đảm bảo đăng nhập thành công trước khi đi tiếp
        Assert.assertTrue(homePage.getGreetingText().contains(email), "LỖI: Đăng nhập thất bại!");

        System.out.println("Bước 3: Điều hướng sang trang Timetable (Lịch trình)");
        // Giả định hàm điều hướng sang Timetable của bạn là navigateToTimetablePage()
        // Nếu bạn chưa viết hàm này trong HomePage, bạn có thể thay bằng: 
        // WebDriverUtils.get().findElement(By.linkText("Timetable")).click();
        homePage.navigateToTimeTablePage(); 

        // Khởi tạo WebDriverWait để chờ các phần tử render ổn định
        WebDriverWait wait = new WebDriverWait(WebDriverUtils.get(), Duration.ofSeconds(10));

        System.out.println("Bước 4: Tìm đúng hàng có Ga đi là '" + departStation + "' và Ga đến là '" + arriveStation + "' rồi bấm 'check price'");
        
        /**
         * Giải thích XPath động dưới đây:
         * - Tìm hàng (tr) bất kỳ nằm trong bảng Lịch trình (WideTable).
         * - Hàng đó phải thỏa mãn: có 1 ô td chứa chữ 'Sài Gòn' VÀ có 1 ô td chứa chữ 'Phan Thiết'.
         * - Từ hàng tìm được, đi sâu vào trong lấy thẻ <a> có chứa text là 'check price'.
         */
        String xpathCheckPriceLink = String.format(
                "//table[contains(@class,'WideTable')]//tr[td[text()='%s'] and td[text()='%s']]//a[text()='check price']", 
                departStation, arriveStation);
        
        By checkPriceLinkBy = By.xpath(xpathCheckPriceLink);
        
        // Chờ link check price hiển thị và click
        wait.until(ExpectedConditions.elementToBeClickable(checkPriceLinkBy)).click();

        System.out.println("Bước 5: Xác thực hệ thống chuyển hướng đúng sang trang Ticket Price và hiển thị đúng bảng giá");
        
        // Định vị thẻ <th> chứa tiêu đề bảng giá vé dựa theo HTML bạn cung cấp
        By priceTableHeaderBy = By.xpath("//table[contains(@class,'MedTable')]//th[@colspan='7']");
        
        // Chờ tiêu đề xuất hiện trên giao diện
        wait.until(ExpectedConditions.visibilityOfElementLocated(priceTableHeaderBy));
        
        // Lấy text thực tế từ giao diện
        String actualHeaderTitle = WebDriverUtils.get().findElement(priceTableHeaderBy).getText().trim();

        // 3. Xác thực kết quả (Assertion)
        System.out.println("Thực tế hiển thị: " + actualHeaderTitle);
        System.out.println("Kỳ vọng hiển thị: " + expectedHeaderTitle);
        
        Assert.assertEquals(actualHeaderTitle, expectedHeaderTitle, 
                "LỖI: Trang Giá vé hiển thị sai thông tin chặng đi hoặc không đúng tiêu đề yêu cầu!");
        
        System.out.println("Kết quả: TC015 Pass - Luồng chuyển tiếp từ Timetable sang Ticket Price hoạt động chính xác.");
    }
}