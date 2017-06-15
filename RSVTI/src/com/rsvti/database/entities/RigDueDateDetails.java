package com.rsvti.database.entities;

import java.util.Date;

public class RigDueDateDetails {
	
	private Rig rig;
	private String firmName;
	private Date dueDate;
	
	public RigDueDateDetails(Rig rig, String firmName, Date dueDate) {
		this.setRig(rig);
		this.setFirmName(firmName);
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

}
