It's **powerful** example of creating and using own **PL/SQL** framework with **small restrictions** on your entities.
The framework based on **CRUD PL/SQL** operations with additional operations over basic operations.
With this framework you can don't think about PL/SQL and concentrate on your business logic.
### Core of this framework has following principles
 - order of fields must be equal to order of arguments in PL/SQL
 - fields name must be equal to names of table columns
 - for building entities hierarchy use @Depends, @Reference
 - your entities must be comply with Java Bean rules
