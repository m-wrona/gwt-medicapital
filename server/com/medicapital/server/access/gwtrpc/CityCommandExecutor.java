package com.medicapital.server.access.gwtrpc;

import java.util.Set;
import com.medicapital.common.commands.entity.SelectCommand;
import com.medicapital.common.commands.entity.SelectCommandResp;
import com.medicapital.common.commands.entity.SelectCountCommand;
import com.medicapital.common.commands.entity.SelectCountCommandResp;
import com.medicapital.common.dao.ServerException;
import com.medicapital.common.entities.City;
import com.medicapital.server.logic.GenericDataFacade;

public class CityCommandExecutor extends CommandExecutor<City> {

	private static final long serialVersionUID = 2224643959611579676L;
	private GenericDataFacade genericDataFacade;

	@Override
	protected SelectCommandResp<City> handleSelectCommand(SelectCommand<City> selectCommand) throws CommandExecutionException, ServerException {
		final Set<City> dbCities = genericDataFacade.getCities();
		return new SelectCommandResp<City>(City.class, dbCities);
	}

	@Override
	protected SelectCountCommandResp<City> handleSelectCountCommand(SelectCountCommand<City> selectCountCommand) throws CommandExecutionException, ServerException {
		return new SelectCountCommandResp<City>(City.class, genericDataFacade.getCitiesCount());
	}

	@Override
	protected Class<City> getEntityClass() {
		return City.class;
	}

	public void setGenericDataFacade(GenericDataFacade genericDataFacade) {
		this.genericDataFacade = genericDataFacade;
	}

}
