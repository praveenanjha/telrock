package com.telrock.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.telrock.model.Person;

/**
 * DAO implementation for Person entity
 * 
 * @author telrock.com
 */
@Service
@Transactional
public class PersonServiceImpl implements PersonService
{
	Logger log = LogManager.getLogger(this.getClass());

	@PersistenceContext
	private EntityManager em;

	@Autowired
	private DepartmentService departmentService;

	public Person save(Person person)	{
		log.info("Saving {}", person);

		// Validate person
		if(!StringUtils.hasText(person.getName()) || !StringUtils.hasText(person.getSurname())) {
			String errorMessage = "Person must be supplied with valid name and surname. "+person;
			log.error(errorMessage);
			throw new IllegalArgumentException(errorMessage);
		}

		// Checking if person has department and if department has been persisted
		if(person.getDepartment()!=null &&
				person.getDepartment().getId()==null) {
			log.debug("Persisting department for {}", person);
			person.setDepartment(
					departmentService.getOrCreateDepartment(
							person.getDepartment().getName(), person.getDepartment().getArea()));
		}

		em.persist(person);
		em.flush();
		
		return person;
	}

	
	public void setDefaultDepartment(Person person) {
		log.info("Setting default department for {}", person);
		
		if(person==null) {
			String errorMessage = "Supplied Person is null";
			log.error(errorMessage);
			throw new IllegalArgumentException(errorMessage);
		}
		
		if(person.getDepartment()!=null) {
			String errorMessage = "Department has already been set for person: "+person;
			log.error(errorMessage);
			throw new IllegalArgumentException(errorMessage);			
		}
		
		person.setDepartment(departmentService.getDefaultDepartment());
	}
}
