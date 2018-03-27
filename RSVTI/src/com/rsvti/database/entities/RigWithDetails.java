package com.rsvti.database.entities;

import java.util.Date;

public class RigWithDetails {
	
	private Rig rig;
	private String firmName;
	private String firmAddress;
	private String firmPhoneNumber;
	private String firmFiscalCode;
	private String firmRegistrationNumber;
	private String firmId;
	private Date dueDate;
	
	public RigWithDetails(Rig rig, Firm firm, Date dueDate) {
		this.setRig(rig);
		this.setFirmName(firm.getFirmName());
		this.setFirmAddress(firm.getAddress());
		this.setFirmPhoneNumber(firm.getPhoneNumber());
		this.setFirmFiscalCode(firm.getFiscalCode());
		this.setFirmRegistrationNumber(firm.getRegistrationNumber());
		this.setFirmId(firm.getId());
		this.setDueDate(dueDate);
	}

	public Rig getRig() {
		return rig;
	}

	public void setRig(Rig rig) {
		this.rig = rig;
	}

	public String getFirmName() {
		return firmName;
	}

	public void setFirmName(String firmName) {
		this.firmName = firmName;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public String getFirmAddress() {
		return firmAddress;
	}

	public void setFirmAddress(String firmAddress) {
		this.firmAddress = firmAddress;
	}

	public String getFirmPhoneNumber() {
		return firmPhoneNumber;
	}

	public void setFirmPhoneNumber(String firmPhoneNumber) {
		this.firmPhoneNumber = firmPhoneNumber;
	}

	public String getFirmFiscalCode() {
		return firmFiscalCode;
	}

	public void setFirmFiscalCode(String firmFiscalCode) {
		this.firmFiscalCode = firmFiscalCode;
	}

	public String getFirmRegistrationNumber() {
		return firmRegistrationNumber;
	}

	public void setFirmRegistrationNumber(String firmRegistrationNumber) {
		this.firmRegistrationNumber = firmRegistrationNumber;
	}

	public String getFirmId() {
		return firmId;
	}

	public void setFirmId(String firmId) {
		this.firmId = firmId;
	}

}
