# ShopSmart — E-Commerce Application & Test Automation Framework

## Project Overview

ShopSmart is a sample e-commerce application built with Java and Spring Boot, supported by a separate automated testing framework using Selenium WebDriver, REST Assured, and TestNG.

The project demonstrates web UI testing, REST API testing, Page Object Model (POM), failure screenshot capture, and HTML test reporting with ExtentReports.

## Technologies Used

* **Language:** Java
* **Backend:** Spring Boot, Spring Data JPA, Hibernate
* **Database:** MySQL
* **Frontend:** HTML, CSS, JavaScript
* **UI Automation:** Selenium WebDriver
* **API Automation:** REST Assured
* **Test Framework:** TestNG
* **Design Pattern:** Page Object Model (POM)
* **Reporting:** ExtentReports
* **Build Tool:** Maven
* **IDE:** IntelliJ IDEA Community Edition
* **Version Control:** Git and GitHub

## Application Features

* View available products
* Search products by name or category
* Add products to the shopping cart
* Increase and decrease product quantities
* Remove products from the cart
* Calculate the cart total
* Manage products through REST APIs

**Note:** This is a learning and portfolio project. The shopping cart is maintained in browser memory; real checkout, payment processing, and persistent order management are not implemented.

## Automated Testing Features

### 1. REST API Testing

REST Assured is used to test the product REST API.

| Test                | Purpose                                  |
| ------------------- | ---------------------------------------- |
| Create Product      | Verify product creation                  |
| Get All Products    | Verify product listing                   |
| Get Missing Product | Verify handling of a nonexistent product |
| Update Product      | Verify product updates                   |
| Delete Product      | Verify product deletion                  |

Base URL: `http://localhost:8080`

Product API endpoint: `/api/products`

### 2. Selenium UI Testing

Selenium WebDriver automates browser interactions with the ShopSmart storefront.

| Test               | Purpose                                             |
| ------------------ | --------------------------------------------------- |
| Product Search     | Verify that searching displays the expected product |
| Add to Cart        | Verify that a product can be added                  |
| Quantity and Total | Verify quantity changes and cart total calculations |
| Remove from Cart   | Verify removal and empty-cart behaviour             |

### 3. Page Object Model

The `ShopSmartPage` class keeps page locators and browser actions separate from test methods, improving readability and maintainability.

### 4. Failure Screenshots

The TestNG listener is configured to capture screenshots when Selenium UI tests fail.

Screenshot directory: `target/screenshots/`

### 5. HTML Test Reports

ExtentReports generates an HTML report containing test statuses, execution details, and environment information.

Report location: `target/reports/ShopSmartTestReport.html`

## Project Structure

```text
shopsmart-automation/
├── pom.xml
├── README.md
├── src/
│   └── test/
│       └── java/
│           └── com/
│               └── shopsmart/
│                   ├── pages/
│                   │   └── ShopSmartPage.java
│                   └── tests/
│                       ├── ProductApiTest.java
│                       ├── ShopSmartUITest.java
│                       ├── ShopSmartTestListener.java
│                       └── ExtentReportManager.java
└── target/
    ├── reports/
    └── screenshots/
```

The `target` directory is generated during test execution and normally should not be committed to Git.

## Prerequisites

Install the following before running the project:

* JDK compatible with the project configuration
* Maven
* MySQL
* IntelliJ IDEA or another Java IDE
* Google Chrome
* Git

## How to Run the Application

1. Start MySQL.
2. Create the `shopsmart_db` database.
3. Configure the database username and password in the Spring Boot application's `application.properties`.
4. Start the Spring Boot application.
5. Open `http://localhost:8080` in your browser.

## How to Run the Automation Tests

1. Start MySQL and the Spring Boot application.
2. Open the `shopsmart-automation` Maven project.
3. Reload the Maven project dependencies.
4. Open a terminal in the automation project directory.
5. Execute:

```bash
mvn clean test
```

The Maven command runs the configured TestNG test suite.

## Test Results

In a verified test run:

* REST API tests: 5 passed
* Selenium UI tests: 4 passed
* Total: 9 passed
* Failures: 0
* Errors: 0

The HTML report generated for the Selenium suite displayed four passed UI tests.

Test results can vary depending on the application state and environment.

## Skills Demonstrated

* Java programming
* REST API testing
* Selenium WebDriver
* TestNG
* Maven dependency management
* Page Object Model
* Assertions and explicit waits
* Failure screenshot capture
* HTML test reporting
* Basic e-commerce application testing
* Git and GitHub workflow

## Future Enhancements

* Run API and UI tests through a unified reporting suite
* Add negative and boundary-value test cases
* Integrate automated tests with GitHub Actions
* Improve test data management
* Add persistent order management and checkout functionality

## Author

**Saleem**

Java Development | API Testing | Selenium Automation
