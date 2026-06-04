package vnuk_2026.railway;

import java.time.LocalDate;
import org.testng.Assert;
import org.testng.annotations.Test;
import vnuk_2026.models.BookTicketForm;
import vnuk_2026.pages.*;

public class BookTicketTest extends RailwayTest {
    
    HomePage homePage = new HomePage();
    LoginPage loginPage = new LoginPage();
    BookTicketPage bookTicketPage = new BookTicketPage();
    TimeTablePage timeTablePage = new TimeTablePage();

    BookTicketForm sampleTicket = BookTicketForm.builder()
            .deparDate(LocalDate.now().plusWeeks(1))
            .departFrom("Đà Nẵng")
            .arriveTo("Nha Trang")
            .seatType(BookTicketForm.SeatType.HARD_BED)
            .amount(1)
            .build();

    @Test
    public void tc002_verifyTicketBookingConfirmationDetails() {
        homePage.open();
        homePage.navigateToLoginPage();
        loginPage.login("example1@udn.vn", "111111111");

        homePage.navigateToBookTicketPage();
        bookTicketPage.bookTicket(sampleTicket);

        Assert.assertEquals(bookTicketPage.getSuccessMessageText(), "Ticket Booked Successfully!");
        Assert.assertEquals(bookTicketPage.getConfirmationDepartStation(), sampleTicket.getDepartFrom());
        Assert.assertEquals(bookTicketPage.getConfirmationArriveStation(), sampleTicket.getArriveTo());
        Assert.assertEquals(bookTicketPage.getConfirmationSeatType(), sampleTicket.getSeatType().getText());
        Assert.assertEquals(bookTicketPage.getConfirmationAmount(), String.valueOf(sampleTicket.getAmount()));
    }

    @Test
    public void tc003_verifyAutofillWhenBookingFromTimetable() {
        homePage.open();
        homePage.navigateToLoginPage();
        loginPage.login("example1@udn.vn", "111111111");

        homePage.navigateToTimeTablePage();
        timeTablePage.clickBookTicketForRoute("Đà Nẵng", "Nha Trang");

        Assert.assertEquals(bookTicketPage.getSelectedDepartStation(), "Đà Nẵng");
        Assert.assertEquals(bookTicketPage.getSelectedArriveStation(), "Nha Trang");
    }
}