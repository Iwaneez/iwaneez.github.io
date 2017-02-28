//package com.iwaneez.stuffer.config.initializer;
//
//import com.iwaneez.stuffer.config.root.PersistenceJPAConfig;
//import com.iwaneez.stuffer.config.root.SecurityConfig;
//import com.iwaneez.stuffer.config.webmvc.WebConfig;
//import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;
//
//public class SpringMvcInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
//
//    // App configuration
//    @Override
//    protected Class<?>[] getRootConfigClasses() {
//        return new Class[]{PersistenceJPAConfig.class, SecurityConfig.class};
//    }
//
//    // Dispatcher servlet configuration
//    @Override
//    protected Class<?>[] getServletConfigClasses() {
//        return new Class[]{WebConfig.class};
//    }
//
//    // Dispatcher servlet mappings
//    @Override
//    protected String[] getServletMappings() {
//        return new String[]{"/"};
//    }
//}