package com.medicapital.server.lang;

import static com.medicapital.server.log.Tracer.tracer;
import java.io.IOException;
import org.scb.gwt.web.server.i18n.GWTI18N;
import com.medicapital.common.lang.GenericText;
import com.medicapital.common.lang.MailText;
import com.medicapital.common.lang.WarningText;

final public class Lang {

	private static MailText mailText;
	private static GenericText genericText;
	private static WarningText warningText;

	public static WarningText warning() {
		try {
			if (warningText == null) {
				warningText = GWTI18N.create(WarningText.class);
			}
			return warningText;
		} catch (IOException e) {
			tracer(Lang.class).error("Language error", e);
			throw new RuntimeException("Couldn't get warning text translations");
		}
	}

	public static GenericText generic() {
		try {
			if (genericText == null) {
				genericText = GWTI18N.create(GenericText.class);
			}
			return genericText;
		} catch (IOException e) {
			tracer(Lang.class).error("Language error", e);
			throw new RuntimeException("Couldn't get generic text translations");
		}
	}

	public static MailText mail() {
		try {
			if (mailText == null) {
				mailText = GWTI18N.create(MailText.class);
			}
			return mailText;
		} catch (IOException e) {
			tracer(Lang.class).error("Language error", e);
			throw new RuntimeException("Couldn't get mail text translations");
		}
	}

	private Lang() {
		// empty
	}
}
