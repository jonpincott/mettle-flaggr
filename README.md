Requirements:
- As an admin user, I want to be able to create a feature which defaults to disabled
- As an admin user, I want to be able to switch on a feature for a user
- As a user, I want to be able to get all the enabled features (a mix of all the globally enabled ones and the ones enabled just for my user)

Framework:
- spring boot
- gradle

Additional libraries:
- spring security
- spring data jpa
- spring validation
- lombok
- mapstruct
- groovy
- spock

To do:
- Add more test cases for unhappy-path events, e.g.
  - resource not found
  - resource already exists
  - validation errors
- Add api versioning
- Add spring profiles