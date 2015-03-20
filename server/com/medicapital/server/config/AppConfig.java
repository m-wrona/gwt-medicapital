package com.medicapital.server.config;

import static com.medicapital.server.log.Tracer.*;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

	public AppConfig() {
		tracer(this).debug("Application configuration - created");
	}
}
