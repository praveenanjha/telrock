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

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.telrock.model.DefaultDepartment;
import com.telrock.model.Department;

@Service
@Transactional
public class DepartmentServiceImpl implements DepartmentService {
	Logger log = LogManager.getLogger(this.getClass());

	@PersistenceContext
	private EntityManager em;


	public Department getOrCreateDepartment(String departmentName, String area) {
		log.info("Retrieving department {} in area {}",departmentName, area);

		// Validate input parameters
		if (!StringUtils.hasText(departmentName) || !StringUtils.hasText(area)) {
			String errorMessage = "Department fields must be populated. "+new Department(departmentName,area);
			log.error(errorMessage);
			throw new IllegalArgumentException("errorMessage");
		}

		// Retrieve results using named query
		List<Department> result = em.createNamedQuery("findDepartmentByNameAndArea", Department.class)
				.setParameter("name", departmentName)
				.setParameter("area", area)
				.getResultList();

		// Validate results error when more than one department matched
		if (result.size() > 1) {
			String errorMessage = new Department(departmentName,area) + " matched "+result.size()+" results";
			log.error(errorMessage);
			throw new IllegalArgumentException("errorMessage");
		}

		// Return matched department
		if (result.size() == 1) {
			log.debug("Retrieved {}", result.get(0));
			return result.get(0);
		} 

		// No matches, create new department
		Department department = new Department(departmentName, area);
		log.debug("Creating {}", department);
		em.persist(department);
		em.flush();

		return department;
	}


	public Department getDefaultDepartment() {
		log.info("Retrieving default department");
		
		// Retrieve department matching default department settings
		return em.createNamedQuery("findDepartmentByNameAndArea", Department.class)
				.setParameter("name", DefaultDepartment.DEFAULT_NAME.toString())
				.setParameter("area", DefaultDepartment.DEFAULT_AREA.toString())
				.getSingleResult();
	}


	public List<Department> findDepartmentsInArea(String area) {
		log.info("Retrieving all departments in area {}", area);

		// Validate input parameter
		if (!StringUtils.hasText(area)) {
			String errorMessage = "Supplied area ["+area+"] must be populated";
			log.error(errorMessage);
			throw new IllegalArgumentException("errorMessage");
		}

		// Retrieve departments using named query, can return empty List
		return em.createNamedQuery("findDepartmentByArea", Department.class)
				.setParameter("area", area)
				.getResultList();
	}
}
