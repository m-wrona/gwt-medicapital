package com.medicapital.client.core.commands;

import com.medicapital.common.commands.entity.DeleteCommand;
import com.medicapital.common.commands.entity.UpdateCommand;
import com.medicapital.common.entities.SerializableEntity;

public interface EditCommandFactory<E extends SerializableEntity> {

    /**
     * Create update command for element
     * 
     * @param entityClass
     * @param element
     * @return
     */
    UpdateCommand<E> createUpdateCommand(E element);

    /**
     * Create delete command for element with chosen ID
     * 
     * @param entityClass
     * @param elementId
     * @param elementIds
     * @return
     */
    DeleteCommand<E> createDeleteCommand(int... elementIds);

}
