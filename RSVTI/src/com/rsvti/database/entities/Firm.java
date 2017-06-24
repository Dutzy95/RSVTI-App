package com.rsvti.database.entities;

import java.util.List;

public class Firm {
	
	private String firmName;
	private String registrationNumber;
	private String fiscalCode;
	private String address;
	private String phoneNumber;
	private String faxNumber;
	private String email;
	private String bankName;
	private String ibanCode;
	private Administrator administrator;
	private List<Rig> rigs;
	private List<Employee> employees;
	
	public Firm(String firmName, String registrationNumber, String fiscalCode, String address, String phoneNumber, String faxNumber, String email,
			String bankName, String ibanCode, Administrator administrator, List<Rig> rigs, List<Employee> employees) {
		this.setFirmName(firmName);
		this.registrationNumber = registrationNumber;
		this.fiscalCode = fiscalCode;
		this.address = address;
		this.phoneNumber = phoneNumber;
		this.faxNumber = faxNumber;
		this.email = email;
		this.bankName = bankName;
		this.ibanCode = ibanCode; 
		this.administrator = administrator;
		this.rigs = rigs;
		this.employees = employees;
	}
	
	@Override
	public boolean equals(Object o) {
		return o instanceof Firm &&
				this.getFirmName().equals(((Firm) o).getFirmName()) &&
				this.getRegistrationNumber().equals(((Firm) o).getRegistrationNumber()) &&
				this.getFiscalCode().equals(((Firm) o).getFiscalCode()) &&
				this.getAddress().equals(((Firm) o).getAddress()) &&
				this.getPhoneNumber().equals(((Firm) o).getPhoneNumber()) &&
				this.getFaxNumber().equals(((Firm) o).getFaxNumber()) &&
				this.getEmail().equals(((Firm) o).getEmail()) &&
				this.getBankName().equals(((Firm) o).getBankName()) &&
				this.getIbanCode().equals(((Firm) o).getIbanCode()) &&
				this.getAdministrator().equals(((Firm) o).getAdministrator());
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
	
	public List<Rig> getRigs() {
		return rigs;
	}
	
	public void setRigs(List<Rig> rigs) {
		this.rigs = rigs;
	}
	
	public void addRig(Rig rig) {
		rigs.add(rig);
	}
	
	public void removeRig(Rig rig) {
		rigs.remove(rig);
	}

	public String getFirmName() {
		return firmName;
	}

	public void setFirmName(String firmName) {
		this.firmName = firmName;
	}

	public List<Employee> getEmployees() {
		return employees;
	}

	public void setEmployees(List<Employee> employees) {
		this.employees = employees;
	}
}
