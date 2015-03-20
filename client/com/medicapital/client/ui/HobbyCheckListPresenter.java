package com.medicapital.client.ui;

import static com.medicapital.client.log.Tracer.tracer;
import java.util.ArrayList;
import java.util.List;

import com.medicapital.client.dao.DaoFactory;
import com.medicapital.client.ui.generic.ElementCheckBoxList;
import com.medicapital.common.commands.CommandResp;
import com.medicapital.common.commands.entity.SelectCommand;
import com.medicapital.common.commands.entity.SelectCommandResp;
import com.medicapital.common.dao.ResponseHandler;
import com.medicapital.common.entities.Hobby;

final public class HobbyCheckListPresenter {

	private static HobbyCheckListPresenter instance = new HobbyCheckListPresenter();
	private final List<Hobby> hobbies = new ArrayList<Hobby>();

	public void getData(final ElementCheckBoxList<Hobby> hobbyCheckBoxList) {
		if (hobbies.isEmpty()) {
			tracer(this).debug("Downloading hobbies list...");
			DaoFactory.getServiceAccess().execute(new SelectCommand<Hobby>(Hobby.class, -1), new ResponseHandler<Hobby>() {
				@Override
				public void handle(final CommandResp<Hobby> response) {
					if (response instanceof SelectCommandResp<?>) {
						final SelectCommandResp<Hobby> hobbyResp = (SelectCommandResp<Hobby>) response;
						hobbies.addAll(hobbyResp.getData());
						hobbyCheckBoxList.init(hobbies);
					}
				}

				@Override
				public void handleException(final Throwable throwable) {
					// ignore

				}
			});
		} else {
			hobbyCheckBoxList.init(hobbies);
		}
	}

	public List<Hobby> getHobbies() {
		return hobbies;
	}

	public static HobbyCheckListPresenter getInstance() {
		return instance;
	}

	private HobbyCheckListPresenter() {
		// empty
	}
}
