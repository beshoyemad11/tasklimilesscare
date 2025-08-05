package com.hackathon.stepdefinitions;

import com.hackathon.pages.LoginPage;
import com.hackathon.pages.MedicationOrdersPage;
import com.hackathon.pages.PatientListPage;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;

import java.time.Duration;

public class SearchPatientSteps {

    WebDriver driver;
    LoginPage loginPage;
    PatientListPage patientListPage;
    MedicationOrdersPage medicationOrdersPage;

    @Before
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();
        loginPage = new LoginPage(driver);
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Given("I am logged in as {string} with password {string}")
    public void iAmLoggedIn(String email, String password) {
        loginPage.login(email, password);
        patientListPage = new PatientListPage(driver);
        patientListPage.goToMedicationOrders();
        medicationOrdersPage = new MedicationOrdersPage(driver);
    }

    @When("I search for patient ID {string}")
    public void iSearchForPatientId(String id) {
        medicationOrdersPage.searchByPatientId(id);
    }

    @When("I search for medicine name {string}")
    public void iSearchForMedicineName(String medicineName) {
        medicationOrdersPage.searchByMedicineName(medicineName);
    }
    @When("I enter the Start Date {string}")
    public void iEnterTheStartDate(String date) {
        MedicationOrdersPage.selectStartDate(date);
    }

    @Then("I should see the patient medication orders")
    public void iShouldSeeThePatientMedicationOrders() {
        Assert.assertTrue(medicationOrdersPage.isResultDisplayed(), "Patient medication order not displayed.");
    }
}
