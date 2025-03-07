# Postman Collection Fetcher & Tester

This project automates fetching a specific Postman collection, saves it locally, and triggers API testing using TestNG and Maven via Jenkins CI/CD.

# Overview

 - Fetches a Postman collection from the Postman API using OkHttp.

 - Saves the collection in a local directory.

 - Jenkins executes TestNG-based tests upon a code push.

 - Generates test reports for validation.

# Features

-  Fetches and saves a specific Postman collection (Simple Grocery Store API).
-  Uses OkHttp for HTTP requests and JSON parsing for response processing.
-  Stores the collection in the postman_collections2 directory.
-  Executes API tests using TestNG and Maven.
-  Triggers automated builds via Jenkins.
-  Generates detailed test reports.

# Prerequisites

Ensure you have the following installed:

- Java 8+

- Maven

- TestNG

- Jenkins

- Postman API Key (saved as an environment variable)

# Setup & Usage

- **Set Up Postman API Key**

      -Before running the project, export your Postman API Key as an environment variable:

      - Windows (Command Prompt)
         ``set POSTMAN_API_KEY=your_api_key_here``
  -**Clone the Repository**
     -  ```git clone https://github.com/MahimaYadowansi/AutoFetch_SimpleGroceryCollection```
`    -   ```cd AutoFetch_SimpleGroceryCollection```
- **Run API Tests**
    - To execute the API test cases using TestNG:
          - ```mvn test```
- **Jenkins Integration**
   - Set up Jenkins Job:
         - Install Maven, TestNG, and Allure Reports plugins.
         - Add the following build steps:
              - mvn clean test
  - Trigger Build Automatically:
        - Use GitHub Webhooks or Poll SCM to trigger builds upon code push.
    # Test Reports & Logs

   - TestNG Reports: Found in target/surefire-reports.

   - Jenkins Console Output: View build logs in Jenkins UI.

   - Error Logs: Printed to the console during execution.


      
      
   
