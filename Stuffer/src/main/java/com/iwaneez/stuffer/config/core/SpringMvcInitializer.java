package com.iwaneez.stuffer.config.core;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import com.iwaneez.stuffer.config.Config;

public class SpringMvcInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

        // App configuration
	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class[] { Config.class };
	}

	// Dispatcher servlet configuration
	@Override
	protected Class<?>[] getServletConfigClasses() {
		return null;
	}

	// Dispatcher servlet mappings
	@Override
	protected String[] getServletMappings() {
		return new String[] { "/" };
	}
}