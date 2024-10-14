# Mars Rover 

This project models the movement of a Mars Rover on a grid.

## Running the code

### Install Java 11
Ensure you have at least Java 11 installed on your machine. You can download it from [here](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html)

### Install sbt
sbt is the de-facto build tool for Scala projects which will allow you to run the tests and the application.
```bash
brew install sbt
```

### Running the tests
To run the tests, navigate to the root of the project and run the following command:
```bash
sbt test
```

### Running the application
To run the application in terminal mode, run:
```bash
sbt run
```
To run the application in file mode using a file with prepared instructions, run:
```bash
sbt "run <path-to-file>"
```

## Project Structure
The entrypoint to the application is `MarsRoverApp`.
- `input` contains the parsing and input validation logic  
- `model` contains the domain model for the Mars Rover - most of the application logic for movement and validation resides here.
- `exception` contains custom exceptions for the application.


## What I would add next
- E2e tests to test entire application and different/more complicated scenarios - a bash script should suffice for a simple file/console based application.
- Code coverage tools
- CI pipeline using Github actions to run tests and linting on every push
- More user-friendly input - currently input parsing is very strict and likely to be error prone even on additional whitespaces. There could also be a retry loop where the user is prompted to re-enter the input if it is invalid instead of just exiting the program. 
- Use of devcontainers/docker for development environment - Java/Scala can admittedly be finicky to set up on different machines, so ideally we would provide the JDK and Scala environment in a container.
- Support for multithreading for large files in parallel. The current implementation works well enough, but multithreading via the use of parallel collections or futures could be used to speed up processing at scale.