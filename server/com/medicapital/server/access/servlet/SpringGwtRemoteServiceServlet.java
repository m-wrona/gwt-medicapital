package com.medicapital.server.access.servlet;

import static com.medicapital.server.log.Tracer.*;
import javax.servlet.http.HttpServletRequest;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import com.google.gwt.user.client.rpc.IncompatibleRemoteServiceException;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.SerializationException;
import com.google.gwt.user.server.rpc.RPC;
import com.google.gwt.user.server.rpc.RPCRequest;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * Servlet handles GWT-RPC requests and dispatches it to the proper services
 * 
 * @author mwronski
 * 
 */
@SuppressWarnings("serial")
public final class SpringGwtRemoteServiceServlet extends RemoteServiceServlet {

	@Override
	public void init() {
		tracer(this).debug("Spring GWT service exporter deployed");
	}

	@Override
	public String processCall(String payload) throws SerializationException {
		try {
			Object handler = getBean(getThreadLocalRequest());
			RPCRequest rpcRequest = RPC.decodeRequest(payload, handler.getClass(), this);
			onAfterRequestDeserialized(rpcRequest);
			tracer(this).debug("Invoking " + handler.getClass().getName() + "." + rpcRequest.getMethod().getName());
			return RPC.invokeAndEncodeResponse(handler, rpcRequest.getMethod(), rpcRequest.getParameters(), rpcRequest.getSerializationPolicy());
		} catch (IncompatibleRemoteServiceException ex) {
			log("An IncompatibleRemoteServiceException was thrown while processing this call.", ex);
			return RPC.encodeResponseForFailure(null, ex);
		}
	}

	/**
	 * Determine Spring bean to handle request based on request URL, e.g. a
	 * request ending in /myService will be handled by bean with name
	 * "myService".
	 * 
	 * @param request
	 * @return handler bean
	 */
	private Object getBean(HttpServletRequest request) {
		String service = getService(request);
		Object bean = getBean(service);
		if (!(bean instanceof RemoteService)) {
			throw new IllegalArgumentException("Spring bean is not a GWT RemoteService: " + service + " (" + bean + ")");
		}
		tracer(this).debug("Bean for service " + service + " is " + bean);
		return bean;
	}

	/**
	 * Parse the service name from the request URL.
	 * 
	 * @param request
	 * @return bean name
	 */
	private String getService(HttpServletRequest request) {
		String url = request.getRequestURI();
		String service = url.substring(url.lastIndexOf("/") + 1);
		tracer(this).debug("Service for URL " + url + " is " + service);
		return service;
	}

	/**
	 * Look up a spring bean with the specified name in the current web
	 * application context.
	 * 
	 * @param name
	 *            bean name
	 * @return the bean
	 */
	private Object getBean(String name) {
		WebApplicationContext applicationContext = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
		if (applicationContext == null) {
			throw new IllegalStateException("No Spring web application context found");
		} else if (!applicationContext.containsBean(name)) {
			throw new IllegalArgumentException("Spring bean not found: " + name);
		}
		return applicationContext.getBean(name);
	}
}
