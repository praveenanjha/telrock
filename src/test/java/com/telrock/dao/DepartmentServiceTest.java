package com.telrock.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.telrock.model.DefaultDepartment;
import com.telrock.model.Department;

@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
@Transactional
public class DepartmentServiceTest {
	
	@PersistenceContext
	private EntityManager em;

	@Autowired
	DepartmentService departmentService;

	private final Department defaultDepartment = new Department(DefaultDepartment.DEFAULT_NAME.toString(), DefaultDepartment.DEFAULT_AREA.toString());
	private final Department[] data = {
			new Department("TestDepartment1", "Area1"),	new Department("TestDepartment2.1", "Area2"),
			new Department("TestDepartment2.2", "Area2"), new Department("TestDepartment3.1", "Area3"),
			new Department("TestDepartment3.2", "Area3"), new Department("TestDepartment3.3", "Area3")
	};

	
	@Before
	public void setup() {
		em.persist(defaultDepartment);
		
		for(Department department:data) {
			em.persist(department);
		}

		em.flush();
	}

	@Test
	public final void testGetOrCreateDepartment_ValidCreate() {
		final String departmentName = "MockNewDepartmentName";
		final String area = "MockNewDepartmentArea";		

		Department actual = departmentService.getOrCreateDepartment(departmentName, area);

		assertNotNull("The returned object can't be null", actual);
		assertNotNull("The id can't be null", actual.getId());
		assertTrue("The id has to be greater than 0", actual.getId() > 0);
		assertEquals("Names not equal", departmentName, actual.getName());
		assertEquals("Areas not equal", area, actual.getArea());
	}

	@Test
	public final void testGetOrCreateDepartment_ValidGet() {
		final Department expected = data[0];
		Department actual = departmentService.getOrCreateDepartment(expected.getName(), expected.getArea());

		assertEquals("Departments not equal", expected, actual);
		assertEquals("Names not equal", expected.getName(), actual.getName());
		assertEquals("Areas not equal", expected.getArea(), actual.getArea());
	}	

	@Test(expected = IllegalArgumentException.class)
	public final void testGetOrCreateDepartment_IllegalArgumentNullName() {
		final String departmentName = null;
		final String area = "MockDepartmentArea";

		departmentService.getOrCreateDepartment(departmentName, area);
		fail("Should throw IllegalArgument Exception");
	}

	@Test(expected = IllegalArgumentException.class)
	public final void testGetOrCreateDepartment_IllegalArgumentEmptyName() {
		final String departmentName = "";
		final String area = "MockDepartmentArea";

		departmentService.getOrCreateDepartment(departmentName, area);
		fail("Should throw IllegalArgument Exception");
	}

	@Test(expected = IllegalArgumentException.class)
	public final void testGetOrCreateDepartment_IllegalArgumentNullArea() {
		final String departmentName = "MockDepartmentName";
		final String area = null;

		departmentService.getOrCreateDepartment(departmentName, area);
		fail("Should throw IllegalArgument Exception");
	}

	@Test(expected = IllegalArgumentException.class)
	public final void testGetOrCreateDepartment_IllegalArgumentEmptyArea() {
		final String departmentName = "MockDepartmentName";
		final String area = "";

		departmentService.getOrCreateDepartment(departmentName, area);
		fail("Should throw IllegalArgument Exception");
	}

	@Test(expected = IllegalArgumentException.class)
	public final void testGetOrCreateDepartment_IllegalArgumentNullParameters() {
		final String departmentName = null;
		final String area = null;

		departmentService.getOrCreateDepartment(departmentName, area);
		fail("Should throw IllegalArgument Exception");
	}

	@Test(expected = IllegalArgumentException.class)
	public final void testGetOrCreateDepartment_IllegalArgumentEmptyParameters() {
		final String departmentName = "";
		final String area = "";

		departmentService.getOrCreateDepartment(departmentName, area);
		fail("Should throw IllegalArgument Exception");
	}

	@Test(expected = IllegalArgumentException.class)
	public final void testGetOrCreateDepartment_IllegalArgumentSpaceParameters() {
		final String departmentName = " ";
		final String area = " ";

		departmentService.getOrCreateDepartment(departmentName, area);
		fail("Should throw IllegalArgument Exception");
	}

	
	@Test
	public final void testGetDefaultDepartment() {
		Department expected = defaultDepartment;
		Department actual = departmentService.getDefaultDepartment();
		
		assertEquals("Departments not equal", expected, actual);
		assertEquals("Names not equal", expected.getName(), actual.getName());
		assertEquals("Areas not equal", expected.getArea(), actual.getArea());
	}


	@Test
	public final void testFindOneDepartmentsInArea() {
		List<Department> expected = new ArrayList<>();
		expected.add(data[0]);
		
		List<Department> actual = departmentService.findDepartmentsInArea(data[0].getArea());
		assertEquals(expected, actual);
	}

	@Test
	public final void testFindMultipleDepartmentsInArea() {
		final String area = "Area3";
		
		List<Department> expected = new ArrayList<>();
		for(Department department: data) {
			if (area.equals(department.getArea()))
				expected.add(department);
		}
		
		List<Department> actual = departmentService.findDepartmentsInArea(area);
		assertEquals(expected, actual);
	}

	@Test
	public final void testFindZeroDepartmentsInArea() {
		final String area = "AreaWithoutDepartments";
		
		List<Department> expected = new ArrayList<>();
		List<Department> actual = departmentService.findDepartmentsInArea(area);
		assertEquals(expected, actual);
	}

	@Test(expected = IllegalArgumentException.class)
	public final void testFindZeroDepartmentsInArea_IllegalArgumentNull() {
		final String area = null;
		departmentService.findDepartmentsInArea(area);
		fail("Should throw IllegalArgument Exception");
	}

	@Test(expected = IllegalArgumentException.class)
	public final void testFindZeroDepartmentsInArea_IllegalArgumentEmptyString() {
		final String area = "";
		departmentService.findDepartmentsInArea(area);
		fail("Should throw IllegalArgument Exception");
	}

	@Test(expected = IllegalArgumentException.class)
	public final void testFindZeroDepartmentsInArea_IllegalArgumentSpaceString() {
		final String area = " ";
		departmentService.findDepartmentsInArea(area);
		fail("Should throw IllegalArgument Exception");
	}

}
