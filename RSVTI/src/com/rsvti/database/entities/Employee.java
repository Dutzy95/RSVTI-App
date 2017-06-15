package com.rsvti.database.entities;

public class Employee {
	
	private String firstName;
	private String lastName;
	private String idCode;
	private String idNumber;
	private String personalIdentificationNumber;
	private EmployeeAuthorization authorization;
	private String title;
	
	public Employee(String firstName, String lastName, String idCode, String idNumber, String personalIdentificationNumber, 
			EmployeeAuthorization authorization, String title) {
		this.setFirstName(firstName);
		this.setLastName(lastName);
		this.setIdCode(idCode);
		this.setIdNumber(idNumber);
		this.setPersonalIdentificationNumber(personalIdentificationNumber);
		this.setAuthorization(authorization);
		this.setTitle(title);
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getIdCode() {
		return idCode;
	}

	public void setIdCode(String idCode) {
		this.idCode = idCode;
	}

	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}

	public String getPersonalIdentificationNumber() {
		return personalIdentificationNumber;
	}

	public void setPersonalIdentificationNumber(String personalIdentificationNumber) {
		this.personalIdentificationNumber = personalIdentificationNumber;
	}

	public EmployeeAuthorization getAuthorization() {
		return authorization;
	}

	public void setAuthorization(EmployeeAuthorization authorization) {
		this.authorization = authorization;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	@Override
	public boolean equals(Object o) {
		return o instanceof Employee &&
				((Employee) o).getFirstName().equals(firstName) &&
				((Employee) o).getLastName().equals(lastName) &&
				((Employee) o).getIdCode().equals(idCode) &&
				((Employee) o).getIdNumber().equals(idNumber) &&
				((Employee) o).getPersonalIdentificationNumber().equals(personalIdentificationNumber) &&
				((Employee) o).getTitle().equals(title);
	}
	
}