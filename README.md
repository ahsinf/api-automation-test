# API Automation Testing - FakerAPI

This project is an automation testing framework for REST APIs using **Java** and **RestAssured**. The focus of testing is on endpoints`Products` from [FakerAPI](https://fakerapi.it/).

## 🚀 Tech Stack
* **Java 11+**
* **RestAssured** (API Request)
* **JUnit 5** (Test Runner)
* **AssertJ** (Assertion)
* **Maven** (Build Tool)

## 📁 Structure Proyek
```
The project follows a standard structure Maven `src/test/java` to separate between test logic and supporting data.
api-automation-test/
├── src/
│   ├── main/java/              
│   │   └── utils/
│   │       └── ConfigReader.java
│   └── test/java/           
│       ├── api/
│       │   ├── endpoints.java 
│       │   └── tests/
│       │       └── ProductApiTest.java  
│       └── base/
│           └── BaseTest.java   
├── target/                    
├── pom.xml                     
└── README.md                  
```
---
## 📝 Scenario Test
1. **TC01 - Get Products Success**: Validates response code 200, JSON format, and mandatory fields (status, code).
2. **TC02 - Data Consistency**: Ensures the number of items in the `data` list matches the query parameter `_quantity`.
3. **TC03 - Data Integrity**: Validates the `price` field is not empty and the `image` field has a valid URL format.
4. **TC04 - Boundary Case**: Tests the API response when the `_quantity` parameter is set to 0.

## 🛠️ How to Run the Test
1. Clone this repository.
2. Make sure Maven is installed (`mvn -version`).
3. Run the following command in the terminal:
```bash
mvn clean test
```
## 📊 Hasil Pengujian
The test report will appear in the terminal via the Maven Surefire plugin and the HTML report file can be found in the target/surefire-reports folder.
