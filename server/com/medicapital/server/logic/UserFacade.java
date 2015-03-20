package com.medicapital.server.logic;

import static com.medicapital.server.log.Tracer.tracer;
import java.util.List;
import org.springframework.stereotype.Component;
import com.medicapital.common.dao.ServerException;
import com.medicapital.common.entities.User;
import com.medicapital.common.entities.UserRole;
import com.medicapital.common.util.Util;
import com.medicapital.common.validation.UserValidator;
import com.medicapital.server.database.DaoEntityAccess;
import com.medicapital.server.database.DaoUser;
import com.medicapital.server.lang.Lang;
import com.medicapital.server.security.Secured;
import com.medicapital.server.util.EmailService;
import com.medicapital.server.util.PasswordGenerator;

@Component
public class UserFacade extends EntityFacade<User> {

	private DaoUser daoUser;
	private EmailService emailService;
	private PasswordGenerator passwordGenerator;

	@Secured
	public List<User> searchUsers(String firstName, String lastName, UserRole userRole, int startRow, int maxRows) {
		tracer(this).debug("Search users - firstName: " + firstName + ", lastName: " + lastName + ", userRole: " + userRole + ", startRow: " + startRow + ", maxRows: " + maxRows);
		User searchCriteria = new User();
		searchCriteria.setFirstName(firstName);
		searchCriteria.setLastName(lastName);
		searchCriteria.setUserRole(userRole);
		searchCriteria.setActive(true);
		return daoUser.searchUsers(searchCriteria, startRow, maxRows);
	}

	@Secured
	public int searchUsersCount(String firstName, String lastName, UserRole userRole) {
		tracer(this).debug("Search users - firstName: " + firstName + ", lastName: " + lastName + ", userRole: " + userRole);
		User searchCriteria = new User();
		searchCriteria.setFirstName(firstName);
		searchCriteria.setLastName(lastName);
		searchCriteria.setUserRole(userRole);
		searchCriteria.setActive(true);
		return daoUser.searchUsersCount(searchCriteria);
	}

	@Override
	public int create(User entity) throws ServerException {
		tracer(this).debug("Registering user: " + entity);
		if (Util.isEmpty(entity.getEmail())) {
			final ServerException e = new ServerException("E-mail not set for new user");
			e.setClientMessage("E-mail not set for new user");
			throw e;
		}
		entity.setPassword(passwordGenerator.generate());
		entity.setActive(true);
		final int userId = super.create(entity);
		tracer(this).debug("Sending confirmation e-mail on address: " + entity.getEmail());
		emailService.send(entity.getEmail(), Lang.mail().registrationConfirmationTitle(), Lang.mail().registrationConfirmation(entity.getFirstName(), entity.getLastName(), entity.getPassword()));
		return userId;
	}

	@Secured
	@Override
	public void update(User entity) throws ServerException {
		tracer(this).debug("Updating user: " + entity);
		User dbUser = daoUser.get(entity.getId());
		if (dbUser == null) {
			throw new ServerException("User not found: " + entity.getLogin());
		}
		if (Util.isEmpty(entity.getPassword())) {
			tracer(this).debug("Password was not updated - setting password from database");
			entity.setPassword(dbUser.getPassword());
		}
		super.update(entity);
		if (!Util.isEmpty(entity.getPassword()) && !entity.getPassword().equals(dbUser.getPassword())) {
			emailService.send(entity.getEmail(), Lang.mail().passwordChangedTitle(), Lang.mail().passwordChanged(entity.getPassword()));
		}
	}

	@Secured
	@Override
	public int getCount() throws ServerException {
		return super.getCount();
	}

	@Secured
	public User get(String login) throws ServerException {
		return daoUser.get(login);
	}

	@Secured
	@Override
	public User get(int entityId) throws ServerException {
		User user = super.get(entityId);
		user.setPassword(null); // password should always be null for safety
		                        // reasons
		return user;
	}

	@Secured
	@Override
	public void delete(int entityId) throws ServerException {
		throw new ServerException("Unsupported operation");
	}

	/**
	 * Check whether login isn't used by somebody else
	 * 
	 * @param login
	 * @return
	 * @throws ServerException
	 */
	public boolean isLoginFree(String login) throws ServerException {
		User user = daoUser.get(login);
		tracer(this).debug("Checking if login '" + login + "' is free - login free: " + (user == null));
		return user == null;
	}

	@Override
	final protected void validateEntity(User user) throws ServerException {
		if (!new UserValidator().validate(user)) {
			user.setPassword(null); // for safety's sake
			throw new ServerException("User not valid: " + user);
		}
	}

	@Override
	final protected DaoEntityAccess<User> getDao() {
		return daoUser;
	}

	final public void setDaoUser(DaoUser daoUser) {
		this.daoUser = daoUser;
	}

	final public void setEmailService(EmailService emailService) {
		this.emailService = emailService;
	}

	final public void setPasswordGenerator(PasswordGenerator passwordGenerator) {
		this.passwordGenerator = passwordGenerator;
	}

}
