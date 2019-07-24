import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class MainClass {

    private static WebDriver driver;

    public static void main(String[] args) {

        // test in Chrome
        System.setProperty("webdriver.chrome.driver","C:\\selenium\\drivers\\chromedriver.exe");
        driver = new ChromeDriver();

        // test in Edge
        //System.setProperty("webdriver.edge.driver","C:\\selenium\\drivers\\MicrosoftWebDriver.exe");
        //driver = new EdgeDriver();

        driver.manage().window().maximize();
        driver.get("https://gmail.com/");

        //find all elements on screens and store them in list
        List<WebElement> elementsOnLoginScreen = checkAllElementsOnScreen();
        //check/do something with elements

        try {
            String username = "stefan.bus91@gmail.com";
            String password = "thisisnotmyrealpassword";

            WebElement fieldUserName = getElementWhenPresent(By.id("identifierId"));
            fieldUserName.sendKeys(username, Keys.ENTER);

            WebElement fieldPassword = getElementWhenPresent(By.name("password"));
            fieldPassword.sendKeys(password, Keys.ENTER);

            //this element is loaded among the last ones, so when present, the page should be (almost) fully loaded, and we can check every element
            try {
                WebElement user = getElementWhenPresent(By.xpath("//*[@id=\"gb\"]/div[2]/div[3]/div/div[2]/div/a"));

                //check if elementname "aria-label" contains the mail address, if yes, login is successful
                if (user.getAttribute("aria-label").contains(username))
                    System.out.println("Login successful");
                else
                    System.err.println("Something is wrong..");
            }
            catch (TimeoutException e){
                System.err.println("Login failed, exiting the application");
                System.exit(-1);
            }

            //find all elements on screens and store them in list
            List<WebElement> elementsOnGmailScreen = checkAllElementsOnScreen();
            //check/do something with elements

            driver.close();
        }
        catch (TimeoutException e){
            System.err.println(e.getMessage());
            System.err.println("Test failed");
        }
    }

    private static WebElement getElementWhenPresent(By locator) {
        return new WebDriverWait(driver, 15).
                until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    private static WebElement getElementWhenClickable(By locator) {
        return new WebDriverWait(driver, 10).
            until(ExpectedConditions.elementToBeClickable(locator));
    }

    private static List<WebElement> checkAllElementsOnScreen(){
        List<WebElement> elementsOnLoginScreen = driver.findElements(By.xpath("//*"));
        System.out.println("There are " + elementsOnLoginScreen.size() + " elements on the page");
        return elementsOnLoginScreen;
    }
}
