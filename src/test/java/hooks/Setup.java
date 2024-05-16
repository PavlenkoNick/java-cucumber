package hooks;

import io.cucumber.java.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class Setup {
    public static WebDriver driver;

    @Before
    public void setWebDriver() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--ignore-ssl-errors=yes");
        options.addArguments("--ignore-certificate-errors");
//        options.addArguments("--start-maximized");
        options.addArguments("--headless=new");
        options.addArguments("--window-size=1920,1080");
        this.driver = new ChromeDriver(options);
    }
}
