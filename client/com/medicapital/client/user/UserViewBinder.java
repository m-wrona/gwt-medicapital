package com.medicapital.client.user;

import com.medicapital.client.core.mvp.EntityViewBinder;
import com.medicapital.common.entities.Localization;
import com.medicapital.common.entities.User;
import com.medicapital.common.util.Util;

public final class UserViewBinder implements EntityViewBinder<User> {

	private SetterUserDataView setterUserDataView;
	private GetterUserDataView getterUserDataView;

	public UserViewBinder(SetterUserDataView setterUserDataView, GetterUserDataView getterUserDataView) {
		this.setterUserDataView = setterUserDataView;
		this.getterUserDataView = getterUserDataView;
	}

	@Override
	public void display(User entity) {
		if (setterUserDataView == null) {
			return;
		}
		setterUserDataView.setLogin(entity.getLogin());
		setterUserDataView.setEmail(entity.getEmail());
		setterUserDataView.setFirstName(entity.getFirstName());
		setterUserDataView.setLastName(entity.getLastName());
		setterUserDataView.setPhoneNumber(entity.getPhoneNumber());
		setterUserDataView.setMobile(entity.getMobile());
		setterUserDataView.setPersonalId(entity.getPersonalId());
		setterUserDataView.setSex(entity.getSex());
		setterUserDataView.setBirthDate(entity.getBirthDate());
		setterUserDataView.setLastLoginDate(entity.getLastLoginDate());
		if (entity.getLocalization() != null) {
			setterUserDataView.setAddress(entity.getLocalization().getAddress());
			setterUserDataView.setPostalCode(entity.getLocalization().getPostalCode());
			setterUserDataView.setCityId(entity.getLocalization().getCityId());
		}
		setterUserDataView.setHobbies(entity.getHobbies());
	}

	@Override
	public User getEntityFromView(User sourceEntity) {
		if (getterUserDataView == null) {
			return null;
		}
		final User user = new User();
		user.setId(sourceEntity != null ? sourceEntity.getId() : -1);
		user.setLogin(getterUserDataView.getLogin());
		user.setEmail(getterUserDataView.getEmail());
		user.setFirstName(getterUserDataView.getFirstName());
		user.setLastName(getterUserDataView.getLastName());
		user.setPhoneNumber(getterUserDataView.getPhoneNumber());
		user.setMobile(getterUserDataView.getMobile());
		user.setSex(getterUserDataView.getSex());
		user.setPersonalId(getterUserDataView.getPersonalId());
		user.setBirthDate(getterUserDataView.getBirthDate());
		user.setHobbies(getterUserDataView.getHobbies());

		if (!Util.isEmpty(getterUserDataView.getAddress()) || !Util.isEmpty(getterUserDataView.getPostalCode()) || getterUserDataView.getCityId() > 0) {
			Localization local = sourceEntity != null ? sourceEntity.getLocalization() : null;
			if (local == null) {
				local = new Localization();
			}
			user.setLocalization(local);
			local.setAddress(getterUserDataView.getAddress());
			local.setPostalCode(getterUserDataView.getPostalCode());
			local.setCityId(getterUserDataView.getCityId());
		}
		return user;
	}

	@Override
	public void clear() {
		if (setterUserDataView == null) {
			return;
		}
		setterUserDataView.setLogin("");
		setterUserDataView.setEmail("");
		setterUserDataView.setFirstName("");
		setterUserDataView.setLastName("");
		setterUserDataView.setPhoneNumber("");
		setterUserDataView.setMobile("");
		setterUserDataView.setPersonalId("");
		setterUserDataView.setSex(null);
		setterUserDataView.setBirthDate(null);
		setterUserDataView.setLastLoginDate(null);
		setterUserDataView.setAddress("");
		setterUserDataView.setPostalCode("");
		setterUserDataView.setCityId(0);
		setterUserDataView.setHobbies(null);
	}

}
