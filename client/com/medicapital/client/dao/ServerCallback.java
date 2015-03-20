package com.medicapital.client.dao;

import static com.medicapital.client.log.Tracer.*;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.medicapital.client.core.WidgetView;

/**
 * Basic handler for client-server GWT-RPC communication
 * 
 * @author mwronski
 * 
 * @param <T>
 *            response type
 */
public class ServerCallback<T> implements AsyncCallback<T> {

	private WidgetView[] blockedViews;

	public ServerCallback(WidgetView... blockedViews) {
		this.blockedViews = blockedViews;
	}

	@Override
	public final void onFailure(Throwable caught) {
		tracer(this).error("Server error", caught);
		unblockViews();
		if (!error(caught)) {
			// TODO show error on screen
		}
	}

	@Override
	public final void onSuccess(T result) {
		tracer(this).debug("Server response: " + result);
		unblockViews();
		response(result);
	}

	/**
	 * Handle server error
	 * 
	 * @param caught
	 * @return true if error was handled, false otherwise
	 */
	public boolean error(Throwable caught) {
		return false;
	}

	private void unblockViews() {
		if (blockedViews != null) {
			for (WidgetView view : blockedViews) {
				view.setViewBlocked(false);
			}
		}
	}

	/**
	 * Handle server response
	 * 
	 * @param result
	 */
	public void response(T result) {
		// empty
	}

}
