Abstract
There is an project that uses Spring 3, JPA 2.0 (using Hibernate 4) and a in-memory database (H2).

The application takes care of initialising the database connection, and there is partially implemented test that also takes care of initialising the connection to the database, so you don't need to take care of it. Any change done to the entities will be automatically reflected in the database, so you don't need to take care about database's structure. It uses JPA 2.0, so no explicit use of any hibernate-related classes would be needed.

1 - Spring (i)

Task 1
	Make that services as for example PersonService to be candidates for auto-detection when using annotation-based configuration, as for example in the test PersonServiceTest. Note that applicationContext.xml has already in place the configuration for auto-detection.

2 - Hibernate / JPA

There are two tables in the database:

Person          Department
* id             * id
name             name
surname          area
* department_id

remark: note that the field person.department_id is a foreign key to department.id

Task 1
	- Define the entity com.telrock.Department (note that the entity com.telrock.model.Person is partially implemented)
	- Implement DepartmentServiceImpl, including tests to check its behaviour.

Task 2
	- Complete the entity com.telrock.Person including the department.
	- Implement PersonServiceImpl, including tests to check its behaviour.

Task 3
	- Make both services transactional.


3 - Spring (ii)

Task 2
	Database configuration is defined in the applicationContext.xml. We now want to externalise this value to a properties file, so we can easily change it.

4 - Java

In Person and Department, override hashCode and equals with custom implementations and implement Comparable<T>.

Write tests to check that it works as expected.
