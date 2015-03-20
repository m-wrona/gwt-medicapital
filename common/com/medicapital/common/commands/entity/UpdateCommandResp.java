package com.medicapital.common.commands.entity;

import com.medicapital.common.commands.CommandResp;
import com.medicapital.common.entities.SerializableEntity;

public class UpdateCommandResp<E extends SerializableEntity> extends CommandResp<E> {

    private int count = 0;
    private E updatedEntity;

    /**
     * Constructor for RPC communication
     */
    protected UpdateCommandResp() {

    }

    public UpdateCommandResp(Class<E> objectClass, int count) {
        super(objectClass);
        this.count = count;
    }

    public UpdateCommandResp(Class<E> objectClass) {
        super(objectClass);
    }

    public int getCount() {
        return count;
    }
    
    public void setUpdatedEntity(E updatedEntity) {
	    this.updatedEntity = updatedEntity;
    }
    
    public E getUpdatedEntity() {
	    return updatedEntity;
    }
    
    @Override
    public String toString() {
        return "[" + getClass() + ", count=" + count + "]";
    }
}
