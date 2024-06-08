package org.example;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SeleniumTest {
    private WebDriver driver;

    @BeforeEach
    void setUp(){
        System.setProperty("webdriver.chrome.driver","src/test/resources/drivers/chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        driver = new ChromeDriver(options);
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
        driver.findElement(By.xpath("(//div[@class='add-resource']//input)[1]")).sendKeys("paciente");
        driver.findElement(By.xpath("(//div[@class='add-resource']//input)[2]")).sendKeys("18");
        driver.findElement(By.xpath("//button[@type='submit']")).click();
        //verify if element showed
        WebElement successMessage = driver.findElement(By.xpath("//p[text()='Paciente cadastrado com sucesso!']"));
        assertTrue(successMessage.isDisplayed(), "Success message is not displayed");
        Thread.sleep(2500);
        String secondCurrentUrl = driver.getCurrentUrl();
        assertEquals("https://odontologic-system.vercel.app/view", secondCurrentUrl);
    }

    @AfterEach
    void tearDown(){
        driver.quit();
    }
}

