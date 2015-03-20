package com.medicapital.common.rpc;

import com.google.gwt.user.client.rpc.IsSerializable;
import com.medicapital.common.commands.CommandReq;
import com.medicapital.common.entities.SerializableEntity;

/**
 * Class wraps command for the server.
 * 
 * @author mwronski
 * 
 * @param <T>
 */
public class ServerRequest<T extends SerializableEntity> implements IsSerializable {

	private String sessionId;
	private CommandReq<T> command;

	public ServerRequest(CommandReq<T> command) {
		this.command = command;
	}

	/**
	 * Constructor for RPC communication
	 */
	protected ServerRequest() {
		// empty
	}

	public CommandReq<T> getCommand() {
		return command;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public String getSessionId() {
		return sessionId;
	}

	@Override
	public String toString() {
		return "[SessionId: " + sessionId + ", command: " + command.toString() + "]";
	}
}
