package com.iwaneez.stuffer.service.impl;

import com.iwaneez.stuffer.persistence.entity.RoleType;
import com.iwaneez.stuffer.service.SecurityService;
import com.iwaneez.stuffer.ui.view.administration.AdminView;
import com.vaadin.navigator.View;
import com.vaadin.spring.access.ViewInstanceAccessControl;
import com.vaadin.ui.UI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ViewInstanceAccessControlImpl implements ViewInstanceAccessControl {

    @Autowired
    private SecurityService securityService;

    private Class[] adminClasses = new Class[]{AdminView.class};

    @Override
    public boolean isAccessGranted(UI ui, String beanName, View view) {
        for (Class clazz : adminClasses) {
            if (clazz.isInstance(view)) {
                return securityService.hasRole(RoleType.ADMIN);
            }
        }
        return true;
    }

}