package org.example;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

public class SeleniumTest {
    private WebDriver driver;
    private Faker faker;

    @BeforeEach
    void setUp(){
        System.setProperty("webdriver.chrome.driver","src/test/resources/drivers/chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--disable-web-security");
        options.addArguments("--user-data-dir=/tmp/chrome_dev_test");
        driver = new ChromeDriver(options);
        faker = new Faker();
    }

    @Test
    @DisplayName("Should open and close chrome browser")
    void shouldOpenProjectSite() throws InterruptedException {
        driver.get("https://odontologic-system.vercel.app/"); // request the page
        Thread.sleep(2000); // waits for 1s.
    }

    @Test
    @DisplayName("Should show pacientes view")
    void shouldOpenPacientesViewInProjectSite() throws InterruptedException {
        driver.get("https://odontologic-system.vercel.app/"); // request the page
        driver.findElement(By.xpath("//a[contains(text(),'Visualizar Pacientes')]")).click();
        Thread.sleep(3000);
        String currentUrl = driver.getCurrentUrl();
        assertEquals("https://odontologic-system.vercel.app/view", currentUrl);
        Thread.sleep(3000);
    }

    @Test
    @DisplayName("Should show add pacients view")
    void shouldOpenAddPacientesView() throws InterruptedException {
        driver.get("https://odontologic-system.vercel.app/"); // request the page
        driver.findElement(By.xpath("//a[contains(text(),'Adicionar Paciente')]")).click();
        Thread.sleep(3000);
        String currentUrl = driver.getCurrentUrl();
        assertEquals("https://odontologic-system.vercel.app/add", currentUrl);

        String inputName = driver.findElement(By.xpath("(//div[@class='add-resource']//input)[1]")).getAttribute("value");
        String inputIdade = driver.findElement(By.xpath("(//div[@class='add-resource']//input)[2]")).getAttribute("value");
        // Verifique se os inputs estao vazios
        assertTrue(inputName.isEmpty(), "Input is not empty");
        assertTrue(inputIdade.isEmpty(), "Input is not empty");
    }

    @Test
    @DisplayName("Should subscribe an pacients")
    void shouldSubscribeAnPacients() throws InterruptedException {
        driver.get("https://odontologic-system.vercel.app/add");

        // faker dinamicamente
        String randomName = faker.name().fullName();
        String randomAge = String.valueOf(faker.number().numberBetween(1, 100));

        driver.findElement(By.xpath("(//div[@class='add-resource']//input)[1]")).sendKeys(randomName);
        driver.findElement(By.xpath("(//div[@class='add-resource']//input)[2]")).sendKeys(randomAge);
        driver.findElement(By.xpath("//button[@type='submit']")).click();
        //verify if element showed
        WebElement successMessage = driver.findElement(By.xpath("//p[text()='Paciente cadastrado com sucesso!']"));
        assertTrue(successMessage.isDisplayed(), "Success message is not displayed");
        Thread.sleep(2500);
        String secondCurrentUrl = driver.getCurrentUrl();
        assertEquals("https://odontologic-system.vercel.app/view", secondCurrentUrl);
    }

    @Test
    @DisplayName("Should delete pacientes")
    void shouldDeletePaciente() throws InterruptedException {
        driver.get("https://odontologic-system.vercel.app/");
        // Mock patient data and set it to localStorage
        String nameFaker = faker.name().fullName();
        String ageFaker = String.valueOf(faker.number().numberBetween(1, 100));
        String patientData = String.format("[{\"id\": %d, \"name\": \"%s\", \"age\": \"%s\"}]",
                faker.number().numberBetween(1, 100),
                nameFaker,
                ageFaker);

        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("localStorage.setItem('patients', arguments[0]);", patientData);

        driver.get("https://odontologic-system.vercel.app/view");

        // Verify if paciente exist
        WebElement patientNameElement = driver.findElement(By.xpath("//li[text()='" + nameFaker + "']"));
        assertTrue(patientNameElement.isDisplayed(), "Patient name is not displayed");
        driver.findElement(By.xpath("//button[text()='Excluir']")).click();
        WebElement pacienteDeleted = driver.findElement(By.xpath("//p[text()='Paciente excluído com sucesso!']"));
        assertTrue(pacienteDeleted.isDisplayed(), "Patient deleted phrase is not displayed");
        Thread.sleep(2000);
    }

    @Test
    @DisplayName("Should show error when adding patient without name")
    void shouldShowErrorWithoutName() throws InterruptedException {
        driver.get("https://odontologic-system.vercel.app/add");

        String randomAge = String.valueOf(faker.number().numberBetween(1, 100));

        driver.findElement(By.xpath("(//div[@class='add-resource']//input)[2]")).sendKeys(randomAge);
        driver.findElement(By.xpath("//button[@type='submit']")).click();

        WebElement errorMessage = driver.findElement(By.xpath("//p[text()='Por favor, preencha todos os campos.']"));
        assertTrue(errorMessage.isDisplayed(), "Error message is not displayed");
        Thread.sleep(2000);
    }

    @Test
    @DisplayName("Should show error when adding patient without age")
    void shouldShowErrorWithoutAge() throws InterruptedException {
        driver.get("https://odontologic-system.vercel.app/add");

        String randomName = faker.name().fullName();

        driver.findElement(By.xpath("(//div[@class='add-resource']//input)[1]")).sendKeys(randomName);
        driver.findElement(By.xpath("//button[@type='submit']")).click();

        WebElement errorMessage = driver.findElement(By.xpath("//p[text()='Por favor, preencha todos os campos.']"));
        assertTrue(errorMessage.isDisplayed(), "Error message is not displayed");
        Thread.sleep(2000);
    }

    @Test
    @DisplayName("Should show error when editing patient without name")
    void shouldShowErrorWhenEditingWithoutName() throws InterruptedException {
        driver.get("https://odontologic-system.vercel.app/");

        String nameFaker = faker.name().fullName();
        String ageFaker = String.valueOf(faker.number().numberBetween(1, 100));
        String patientData = String.format("[{\"id\": %d, \"name\": \"%s\", \"age\": \"%s\"}]",
                faker.number().numberBetween(1, 100),
                nameFaker,
                ageFaker);

        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("localStorage.setItem('patients', arguments[0]);", patientData);

        driver.get("https://odontologic-system.vercel.app/view");

        driver.findElement(By.xpath("(//div[@class='view-resources']//li[1]/a)")).click();
        Thread.sleep(2000);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='edit-resource']//input")));

        WebElement nameInput = driver.findElement(By.xpath("(//div[@class='edit-resource']//input)[1]"));
        nameInput.sendKeys(Keys.chord(Keys.CONTROL, "a"));
        nameInput.sendKeys(Keys.DELETE);

        driver.findElement(By.xpath("//button[text()='Salvar']")).click();


        WebElement errorMessage = driver.findElement(By.xpath("//p[text()='Por favor, preencha todos os campos.']"));
        assertTrue(errorMessage.isDisplayed(), "Error message is not displayed");
        Thread.sleep(1000);
    }

    @Test
    @DisplayName("Should show error when editing patient without age")
    void shouldShowErrorWhenEditingWithoutAge() throws InterruptedException {
        driver.get("https://odontologic-system.vercel.app/");

        String nameFaker = faker.name().fullName();
        String ageFaker = String.valueOf(faker.number().numberBetween(1, 100));
        String patientData = String.format("[{\"id\": %d, \"name\": \"%s\", \"age\": \"%s\"}]",
                faker.number().numberBetween(1, 100),
                nameFaker,
                ageFaker);

        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("localStorage.setItem('patients', arguments[0]);", patientData);

        driver.get("https://odontologic-system.vercel.app/view");

        driver.findElement(By.xpath("(//div[@class='view-resources']//li[1]/a)")).click();
        Thread.sleep(2000);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='edit-resource']//input")));

        WebElement nameInput = driver.findElement(By.xpath("(//div[@class='edit-resource']//input)[2]"));
        nameInput.sendKeys(Keys.chord(Keys.CONTROL, "a"));
        nameInput.sendKeys(Keys.DELETE);

        driver.findElement(By.xpath("//button[text()='Salvar']")).click();

        WebElement errorMessage = driver.findElement(By.xpath("//p[text()='Por favor, preencha todos os campos.']"));
        assertTrue(errorMessage.isDisplayed(), "Error message is not displayed");
        Thread.sleep(1000);
    }

    @AfterEach
    void tearDown(){
        if (driver != null) {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("window.localStorage.removeItem('patients');");
            driver.quit();
        }
    }
}

