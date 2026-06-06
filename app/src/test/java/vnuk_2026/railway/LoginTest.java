package vnuk_2026.railway;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import vnuk_2026.pages.HomePage;
import vnuk_2026.pages.LoginPage;
import vnuk_2026.utils.WebDriverUtils;

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

    @Test
    public void tc014_verifyLoginWithEmptyFields() {
        System.out.println("--- Chạy TC014: Đăng nhập để trống tất cả các trường ---");
        System.out.println("Bước 1: Truy cập trang chủ Railway");
        homePage.open();

        System.out.println("Bước 2: Di chuyển đến giao diện Đăng nhập");
        homePage.navigateToLoginPage();

        System.out.println("Bước 3: Để trống cả Username và Password rồi nhấn Đăng nhập");
        // Truyền chuỗi rỗng "" để mô phỏng hành động không nhập gì
        loginPage.login("", ""); 

        // Khởi tạo WebDriverWait để xử lý bất đồng bộ khi thông báo lỗi render ra UI
        WebDriverWait wait = new WebDriverWait(WebDriverUtils.get(), Duration.ofSeconds(5));

        // Khai báo các bộ định vị (Locators) theo cấu trúc HTML bạn cung cấp
        By mainErrorBy = By.cssSelector("p.message.error.LoginForm");
        By usernameErrorBy = By.xpath("//label[@for='username' and contains(@class, 'validation-error')]");
        By passwordErrorBy = By.xpath("//label[@for='password' and contains(@class, 'validation-error')]");

        System.out.println("Bước 4: Xác thực các thông báo lỗi hiển thị chuẩn xác trên UI");
        
        // 1. Chờ và kiểm tra thông báo lỗi tổng của FormLoginForm
        wait.until(ExpectedConditions.visibilityOfElementLocated(mainErrorBy));
        String actualMainError = WebDriverUtils.get().findElement(mainErrorBy).getText().trim();
        String expectedMainError = "There was a problem with your login and/or errors exist in your form.";
        Assert.assertEquals(actualMainError, expectedMainError, "LỖI: Thông báo lỗi tổng của Form hiển thị sai!");

        // 2. Chờ và kiểm tra thông báo lỗi riêng của ô Username
        wait.until(ExpectedConditions.visibilityOfElementLocated(usernameErrorBy));
        String actualUsernameError = WebDriverUtils.get().findElement(usernameErrorBy).getText().trim();
        String expectedUsernameError = "You must specify a username.";
        Assert.assertEquals(actualUsernameError, expectedUsernameError, "LỖI: Thông báo lỗi để trống Username hiển thị sai!");

        // 3. Chờ và kiểm tra thông báo lỗi riêng của ô Password
        wait.until(ExpectedConditions.visibilityOfElementLocated(passwordErrorBy));
        String actualPasswordError = WebDriverUtils.get().findElement(passwordErrorBy).getText().trim();
        String expectedPasswordError = "You must specify a password.";
        Assert.assertEquals(actualPasswordError, expectedPasswordError, "LỖI: Thông báo lỗi để trống Password hiển thị sai!");
        
        System.out.println("Kết quả: TC014 Pass - Hệ thống hiển thị đầy đủ và chính xác cả 3 thông báo lỗi.");
    }
}