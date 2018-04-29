package com.telrock.model;

import static org.junit.Assert.*;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.junit.Test;

public class PersonTest {

	@Test
	public final void testHashCode() {
		final String name = "MyName";
		final String surname = "MySurname";
		
		Person p1 = new Person(name, surname, null);
		p1.setId(1L);
		Person p2 = new Person(name, surname, null);
		p2.setId(1L);
		Person p3 = new Person(name, surname, null);
		p3.setId(3L);
		
		assertEquals("valid test failed", p1.hashCode(),p2.hashCode());
		assertTrue("invalid test failed",p1.hashCode()!=p3.hashCode());
	}

	@Test
	public final void testToString() {
		final String name = "MyName";
		final String surname = "MySurname";
		final Department dept = new Department();
		final long id = 1L;
		
		String expected = new ToStringBuilder(new Person(), ToStringStyle.SHORT_PREFIX_STYLE).append("id", id).append("name", name)
				.append("surname", surname).append("department", dept).toString();
		Person person1 = new Person(name, surname, dept);
		person1.setId(id);
		Person person2 = new Person(name, surname, dept);
		person2.setId(2L);
		
		assertEquals("valid test failed",expected, person1.toString());
		assertTrue("invalid test failed",expected!=person2.toString());
	}

	@Test
	public final void testEqualsObject() {
		final String name = "MyName";
		final String surname = "MySurname";
		
		Person p1 = new Person(name, surname, null);
		p1.setId(1L);
		Person p2 = new Person(name, surname, null);
		p2.setId(1L);
		Person p3 = new Person(name, surname, null);
		p3.setId(3L);
		
		assertEquals("valid test failed", p1,p2);
		assertFalse("invalid test failed",p1.equals(p3));
	}

	@Test
	public final void testCompareTo() {
		final String name = "MyName";
		final String surname = "MySurname";
		
		Person p1 = new Person(name, surname, null);
		p1.setId(1L);
		Person p2 = new Person(name, surname, null);
		p2.setId(1L);
		Person p3 = new Person(name, surname, null);
		p3.setId(3L);
		
		assertTrue("Equal test failed", p1.compareTo(p2)==0);
		assertTrue("Before test failed", p1.compareTo(p3)<0);
		assertTrue("After test failed", p3.compareTo(p1)>0);
	}

	@Test
	public final void testCompareTo_noIDs() {
		
		Person p1 = new Person("a", "a", null);
		Person p2 = new Person("a", "b", null);
		Person p3 = new Person("b", "a", null);
		
		assertTrue("surname to surname failed", p1.compareTo(p2)<0);
		assertTrue("name to name failed", p2.compareTo(p3)<0);
	}

}
