# Unit Test Plan for SAMS

This document contains the test plan for unit testing the backend of SAMS (which runs on Spring Boot). We would be using JUnit5 and Spring Boot Test Runner for writing the unit tests. The unit tests are organized by the data models and test all the public functions of the Service Layer. To isolate the service layer, the database layer is mocked by Mockito. Assert4J is used to improve readability of assertions.

## Authors

Group 13 (Eternal Blue)

Authors:

- Aaditya Agrawal (19CS10003)
- Debanjan Saha (19CS30014)
- Deep Majumder (19CS30015)

## Expenditure

### Create Expenditure With Valid ShowId

Tests the method `createExpenditure`.

Test Data:

1. Expenditure testobject:
    - amount: 212.50
    - reason: "Electricity Bill"
    - showId: 404

2. AccountantId: 30014

Successful creation:

- Create an Expenditure object.
- Save it using `createExpenditure`.
- The returned Expenditure object **must not** be null.
- The returned object must match the attributes of test object.

### Get All Expenditure

Tests `getAllExpenditures` method.

Test Data:

1. Expenditure testobject1:
    - amount: 212.50
    - reason: "Electricity Bill"
    - showId: 404
2. Expenditure testobject2:
    - amount: 1250.00
    - reason: "Software Developer Payment"
    - showId: 404
3. AccountantId: 30014

Creation:

- Create two Expenditure objects using test data and `createExpenditure` function and save it.

Successful Retrieval:

- `getAllExpenditures` must return a **non-empty** List of Expenditure of size 2.
- The returned list must contain two Expenditure objects which should match test data.

### Get Expenditure By Id

Tests the method `getExpenditure`.

Test Data:

1. Expenditure testobject:
    - amount: 212.50
    - reason: "Electricity Bill"
    - showId: 404
    - id: 10

2. Get with id:
    - 10 (Valid)
    - 11 (Invalid)

Creation:

- Create an Expenditure object using test data and save it with the help of `createExpenditure` function.

Successful Retrieval with valid id:

- On calling `getExpenditure` with id 10, it should return an Optional of Expenditure.
- The returned Optional **must not** be empty.
- Expenditure object is extracted from returned Optional. The attributes of the extracted object must match the testobject.

Unsuccessful Retrieval with invalid id:

- On calling `getExpenditure` with id 11, it should return an Optional of Expenditure.
- The returned Optional **must** be empty

### Get Expenditure By Show Id

Tests the method `getExpenditureByShow`.

Test Data:

1. Expenditure testobject1:
    - amount: 212.50
    - reason: "Electricity Bill"
    - showId: 404
2. Expenditure testobject2:
    - amount: 1250.00
    - reason: "Software Developer Payment"
    - showId: 404
3. Get with showId:
    - 404 (Valid)
    - 101 (Invalid)

Creation:

- Create an Expenditure object using test data and save it with the help of `createExpenditure` function.

Successful Retrieval with valid showId:

- On calling `getExpenditureByShow` with showId 404, it should return a List of Expenditure.
- The returned List **must not** be empty and should have size 2.
- The attributes of each of the objects inside the List must match the testobject1 and testobject2.

Unsuccessful Retrieval with invalid showId:

- On calling `getExpenditureByShow` with showId 101, it should return a List of Expenditure.
- The returned List **must** be empty.
- Create a User object.
- Save it using `createUser`.
- The returned `Optional` must **not** be empty.
- The returned User is extracted. It must match with original user on `getUsername`, `getPassword` and `getType`.

## Ticket

### Create ticket

Tests the `createTicket` method.

#### Successful creation of regular ticket

- Use the following Show:
  - Date: 2021-10-20
  - Time: 1700
  - Duration: 2 hr
  - Balcony Seats = 100 and price = 1000
  - Regular Seats = 500 and price = 450
  - ID: 10
- Using UserID = 100, create regular `Ticket` object
- Using SalespersonID = 1000, call create ticket with above ticket object
- Check that the returned `Optional` is **not** empty.
- Check the `Ticket` extracted from `Optional` is same as created ticket.

#### Successful creation of balcony ticket

- Use the following Show:
  - Date: 2021-10-20
  - Time: 1700
  - Duration: 2 hr
  - Balcony Seats = 100 and price = 1000
  - Regular Seats = 500 and price = 450
  - ID: 10
- Using UserID = 100, and showId 10, create balcony `Ticket` object
- Using SalespersonID = 1000, call create ticket with above ticket object
- Check that the returned `Optional` is **not** empty.
- Check the `Ticket` extracted from `Optional` is same as created ticket.

#### Unsuccessful creation of ticket with invalid show id

- Use the following Show:
  - Date: 2021-10-20
  - Time: 1700
  - Duration: 2 hr
  - Balcony Seats = 100 and price = 1000
  - Regular Seats = 500 and price = 450
  - ID: 10
- Using UserID = 100, and showId 11, create balcony `Ticket` object
- Using SalespersonID = 1000, call create ticket with above ticket object
- Check that the returned `Optional` **is** empty.

#### Unsuccessful creation of regular ticket when all tickets are sold out

- Use the following Show:
  - Date: 2021-10-20
  - Time: 1700
  - Duration: 2 hr
  - Balcony Seats = 100 and price = 1000
  - Regular Seats = 500 and price = 450
  - ID: 10
- Save 500 regular tickets for this show.
- Using UserID = 100, and showId 10, create regular Ticket` object.
- Using SalespersonID = 1000, call create ticket with above ticket object.
- Check that the returned `Optional` **is** empty.

#### Unsuccessful creation of balcony ticket when all tickets are sold out

- Use the following Show:
  - Date: 2021-10-20
  - Time: 1700
  - Duration: 2 hr
  - Balcony Seats = 100 and price = 1000
  - Regular Seats = 500 and price = 450
  - ID: 10
- Save 100 balcony tickets for this show.
- Using UserID = 100, and showId 10, create regular Ticket` object.
- Using SalespersonID = 1000, call create ticket with above ticket object.
- Check that the returned `Optional` **is** empty

#### Unsuccessful creation of ticket after show has started

- Use the following Show:
  - Date: Current day
  - Time: Current time
  - Duration: 2 hr
  - Balcony Seats = 100 and price = 1000
  - Regular Seats = 500 and price = 450
  - ID: 10
- Save 100 balcony tickets for this show.
- Using UserID = 100, and showId 10, create regular Ticket` object.
- Using SalespersonID = 1000, call create ticket with above ticket object.
- Check that the returned `Optional` **is** empty

### Delete Ticket

Tests the `deleteTicket` method of `TicketService`.

#### Successful delete existing ticket more than 3 days before

- Use the following Show:
  - Date: Curr Date + 10 days
  - Time: 1700hrs
  - Duration: 2 hr
  - Balcony Seats = 100 and price = 1000
  - Regular Seats = 500 and price = 450
  - ID: 10
- Use the following Ticket:
  - Regular
  - Show ID: 10
  - Price 450
  - User ID: 100
- Call `DeleteTicket` by ticket ID.
- The returned result must have deleted field as *true* and refund amount as 450 - 5 = 445

#### Successful delete existing regular ticket less than 3 days and before 1 days

- Use the following Show:
  - Date: Curr Date + 2 days
  - Time: 1700hrs
  - Duration: 2 hr
  - Balcony Seats = 100 and price = 1000
  - Regular Seats = 500 and price = 450
  - ID: 10
- Use the following Ticket:
  - Regular
  - Show ID: 10
  - Price 450
  - User ID: 100
- Call `DeleteTicket` by ticket ID.
- The returned result must have deleted field as *true* and refund amount as 450 - 10 = 440

#### Successful delete existing balcony ticket less than 3 days and before 1 days

- Use the following Show:
  - Date: Curr Date + 2 days
  - Time: 1700hrs
  - Duration: 2 hr
  - Balcony Seats = 100 and price = 1000
  - Regular Seats = 500 and price = 450
  - ID: 10
- Use the following Ticket:
  - Balcony
  - Show ID: 10
  - Price: 1000
  - User ID: 100
- Call `DeleteTicket` by ticket ID.
- The returned result must have deleted field as *true* and refund amount as 1000 - 15 = 985

#### Successful delete existing balcony ticket on day of show

- Use the following Show:
  - Date: Curr Date
  - Time: 2359 hrs
  - Duration: 2 hr
  - Balcony Seats = 100 and price = 1000
  - Regular Seats = 500 and price = 450
  - ID: 10
- Use the following Ticket:
  - Balcony
  - Show ID: 10
  - Price: 450
  - User ID: 100
- Call `DeleteTicket` by ticket ID.
- The returned result must have deleted field as *true* and refund amount as 450 * 0.5 = 225.0

#### Unsuccessful delete non-existent ticket

- Use the following Show:
  - Date: Curr Date + 10 days
  - Time: 1700 hrs
  - Duration: 2 hr
  - Balcony Seats = 100 and price = 1000
  - Regular Seats = 500 and price = 450
  - ID: 10
- Use the following Ticket:
  - Balcony
  - Show ID: 10
  - Price: 450
  - User ID: 100
  - ID: 10
- Call `DeleteTicket` by 11 as ticket ID.
- The returned result must have deleted field as *false*.

#### Unsuccessful delete ticket with non-existent show

- Use the following Show:
  - Date: Curr Date + 10days
  - Time: 1700 hrs
  - Duration: 2 hr
  - Balcony Seats = 100 and price = 1000
  - Regular Seats = 500 and price = 450
  - ID: 10
- Use the following Ticket:
  - Balcony
  - Show ID: 13
  - Price: 450
  - User ID: 100
  - ID: 10
- Call `DeleteTicket` by ticket ID.
- The returned result must have deleted field as *false*.

#### Unsuccessful delete ticket after show is over

- Use the following Show:
  - Date: Curr Date
  - Time: Curr Time
  - Duration: 2 hr
  - Balcony Seats = 100 and price = 1000
  - Regular Seats = 500 and price = 450
  - ID: 10
- Use the following Ticket:
  - Balcony
  - Show ID: 13
  - Price: 450
  - User ID: 100
  - ID: 10
- Call `DeleteTicket` by ticket ID.
- The returned result must have deleted field as *false*.

### Get All Tickets

- Create some tickets
- Get all tickets from service
- Check whether they were the tickets created

### Get All Tickets of an User

- Create some tickets with a given user ID
- Get all tickets of that user ID from service
- Check whether they were the tickets created

## Transaction

### Create transaction

Tests the `createTransaction` method.

- Create a transaction object
- Call `createTransaction` with the same parameters.
- Check that the returned transaction is the same as the original transaction

### Get all transactions

Tests the `allTransactions` method.

- Pre-populate the repository with a list of transactions
- Call `allTransactions`
- Check that the transactions  returned is same as the original transactions

### Get transaction by id

Tests the `getTransactionById` method.

- Pre-populate the repository with a list of transactions
- Call `getTransactionById` with the id of the first transaction of the list
- Check whether the returned transaction is same as the first transaction of the list

### Get transaction by Show id

Tests the `getTransactionsByShowId` method.

- Pre-populate the repository with a list of transactions
- Call `getTransactionsByShowId` method with a show ID which has transactions.
- Filter the original list over show ID
- Check this new list to be identical with the obtained transactions

### Get transaction by Salesperson id

Tests the `getTransactionsBySalespersonId` method.

- Pre-populate the repository with a list of transactions
- Call `getTransactionBySalespersonId` method with a salesperson ID which has transactions.
- Filter the original list over salesperson ID
- Check this new list to be identical with the obtained transactions

## Show

### Create Show

Tests the `createShow` method.

Test Data:

1. Show Object:
    - date: 2021-01-01 (yyyy-mm-dd)
    - time: 10:49:00 (hh:mm:ss)
    - duration: 125 (minutes)
    - balconyTicketCount: 5
    - regularTicketCount: 5
    - balconyTicketPrice: 100.0
    - regularTicketPrice: 50.0

Successful creation:

- Create a Show object with given test data.
- Save it using `createShow` function.
- An `Optional` is returned.
- The returned `Optional` **must not** be empty.
- The Show object is extracted. Its attributes **must** match with that of testdata.

### Get Show By Id

Tests the `getShow` method.

Test Data:

1. Show Object:
    - date: 2021-01-01 (yyyy-mm-dd)
    - time: 10:49:00 (hh:mm:ss)
    - duration: 125 (minutes)
    - balconyTicketCount: 5
    - regularTicketCount: 5
    - balconyTicketPrice: 100.0
    - regularTicketPrice: 50.0

Successful Retrieval:

- Create a Show object with given test data.
- Save it using `createShow` function.
- The test method is called with the auto-generated id of the created object.
- On calling `getShow` method, an `Optional` is returned.
- The returned `Optional` **must not** be empty.
- The Show object is extracted. Its attributes **must** match with that of testdata.

### Get All Shows

Tests the `getAll` method.

Test Data:

1. Show Object1:
    - date: 2021-01-01 (yyyy-mm-dd)
    - time: 10:49:00 (hh:mm:ss)
    - duration: 125 (minutes)
    - balconyTicketCount: 5
    - regularTicketCount: 5
    - balconyTicketPrice: 100.0
    - regularTicketPrice: 50.0

2. Show Object2:
    - date: 2021-01-02 (yyyy-mm-dd)
    - time: 07:49:00 (hh:mm:ss)
    - duration: 150 (minutes)
    - balconyTicketCount: 5
    - regularTicketCount: 5
    - balconyTicketPrice: 100.0
    - regularTicketPrice: 50.0

Successful Retrieval:

- Create two show objects with given test data.
- Save them using `createShow` function.
- On calling `getAll` method, a List of Show is returned.
- The returned List **must not** be empty and its size must be equal to 2.

## User

### Create User When User is Not Present

Tests the `createUser` method.

Test Data:

1. User Object:
    - username: John
    - password: Password
    - type: Customer

Successful creation:

- Create a User object with given test data when already a user with **same username** is present in database.
- Save it using `createUser` method.
- An `Optional` is returned.
- The returned `Optional` **must not** be empty.
- The extracted User from the `Optional` must have the same attributes as that of test data.

### Create User When User is Present

Tests the `createUser` method.

Test Data:

1. User Object:
    - username: "John"
    - password: "Password"
    - type: Customer

Unsuccessful creation:

- Create a User object with given test data when already a user with **same username** is present in database.
- Try to save it using `createUser` method.
- An `Optional` is returned.
- The returned `Optional` **must** be empty.

### Get All Users

Tests the `getAllUsers` method.

Test Data:

1. User Object1:
    - username: "John"
    - password: "Password"
    - type: Customer

2. User Object2:
    - username: "aaditya"
    - password: "pass"
    - type: Manager

Successful Retrieval:

- Create two User objects with given test data.
- Save it using `createUser` method.
- On calling `getAllUsers`, a List of User is returned.
- The returned List **must** be of size 2.

### Get User By Id

Tests the `getUser` method.

Test Data:

1. User Object:
    - username: "John"
    - password: "Password"
    - type: Customer

Successful Retrieval:

- Create a User object with given test data.
- Save it using `createUser` method.
- An `Optional` is returned during creation of object.
- On calling `getUser` with the id of created object, an `Optional` is returned.
- The returned `Optional` **must not** be empty.
- The extracted User from the `Optional` must have the same attributes as that of test data or it should match with the created object.

### Delete User

Tests the `deleteUser` method.

Test Data:

1. User Object:
    - username: "John"
    - password: "Password"
    - type: Customer

Successful Deletion:

- Create a User object with given test data.
- Save it using `createUser` method.
- An `Optional` is returned during creation of object.
- On calling `deleteUser` with the id of created object, a boolean value is returned.
- The returned value **must** be `true`.
