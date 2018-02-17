package com.iwaneez.stuffer.service.impl;

import com.iwaneez.stuffer.service.SecurityService;
import com.vaadin.navigator.View;
import com.vaadin.spring.access.ViewInstanceAccessControl;
import com.vaadin.ui.UI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ViewInstanceAccessControlImpl implements ViewInstanceAccessControl {

    @Autowired
    private SecurityService securityService;

    @Override
    public boolean isAccessGranted(UI ui, String beanName, View view) {
        return true;
//        if (securityService.hasRole(Role.ADMIN)) {
//            return true;
//        } else if (securityService.hasRole(Role.APP_USER)) {
//            return true;
//        } else if (securityService.hasRole(Role.USER)) {
//            if (!(view instanceof NamedView)) {
//                return false;
//            }
//            return Arrays.asList(new String[] {
//                    DashboardView.VIEW_NAME,
//                    ApplicationLogView.VIEW_NAME,
//                    ExceptionStackTraceView.VIEW_NAME,
//                    AuditView.VIEW_NAME,
//                    AboutView.VIEW_NAME
//            }).contains(((NamedView) view).getViewName());
//        } else {
//            return false;
//        }
    }

}