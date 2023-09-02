## Hackathon Step-by-Step Checklist
### General Notes:
* This is non-TDD
    * A TDD version would start from the top down ( Mocks first)
* Example code below is figurative, not to be taken literally
* Don't forget the appropriate/correct annotations (Especially in test classes)
* If you cannot debug/fix an issue MOVE ON!:  make comments in the file to let me know your efforts
    * You may have time to come back to fix later
* Log each unique exception prior to throwing
    * Just need to demonstrate that you've considered this important task
* Create constants for error messages and other "magic strings" and "magic numbers"
* Validate ALL input received by the controller mappings

### Checklist
- [ ] Step 1: Read/understand the requirements clearly

- [ ] Step 2: DB (the pom.xml and application.properties are provided)

    - [ ] Open SQL Developer
        - [ ] Drag the schema SQL file onto the SQL Developer window and run it
        - [ ] Drag the data SQL file onto the SQL Developer window and run it
        - [ ] Write/validate the required SQL Queries in SQL Developer
    - [ ] Create the Model(s) to match the DB record
    - [ ] Create/Edit the MyBatis Mapper.xml file with the model mappings/queries from SQL Developer
    - [ ] Create the MyBatis Mapper.java Interface
    - [ ] Create the DAO Interface & DAO Implementation
    - [ ] Edit the IntegrationTests test class
        - [ ] Use the Query results from SQL Developer to create your static List<> of test data
        - [ ] Write simple integration tests to validate the DAO/MyBatis/DB are all working correctly
            - [ ] *.getAll(), *.getOneById(), or whatever DAO methods you have created
            - [ ] Try to write at least one negative test (exceptions are low hanging fruit)

- [ ] Step 3:  Service Layer

    - [ ] Create a Service class
    - [ ] Write succinct methods that simply return results of DAO calls
    - [ ] Any other necessary business logic is placed in the service class
        - [ ] Examples: Data manipulation/transformation, Data gathering such as from DAO or others
        - [ ] Writing tests can be delayed - move on and come back to the tests later
        - [ ] Tests only required for Non-DAO method calls


- [ ] Step 4: Controller

    - [ ] Scaffold the `@*Mapping()`s and corresponding methods
    - [ ] 	@RequestParam, @PathVariable where necessary
    - [ ] Call Service method to gather data
    - [ ] Handle exceptions
    - [ ] 	Return the correct status code
    - [ ] Write the End-To-End tests first - they are the quickest to write
    - [ ] Write the Web Layer tests (@WebMvcTest)

- [ ] Step 5: Run it up and ensure it all works (including passing tests)

