import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Blink22_Blog {

	public static void main(String[] args) throws InterruptedException {
		WebElement submitBtn = null;
		String validationMessage;
		WebDriver driver = new ChromeDriver();
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.get("https://www.blink22.com/"); 
        Thread.sleep(1000);
        driver.manage().window().maximize();
        driver.findElement(By.linkText("Blog")).click();
        Thread.sleep(1000);
        System.out.println("\nTest Case 1: Extract Placeholder Texts");
        System.out.println(driver.findElement(By.id("fullname")).getAttribute("placeholder"));
        System.out.println(driver.findElement(By.id("email")).getAttribute("placeholder"));
        System.out.println("\nTest Case 2: Required Field Validation");
        driver.findElement(By.id("fullname")).clear();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        try {
            WebElement cookieBtn = wait.until(ExpectedConditions.elementToBeClickable(By.id("rcc-confirm-button")));
            cookieBtn.click();
        } catch (Exception e) {
            System.out.println("Cookie pop-up not found.");
        }
        try {
            submitBtn = wait.until(ExpectedConditions.elementToBeClickable(By.id("_form_5_submit")));
            submitBtn.click();
            validationMessage = submitBtn.getAttribute("validationMessage");
            if (!validationMessage.isEmpty()) {
                System.out.println("Validation message shown: " + validationMessage);
            } else {
                System.out.println("No validation message found.");
            }
        } catch (Exception e) {
            System.out.println("Cookie pop-up not found.");
        }
        System.out.println("\nTest Case 3: Invalid Email Format");
        driver.findElement(By.id("email")).sendKeys("test@invalid");
        submitBtn.click();
        Thread.sleep(1000);
        validationMessage = driver.findElement(By.id("email")).getAttribute("validationMessage");
        if (!validationMessage.isEmpty()) {
            System.out.println("Validation message shown: " + validationMessage);
        } else {
            System.out.println("No validation message found.");
        }
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(8));
        System.out.println("\nTest Case 4: Valid Submission");
        driver.findElement(By.id("fullname")).sendKeys("John Doe");
        driver.findElement(By.id("email")).clear();
        driver.findElement(By.id("email")).sendKeys("test@example.co");
        Thread.sleep(2000);
        submitBtn.click();
        Thread.sleep(2000); // Wait for thank-you message
        String formText = driver.findElement(By.className("_form-thank-you")).getText();
        System.out.println(formText);
        driver.quit();
        }	
	}


