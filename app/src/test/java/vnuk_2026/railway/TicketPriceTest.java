package vnuk_2026.railway;

import org.testng.Assert;
import org.testng.annotations.Test;
import vnuk_2026.pages.HomePage;
import vnuk_2026.pages.TicketPricePage;

public class TicketPriceTest extends RailwayTest {
    
    HomePage homePage = new HomePage();
    TicketPricePage ticketPricePage = new TicketPricePage();

    @Test
    public void tc001_verifyTicketPricePageDisplaysCorrectRoute() {
        String route = "Sài Gòn to Phan Thiết";
        String expectedHeader = "Ticket price from Sài Gòn to Phan Thiết";

        homePage.open();
        homePage.navigateToTicketPricePage();
        
        ticketPricePage.clickCheckPriceForRoute(route);
        String actualHeader = ticketPricePage.getPricePageHeader();
        
        Assert.assertEquals(actualHeader, expectedHeader, 
                "Lỗi điều hướng: Hệ thống không tải đúng bảng giá của tuyến " + route);
    }
}