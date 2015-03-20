package com.medicapital.server.access.gwtrpc;

import static org.mockito.Mockito.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import java.util.HashSet;
import java.util.Set;
import org.junit.Before;
import org.junit.Test;
import com.medicapital.common.commands.entity.SelectCommand;
import com.medicapital.common.commands.entity.SelectCommandResp;
import com.medicapital.common.commands.entity.SelectCountCommand;
import com.medicapital.common.commands.entity.SelectCountCommandResp;
import com.medicapital.common.entities.City;
import com.medicapital.common.rpc.ServerRequest;
import com.medicapital.common.rpc.ServerResponse;
import com.medicapital.server.access.gwtrpc.CityCommandExecutor;
import com.medicapital.server.logic.GenericDataFacade;
import com.medicapital.server.security.SecurityManager;
import com.medicapital.server.security.SessionFactory;

public class CityCommandExecutorTest {

    private GenericDataFacade genericDataFacade;
    private CityCommandExecutor cityCommandExecutor;

    @Before
    public void init() {
    	genericDataFacade = mock(GenericDataFacade.class);
        cityCommandExecutor = new CityCommandExecutor();
        cityCommandExecutor.setGenericDataFacade(genericDataFacade);
        SessionFactory sessionFactory = new SessionFactory();
		sessionFactory.setSupportLocalSession(true);
		cityCommandExecutor.setSessionFactory(sessionFactory);
		cityCommandExecutor.setSecurityManager(new SecurityManager());
    }

    @Test
    public void testGetCities() {
    	Set<City> cities = new HashSet<City>();
    	cities.add(new City());
    	when(genericDataFacade.getCities()).thenReturn(cities);
        SelectCommand<City> selectCommand = new SelectCommand<City>(City.class);
        ServerResponse<City> serverResponse = cityCommandExecutor.execute(new ServerRequest<City>(selectCommand));
        assertNotNull(serverResponse);
        assertNotNull(serverResponse.getResponse());
        assertEquals(SelectCommandResp.class, serverResponse.getResponse().getClass());
        SelectCommandResp<City> cityResp = (SelectCommandResp<City>) serverResponse.getResponse();
        assertEquals(1, cityResp.getCount());
    }
    
    @Test
    public void testGetCitiesCount() {
    	when(genericDataFacade.getCitiesCount()).thenReturn(3);
        SelectCountCommand<City> selectCommand = new SelectCountCommand<City>(City.class);
        ServerResponse<City> serverResponse = cityCommandExecutor.execute(new ServerRequest<City>(selectCommand));
        assertNotNull(serverResponse);
        assertNotNull(serverResponse.getResponse());
        assertEquals(SelectCountCommandResp.class, serverResponse.getResponse().getClass());
        SelectCountCommandResp<City> cityResp = (SelectCountCommandResp<City>) serverResponse.getResponse();
        assertEquals(3, cityResp.getCount());
    }
}
