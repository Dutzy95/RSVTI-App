package com.rsvti.model.entities;

import java.util.Date;

import com.rsvti.common.Constants;

public class Employee {
	
	private String firstName;
	private String lastName;
	private String idCode;
	private String idNumber;
	private String personalIdentificationNumber;
	private EmployeeAuthorization authorization;
	private String title;
	private Date birthDate;
	private String birthCity;
	private String homeAddress;
	private String homeRegion;
	private boolean isRsvti;
	
	public Employee(String firstName, String lastName, String idCode, String idNumber, String personalIdentificationNumber, Date birthDate, 
			String birthCity, String homeAddress, String homeRegion, EmployeeAuthorization authorization, String title, boolean isRsvti) {
		this.setFirstName(firstName);
		this.setLastName(lastName);
		this.setIdCode(idCode);
		this.setIdNumber(idNumber);
		this.setPersonalIdentificationNumber(personalIdentificationNumber);
		this.setAuthorization(authorization);
		this.setTitle(title);
		this.setBirthDate(birthDate);
		this.setBirthCity(birthCity);
		this.setHomeRegion(homeRegion);
		this.setHomeAddress(homeAddress);
		this.setRsvti(isRsvti);
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

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public String getBirthCity() {
		return birthCity;
	}

	public void setBirthCity(String birthCity) {
		this.birthCity = birthCity;
	}

	public String getHomeRegion() {
		return homeRegion;
	}

	public void setHomeRegion(String homeRegion) {
		this.homeRegion = homeRegion;
	}

	public boolean isRsvti() {
		return isRsvti;
	}

	public void setRsvti(boolean isRsvti) {
		this.isRsvti = isRsvti;
	}

	public String getHomeAddress() {
		return homeAddress;
	}

	public void setHomeAddress(String homeAddress) {
		this.homeAddress = homeAddress;
	}
	
	public String toString() {
		return "firstName: " + firstName +
				"\nlastName: " + lastName +
				"\nidCode: " + idCode +
				"\nidNumber: " + idNumber +
				"\npersonalIdentificationNumber: " + personalIdentificationNumber +
				"\nauthorization: " + authorization.toString() +
				"\ntitle: " + title +
				"\nbirthDate: " + Constants.DEFAULT_DATE_FORMATTER.format(birthDate) +
				"\nbirthCity: " + birthCity +
				"\nhomeAddress: " + homeAddress +
				"\nhomeRegion: " + homeRegion +
				"\nisRsvti: " + isRsvti;
	}
}