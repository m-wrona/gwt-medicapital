package com.medicapital.common.entities;

public class VisitType implements SerializableEntity {

	public static final int DEFAULT_DURATION = 15;

	private int visitTypeId;
	private int doctorId;
	private int duration;
	private String name;
	private String description;

	@Override
	public int getId() {
		return visitTypeId;
	}

	@Override
	public void setId(int entityId) {
		visitTypeId = entityId;
	}

	public int getDoctorId() {
		return doctorId;
	}

	public void setDoctorId(int doctorId) {
		this.doctorId = doctorId;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		StringBuilder string = new StringBuilder('[');
		string.append("visitTypeId: ").append(visitTypeId);
		string.append(", doctorId: ").append(doctorId);
		string.append(", duration: ").append(duration);
		string.append(", name: ").append(name);
		string.append(", description: ").append(description);
		string.append(']');
		return string.toString();
	}

	@Override
	public boolean equals(Object obj) {
		return EntitiesUtils.equals(this, obj);
	}

}
