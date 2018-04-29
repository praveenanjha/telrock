package com.telrock.model;

import static org.junit.Assert.*;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.junit.Test;

public class DepartmentTest {

	@Test
	public final void testHashCode() {
		final String name = "DeptName";
		final String area = "DeptArea";
		
		Department d1 = new Department(name, area);
		d1.setId(1L);
		Department d2 = new Department(name, area);
		d2.setId(1L);
		Department d3 = new Department(name, area);
		d3.setId(2L);
		
		assertEquals(d1.hashCode(),d2.hashCode());
		assertTrue(d1.hashCode()!=d3.hashCode());
	}
	
	@Test
	public final void testToString() {
		final String name = "DeptName";
		final String area = "DeptArea";
		final long id = 2L;
		
		String expected = new ToStringBuilder(new Department(), ToStringStyle.SHORT_PREFIX_STYLE)
				.append("id", id).append("name", name).append("area", area).toString();		
		Department department = new Department(name, area);
		department.setId(id);
		
		assertEquals(expected,department.toString());
	}

	@Test
	public final void testEqualsObject() {
		final String name = "DeptName";
		final String area = "DeptArea";
		
		Department d1 = new Department(name, area);
		d1.setId(1L);
		Department d2 = new Department(name, area);
		d2.setId(1L);
		Department d3 = new Department(name, area);
		d3.setId(2L);
		
		assertEquals("Equal objects not returning equal", d1,d2);
		assertFalse("Non equal objects return equal",d1.equals(d3));
	}

	@Test
	public final void testCompareTo() {
		Department d1 = new Department();
		d1.setId(1L);
		Department d2 = new Department();
		d2.setId(1L);
		Department d3 = new Department();
		d3.setId(3L);
		
		assertTrue("equal test failed",d1.compareTo(d2)==0);
		assertTrue("before test failed",d1.compareTo(d3)<0);
		assertTrue("after test failed",d3.compareTo(d1)>0);
	}

	@Test
	public final void testCompareTo_noIDs() {
		Department d1 = new Department("a", "a");
		Department d2 = new Department("a", "b");
		Department d3 = new Department("b", "a");
		
		assertTrue("department to department failed",d1.compareTo(d2)<0);
		assertTrue("area to area failed",d2.compareTo(d3)<0);
	}

}
