# Banking Transactions API

Simple Spring Boot API for creating accounts, transferring money, and viewing transaction history.

## Build the project

```bash
./mvnw clean package
```

## Run the project

```bash
./mvnw spring-boot:run
```

The API runs at:

```text
http://localhost:8080
```

## Run tests

```bash
./mvnw test
```

## Endpoints

```text
POST /api/accounts
GET /api/accounts/{accountId}
POST /api/transactions
GET /api/accounts/{accountId}/transactions
```

### Create an account

```json
{
  "ownerName": "Shyana",
  "initialBalance": 1000
}
```

### Create a transaction

```json
{
  "sourceAccountId": "source-account-id",
  "destinationAccountId": "destination-account-id",
  "amount": 100
}
```