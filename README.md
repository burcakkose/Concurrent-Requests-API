# solactive backend challenge

## Requirements

reqirements: java version 1.8, Maven version 3.3+ required
Open your IDE to import this project and clone into your machine. I preffered Eclipse IDE to work on this project

## Run Server

Open SolactiveApplication.java class on your IDE and Run As Java Application.
access server via localhost:8080 

## Run Tests
I used Junit testing framework for unit tests. To run test cases open SmokeTest.java & StatisticsServiceTest.java under file path src\test\java\com\solactive and Run As JUnit Test.

## Endpoints

There are 3 enpoints to run:

- `POST /tick`
- `GET /statistics`
- `GET /statistics/{instrument}`

There is also a postman collection ready at `docs/Solactive.postman_collection.json`

## Assumptions

### 1. Constant Time and Memory (O(1)) Requirement

Because of the given constant time and memory (O(1)) requirement, statistics calculations must be done at write time not read time.

### 2. Scheduled Update Method

I didn't make calculations at the "/ticks" endpoint because it can have a higher computational cost. `POST "/ticks"` might be concurrently called so it can cause race conditions or wrong calculations. I wrote a scheduled method that updates the tick records with respect to the last 60 seconds to avoid race conditions and wrong calculations. I assume my scheduled method does calculations below 1 second.

## If I had more time...

I'd like to add more conditions for edge cases and write unit tests for controller class.

## Did I like this challenge?

I really liked the challenge. It looks like a small task but there were a lot of challenges. I refresh my data pipeline knowledge.
