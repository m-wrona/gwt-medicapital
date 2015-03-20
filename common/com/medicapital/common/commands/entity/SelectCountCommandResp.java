package com.medicapital.common.commands.entity;

import com.medicapital.common.commands.CommandResp;
import com.medicapital.common.entities.SerializableEntity;

public class SelectCountCommandResp<E extends SerializableEntity> extends CommandResp<E> {

    private int count = 0;

    /**
     * Constructor for RPC communication
     */
    protected SelectCountCommandResp() {

    }

    public SelectCountCommandResp(Class<E> objectClass, int count) {
        super(objectClass);
        this.count = count;
    }

    public SelectCountCommandResp(Class<E> objectClass) {
        super(objectClass);
    }

    public int getCount() {
        return count;
    }

    @Override
    public String toString() {
        return "[" + getClass() + ", count=" + count + "]";
    }
}
