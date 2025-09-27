FROM maven:3.8.6-openjdk-17

# Set working directory
WORKDIR /app

# Copy project files
COPY . .

# Build the project (skip tests for image building)
RUN mvn clean compile -DskipTests

# Set environment variable for test execution
ENV MAVEN_OPTS="-Xmx1024m"

# Default command to run tests
ENTRYPOINT ["mvn", "test"]

# Allow passing environment and tags as build args
ARG ENV=qa
ARG TAGS=@smoke

ENV env=${ENV}
ENV cucumber.filter.tags=${TAGS}