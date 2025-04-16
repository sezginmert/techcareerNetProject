import base.BaseTest;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.LoginPage;
import pages.MainPage;
import pages.RegisterPage;

public class LoginTest extends BaseTest {


    LoginPage loginPage = new LoginPage();
    RegisterPage registerPage = new RegisterPage();
    MainPage mainPage = new MainPage();

    HomePage homePage = new HomePage();

    @Test(description = "Basarili kullanici giris kontrolu")
    public void loginSuccessFullControl(){
        registerPage.singUpLogin();
        loginPage.loginEmail(loginMail);
        loginPage.loginPassword(loginPassword);
        loginPage.loginButton();

        homePage.loginControl("testUser");

    }


    @Test(description = "Basarisiz kullanici giris kontrolu")
    public void loginUnSuccessFullControl(){
        registerPage.singUpLogin();
        loginPage.loginEmail(loginMail);
        loginPage.loginPassword("545635");
        loginPage.loginButton();

        mainPage.errorControlMessage("Your email or password is incorrect!");

    }
}
