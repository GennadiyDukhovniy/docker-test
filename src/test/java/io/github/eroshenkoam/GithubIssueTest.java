package io.github.eroshenkoam;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.WebDriverRunner;
import io.qameta.allure.Issue;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;

import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.open;
import static io.qameta.allure.Allure.step;

public class GithubIssueTest {

    private final static int ISSUE_NUMBER = 12;

    @BeforeEach
    public void initDriver() throws IOException {
        final String url = "http://localhost:4444/wd/hub";
        RemoteWebDriver driver = new RemoteWebDriver(new URL(url), DesiredCapabilities.chrome());
        driver.manage().window().setSize(new Dimension(1920,1024));
        WebDriverRunner.setWebDriver(driver);
    }

    @Test
    @Issue("AE-1")
    @DisplayName("Открываем сщуствующую Issue")
    public void testIssue() {
        step("Открываем главную страницу", () -> {
            open("https://github.com");
        });
        step("Открываем страницу с репозиторием", () -> {
            $x("//*[contains(@class, 'header-search-input')]").click();
            $x("//*[contains(@class, 'header-search-input')]").sendKeys("eroshenkoam/allure-example");
            $x("//*[contains(@class, 'header-search-input')]").submit();
            $x("//a[@href='/eroshenkoam/allure-example']").click();
        });
        step("Открываем страницу с задачами в репозитории", () -> {
            $x("//a[contains(@data-selected-links, 'repo_issues')]").click();
        });
        step("Проверяем наличие задачи с ID=" + ISSUE_NUMBER, () -> {
            $x("//a[@id='issue_12_link']").click();
        });
    }

    @AfterEach
    public void stopDriver() {
        Optional.ofNullable(WebDriverRunner.getWebDriver()).ifPresent(WebDriver::quit);
    }

}
