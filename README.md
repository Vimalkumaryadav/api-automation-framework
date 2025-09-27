# API Automation Framework

A modern, scalable API automation framework built with **Java 17**, **Rest Assured**, and **Cucumber BDD**.

## 🚀 Quick Start

### Prerequisites
- Java 17+
- Maven 3.6+
- Allure (for reporting)

### Running Tests

```bash
# Run smoke tests against QA environment
mvn clean test -Denv=qa -Dcucumber.filter.tags="@smoke"

# Run regression suite against DEV environment
mvn clean test -Denv=dev -Dcucumber.filter.tags="@regression"

# Run with parallel execution
mvn clean test -Denv=qa -Dcucumber.filter.tags="@smoke" -Dparallel=true
```

### Generate Reports

```bash
# Generate and serve Allure reports
allure serve target/allure-results
```

## 📂 Project Structure

```
api-automation-bdd/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   ├── config/          # Environment configurations
│   │   │   ├── models/          # POJOs for request/response
│   │   │   ├── clients/         # API clients
│   │   │   ├── utils/           # Utilities (retry, JSON reader)
│   │   │   └── reporting/       # Reporting integration
│   │   └── resources/
│   │       ├── config/          # Environment property files
│   │       ├── testdata/        # JSON test data
│   │       └── log4j2.xml       # Logging configuration
│   └── test/
│       ├── java/
│       │   ├── stepdefinitions/ # Cucumber step definitions
│       │   ├── runners/         # Test runners
│       │   └── hooks/           # Setup/teardown hooks
│       └── resources/
│           └── features/        # Cucumber feature files
├── pom.xml
└── Dockerfile
```

## 🔧 Key Features

- ✅ **Multi-environment support** (QA, DEV, PROD)
- ✅ **Multiple authentication types** (Bearer, API Key, Basic Auth)
- ✅ **Retry logic** for flaky endpoints
- ✅ **Parallel execution** support
- ✅ **Allure reporting** with request/response logging
- ✅ **Docker support** for CI/CD
- ✅ **Comprehensive logging** with log4j2

## 🐳 Docker Usage

```bash
# Build Docker image
docker build -t api-automation .

# Run tests in Docker
docker run -e env=qa -e cucumber.filter.tags=@smoke api-automation
```

## 📊 CI/CD Integration

The framework is ready for integration with:
- GitHub Actions
- Azure DevOps
- Jenkins
- Any CI/CD platform supporting Maven and Docker