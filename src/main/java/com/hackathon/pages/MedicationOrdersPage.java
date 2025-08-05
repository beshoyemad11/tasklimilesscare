package com.hackathon.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class MedicationOrdersPage {

    WebDriver driver;
    static WebDriverWait wait;

    private By prescribedMedicationsLink = By.cssSelector("a[href='/medication-orders']");
    private By searchInputid = By.xpath("//span[@role='combobox' and contains(@aria-label, 'Search by Patient ID')]");
    private static By searchButton = By.xpath("//button[contains(text(), 'Search')]");
    private static By resultsTable = By.cssSelector("table tbody tr");
    private static By startDateInput = By.xpath("//input[@placeholder='dd/mm/yyyy']");


    public MedicationOrdersPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void navigateToMedicationOrders() {
        wait.until(ExpectedConditions.elementToBeClickable(prescribedMedicationsLink)).click();
    }

    public void searchByPatientId(String patientId) {

        wait.until(ExpectedConditions.visibilityOfElementLocated(searchInputid)).sendKeys(patientId);
        By firstResult = By.cssSelector("li.p-dropdown-item");
        wait.until(ExpectedConditions.elementToBeClickable(firstResult)).click();
        wait.until(ExpectedConditions.elementToBeClickable(searchButton)).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(resultsTable));
    }

    public static void searchByMedicineName(String medicineName) {
        WebElement searchBox = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[@role='combobox' and contains(@aria-label, 'Search by name')]")));
        searchBox.click();
        WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input.p-dropdown-filter")));
        input.sendKeys(medicineName);
        input.sendKeys(Keys.ENTER);
        wait.until(ExpectedConditions.elementToBeClickable(searchButton)).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(resultsTable));
    }

    public static void selectStartDate(String date) {
        // Click on the date input to open the calendar
        wait.until(ExpectedConditions.elementToBeClickable(startDateInput)).click();

        String[] parts = date.split("/");
        String day = parts[0];
        String month = parts[1];
        String year = parts[2];

        // Select year and month from dropdowns (if applicable)
        // You may need to adapt the selectors below based on the actual calendar structure

        // Select year
        By yearDropdown = By.cssSelector("//button[contains(@class,'p-datepicker-year') and @aria-label='Choose Year']");
        wait.until(ExpectedConditions.elementToBeClickable(yearDropdown)).click();
        WebElement yearSelect = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//option[text()='" + year + "']")));
        yearSelect.click();

        // Select month
        By monthDropdown = By.cssSelector("//button[@aria-label='Choose Month' and contains(@class, 'p-datepicker-month')]");
        wait.until(ExpectedConditions.elementToBeClickable(monthDropdown)).click();
        int monthIndex = Integer.parseInt(month) - 1; // 0-based index
        WebElement monthSelect = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//select[@class='p-datepicker-month']/option[" + (monthIndex + 1) + "]")));
        monthSelect.click();

        // Select the day
        WebElement dayElement = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//td[not(contains(@class, 'p-datepicker-other-month'))]//span[text()='4']")));

        dayElement.click();
    }


    public boolean isResultDisplayed() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(resultsTable)).isDisplayed();
    }

}
