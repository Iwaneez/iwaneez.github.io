package com.iwaneez.stuffer.ui.component.masterdetail;

import com.iwaneez.stuffer.ui.component.Localizable;
import com.iwaneez.stuffer.util.Localization;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.themes.ValoTheme;

public abstract class DetailComponent<T> extends CustomComponent implements Localizable {

    private static final int BUTTON_DEFAULT_EM_WIDTH = 8;

    private Button cancelButton, saveButton;


    protected DetailComponent() {
        Component detailContent = createDetail(createButtonControls());

        setCompositionRoot(detailContent);
        setSizeFull();
        localize();
    }

    protected abstract Component createDetail(Component buttonControls);

    protected abstract void loadItem(T item);

    protected abstract void save();

    protected Component createButtonControls() {
        cancelButton = new Button(VaadinIcons.CLOSE_CIRCLE, event -> loadItem(null));
        cancelButton.addStyleNames(ValoTheme.BUTTON_SMALL);
        cancelButton.setWidth(BUTTON_DEFAULT_EM_WIDTH, Unit.EM);

        saveButton = new Button(VaadinIcons.CHECK_CIRCLE, event -> save());
        saveButton.addStyleNames(ValoTheme.BUTTON_SMALL, ValoTheme.BUTTON_FRIENDLY);
        saveButton.setWidth(BUTTON_DEFAULT_EM_WIDTH, Unit.EM);

        HorizontalLayout horizontalLayout = new HorizontalLayout(cancelButton, saveButton);

        return horizontalLayout;
    }

    @Override
    public void localize() {
        cancelButton.setCaption(Localization.get("general.masterdetail.button.cancel"));
        saveButton.setCaption(Localization.get("general.masterdetail.button.save"));
    }
}
