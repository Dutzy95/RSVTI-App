package com.rsvti.database.entities;

public class Firm {
	
	private String registrationNumber;
	private String fiscalCode;
	private String address;
	private String phoneNumber;
	private String faxNumber;
	private String email;
	private String bankName;
	private String ibanCode;
	private Administrator administrator;
	
	public Firm(String registrationNumber, String fiscalCode, String address, String phoneNumber, String faxNumber, String email,
			String bankName, String ibanCode, Administrator administrator) {
		this.setRegistrationNumber(registrationNumber);
		this.setFiscalCode(fiscalCode);
		this.setAddress(address);
		this.setPhoneNumber(phoneNumber);
		this.setFaxNumber(faxNumber);
		this.setEmail(email);
		this.setBankName(bankName);
		this.setIbanCode(ibanCode);
		this.setAdministrator(administrator);
	}

	public String getRegistrationNumber() {
		return registrationNumber;
	}

	public void setRegistrationNumber(String registrationNumber) {
		this.registrationNumber = registrationNumber;
	}

	public String getFiscalCode() {
		return fiscalCode;
	}

	public void setFiscalCode(String fiscalCode) {
		this.fiscalCode = fiscalCode;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getFaxNumber() {
		return faxNumber;
	}

	public void setFaxNumber(String faxNumber) {
		this.faxNumber = faxNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getIbanCode() {
		return ibanCode;
	}

	public void setIbanCode(String ibanCode) {
		this.ibanCode = ibanCode;
	}

	public Administrator getAdministrator() {
		return administrator;
	}

	public void setAdministrator(Administrator administrator) {
		this.administrator = administrator;
	}
}
