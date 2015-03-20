package com.medicapital.common.commands.visit;

import com.medicapital.common.commands.CommandResp;
import com.medicapital.common.entities.PatientVisit;

public class BookVisitCommandResp extends CommandResp<PatientVisit> {

	private PatientVisit bookedVisit;

	public BookVisitCommandResp(PatientVisit bookedVisit) {
		super(PatientVisit.class);
		this.bookedVisit = bookedVisit;
	}

	/**
	 * Constructor for RPC communication
	 */
	protected BookVisitCommandResp() {
		// empty
	}

	public PatientVisit getBookedVisit() {
		return bookedVisit;
	}
	
	@Override
	public String toString() {
	    StringBuilder string = new StringBuilder("[ " + getClass().getName());
	    string.append(", bookedVisit: ").append(bookedVisit);
	    string.append(']');
	    return string.toString();
	}

}
