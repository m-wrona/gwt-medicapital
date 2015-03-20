package com.medicapital.common.entities;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * Class represents single entity which can be serialized using GWT-RPC
 * communication.
 * 
 * @author mwronski
 * 
 */
public interface SerializableEntity extends IsSerializable {

	/**
	 * Get entity ID
	 * 
	 * @return
	 */
	int getId();

	/**
	 * Set entitie's id
	 * 
	 * @param entityId
	 */
	void setId(int entityId);
}
