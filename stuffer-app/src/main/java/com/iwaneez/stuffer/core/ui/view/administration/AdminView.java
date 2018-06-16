package com.iwaneez.stuffer.core.ui.view.administration;

import com.iwaneez.stuffer.core.ui.component.Localizable;
import com.iwaneez.stuffer.core.util.Localization;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import javax.annotation.PostConstruct;

@SpringView(name = AdminView.VIEW_NAME)
public class AdminView extends VerticalLayout implements View, Localizable {

    public static final String VIEW_NAME = "AdminView";

    private TabSheet.Tab usersAdministration;


    @PostConstruct
    public void init() {
        TabSheet administrationTabSheet = new TabSheet();
        administrationTabSheet.addStyleNames(ValoTheme.TABSHEET_COMPACT_TABBAR, ValoTheme.TABSHEET_FRAMED);
        administrationTabSheet.setSizeFull();

        usersAdministration = administrationTabSheet.addTab(new UsersAdministration());
        usersAdministration.setIcon(VaadinIcons.GROUP);

        addComponent(administrationTabSheet);

        setSizeFull();
        localize();
    }

    @Override
    public void localize() {
        usersAdministration.setCaption(Localization.get("administration.users.caption"));
    }
}