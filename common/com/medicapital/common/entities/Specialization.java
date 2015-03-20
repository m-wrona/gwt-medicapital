package com.medicapital.common.entities;

/**
 * The persistent class for the Specialization database table.
 * 
 */
public class Specialization implements SerializableEntity {

	private int specializationId;

	private String name;

	public Specialization() {
	}

	@Override
	public void setId(int entityId) {
		specializationId = entityId;
	}

	public int getId() {
		return this.specializationId;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public boolean equals(Object obj) {
		if (super.equals(obj)) {
			return true;
		} else if (!(obj instanceof Specialization)) {
			return false;
		}
		Specialization other = (Specialization) obj;
		return specializationId == other.specializationId;
	}

	@Override
	public String toString() {
		StringBuilder info = new StringBuilder("[");
		info.append("specializationId=").append(specializationId);
		info.append(", name=").append(name);
		info.append("]");
		return info.toString();
	}

}