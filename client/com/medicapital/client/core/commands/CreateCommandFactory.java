package com.medicapital.client.core.commands;

import com.medicapital.common.commands.entity.CreateCommand;
import com.medicapital.common.entities.SerializableEntity;

public interface CreateCommandFactory<E extends SerializableEntity> {

    /**
     * Create update command for element
     * 
     * @param entityClass
     * @param element
     * @return
     */
    CreateCommand<E> createCreateCommand(final E element);

}

