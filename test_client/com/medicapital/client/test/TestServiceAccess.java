package com.medicapital.client.test;

import static com.medicapital.client.log.Tracer.tracer;
import java.util.ArrayList;
import java.util.List;
import com.medicapital.common.commands.CommandReq;
import com.medicapital.common.commands.CommandResp;
import com.medicapital.common.dao.ResponseHandler;
import com.medicapital.common.dao.ServiceAccess;
import com.medicapital.common.entities.SerializableEntity;

public class TestServiceAccess implements ServiceAccess {

	private final List<CommandReq<?>> received = new ArrayList<CommandReq<?>>();
	private final List<CommandResp<?>> responses = new ArrayList<CommandResp<?>>();
	private final Throwable throwable;

	public TestServiceAccess() {
		throwable = null;
	}

	public TestServiceAccess(CommandResp<?> command) {
		responses.add(command);
		throwable = null;
	}

	public TestServiceAccess(Throwable throwable) {
		this.throwable = throwable;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <E extends SerializableEntity> void execute(CommandReq<E> command, ResponseHandler<E> responseHandler) {
		tracer(this).debug("Command received: " + command);
		received.add(command);
		if (!responses.isEmpty()) {
			responseHandler.handle((CommandResp<E>) responses.remove(0));
		} else {
			responseHandler.handleException(throwable);
		}
	}

	public <T extends SerializableEntity> void addResponse(CommandResp<T> command) {
		responses.add(command);
	}
	
	public List<CommandReq<?>> getReceived() {
	    return received;
    }

}
