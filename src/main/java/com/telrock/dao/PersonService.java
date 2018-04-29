/*
 * Copyright Telrock Communications Limited 2008 * 
 *
 * $Header:  $
 * $Revision:  $
 * $Date:  $ 
 * 
 */
package com.telrock.dao;

import com.telrock.model.Person;

public interface PersonService
{
	/**
	 * Saves a person in the database.
	 * 
	 * @param person
	 * @return Person object with the id that has been assigned in the database.
	 */
	Person save(Person person);

	/**
	 * Sets the default department to a given person.
	 * 
	 * @param person
	 */
	void setDefaultDepartment(Person person);
}
