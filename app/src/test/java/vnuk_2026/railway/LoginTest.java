package vnuk_2026.railway;

import org.testng.Assert;
import org.testng.annotations.Test;
import vnuk_2026.pages.HomePage;
import vnuk_2026.pages.LoginPage;

public class LoginTest extends RailwayTest {

    HomePage homePage = new HomePage();
    LoginPage loginPage = new LoginPage();

    @Test
    public void tc011_verifyLoginSuccessfullyWithValidAccount() {
        // 1. Khai báo dữ liệu kiểm thử (Test Data)
        String email = "thuylam@gmail.com";
        String password = "123456789";

        // 2. Các bước thực hiện (Test Steps)
        System.out.println("--- Chạy TC011: Đăng nhập thành công với tài khoản hợp lệ ---");
        System.out.println("Bước 1: Truy cập trang chủ Railway");
        homePage.open();

        System.out.println("Bước 2: Di chuyển đến giao diện Đăng nhập");
        homePage.navigateToLoginPage();

        System.out.println("Bước 3: Tiến hành đăng nhập với tài khoản đúng");
        loginPage.login(email, password);

        // 3. Xác thực kết quả (Assertion)
        System.out.println("Bước 4: Kiểm tra lời chào hiển thị đúng Email người dùng");
        String greetingText = homePage.getGreetingText();
        Assert.assertTrue(greetingText.contains(email), 
                "LỖI: Đăng nhập thất bại! Giao diện không hiển thị lời chào đúng cho email: " + email);
    }

    @Test
    public void tc012_verifyLoginUnsuccessfullyWithInvalidPassword() {
        // 1. Khai báo dữ liệu kiểm thử (Test Data)
        String email = "thuylam@gmail.com";
        String invalidPassword = "wrong_password_999"; // Mật khẩu sai

        // 2. Các bước thực hiện (Test Steps)
        System.out.println("--- Chạy TC012: Đăng nhập thất bại khi dùng sai mật khẩu ---");
        System.out.println("Bước 1: Truy cập trang chủ Railway");
        homePage.open();

        System.out.println("Bước 2: Di chuyển đến giao diện Đăng nhập");
        homePage.navigateToLoginPage();

        System.out.println("Bước 3: Tiến hành đăng nhập với mật khẩu sai");
        loginPage.login(email, invalidPassword);

        // 3. Xác thực kết quả (Assertion)
        System.out.println("Bước 4: Kiểm tra hệ thống không được phép đăng nhập");
        String greetingText = homePage.getGreetingText();
        
        // Khi đăng nhập lỗi, hệ thống không tạo session và getGreetingText() phải trả về chuỗi rỗng ""
        Assert.assertEquals(greetingText, "", 
                "LỖI: Nghiêm trọng! Hệ thống vẫn cho phép đăng nhập thành công dù nhập sai mật khẩu.");
    }

    @Test
    public void tc013_verifyLogoutSuccessfully() {
        // 1. Khai báo dữ liệu kiểm thử (Test Data)
        String email = "thuylam@gmail.com";
        String password = "123456789";

        // 2. Các bước thực hiện (Test Steps)
        System.out.println("--- Chạy TC013: Kiểm tra chức năng Đăng xuất (Log out) ---");
        System.out.println("Bước 1: Truy cập trang chủ Railway");
        homePage.open();

        System.out.println("Bước 2: Di chuyển đến giao diện Đăng nhập");
        homePage.navigateToLoginPage();

        System.out.println("Bước 3: Tiến hành đăng nhập hợp lệ");
        loginPage.login(email, password);

        // Kiểm tra nhanh xem đã đăng nhập thành công chưa bằng cách check Lời chào (Greeting)
        Assert.assertTrue(homePage.getGreetingText().contains(email), 
                "LỖI: Đăng nhập thất bại, không thể tiến hành test bước Logout!");

        System.out.println("Bước 4: Nhấn vào nút Log out");
        // Hàm này sẽ tự động tìm đến menu chứa chữ "Log out" của bạn và click
        homePage.logout(); 

        // 3. Xác thực kết quả (Assertion)
        System.out.println("Bước 5: Kiểm tra xem hệ thống đã đăng xuất hoàn toàn chưa");
        String greetingTextAfterLogout = homePage.getGreetingText();
        
        // Khi đã logout thành công, hàm getGreetingText() sẽ không tìm thấy tên bạn và trả về chuỗi rỗng ""
        Assert.assertEquals(greetingTextAfterLogout, "", 
                "LỖI: Thao tác Log out thất bại! Người dùng vẫn chưa được đăng xuất khỏi hệ thống.");
    }
}