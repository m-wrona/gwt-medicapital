package com.medicapital.common.entities;

final class EntitiesUtils {

	@SuppressWarnings("unchecked")
    public static <T extends SerializableEntity> boolean equals(T entity, Object o) {
		if (o.getClass() != entity.getClass()) {
			return false;
		}
		T other = (T) o;
		return entity.getId() == other.getId();
	}

	private EntitiesUtils() {
		// empty
	}
}
