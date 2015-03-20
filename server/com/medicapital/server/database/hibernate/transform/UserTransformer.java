package com.medicapital.server.database.hibernate.transform;

import java.util.HashSet;
import java.util.Set;

import com.medicapital.common.entities.User;
import com.medicapital.common.entities.User.Sex;
import com.medicapital.common.entities.search.SearchUserCriteria;
import com.medicapital.common.entities.UserRole;
import com.medicapital.server.database.hibernate.entities.HobbyEntity;
import com.medicapital.server.database.hibernate.entities.UserEntity;

public class UserTransformer implements Transformer<User, UserEntity> {

	private LocalizationTransformer localizationTransformer = new LocalizationTransformer();

	@Override
	public UserEntity createEntity(int entityId) {
		UserEntity entity = new UserEntity();
		entity.setUserId(entityId);
		return entity;
	}

	@Override
	public User createPojo(UserEntity persistenceEntity) {
		if (persistenceEntity == null) {
			return null;
		}
		User user = new User();
		user.setId(persistenceEntity.getUserId());
		user.setLogin(persistenceEntity.getLogin());
		user.setPassword(persistenceEntity.getPassword());
		user.setEmail(persistenceEntity.getEmail());
		user.setFirstName(persistenceEntity.getFirstName());
		user.setLastName(persistenceEntity.getLastName());
		if (persistenceEntity.getSex() != null) {
			user.setSex(Sex.valueOf(persistenceEntity.getSex()));
		}
		user.setBirthDate(persistenceEntity.getBirthDate());
		user.setLastLoginDate(persistenceEntity.getLastLoginDate());
		user.setMobile(persistenceEntity.getMobile());
		user.setPhoneNumber(persistenceEntity.getPhoneNumber());
		user.setPersonalId(persistenceEntity.getPersonalId());
		if (persistenceEntity.getUserRole() != null) {
			user.setUserRole(UserRole.valueOf(persistenceEntity.getUserRole()));
		}
		user.setLocalization(localizationTransformer.createPojo(persistenceEntity.getLocalization()));
		if (persistenceEntity.getHobbies() != null) {
			Set<Integer> hobbiesIds = new HashSet<Integer>();
			for (HobbyEntity hobby : persistenceEntity.getHobbies()) {
				hobbiesIds.add(hobby.getHobbyId());
			}
			user.setHobbies(hobbiesIds.isEmpty() ? null : hobbiesIds);
		}
		user.setActive(persistenceEntity.isActive());
		return user;
	}

	@Override
	public UserEntity createEntity(User pojoEntity) {
		if (pojoEntity == null) {
			return null;
		}
		UserEntity user = new UserEntity();
		user.setUserId(pojoEntity.getId());
		user.setLogin(pojoEntity.getLogin());
		user.setPassword(pojoEntity.getPassword());
		user.setEmail(pojoEntity.getEmail());
		user.setFirstName(pojoEntity.getFirstName());
		user.setLastName(pojoEntity.getLastName());
		if (pojoEntity.getSex() != null) {
			user.setSex(pojoEntity.getSex().toString());
		}
		if (pojoEntity.getUserRole() != null) {
			user.setUserRole(pojoEntity.getUserRole().toString());
		}
		user.setBirthDate(pojoEntity.getBirthDate());
		user.setLastLoginDate(pojoEntity.getLastLoginDate());
		user.setMobile(pojoEntity.getMobile());
		user.setPhoneNumber(pojoEntity.getPhoneNumber());
		user.setPersonalId(pojoEntity.getPersonalId());
		user.setLocalization(localizationTransformer.createEntity(pojoEntity.getLocalization()));
		if (pojoEntity.getHobbies() != null) {
			Set<HobbyEntity> hobbiesIds = new HashSet<HobbyEntity>();
			for (int hobbyId : pojoEntity.getHobbies()) {
				HobbyEntity hobby = new HobbyEntity();
				hobby.setHobbyId(hobbyId);
			}
			user.setHobbies(hobbiesIds.isEmpty() ? null : hobbiesIds);
		}
		user.setActive(pojoEntity.isActive());
		return user;
	}

	public UserEntity createEntity(SearchUserCriteria searchUserCriteria) {
		if (searchUserCriteria == null) {
			return null;
		}
		UserEntity user = new UserEntity();
		user.setFirstName(searchUserCriteria.getFirstName());
		user.setLastName(searchUserCriteria.getLastName());
		if (searchUserCriteria.getSex() != null) {
			user.setSex(searchUserCriteria.getSex().toString());
		}
		user.setLocalization(localizationTransformer.createEntity(searchUserCriteria.getLocalization()));
		return user;
	}

}
