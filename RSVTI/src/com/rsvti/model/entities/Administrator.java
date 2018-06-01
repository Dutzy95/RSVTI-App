package com.rsvti.model.entities;

public class Administrator {
	
	private String firstName;
	private String lastName;
	private String idCode;
	private String idNumber;
	private String phoneNumber;
	
	/**
	 * Constructor for the administrator of a firm.
	 * @param name admin name
	 * @param idCode admin id code (serie de buletin)
	 * @param idNumber admin id number (numar de buletin)
	 * @param phoneNumber admin personal phone number
	 */
	public Administrator(String firstName, String lastName, String idCode, String idNumber, String phoneNumber) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.idCode = idCode;
		this.idNumber = idNumber;
		this.phoneNumber = phoneNumber;
	}
	
	@Override
	public boolean equals(Object o) {
		return o instanceof Administrator &&
				this.getIdCode().equals(((Administrator) o).getIdCode()) &&
				this.getIdNumber().equals(((Administrator) o).getIdNumber()) &&
				this.getFirstName().equals(((Administrator) o).getFirstName()) &&
				this.getLastName().equals(((Administrator) o).getLastName()) &&
				this.getPhoneNumber().equals(((Administrator) o).getPhoneNumber());
	}
	
	public String getFirstName() {
		return firstName;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public String getIdCode() {
		return idCode;
	}
	
	public String getIdNumber() {
		return idNumber;
	}
	
	public String getPhoneNumber() {
		return phoneNumber;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public void setIdCode(String idCode) {
		this.idCode = idCode;
	}
	
	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}
	
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	public String toString() {
		return "firstName: " + firstName + 
			"\nlastName: " + lastName +
			"\nidCode: " + idCode + 
			"\nidNumber: " + idNumber + 
			"\nphoneNumber: " + phoneNumber;
	}
}
