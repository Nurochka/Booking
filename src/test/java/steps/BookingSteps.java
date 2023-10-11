package steps;

import com.codeborne.selenide.*;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;

import java.awt.*;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

public class BookingSteps {

    private String city;


    @Before
    public void setup() {
        Configuration.browser = "chrome";
        Configuration.timeout = 5000;
        WebDriverManager.chromedriver().setup();
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--incognito");
        chromeOptions.addArguments("--remote-allow-origins=*");

        WebDriver driver = new ChromeDriver(chromeOptions);
        WebDriverRunner.setWebDriver(driver);
        getWebDriver().manage().window().maximize();
    }

   @After
    public void close() {
        getWebDriver().quit();
    }


    @Given("User is looking for hotel in {string} city")
    public void userIsLookingForHotelInCity(String city) {
        this.city = city;
    }

    @When("User does search")
    public void userDoesSearch() throws InterruptedException, AWTException {
        open("https://www.booking.com/");
        Thread.sleep(5000);

        Actions action = new Actions(getWebDriver());
        action.sendKeys(Keys.ESCAPE).perform();

        $(By.xpath("//input[@name='ss']")).shouldBe(Condition.visible, Duration.ofSeconds(10)).sendKeys(city);
        Thread.sleep(2000);
        $(By.xpath("//button[@type='submit']")).click();
        Thread.sleep(2000);
        $(".a1139161bf").shouldBe(Condition.visible, Duration.ofSeconds(10)).click();
    }

    @Then("Hotel {string} should be on the first page")
    public void hotelShouldBeOnTheFirstPage(String hotel) {
        List<String> hotelsNames = new ArrayList<>();
        for (SelenideElement element : $$(By.xpath("//div[@data-testid='title']"))) {
            hotelsNames.add((element.getText()));
        }

        Assert.assertTrue(hotelsNames.contains(hotel));
    }

    @And("Hotel {string} rating is {string}")
    public void hotelRoyalLancasterLondonRatingIs(String hotel, String rating) throws InterruptedException {
        $(By.xpath("//div[contains(text()," + "'" + hotel + "')" + "]")).click();
        switchTo().window(1);
        Thread.sleep(5000);
        String currentRating = $(By.xpath("//div[@data-testid='review-score-right-component']//div[@class='a3b8729ab1 d86cee9b25']")).getText();
        Assert.assertEquals(currentRating, rating);

    }
}
