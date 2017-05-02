package com.rsvti.database.entities;

public class Administrator {
	
	private String name;
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
	public Administrator(String name, String idCode, String idNumber, String phoneNumber) {
		this.name = name;
		this.idCode = idCode;
		this.idNumber = idNumber;
		this.phoneNumber = phoneNumber;
	}
	
	@Override
	public boolean equals(Object o) {
		return o instanceof Administrator &&
				this.getIdCode().equals(((Administrator) o).getIdCode()) &&
				this.getIdNumber().equals(((Administrator) o).getIdNumber()) &&
				this.getName().equals(((Administrator) o).getName()) &&
				this.getPhoneNumber().equals(((Administrator) o).getPhoneNumber());
	}
	
	public String getName() {
		return name;
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
	
	public void setName(String name) {
		this.name = name;
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
}
