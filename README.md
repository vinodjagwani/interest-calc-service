# Getting Started

### System requirements

1.  Windows 10 or Mac or Linux or Ubuntu, minimum memory requirement is 8 gb

### Software requirements
1. Java SDK v1.8 or higher
2. Gradle (3.2+)
3. Postgres (9.0+)
4. Kafka

### Installation Instructions

1. Download Java Java SDK v1.8 or higher from https://www.oracle.com/java/technologies/javase-jdk16-downloads.html
2. Install Java by following link https://data-flair.training/blogs/install-java/
3. Set Java home (JAVA_HOME) by using this link https://www.baeldung.com/java-home-on-windows-7-8-10-mac-os-x-linux
4. Verify by typing on console java -version
5. Download gradle and placed somewhere https://gradle.org/install/
6. Set Gradle home (GRADLE_HOME)
7. Postgres https://www.postgresql.org/download/
8. Download intelij idea and run project by following this link https://vaadin.com/docs/v14/flow/tutorials/in-depth-course/project-setup

### Deployment Instructions

Following are the steps to run the application.

1. "gradle clean build" on root of project
2. ./gradlew bootRun

or

Use docker compose with following command on root of folder

docker-compose up

### Explanation of important choices in your solution

interest-calc-service is developed on spring and springboot framework, its entirely using spring webflux
which is reactive library from spring, it intentionally designs as reactive for non-blocking and
performance purpose. Apart from it uses postgres as database and kafka for sending message to the topic
for persistence I am using reactive jdbc because of reactive in nature.
Also spring security is incorporated but apis are not functional yet but can easily be functional with few
steps. Unit tests are written by using mockito.

### Remaining work 

Integration test (which shall be outside from this project such as using robot framework) and associated with
cicd pipelines.

### How would you handle account closures ?

Account closure will be handled by using active and deActive status and all logic will be performed based on validation.

### There are other use cases and approaches that can be discussed in interview.