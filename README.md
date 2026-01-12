# API Automation Testing - FakerAPI

This project is a professional-grade API automation testing framework built with **Java** and **RestAssured**. It is designed to validate data integrity, filtering logic, and boundary cases for the [FakerAPI](https://fakerapi.it/) endpoints.

## 🚀 Tech Stack
* **Language:** Java 11 or higher
* **API Client:** RestAssured (Fluent API testing)
* **Test Runner:** JUnit 5 (Modern test execution)
* **Assertion:** AssertJ (Human-readable assertions)
* **Build Tool:** Maven (Dependency and build management)

## 📋 Prerequisites & Environment Setup
To run this project locally, ensure your environment meets the following requirements:

1. **Java Development Kit (JDK) 11+**:
   - Check version: `java -version`
2. **Apache Maven**:
   - Check version: `mvn -version`
3. **Internet Access**: Required to download Maven dependencies and reach the public FakerAPI endpoints.
4. **IDE (Optional):** IntelliJ IDEA is recommended for the best development experience.

## 📁 Project Structure
The project follows a standard Maven directory structure to ensure modularity and clean code.
```text
api-automation-test/
├── src/
│   └── test/java/              
│       ├── api/tests/          # Test classes (Products, Persons, Companies)
│       ├── api/endpoints.java  # Centralized Endpoint configurations
│       └── base/BaseTest.java  # Global setup (Base URI and common configs)
├── .gitignore                  # Excludes target/ and IDE files from Git
├── pom.xml                     # Maven configuration and dependencies
└── README.md
```
## 📝 Test Scenarios
his framework covers 11 critical test scenarios across three different endpoints:

1. **Products API**
- TC01 - Get Products Success: Validates 200 OK status and correct JSON structure.
- TC02 - Data Consistency: Ensures the returned items count matches the _quantity parameter.
- TC03 - Data Integrity: Validates that price is a numeric value and image is a valid URL.
- TC04 - Boundary Case: Tests behavior when _quantity is set to 0 (Bug Detected: Returns 10 items instead of 0).

2. **Persons API**
- TC05 - Filter by Gender: Confirms that the female filter returns only female data entries.
- TC06 - Address Object Check: Validates the presence and integrity of nested address objects (city, country).
- TC07 - Birthday Range: Ensures the birth year aligns with the requested _birthday_start filter.
- TC08 - Negative - Invalid Format: Tests how the API handles incorrect date formats (e.g., DD-Mon-YYYY).

3. **Companies API**
- TC09 - Get Companies Success: Validates successful data retrieval for the Companies endpoint.
- TC10 - Contact Details: Ensures email, phone, and website fields are correctly formatted.
- TC11 - Country Data Check: Validates that the country field is not null (Bug Detected: Found null values in certain entries).

## 🛠️ How to Run the Tests
1. **Clone the repository:**
```bash
    git clone https://github.com/ahsinf/api-automation-test.git
    cd api-automation-test
```
2. **Execute all tests:**
```bash
    mvn clean test
```
3. **Generate HTML Report:**
```bash
    mvn surefire-report:report
```
## 📊 Reporting
After running the report command, you can find a visual representation of the test results (Pass/Fail status and error logs) at: target/site/surefire-report.html (Open this file in any web browser like Chrome or Edge).