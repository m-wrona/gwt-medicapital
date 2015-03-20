package com.medicapital.common.dao;

import com.medicapital.common.commands.CommandResp;
import com.medicapital.common.entities.SerializableEntity;

/**
 * Interface allows to receive responses from service
 * 
 * @author mwronski
 * 
 * @param <T>
 */
public interface ResponseHandler<T extends SerializableEntity> {

	/**
	 * Handle response
	 * 
	 * @param response
	 */
	void handle(CommandResp<T> response);

	/**
	 * Handle response in form of an exception
	 * 
	 * @param throwable
	 */
	void handleException(Throwable throwable);

}
