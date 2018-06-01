package com.rsvti.model.entities;

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
	private String executiveName;
	private Administrator administrator;
	private List<Rig> rigs;
	private List<Employee> employees;
	private String id;
	
	public Firm(String firmName, String registrationNumber, String fiscalCode, String address, String phoneNumber, String faxNumber, String email,
			String bankName, String ibanCode, String executiveName, Administrator administrator, List<Rig> rigs, List<Employee> employees) {
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
		this.setExecutiveName(executiveName);
	}
	
	@Override
	public boolean equals(Object o) {
		return o instanceof Firm && ((Firm) o).getId().equals(id);
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

	public String getExecutiveName() {
		return executiveName;
	}

	public void setExecutiveName(String executiveName) {
		this.executiveName = executiveName;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public String toString() {
		return "firmName: " + firmName +
				"\nregistrationNumber: " + registrationNumber +
				"\nfiscalCode: " + fiscalCode +
				"\naddress: " + address +
				"\nphoneNumber: " + phoneNumber +
				"\nfaxNumber: " + faxNumber +
				"\nemail: " + email +
				"\nbankName: " + bankName +
				"\nibanCode: " + ibanCode +
				"\nexecutiveName: " + executiveName +
				"\nadministrator: " + administrator.toString() +
				"\nid: " + id;
	}
}
