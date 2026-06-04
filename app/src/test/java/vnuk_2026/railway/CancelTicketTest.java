package vnuk_2026.railway;

import java.time.LocalDate;
import org.testng.Assert;
import org.testng.annotations.Test;
import vnuk_2026.models.BookTicketForm;
import vnuk_2026.pages.*;

public class CancelTicketTest extends RailwayTest {
    
    HomePage homePage = new HomePage();
    LoginPage loginPage = new LoginPage();
    BookTicketPage bookTicketPage = new BookTicketPage();
    MyTicketPage myTicketPage = new MyTicketPage();

    BookTicketForm sampleTicket = BookTicketForm.builder()
            .deparDate(LocalDate.now().plusWeeks(1))
            .departFrom("Quảng Ngãi")
            .arriveTo("Sài Gòn")
            .seatType(BookTicketForm.SeatType.SOFT_BED)
            .amount(9) // Đổi sang số 9 để đảm bảo độc nhất vô nhị
            .build();

    @Test
    public void tc002_verifyUserCanCancelTicketSuccessfully() {
        // 1. Đăng nhập hệ thống
        homePage.open();
        homePage.navigateToLoginPage();
        loginPage.login("example1@udn.vn", "111111111");

        // 2. Chủ động đặt 1 vé mới để có data test xóa (tránh xóa nhầm vé cũ của bạn)
        homePage.navigateToBookTicketPage();
        bookTicketPage.bookTicket(sampleTicket);

        // 3. Vào trang danh sách vé
        homePage.navigateToMyTicketPage();

        // 4. Gọi hàm hủy tấm vé vừa đặt
        myTicketPage.cancelTicket(
                sampleTicket.getDepartFrom(),
                sampleTicket.getArriveTo(),
                sampleTicket.getSeatType().getText(),
                sampleTicket.getAmount()
        );

        // Đợi 1 chút để trang reload lại bảng dữ liệu sau khi Alert đóng
        try { Thread.sleep(2000); } catch (InterruptedException ignored) {}

        // 5. Kiểm tra vé đã thực sự biến mất (Kỳ vọng isFound trả về false)
        boolean isFound = myTicketPage.isTicketDisplayed(
                sampleTicket.getDepartFrom(),
                sampleTicket.getArriveTo(),
                sampleTicket.getSeatType().getText(),
                sampleTicket.getAmount()
        );
        
        Assert.assertFalse(isFound, "Lỗi: Hệ thống không xóa dữ liệu, tấm vé vẫn còn tồn tại trong danh sách sau khi đã hủy!");
    }
}