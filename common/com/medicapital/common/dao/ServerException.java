package com.medicapital.common.dao;

/**
 * Server side exception
 * 
 * @author mwronski
 * 
 */
public class ServerException extends RuntimeException {

	private static final long serialVersionUID = 3394840845461519687L;

	private String clientMessage;

	public ServerException() {
		// empty
	}

	public ServerException(String errorMessage) {
		super(errorMessage);
	}

	public ServerException(Throwable throwable) {
		super(throwable);
	}

	public ServerException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	final public void setClientMessage(String clientMessage) {
		this.clientMessage = clientMessage;
	}

	final public String getClientMessage() {
		return clientMessage;
	}

	@Override
	public String toString() {
		StringBuilder string = new StringBuilder();
		string.append("Exception client message: ").append(clientMessage).append('\n');
		string.append("Exception message: ").append(super.toString());
		return string.toString();
	}

}
