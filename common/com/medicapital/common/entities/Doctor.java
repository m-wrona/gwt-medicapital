package com.medicapital.common.entities;

import java.util.Set;

/**
 * The persistent class for the Doctor database table.
 * 
 */
public class Doctor implements SerializableEntity {

	private int doctorId;

	private float averageEvaluation;

	private String notes;

	private String workId;

	private User user;

	private Set<Specialization> specializations;

	private int galleryPhotoCount;

	@Override
	public void setId(int doctorId) {
		this.doctorId = doctorId;
	}

	@Override
	public int getId() {
		return doctorId;
	}

	public float getAverageEvaluation() {
		return this.averageEvaluation;
	}

	public void setAverageEvaluation(float averageEvaluation) {
		this.averageEvaluation = averageEvaluation;
	}

	public String getNotes() {
		return this.notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public void setWorkId(String workId) {
		this.workId = workId;
	}

	public String getWorkId() {
		return workId;
	}

	public void setUser(User user) {
		if (doctorId > 0 && user.getId() > 0 && doctorId != user.getId()) {
			throw new IllegalArgumentException("DoctorId and UserId aren't the same");
		}
		this.user = user;
		user.setUserRole(UserRole.Doctor);
	}

	public User getUser() {
		return user;
	}

	public void setSpecializations(Set<Specialization> specializations) {
		this.specializations = specializations;
	}

	public Set<Specialization> getSpecializations() {
		return specializations;
	}

	public void setGalleryPhotoCount(int galleryPhotoCount) {
		this.galleryPhotoCount = galleryPhotoCount;
	}

	public int getGalleryPhotoCount() {
		return galleryPhotoCount;
	}

	@Override
	public String toString() {
		StringBuilder data = new StringBuilder("[");
		data.append("doctorId: " + doctorId);
		data.append(", averageEvaluation: " + averageEvaluation);
		data.append(", workId: " + workId);
		data.append(", notes: " + notes);
		data.append(", user: " + user);
		data.append(", speicalizations: " + specializations);
		data.append(", galleryPhotoCount: " + galleryPhotoCount);
		data.append("]");
		return data.toString();
	}

	@Override
	public boolean equals(Object obj) {
		if (super.equals(obj)) {
			return true;
		} else if (!(obj instanceof Doctor)) {
			return false;
		}
		Doctor other = (Doctor) obj;
		return doctorId == other.doctorId;
	}

}