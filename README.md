It's **powerful** example of creating and using own **PL/SQL** framework with **small restrictions** on your entities.
The framework based on **CRUD PL/SQL** operations with additional operations over basic operations.
With this framework you can don't think about PL/SQL and concentrate on your business logic.
### Core principles
 - order of fields must be equal to order of arguments in PL/SQL
 - fields order must be equal to order of table columns
 - for building entities hierarchy use **@ManyToOne**, **@OneToMany**, **OneToOne**
 - your entities must be comply with Java Bean rules

### Capabilities
 - support String, Integer, Double, Date, Boolean
 - support OneToOne, ManyToOne, OneToMany
 - transparent addition custom entities
 - lightweight core
 - minimum amount PL/SQL scripts
