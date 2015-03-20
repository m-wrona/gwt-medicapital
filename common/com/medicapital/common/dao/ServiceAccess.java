package com.medicapital.common.dao;

import com.medicapital.common.commands.CommandReq;
import com.medicapital.common.entities.SerializableEntity;

/**
 * Interface represents access to the service resources
 * 
 * @author michal
 * 
 */
public interface ServiceAccess {

    /**
     * Execute command
     * 
     * @param <E>
     *            element type requested in command
     * @param command
     * @param responseHandler
     */
    <E extends SerializableEntity> void execute(CommandReq<E> command, ResponseHandler<E> responseHandler);
}
