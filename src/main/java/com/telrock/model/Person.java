package com.telrock.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.CompareToBuilder;

/**
 * Person entity
 * 
 * @author com.telrock
 */
@Entity
public class Person
implements Comparable<Person> {
	@Id
	@GeneratedValue
	private Long id;

	@Column
	private String name;

	@Column
	private String surname;
	
	@ManyToOne
	private Department department;

	public Person(){}

	public Person(String name, String surname, Department department)
	{
		this.name = name;
		this.surname = surname;
		this.department = department;
	}

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getSurname()
	{
		return surname;
	}

	public void setSurname(String surname)
	{
		this.surname = surname;
	}

	public Department getDepartment()
	{
		return department;
	}

	public void setDepartment(Department department)
	{
		this.department = department;
	}

	
	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).append("id", id).append("name", name)
				.append("surname", surname).append("department", department).toString();
	}

	@Override
	public boolean equals(final Object other) {
		if (!(other instanceof Person)) {
			return false;
		}
		Person castOther = (Person) other;
		return new EqualsBuilder().append(id, castOther.id).isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(id).toHashCode();
	}

	@Override
	public int compareTo(final Person other) {
		return new CompareToBuilder().append(id, other.id).append(name, other.name).append(surname, other.surname)
				.append(department, other.department).toComparison();
	}
	
}
