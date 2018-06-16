package com.iwaneez.stuffer.core.config;

import com.iwaneez.stuffer.core.persistence.entity.RoleType;
import com.iwaneez.stuffer.core.service.SecurityService;
import com.iwaneez.stuffer.core.ui.view.administration.AdminView;
import com.vaadin.navigator.View;
import com.vaadin.spring.access.ViewInstanceAccessControl;
import com.vaadin.ui.UI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ViewInstanceAccessControlStrategy implements ViewInstanceAccessControl {

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