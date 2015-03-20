package com.medicapital.server.lang;

import static org.junit.Assert.assertEquals;
import java.io.UnsupportedEncodingException;
import org.junit.Test;

public class TestLang {

	@Test
	public void testCheckLangSupport() throws UnsupportedEncodingException {
		assertEquals("MediCapital - zapomniane hasło", Lang.mail().forgottenPasswordTitle());
	}
}
