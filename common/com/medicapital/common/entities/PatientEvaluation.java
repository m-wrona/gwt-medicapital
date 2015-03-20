package com.medicapital.common.entities;

import java.util.Date;

/**
 * The persistent class for the PetientEvaluation database table.
 * 
 */
public class PatientEvaluation implements SerializableEntity {

	private int evaluationId;

	private Date created;

	private float evaluation;

	private String title;

	private String description;

	private int visitId;

	public int getId() {
		return evaluationId;
	}

	public void setId(int evaluationId) {
		this.evaluationId = evaluationId;
	}

	public void setVisitId(int visitId) {
		this.visitId = visitId;
	}

	public int getVisitId() {
		return visitId;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public float getEvaluation() {
		return evaluation;
	}

	public void setEvaluation(float evaluation) {
		this.evaluation = evaluation;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public boolean equals(Object obj) {
		if (super.equals(obj)) {
			return true;
		} else if (!(obj instanceof PatientEvaluation)) {
			return false;
		}
		PatientEvaluation other = (PatientEvaluation) obj;
		return evaluationId == other.evaluationId;
	}

	@Override
	public String toString() {
		StringBuilder data = new StringBuilder("[");
		data.append("id: " + evaluationId);
		data.append(", title: " + title);
		data.append(", description: " + description);
		data.append(", created: " + created);
		data.append(", evaluation: " + evaluation);
		data.append(", visitId: " + visitId);
		data.append("]");
		return data.toString();
	}

}