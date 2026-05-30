package vnuk_2026.railway;

import java.time.LocalTime;

import org.testng.Assert;
import org.testng.annotations.Test;

import vnuk_2026.pages.HomePage;
import vnuk_2026.pages.TimeTablePage;

public class TimeTableTest extends RailwayTest {
    
    HomePage homePage = new HomePage();
    TimeTablePage timeTablePage = new TimeTablePage();

    @Test
    public void verifyArrivalTimeForTrainFromDaNang() {
        homePage.open();

        homePage.navigateToTimeTablePage();

        Assert.assertEquals(
            timeTablePage.getDepartureTime("Đà Nẵng", "Sài Gòn"),
            LocalTime.of(7, 20)
        );

        Assert.assertEquals(
            timeTablePage.getDepartureTime("Đà Nẵng", "Nha Trang"),
            LocalTime.of(14, 0)
        );

        Assert.assertEquals(
            timeTablePage.getDepartureTime("Đà Nẵng", "Huế"),
            LocalTime.of(18, 0)
        );

        Assert.assertEquals(
            timeTablePage.getDepartureTime("Đà Nẵng", "Quảng Ngãi"),
            LocalTime.of(6, 30)
        );

    }

}
