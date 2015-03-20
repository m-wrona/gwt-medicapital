package com.medicapital.server.access.gwtrpc;

import static com.medicapital.server.log.Tracer.tracer;
import javax.servlet.http.HttpSession;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.medicapital.common.commands.CommandReq;
import com.medicapital.common.commands.CommandResp;
import com.medicapital.common.commands.entity.CreateCommand;
import com.medicapital.common.commands.entity.CreateCommandResp;
import com.medicapital.common.commands.entity.DeleteCommand;
import com.medicapital.common.commands.entity.DeleteCommandResp;
import com.medicapital.common.commands.entity.SelectCommand;
import com.medicapital.common.commands.entity.SelectCommandResp;
import com.medicapital.common.commands.entity.SelectCountCommand;
import com.medicapital.common.commands.entity.SelectCountCommandResp;
import com.medicapital.common.commands.entity.UpdateCommand;
import com.medicapital.common.commands.entity.UpdateCommandResp;
import com.medicapital.common.dao.RemoteCommandService;
import com.medicapital.common.dao.ServerException;
import com.medicapital.common.entities.SerializableEntity;
import com.medicapital.common.rpc.ServerRequest;
import com.medicapital.common.rpc.ServerResponse;
import com.medicapital.server.logic.EntityFacade;
import com.medicapital.server.security.SessionData;
import com.medicapital.server.security.SessionFactory;

/**
 * Class receives commands from clients and handles it properly.
 * 
 * @author mwronski
 * 
 * @param <T>
 *            type of entity to which commands aplly.
 */
abstract class CommandExecutor<T extends SerializableEntity> extends RemoteServiceServlet implements RemoteCommandService<T>, ApplicationContextAware {

	private static final long serialVersionUID = 8218032486207330576L;
	private ApplicationContext applicationContext;
	private EntityFacade<T> facade;
	private com.medicapital.server.security.SecurityManager securityManager;
	private SessionFactory sessionFactory;

	/*
	 * NOTE: this method can't be final or synchronized because it's wrapped by
	 * GWTHandler. This method shouldn't be overwritten by inheriting classes.
	 */
	@Override
	public ServerResponse<T> execute(final ServerRequest<T> command) throws ServerException {
		try {
			tracer(this).debug("Handling command: " + command);
			securityManager.setSessionData(getSessionData());
			preCommandExecution();
			final CommandResp<T> clientResponse = handleCommand(command.getCommand());
			tracer(this).debug("Command handled successfully - response: " + clientResponse);
			final ServerResponse<T> serverResponse = new ServerResponse<T>(clientResponse);
			setResponseSessionData(serverResponse);
			return serverResponse;
		} catch (final ServerException e) {
			tracer(this).error("Error occured while handling command: " + command, e);
			throw e;
		} catch (final Exception e) {
			tracer(this).error("Error occured while handling command: " + command, e);
			throw new ServerException("Command execution error");
		} finally {
			postCommandExecution();
		}
	}

	/**
	 * Do additional stuff before command execution
	 * 
	 * @throws CommandExecutionException
	 * @throws ServerException
	 */
	protected void preCommandExecution() throws CommandExecutionException, ServerException {
		// empty
	}

	/**
	 * Clean stuff after command execution
	 * 
	 * @throws CommandExecutionException
	 * @throws ServerException
	 */
	protected void postCommandExecution() throws CommandExecutionException, ServerException {
		// empty
	}

	/**
	 * Handle client command
	 * 
	 * @param clientCommand
	 * @return null if command was not executed
	 * @throws CommandExecutionException
	 * @throws ServerException
	 */
	protected CommandResp<T> handleCommand(final CommandReq<T> clientCommand) throws CommandExecutionException, ServerException {
		if (clientCommand instanceof SelectCommand<?>) {
			return handleSelectCommand((SelectCommand<T>) clientCommand);
		} else if (clientCommand instanceof SelectCountCommand<?>) {
			return handleSelectCountCommand((SelectCountCommand<T>) clientCommand);
		} else if (clientCommand instanceof CreateCommand<?>) {
			return handleCreateCommand((CreateCommand<T>) clientCommand);
		} else if (clientCommand instanceof DeleteCommand<?>) {
			return handleDeleteCommand((DeleteCommand<T>) clientCommand);
		} else if (clientCommand instanceof UpdateCommand<?>) {
			return handleUpdateCommand((UpdateCommand<T>) clientCommand);
		}
		return null;
	}

	protected SelectCommandResp<T> handleSelectCommand(final SelectCommand<T> selectCommand) throws CommandExecutionException, ServerException {
		if (getFacade() != null && selectCommand.getElementId() > 0) {
			T entity = getFacade().get(selectCommand.getElementId());
			SelectCommandResp<T> resp = new SelectCommandResp<T>(getEntityClass());
			resp.add(entity);
			return resp;
		}
		return null;
	}

	protected SelectCountCommandResp<T> handleSelectCountCommand(final SelectCountCommand<T> selectCountCommand) throws CommandExecutionException, ServerException {
		if (getFacade() != null) {
			int totalCount = getFacade().getCount();
			SelectCountCommandResp<T> resp = new SelectCountCommandResp<T>(getEntityClass(), totalCount);
			return resp;
		}
		return null;
	}

	protected CreateCommandResp<T> handleCreateCommand(final CreateCommand<T> createCommand) throws CommandExecutionException, ServerException {
		if (getFacade() != null) {
			int createdEntityId = getFacade().create(createCommand.getElementToCreate());
			CreateCommandResp<T> resp = new CreateCommandResp<T>(getEntityClass(), createdEntityId);
			return resp;
		}
		return null;
	}

	protected DeleteCommandResp<T> handleDeleteCommand(final DeleteCommand<T> deleteCommand) throws CommandExecutionException, ServerException {
		if (getFacade() != null) {
			int deletedEntities = 0;
			for (int entityId : deleteCommand.getElementIds()) {
				getFacade().delete(entityId);
				deletedEntities++;
			}
			DeleteCommandResp<T> resp = new DeleteCommandResp<T>(getEntityClass(), deletedEntities);
			return resp;
		}
		return null;
	}

	protected UpdateCommandResp<T> handleUpdateCommand(final UpdateCommand<T> updateCommand) throws CommandExecutionException, ServerException {
		if (getFacade() != null) {
			getFacade().update(updateCommand.getElementToEdit());
			UpdateCommandResp<T> resp = new UpdateCommandResp<T>(getEntityClass(), 1);
			return resp;
		}
		return null;
	}

	@Override
	final public void setApplicationContext(final ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

	/**
	 * Get application context
	 * 
	 * @return
	 */
	final protected ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	/**
	 * Get session for current HTTP request
	 * 
	 * @return
	 */
	final protected HttpSession getHttpSession() {
		return sessionFactory.getSession(getThreadLocalRequest());
	}

	/**
	 * Get information about current logged user
	 * 
	 * @return
	 */
	final protected SessionData getSessionData() {
		if (getHttpSession() == null) {
			return null;
		}
		final Object clientSession = getHttpSession().getAttribute(SessionData.SESSION_DATA);
		return clientSession != null ? (SessionData) clientSession : null;
	}

	/**
	 * Set information about current logged user
	 * 
	 * @param sessionData
	 */
	final protected void setSessionData(final SessionData sessionData) {
		if (getHttpSession() != null) {
			getHttpSession().setAttribute(SessionData.SESSION_DATA, sessionData);
		}
	}

	/**
	 * Save user info in response
	 * 
	 * @param serverResponse
	 */
	private void setResponseSessionData(final ServerResponse<T> serverResponse) {
		final SessionData userInfo = getSessionData();
		if (userInfo != null) {
			serverResponse.setSessionId(userInfo.getSessionId());
			serverResponse.setExpiryDate(userInfo.getExpiryDate());
		}
	}

	abstract protected Class<T> getEntityClass();

	public void setFacade(EntityFacade<T> facade) {
		this.facade = facade;
	}

	public EntityFacade<T> getFacade() {
		return facade;
	}

	final public void setSecurityManager(com.medicapital.server.security.SecurityManager securityManager) {
		this.securityManager = securityManager;
	}

	final public com.medicapital.server.security.SecurityManager getSecurityManager() {
		return securityManager;
	}

	final public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

}
