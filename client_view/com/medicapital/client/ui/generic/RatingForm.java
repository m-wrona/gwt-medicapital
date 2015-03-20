package com.medicapital.client.ui.generic;

import org.cobogw.gwt.user.client.ui.Rating;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Composite;
import com.medicapital.client.lang.Lang;

/**
 * Form enables to give evaluation and rating.
 * 
 * @author mwronski
 * 
 */
final public class RatingForm extends Composite {

	private final Rating rating;
	private int evaluation;
	private boolean enabled = true;

	public RatingForm(int initValue, int maxRating) {
		rating = new Rating(initValue, maxRating);
		initWidget(rating);
		initHandlers();
	}

	/**
	 * Create default rating form with rating scale 0-6. Initial value is 0.
	 * Instance will have also rating titles set.
	 */
	public RatingForm() {
		rating = new Rating(0, 6);
		rating.setTitles(Lang.getRatingTitles());
		initWidget(rating);
		initHandlers();
	}

	private void initHandlers() {
		evaluation = rating.getValue();
		rating.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				if (!enabled) {
					rating.setValue(evaluation);
				} else {
					evaluation = rating.getValue();
				}
			}
		});
	}

	public void setRating(int rating) {
		this.rating.setValue(rating);
		evaluation = rating;
	}

	public int getRating() {
		return rating.getValue();
	}

	/**
	 * Set rating titles
	 * 
	 * @param titles
	 */
	public void setTitles(String[] titles) {
		rating.setTitles(titles);
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

}
