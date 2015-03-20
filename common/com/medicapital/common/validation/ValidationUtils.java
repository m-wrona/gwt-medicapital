package com.medicapital.common.validation;

import com.medicapital.common.entities.SerializableEntity;

final public class ValidationUtils {

	public static boolean isText(String text) {
		if (isEmpty(text)) {
			return false;
		}
		return text.matches("\\w+");
	}

	public static boolean isEmailValid(String eMail) {
		if (isEmpty(eMail)) {
			return false;
		}
		return eMail.matches(".+@.+\\..+");
	}

	public static boolean isEntityIdSet(SerializableEntity entity) {
		return entity.getId() > 0;
	}

	public static boolean isEmpty(String string) {
		return string == null || string.isEmpty();
	}

	private ValidationUtils() {
		// empty
	}
}
