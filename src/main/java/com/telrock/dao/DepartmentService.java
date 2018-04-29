/*
 * Copyright Telrock Communications Limited 2008 * 
 *
 * $Header:  $
 * $Revision:  $
 * $Date:  $ 
 * 
 */
package com.telrock.dao;

import java.util.List;

import com.telrock.model.Department;

public interface DepartmentService
{
	static final String DEFAULT_NAME = "Default department";
	static final String DEFAULT_AREA = "Default area";

	/**
	 * It will return an existing department in the DB if it already exists, and in other case the department will be
	 * created before returning.
	 * 
	 * @param departmentName
	 *           name to check.
	 * @return department.
	 */
	Department getOrCreateDepartment(String departmentName, String area);

	/**
	 * Returns the department that has <code>DEFAULT_NAME</code> as its name and <code>DEFAULT_AREA</code> as its area.
	 * 
	 * @return default department.
	 */
	Department getDefaultDepartment();

	/**
	 * Given an area searches all departments that belong to this area.
	 * 
	 * @param area
	 *           area to search for.
	 * @return list of departments in the area.
	 */
	List<Department> findDepartmentsInArea(String area);
}
