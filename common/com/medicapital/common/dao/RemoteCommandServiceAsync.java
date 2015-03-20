package com.medicapital.common.dao;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.medicapital.common.entities.SerializableEntity;
import com.medicapital.common.rpc.ServerRequest;
import com.medicapital.common.rpc.ServerResponse;

public interface RemoteCommandServiceAsync {

    <T extends SerializableEntity> void execute(ServerRequest<T> command, AsyncCallback<ServerResponse<T>> callback);
}
