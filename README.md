
## Requirements

reqirements: java version 1.8, Maven version 3.3+ required
Open your IDE to import this project and clone into your machine. I preffered Eclipse IDE to work on this project

## Run Server

Open SolactiveApplication.java class on your IDE and Run As Java Application.
access server via localhost:8080 

## Endpoints

There are 3 enpoints to run:

- `POST /tick`
- `GET /statistics`
- `GET /statistics/{instrument}`

## Assumptions

### 1. Constant Time and Memory (O(1))

Statistics calculations have be done at write time not read time.

### 2. Scheduled Update Method

I didn't make calculations at the "/ticks" endpoint because it can have a higher computational cost. `POST "/ticks"` might be concurrently called so it can cause race conditions or wrong calculations. I wrote a scheduled method that updates the tick records with respect to the last 60 seconds to avoid race conditions and wrong calculations. I assume my scheduled method does calculations below 1 second.

