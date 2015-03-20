package com.medicapital.client.login;

import java.util.Date;
import com.google.gwt.user.client.ui.Widget;

/**
 * This view displays info about logged in user
 * 
 * @author michal
 * 
 */
public interface LoggedUserView {

	void setLogin(String text);
	
	void setLastLoginDate(Date date);
	
	/**
	 * Get view as widget
	 * 
	 * @return
	 */
	Widget asWidget();
	
	void setVisible(boolean show);
	
	void setLogoutButtonEnabled(boolean enabled);
}
