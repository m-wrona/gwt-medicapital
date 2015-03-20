package com.medicapital.common.rpc;

import java.util.Date;

import com.google.gwt.user.client.rpc.IsSerializable;
import com.medicapital.common.commands.CommandResp;
import com.medicapital.common.entities.SerializableEntity;

/**
 * Class wraps response from server after command execution.
 * 
 * @author mwronski
 * 
 * @param <T>
 */
public class ServerResponse<T extends SerializableEntity> implements IsSerializable {

	private CommandResp<T> response;
	private String sessionId;
	private Date expiryDate;

	public ServerResponse(CommandResp<T> response) {
		this.response = response;
	}

	/**
	 * Constructor for RPC communication
	 */
	protected ServerResponse() {
		// empty
	}

	public CommandResp<T> getResponse() {
		return response;
	}

	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}

	public Date getExpiryDate() {
		return expiryDate;
	}
	
	public void setSessionId(String sessionId) {
	    this.sessionId = sessionId;
    }
	
	public String getSessionId() {
	    return sessionId;
    }

	@Override
	public String toString() {
		return "[SessionId: " + sessionId + ", expiry date: " + expiryDate + ", response: " + response + "]";
	}

}