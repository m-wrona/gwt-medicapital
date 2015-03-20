package com.medicapital.common.dao;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.medicapital.common.entities.SerializableEntity;
import com.medicapital.common.rpc.ServerRequest;
import com.medicapital.common.rpc.ServerResponse;

@RemoteServiceRelativePath("CommandService")
public interface RemoteCommandService<T extends SerializableEntity> extends RemoteService {

    /**
     * Execute command on server side
     * 
     * @param command
     * @return
     * @throws ServerException
     */
    ServerResponse<T> execute(ServerRequest<T> command) throws ServerException;

}
