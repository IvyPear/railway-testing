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
        String existingEmail = "example1@udn.vn";
        String dummyPass = "123456789";
        String dummyPid = "012345678912";
        String expectedError = "This email address is already in use.";

        homePage.open();
        homePage.navigateToRegisterPage();
        registerPage.register(existingEmail, dummyPass, dummyPass, dummyPid);
        
        Assert.assertEquals(registerPage.getErrorMessage(), expectedError, "Lỗi: Thông báo đăng ký trùng email không chính xác!");
    }

    @Test
    public void tc007_verifyRegisterWithInvalidEmailFormatShowsError() {
        String invalidEmail = "mygmail.com"; 
        String password = "123456789";
        String pid = "111111111";
        String expectedError = "Invalid email address";

        homePage.open();
        homePage.navigateToRegisterPage();
        registerPage.register(invalidEmail, password, password, pid);
        
        Assert.assertEquals(registerPage.getEmailValidationError(), expectedError, "Lỗi: Thông báo sai định dạng email không hiển thị chính xác!");
    }

    @Test
    public void tc008_verifyRegisterWithInvalidPasswordLengthShowsError() {
        String email = "my1@gmail.com"; 
        String invalidPass = "12345";
        String pid = "123456789";
        String expectedError = "Invalid password length";

        homePage.open();
        homePage.navigateToRegisterPage();
        registerPage.register(email, invalidPass, invalidPass, pid);
        
        Assert.assertEquals(registerPage.getPasswordValidationError(), expectedError, "Lỗi: Thông báo sai độ dài mật khẩu không hiển thị chính xác!");
    }

    // BỔ SUNG: Kịch bản test lỗi Confirm Password không khớp với Password
    @Test
    public void tc009_verifyRegisterWithMismatchedPasswordsShowsError() {
        // Dữ liệu đầu vào cố tình làm sai lệch mật khẩu xác nhận
        String email = "my1@gmail.com"; 
        String password = "123456789";
        String confirmPassword = "123456790"; 
        String pid = "123456789";
        
        // Kết quả mong muốn: Lỗi validation chữ đỏ dưới ô Confirm Password
        String expectedError = "The two passwords do not match";

        // 1. Mở trang chủ
        homePage.open();
        
        // 2. Chuyển sang trang Đăng ký
        homePage.navigateToRegisterPage();
        
        // 3. Thực hiện điền form
        registerPage.register(email, password, confirmPassword, pid);
        
        // 4. Lấy câu thông báo lỗi nhỏ màu đỏ dưới ô nhập confirm password và so sánh
        String actualError = registerPage.getConfirmPasswordValidationError();
        Assert.assertEquals(actualError, expectedError, "Lỗi: Thông báo xác nhận mật khẩu không khớp hiển thị không chính xác!");
    }
}