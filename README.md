# RiskAssessment2 – Automation Exercise

This is an automated testing project for the Risk LexisNexis website. It uses Serenity BDD to combine clean reporting with readable Cucumber scenarios, running through a real-life test case for validating a job search feature.

---

## 📌 What It Does

This project automates the following journey:

1. Open the LexisNexis Risk homepage  
2. Accept cookie popup  
3. Navigate via "About Us" → "Careers"  
4. Open the careers page in a new window  
5. Click “Search jobs”  
6. Search for the term `automation tester`  
7. Assert that at least one result is shown — otherwise, fail with actual result output

---

## 💻 Tech Stack

| Tool            | Version    |
|-----------------|------------|
| Java            | 17         |
| Selenium        | 4.1.2      |
| Serenity BDD    | 3.6.12     |
| Cucumber        | 6.10.4     |
| Maven           | ✅ Used for build/run/dependency management

---

## 🚀 How to Run the Tests

1. **Clone this repo**
git clone https://github.com/uzair10/automation-exercise-2.git
cd RiskAssessment2
2. **Clean and install dependencies**
  MVN clean install -U
## Run the tests
mvn verify
**After the tests complete, open**
target/site/serenity/index.html
**Folder structure**
src/
  └── test/
      ├── java/
      │   ├── stepdefinitions/     → Step implementations
      │   ├── runners/             → Test runner using CucumberWithSerenity
      │   └── pages/               → Page Object Model classes
      └── resources/
          └── features/           → Cucumber .feature files
  **Config Files**
          Serenity.properties - project settings
          Pom.xml - handles dependency and plugin versions
 **Scenario Sample**
 Scenario: Search for automation tester jobs
  Given I open the risk.lexisnexis website
  And I accept cookies
  When I navigate to the Careers page
  Then I should see the Careers page
  When I click on Search jobs
  And I search for "automation tester"
  Then I should see at least one search result

  **Notes**

 Handles popups (cookies) using explicit waits

Opens the careers page in a new browser window and switches automatically

If no job results found, it prints the available ones and fails gracefully

Designed to be extendable for other roles and test paths

 **Author**
 Uzair Akubat
 Automation QA Engineer
