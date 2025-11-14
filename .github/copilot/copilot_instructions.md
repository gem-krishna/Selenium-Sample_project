# Copilot Migration Instructions
### Selenium + Java / Rest Assured → Playwright Java
### Feature file: `Login.feature`
### Step Definitions Source: `/src/test/java/stepDefinitions/LoginSteps.java`

---

## 📌 Objective

Migrate ALL existing Selenium-based and Rest Assured–based step definitions into **Playwright Java**, without modifying `Login.feature`.

Copilot must:
- Scan all Selenium + RestAssured usage
- Generate Playwright page objects
- Create new Playwright step definitions
- Remove all Selenium/RestAssured imports
- Validate migration with PowerShell scripts

---

## 📁 Required Folder Structure

# Target structure Playwright must use:

```
/src/test/java/
├── playwright/
│ ├── base/
│ │ └── PlaywrightFactory.java
│ ├── pages/
│ │ └── LoginPage.java
│ ├── steps/
│ │ └── LoginSteps_Playwright.java
│ └── api/
│ └── ApiClient.java
```

## 🔄 Migration Steps Copilot Must Follow

### **Step 1 — Scan old project**
# Run:

```bash
pwsh .github/copilot/Scan-SeleniumUsage.ps1
```


### **Step 2 — Create Playwright Factory**
# Copilot must generate:

```java
package playwright.base;

import com.microsoft.playwright.*;

public class PlaywrightFactory {
    private static Playwright playwright;
    private static Browser browser;
    private static Page page;

    public static Page getPage() {
        if (playwright == null) {
            playwright = Playwright.create();
            browser = playwright.chromium()
                .launch(new BrowserType.LaunchOptions().setHeadless(false));
            page = browser.newPage();
        }
        return page;
    }
}
```
### **Step 3 — Create Page Objects**
# Example for Login:

```java
package playwright.pages;

import com.microsoft.playwright.*;

public class LoginPage {
    private Page page;

    public LoginPage(Page page) {
        this.page = page;
    }

    Locator username = page.locator("#username");
    Locator password = page.locator("#password");
    Locator loginButton = page.locator("#login");

    public void openLoginPage(String url) { page.navigate(url); }
    public void enterUsername(String user) { username.fill(user); }
    public void enterPassword(String pass) { password.fill(pass); }
    public void clickLogin() { loginButton.click(); }
}
```
## **Step 4 — Convert Step Definitions**
# Copilot must generate: /src/test/java/playwright/steps/LoginSteps_Playwright.java

```java

package playwright.steps;

import com.microsoft.playwright.*;
import io.cucumber.java.en.*;
import playwright.base.PlaywrightFactory;
import playwright.pages.LoginPage;

public class LoginSteps_Playwright {

    Page page = PlaywrightFactory.getPage();
    LoginPage loginPage = new LoginPage(page);

    @Given("User navigates to Login page")
    public void navigateToLogin() {
        loginPage.openLoginPage("https://example.com/login");
    }

    @When("User enters username {string}")
    public void enterUsername(String uname) {
        loginPage.enterUsername(uname);
    }

    @When("User enters password {string}")
    public void enterPassword(String pwd) {
        loginPage.enterPassword(pwd);
    }

    @Then("User clicks login button")
    public void clickLogin() {
        loginPage.clickLogin();
    }
}
```
## **Step 5 — Remove Selenium & RestAssured**
# Copilot must delete imports:

```java
org.openqa.selenium.*
io.restassured.*
```
## **Step 6 — Validate**
# Run:
```bash
pwsh .github/copilot/MigrationReport.ps1
```