import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.concurrent.TimeUnit;

public class YandexTests {
    private WebDriverWait wait;
    private WebDriver driver;
    private By cssLocator = By.cssSelector("div[data-id='t177047760351003313'] span._nb-checkbox-flag._nb-checkbox-normal-flag");
    private By cssLocator2 = By.cssSelector("div[data-id='t180425460071531320'] span._nb-checkbox-flag._nb-checkbox-normal-flag");
    private By enterEmailButton = By.cssSelector("button.Button2");
    private By inputLogin = By.xpath("//input[@id='passp-field-login']");
    private By inputPass = By.cssSelector("input#passp-field-passwd");
    private By submitButton = By.cssSelector("button.Button2_type_submit");
    private By deleteButton = By.cssSelector("div[title='Удалить (Delete)']");
    private By writeLetterButton = By.cssSelector("a[href='#compose']");
    private By inputAddress = By.cssSelector("div#compose-field-1");
    private By inputSubject = By.cssSelector("input#compose-field-subject-4");
    private By sendEmailButton = By.cssSelector("button.Button2.Button2_pin_circle-circle." + "Button2_view_default.Button2_size_l");
    private By lightboxSentEmail = By.cssSelector("div.ComposeDoneScreen-Wrapper");
    private By lightboxSentEmailTitle = By.cssSelector("div.ComposeDoneScreen-Title");
    String login = "user@yandex.ru";
    String password = "password1234";
    String letterSubject = "letterSubject";

    @Before
    public void setUp() throws InterruptedException {
        System.setProperty("webdriver.chrome.driver", "drivers/chromedriver.exe");
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, 5);
        driver.navigate().to("https://mail.yandex.ru/?uid=611739712#inbox");
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        driver.findElement(enterEmailButton).click();
        driver.findElement(inputLogin).sendKeys(login);
        Thread.sleep(3000);
        driver.findElement(submitButton).click();
        driver.findElement(inputPass).sendKeys(password);
        Thread.sleep(3000);
        driver.findElement(enterEmailButton).click();
    }

    @After
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void ya_1_highlightingMessages() {
        driver.findElement(cssLocator).click();
        driver.findElement(cssLocator2).click();
    }

    @Test
    public void ya_2_selectedMessageDeletion() throws InterruptedException {
        driver.findElement(cssLocator).click();
        driver.findElement(deleteButton).click();
        wait.until(ExpectedConditions.invisibilityOfElementLocated(cssLocator));
        Assert.assertTrue("Удаление не выполнилось", driver.findElements(cssLocator).size() == 0);
    }

    @Test
    public void ya_3_SendingMessage() throws InterruptedException {
        driver.findElement(writeLetterButton).click();
        driver.findElement(inputAddress).sendKeys(login);
        driver.findElement(inputSubject).sendKeys(letterSubject);
        driver.findElement(sendEmailButton).click();
        String actualTitle = driver.findElement(lightboxSentEmailTitle).getText();
        String expectedTitle = "Письмо отправлено";
        Assert.assertTrue("Не отобразилось сообщение об отправке",
                driver.findElement(lightboxSentEmail).isDisplayed());
        Assert.assertEquals("Неправильный текст сообщения об отправке", expectedTitle, actualTitle);
    }
}
