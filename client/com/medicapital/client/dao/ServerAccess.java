package com.medicapital.client.dao;

import static com.medicapital.client.log.Tracer.tracer;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.medicapital.client.login.SessionManager;
import com.medicapital.common.commands.CommandReq;
import com.medicapital.common.dao.ServerException;
import com.medicapital.common.dao.RemoteCommandService;
import com.medicapital.common.dao.RemoteCommandServiceAsync;
import com.medicapital.common.dao.ResponseHandler;
import com.medicapital.common.dao.ServiceAccess;
import com.medicapital.common.entities.SerializableEntity;
import com.medicapital.common.rpc.ServerRequest;
import com.medicapital.common.rpc.ServerResponse;

/**
 * Class allows to communicate with server
 * 
 * @author mwronski
 * 
 */
public class ServerAccess implements ServiceAccess {

	private static final String MODULE_NAME = GWT.getModuleBaseURL();
	static final String SERVICE_MAIN_ENTRY_POINT = MODULE_NAME + "CommandService/";
	private final RemoteCommandServiceAsync commandServer;

	public ServerAccess() {
		commandServer = GWT.create(RemoteCommandService.class);
		if (commandServer == null) {
			tracer(this).error("Connection to server command service - ERROR");
			throw new ServerException("Connection to server command service - ERROR");
		}
		tracer(this).debug("Connection to server command service - OK");
	}

	@Override
	public <E extends SerializableEntity> void execute(final CommandReq<E> command, final ResponseHandler<E> handler) {
		tracer(this).debug("Sending command to server: " + command);
		setServiceEntryPoint(command);
		final ServerRequest<E> request = new ServerRequest<E>(command);
		request.setSessionId(SessionManager.getInstacne().getSessionId());
		commandServer.execute(request, new AsyncCallback<ServerResponse<E>>() {

			@Override
			public void onFailure(final Throwable caught) {
				tracer(this).error("Server command execution error: " + caught.getMessage() + ", command: " + command, caught);
				handler.handleException(caught);
			}

			@Override
			public void onSuccess(final ServerResponse<E> response) {
				tracer(this).debug("Response received from server - sessionId: " + response.getSessionId() + ", expiry date: " + response.getExpiryDate());
				SessionManager.getInstacne().setSessionExpiryDate(response.getExpiryDate());
				tracer(this).debug("Response received from server: " + response.getResponse());
				handler.handle(response.getResponse());
			}
		});
	}

	/**
	 * Set service entry point for command so it could be handled by proper
	 * command executor
	 * 
	 * @param <E>
	 * @param command
	 */
	private <E extends SerializableEntity> void setServiceEntryPoint(final CommandReq<E> command) {
		final String objectClassName = command.getEntityClassName();
		final int lastDot = objectClassName.lastIndexOf('.');
		final String objectClassEntryPoint = objectClassName.substring(lastDot + 1) + ".rpc";
		final ServiceDefTarget serviceDefTarget = (ServiceDefTarget) commandServer;
		serviceDefTarget.setServiceEntryPoint(SERVICE_MAIN_ENTRY_POINT + objectClassEntryPoint);
		tracer(this).debug("Service entry point set to - " + serviceDefTarget.getServiceEntryPoint() + " for object class name: " + objectClassName);
	}

}
