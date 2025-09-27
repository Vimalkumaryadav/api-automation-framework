# API Automation Framework - AI Coding Assistant Instructions

## 🏗️ Architecture Overview
This is a **Java 17 + Rest Assured + Cucumber BDD** API automation framework with modular design:
- **config/**: Environment configurations via `.properties` files (qa, dev, prod)
- **models/**: POJOs for request/response mapping with Jackson annotations
- **clients/**: REST API client wrappers (GET, POST, PUT, DELETE, PATCH)
- **utils/**: Retry logic, JSON readers, logging helpers
- **stepdefinitions/**: Cucumber step implementations linking Gherkin to API calls
- **runners/**: JUnit test runners with Cucumber integration and parallel execution

## 🔧 Critical Development Patterns

### Environment Configuration
- Use `ConfigManager.getHeaders()` for authentication headers
- Environment switching: `-Denv=qa|dev|prod` loads corresponding `application-{env}.properties`
- Authentication types: Bearer token, API Key, Basic Auth configured per environment

### Test Data Management  
- JSON test data files in `src/main/resources/testdata/`
- POJOs in `models/` package with Jackson serialization
- Use `@JsonProperty` annotations for field mapping

### Step Definition Pattern
```java
// Standard pattern for API steps
@When("I send a GET request")
public void i_send_a_get_request() {
    response = new ApiClient().get(endpoint, ConfigManager.getHeaders());
}
```

### Cucumber Tagging Strategy
- `@smoke`: Critical path tests for quick validation
- `@regression`: Full test suite for comprehensive coverage
- `@getUser`, `@postUser`: Feature-specific tags for targeted execution

## 🚀 Essential Commands

### Local Development
```bash
# Run smoke tests against QA environment
mvn clean test -Denv=qa -Dcucumber.filter.tags="@smoke"

# Run regression suite with parallel execution
mvn clean test -Denv=dev -Dcucumber.filter.tags="@regression" -Dparallel=true
```

### Reporting
```bash
# Generate and serve Allure reports (primary reporting)
allure serve target/allure-results

# Alternative Extent Reports available in reporting/ package
```

## 📝 Adding New Test Cases

1. **Feature File**: Create `.feature` file in `src/test/resources/features/`
2. **Step Definitions**: Implement matching steps in `stepdefinitions/` package
3. **Models**: Create POJOs in `models/` if new request/response structures needed
4. **Test Data**: Add JSON files to `testdata/` directory
5. **Runner**: Use existing runners or create tagged runner for new test suites

## 🔐 Authentication Handling
Framework supports multiple auth types via `ConfigManager`:
- Bearer tokens: Set `bearer.token` in properties
- API keys: Configure `api.key` property  
- Basic auth: Use `basic.auth.user` and `basic.auth.password`

## 🐛 Debugging & Logging
- **log4j2.xml**: Centralized logging configuration
- Request/response logging automatically captured in Allure reports
- Retry logic built into clients for handling flaky endpoints
- Use `@smoke` tags for quick issue isolation

## 🔄 CI/CD Integration
- GitHub Actions & Azure DevOps ready
- Docker support with Maven 3.8.6 + OpenJDK 17 base image
- Parallel execution via Surefire plugin configuration
- Environment-specific test execution with `-Denv` parameter