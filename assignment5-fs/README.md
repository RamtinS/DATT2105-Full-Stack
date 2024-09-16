# Assignment 5: Database and JWT authentication for calculator

## Task description

### Part 1

The exercise is to create a backend database solution (using Spring BOOT and Spring JDBC or Spring JPA) for the calculator.

### Requirements

1. **User Authentication**: The calculator should allow multiple users to log in, but only one at a time. Plaintext username and password are enough.
2. **Database Storage**: Each user should be able to send and store calculations in a database (you can choose any DBMS).
3. **Database Design**: Create two database tables in a one-to-many relationship to store users and their calculations.
4. **Retrieve Calculations**: Each user should be able to retrieve their previous calculations from the database. These should be presented in a list, allowing the user to select from them. Consider implementing pagination to avoid overwhelming the user with too many calculations. At a minimum, the last ten calculations should be available for selection.
5. **Architecture**: Use a layered architecture for your solution.

### Part 2

The part 2 of the assignment is to use JWT token-based authentication and session storage for the calculator.

### Requirements

1. **JWT Authentication**: Implement JWT token-based authentication/login functionality in the calculator using Spring Security. Provide an endpoint for generating JWT tokens. The token should be valid for a maximum of 5 minutes.
    - Implement a custom authorization filter for token validation.
2. **Session Storage**: Use session storage to store the token and offer session functionality to the end-user.
3. **Token Expiry**: Ensure the token is valid for a maximum of 5 minutes. The frontend must retrieve a new token when the current token expires.
