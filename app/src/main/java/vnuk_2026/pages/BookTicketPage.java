package vnuk_2026.pages;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import vnuk_2026.models.BookTicketForm;
import vnuk_2026.utils.WebDriverUtils;

public class BookTicketPage {

    private final By departDateDrdBy = By.name("Date");
    private final By departFromDrdBy = By.name("DepartStation");
    private final By arriveToDrdBy = By.name("ArriveStation");
    private final By seatTypeDrdBy = By.name("SeatType");
    private final By ticketAmountDrdBy = By.name("TicketAmount");
    private final By bookTicketBtnBy = By.cssSelector("input[type=submit][value='Book ticket']");

    // Định vị thông báo thành công và dòng dữ liệu duy nhất trong bảng xác nhận
    private final By successMsgBy = By.cssSelector("#content h1");
    private final By confirmationRowCellsBy = By.xpath("//table[contains(@class,'MyTable') or contains(@class,'Grid')]//tr[@class='OddRow' or td][1]/td");

    public void bookTicket(BookTicketForm data) {
        selectDepartDate(data.getDeparDate());
        selectDepart(data.getDepartFrom());
        selectArrive(data.getArriveTo());
        selectSeatType(data.getSeatType());
        selectTicketAmount(data.getAmount());
        clickBookTicketButton();
    }
    
    public void selectDepartDate(LocalDate date) {
        Select select = new Select(WebDriverUtils.get().findElement(departDateDrdBy));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yyyy");
        select.selectByVisibleText(date.format(formatter));
    }

    public void selectTicketAmount(int amount) {
        Select select = new Select(WebDriverUtils.get().findElement(ticketAmountDrdBy));
        select.selectByVisibleText(String.valueOf(amount));
    }

    public void selectDepart(String from) {
        Select select = new Select(WebDriverUtils.get().findElement(departFromDrdBy));
        select.selectByVisibleText(from);
        try { Thread.sleep(1000); } catch (InterruptedException ignored) {}
    }

    public void selectArrive(String to) {
        Select select = new Select(WebDriverUtils.get().findElement(arriveToDrdBy));
        select.selectByVisibleText(to);
    }

    public void selectSeatType(BookTicketForm.SeatType seatType) {
        Select select = new Select(WebDriverUtils.get().findElement(seatTypeDrdBy));
        select.selectByVisibleText(seatType.getText());
    }

    public void clickBookTicketButton() {
        JavascriptExecutor js = (JavascriptExecutor) WebDriverUtils.get();
        js.executeScript("window.scrollTo(0, document.body.scrollHeight);");
        WebDriverUtils.get().findElement(bookTicketBtnBy).click();
    }

    public String getSelectedDepartStation() {
        Select select = new Select(WebDriverUtils.get().findElement(departFromDrdBy));
        return select.getFirstSelectedOption().getText();
    }

    public String getSelectedArriveStation() {
        Select select = new Select(WebDriverUtils.get().findElement(arriveToDrdBy));
        return select.getFirstSelectedOption().getText();
    }

    // Đợi trang kết quả xuất hiện ổn định
    private void waitForConfirmationPageLoad() {
        WebDriverWait wait = new WebDriverWait(WebDriverUtils.get(), Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(successMsgBy));
    }

    public String getSuccessMessageText() {
        waitForConfirmationPageLoad();
        return WebDriverUtils.get().findElement(successMsgBy).getText();
    }

    // Đọc text theo vị trí ô chỉ mục (index) cụ thể trong dòng dữ liệu bảng xác nhận
    public String getConfirmationDepartStation() {
        waitForConfirmationPageLoad();
        return WebDriverUtils.get().findElements(confirmationRowCellsBy).get(0).getText().trim();
    }

    public String getConfirmationArriveStation() {
        waitForConfirmationPageLoad();
        return WebDriverUtils.get().findElements(confirmationRowCellsBy).get(1).getText().trim();
    }

    public String getConfirmationSeatType() {
        waitForConfirmationPageLoad();
        return WebDriverUtils.get().findElements(confirmationRowCellsBy).get(2).getText().trim();
    }

    public String getConfirmationAmount() {
        waitForConfirmationPageLoad();
        return WebDriverUtils.get().findElements(confirmationRowCellsBy).get(5).getText().trim();
    }
}