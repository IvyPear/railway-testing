package vnuk_2026.railway;

import org.testng.Assert;
import org.testng.annotations.Test;
import vnuk_2026.pages.HomePage;
import vnuk_2026.pages.RegisterPage;

public class RegisterTest extends RailwayTest {
    
    HomePage homePage = new HomePage();
    RegisterPage registerPage = new RegisterPage();

    @Test
    public void tc006_verifyRegisterWithExistingEmailShowsError() {
        // Dữ liệu đầu vào
        String existingEmail = "example1@udn.vn";
        String dummyPass = "111111111";
        String dummyPid = "111111111";
        
        // Kết quả mong muốn
        String expectedError = "This email address is already in use.";

        // 1. Mở trang chủ
        homePage.open();
        
        // 2. Chuyển sang trang Đăng ký
        homePage.navigateToRegisterPage();
        
        // 3. Thực hiện điền form với email đã tồn tại
        registerPage.register(existingEmail, dummyPass, dummyPass, dummyPid);
        
        // 4. Lấy câu thông báo lỗi thực tế trên màn hình
        String actualError = registerPage.getErrorMessage();
        
        // 5. Kiểm tra kết quả
        Assert.assertEquals(actualError, expectedError, "Lỗi: Thông báo đăng ký trùng email không chính xác!");
    }
}