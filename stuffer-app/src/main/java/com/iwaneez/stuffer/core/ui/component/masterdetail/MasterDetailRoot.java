package com.iwaneez.stuffer.core.ui.component.masterdetail;

import com.iwaneez.stuffer.core.ui.component.Localizable;
import com.iwaneez.stuffer.core.util.Localization;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

import java.util.Optional;

public abstract class MasterDetailRoot<T, M extends MasterComponent<T> & ItemSaveListener<T>, D extends DetailComponent<T>> extends CustomComponent implements Localizable {

    private static final String STYLE_NAME_MD_ROOT = "md-root";
    private static final String STYLE_NAME_MD_SPLITPANEL = "md-splitpanel";
    private static final int BUTTON_DEFAULT_EM_WIDTH = 8;

    private M masterView;
    private D detailView;

    private Button createButton, editButton, deleteButton;


    public MasterDetailRoot(M master, D detail) {
        masterView = master;
        detailView = detail;
        detailView.addItemSaveListener(masterView);

        addStyleName(STYLE_NAME_MD_ROOT);
        Component buttonControls = createButtonControls();

        HorizontalLayout splitPanel = new HorizontalLayout(masterView, detailView);
        splitPanel.addStyleNames(ValoTheme.SPLITPANEL_LARGE, STYLE_NAME_MD_SPLITPANEL);
        splitPanel.setSizeFull();

        VerticalLayout content = new VerticalLayout(buttonControls, splitPanel);
        content.setMargin(false);

        setCompositionRoot(content);
        localize();
    }

    private Component createButtonControls() {
        createButton = new Button(VaadinIcons.PLUS_CIRCLE_O, this::createButtonClick);
        createButton.addStyleNames(ValoTheme.BUTTON_FRIENDLY, ValoTheme.BUTTON_SMALL);
        createButton.setWidth(BUTTON_DEFAULT_EM_WIDTH, Unit.EM);

        editButton = new Button(VaadinIcons.EDIT, this::editButtonClick);
        editButton.addStyleNames(ValoTheme.BUTTON_SMALL);
        editButton.setWidth(BUTTON_DEFAULT_EM_WIDTH, Unit.EM);

        deleteButton = new Button(VaadinIcons.TRASH, this::deleteButtonClick);
        deleteButton.addStyleNames(ValoTheme.BUTTON_DANGER, ValoTheme.BUTTON_SMALL);
        deleteButton.setWidth(BUTTON_DEFAULT_EM_WIDTH, Unit.EM);

        HorizontalLayout buttonControlLayout = new HorizontalLayout(createButton, editButton, deleteButton);

        return buttonControlLayout;
    }

    private Optional<T> getSelected() {
        return masterView.getSelected();
    }

    private void createButtonClick(Button.ClickEvent clickEvent) {
        detailView.setItem(null);
        detailView.getFocus();
    }

    private void editButtonClick(Button.ClickEvent clickEvent) {
        getSelected().ifPresent(item -> {
            detailView.setItem(item);
            detailView.getFocus();
        });
    }

    private void deleteButtonClick(Button.ClickEvent clickEvent) {
        getSelected().ifPresent(item -> {
            deleteItem(item);
            masterView.reload();
            detailView.setItem(null);
        });
    }

    protected abstract void deleteItem(T item);

    public M getMasterView() {
        return masterView;
    }

    public D getDetailView() {
        return detailView;
    }

    @Override
    public void localize() {
        createButton.setCaption(Localization.get("general.button.create"));
        editButton.setCaption(Localization.get("general.button.edit"));
        deleteButton.setCaption(Localization.get("general.button.delete"));
    }
}
