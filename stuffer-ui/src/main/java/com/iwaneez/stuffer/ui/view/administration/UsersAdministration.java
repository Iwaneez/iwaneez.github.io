package com.iwaneez.stuffer.ui.view.administration;

import com.vaadin.ui.VerticalLayout;

public class UsersAdministration extends VerticalLayout {

    private  UserMasterDetailRoot usersMasterDetail;

    public UsersAdministration() {
        usersMasterDetail = new UserMasterDetailRoot();

        addComponent(usersMasterDetail);
        setSizeFull();
    }
}
