package com.medicapital.common.entities;

import com.google.gwt.user.client.rpc.IsSerializable;

public enum UserRole implements IsSerializable {

	User(1), Doctor(2);

	private int accessLevel;

	private UserRole(int accessLevel) {
		this.accessLevel = accessLevel;
	}

	public int getAccessLevel() {
		return accessLevel;
	}
}
