# Crowd Collective boilerplate

Hi! Odds are, you're seeing this because you are a candidate for Crowd Collective, and we have sent you one of our cases for you to implement. In order to make things a bit easier for you, we have created this sample project for you to get started.

Nothing is sacred in here: you don't have to use this at all for your case, or you can change the structure completely to how you like it. We like to see how you structure your projects!

Don't like Spring? Prefer Gradle over Maven? Just delete and do your thing.

## How to get started

### Prerequisites

- JDK 21 or later
- Maven

### Run the app

To start the application, run the following command:

```bash
mvn spring-boot:run
```

Verify that the app is running with the http://localhost:8080/ping endpoint.

## Instructions

You can find the instructions [here](./instructions.md)

## How to submit

When you're satisfied with your solution, create a pull request to this repo in GitHub, and we can review it together in the next interview!

To make life a bit easier on us, you can also change this README.md to let us know about your solution or anything we need to do on our end to run your code. 
For instance, if you need a database for your solution, how can we get that up and running?

## Antons notes
One needs to have docker-compose installed to run this application.
Run this to start the application:
```bash
docker-compuse up
```
This will start two different docker containers. One containing the database, and one hosting the webapplication.


## Good luck and have fun!
