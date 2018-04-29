/*
 * Copyright Telrock Communications Limited 2008 * 
 *
 * $Header:  $
 * $Revision:  $
 * $Date:  $ 
 * 
 */
package com.telrock.dao;

import static org.junit.Assert.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.telrock.dao.PersonService;
import com.telrock.model.DefaultDepartment;
import com.telrock.model.Department;
import com.telrock.model.Person;

@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
@Transactional
public class PersonServiceTest {
	@PersistenceContext
	private EntityManager em;

	@Autowired
	PersonService personService;

	private final Department defaultDepartment = new Department(DefaultDepartment.DEFAULT_NAME.toString(), DefaultDepartment.DEFAULT_AREA.toString());


	@Test
	public void testSaveNewPersonWithoutDepartment_valid() {
		final String name = "the name";
		final String surname = "MySurname";

		Person p1 = new Person(name,surname,null);
		Person p2 = personService.save(p1);

		assertNotNull("The returned object can't be null", p2);
		assertNotNull("The id can't be null", p2.getId());
		assertTrue("The id has to be greater than 0", p2.getId() > 0);
		assertEquals("Names not equal", name, p2.getName());
		assertEquals("Surnames not equal", surname, p2.getSurname());
	}

	@Test
	public void testSaveNewPersonWithNewDepartment_valid() {
		final String name = "the name";
		final String surname = "MySurname";
		final Department department = new Department("DeptName", "DeptArea");

		Person expected = new Person(name, surname, department);
		Person actual = personService.save(expected);

		assertNotNull("The returned object can't be null", actual);
		assertNotNull("The id can't be null", actual.getId());
		assertTrue("The id has to be greater than 0", actual.getId() > 0);
		assertNotNull("Department id can't be null", actual.getDepartment().getId());
		assertTrue("Department id has to be greater than 0", actual.getDepartment().getId() > 0);
		assertEquals("Department names not equal", department.getName(), actual.getDepartment().getName());
		assertEquals("Department areas not equal", department.getArea(), actual.getDepartment().getArea());
	}

	@Test
	public void testSaveNewPersonWithExistingDepartment_valid() {
		final String name = "the name";
		final String surname = "MySurname";
		final String departmentName = "DeptName";
		final String departmentArea = "DeptArea";
		final Department department = new Department(departmentName, departmentArea);
		final Department expectedDepartment = new Department(departmentName, departmentArea);
		em.persist(expectedDepartment);
		em.flush();

		Person actual = personService.save(new Person(name, surname, department));
		
		assertEquals("Deparment has been duplicated",expectedDepartment, actual.getDepartment());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSave_IllegalArgumentNullName() {
		final String name = null;
		final String surname = "MySurname";

		personService.save(new Person(name,surname,null));

		fail("Should throw IllegalArgument Exception");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSave_IllegalArgumentEmptyName() {
		final String name = "";
		final String surname = "MySurname";

		personService.save(new Person(name,surname,null));

		fail("Should throw IllegalArgument Exception");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSave_IllegalArgumentSpaceName() {
		final String name = " ";
		final String surname = "MySurname";

		personService.save(new Person(name,surname,null));

		fail("Should throw IllegalArgument Exception");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSave_IllegalArgumentNullSurname() {
		final String name = "MyName";
		final String surname = null;

		personService.save(new Person(name,surname,null));

		fail("Should throw IllegalArgument Exception");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSave_IllegalArgumentEmptySurname() {
		final String name = "MyName";
		final String surname = "";

		personService.save(new Person(name,surname,null));

		fail("Should throw IllegalArgument Exception");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSave_IllegalArgumentSpaceSurname() {
		final String name = "MyName";
		final String surname = " ";

		personService.save(new Person(name,surname,null));

		fail("Should throw IllegalArgument Exception");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSave_IllegalArgumentNullParameters() {
		final String name = null;
		final String surname = null;

		personService.save(new Person(name,surname,null));

		fail("Should throw IllegalArgument Exception");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSave_IllegalArgumentEmptyParameters() {
		final String name = "";
		final String surname = "";

		personService.save(new Person(name,surname,null));

		fail("Should throw IllegalArgument Exception");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSave_IllegalArgumentSpaceParameters() {
		final String name = " ";
		final String surname = " ";

		personService.save(new Person(name,surname,null));

		fail("Should throw IllegalArgument Exception");
	}


	/**
	 * Adds default department to database
	 */
	private void setupDefaultDepartment() {
		em.persist(defaultDepartment);
		em.flush();		
	}

	@Test
	public void testSetDefaultDepartment_valid() {
		setupDefaultDepartment();
		Person person = new Person("MyName", "MySurname", null);

		personService.setDefaultDepartment(person);

		assertEquals(defaultDepartment, person.getDepartment());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSetDefaultDepartment_IllegalArgumentNullPerson() {
		personService.setDefaultDepartment(null);

		fail("Should throw IllegalArgument Exception");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSetDefaultDepartment_IllegalArgumentPersonWithDepartment() {
		Person person = new Person("MyName", "MySurname", new Department());
		personService.setDefaultDepartment(person);

		fail("Should throw IllegalArgument Exception");
	}
}
