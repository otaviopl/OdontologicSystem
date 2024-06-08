package org.example;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static org.junit.jupiter.api.Assertions.assertEquals;

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

    @AfterEach
    void tearDown(){
        driver.quit();
    }
}

