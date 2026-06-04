package vnuk_2026.railway;

import java.time.LocalDate;
import org.testng.Assert;
import org.testng.annotations.Test;
import vnuk_2026.models.BookTicketForm;
import vnuk_2026.pages.*;

public class MyTicketTest extends RailwayTest {

    HomePage homePage = new HomePage();
    LoginPage loginPage = new LoginPage();
    BookTicketPage bookTicketPage = new BookTicketPage();
    MyTicketPage myTicketPage = new MyTicketPage();

    BookTicketForm sampleTicket = BookTicketForm.builder()
            .deparDate(LocalDate.now().plusWeeks(1))
            .departFrom("Đà Nẵng")
            .arriveTo("Nha Trang")
            .seatType(BookTicketForm.SeatType.HARD_BED)
            .amount(1)
            .build();

    @Test
    public void tc004_verifyTicketAppearsInMyTicketPageAfterBooking() {
        homePage.open();
        homePage.navigateToLoginPage();
        loginPage.login("example1@udn.vn", "111111111");

        homePage.navigateToBookTicketPage();
        bookTicketPage.bookTicket(sampleTicket);

        homePage.navigateToMyTicketPage();

        boolean isFound = myTicketPage.isTicketDisplayed(
                sampleTicket.getDepartFrom(),
                sampleTicket.getArriveTo(),
                sampleTicket.getSeatType().getText(),
                sampleTicket.getAmount()
        );
        Assert.assertTrue(isFound, "Tấm vé vừa đặt không xuất hiện trong danh sách My Ticket!");
    }
}