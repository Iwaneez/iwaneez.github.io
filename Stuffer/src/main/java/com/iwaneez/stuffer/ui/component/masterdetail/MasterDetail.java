package com.iwaneez.stuffer.ui.component.masterdetail;

import com.iwaneez.stuffer.event.BusEvent;
import com.iwaneez.stuffer.ui.component.Localizable;
import com.iwaneez.stuffer.util.Localization;
import com.iwaneez.stuffer.util.SessionScopedEventBus;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

import java.util.Optional;

public abstract class MasterDetail<T, M extends MasterComponent<T>, D extends DetailComponent<T>> extends CustomComponent implements Localizable {

    private static final int BUTTON_DEFAULT_EM_WIDTH = 8;

    private M masterView;
    private D detailView;

    private Button createButton, editButton, deleteButton;


    public MasterDetail(M masterView, D detailView) {
        this.masterView = masterView;
        this.detailView = detailView;

        Component buttonControls = createButtonControls();

        HorizontalSplitPanel splitPanel = new HorizontalSplitPanel(masterView, detailView);
        splitPanel.setStyleName(ValoTheme.SPLITPANEL_LARGE);
        splitPanel.setSizeFull();

        VerticalLayout content = new VerticalLayout(buttonControls, splitPanel);
        content.setExpandRatio(splitPanel, 1);
        content.setSizeFull();

        setCompositionRoot(content);
        setSizeFull();
        localize();
    }

    private Component createButtonControls() {
        createButton = new Button(VaadinIcons.PLUS_CIRCLE_O, this::createButtonClicked);
        createButton.addStyleNames(ValoTheme.BUTTON_FRIENDLY, ValoTheme.BUTTON_SMALL);
        createButton.setWidth(BUTTON_DEFAULT_EM_WIDTH, Unit.EM);

        editButton = new Button(VaadinIcons.EDIT, this::editButtonClicked);
        editButton.addStyleNames(ValoTheme.BUTTON_SMALL);
        editButton.setWidth(BUTTON_DEFAULT_EM_WIDTH, Unit.EM);

        deleteButton = new Button(VaadinIcons.TRASH, this::deleteButtonClicked);
        deleteButton.addStyleNames(ValoTheme.BUTTON_DANGER, ValoTheme.BUTTON_SMALL);
        deleteButton.setWidth(BUTTON_DEFAULT_EM_WIDTH, Unit.EM);

        HorizontalLayout buttonControlLayout = new HorizontalLayout(createButton, editButton, deleteButton);

        return buttonControlLayout;
    }

    protected M getMasterView() {
        return masterView;
    }

    protected D getDetailView() {
        return detailView;
    }

    protected Optional<T> getSelected() {
        return masterView.getSelected();
    }

    private void createButtonClicked(Button.ClickEvent clickEvent) {
        detailView.loadItem(null);
    }

    private void editButtonClicked(Button.ClickEvent clickEvent) {
        getSelected().ifPresent(item -> detailView.loadItem(item));
    }

    private void deleteButtonClicked(Button.ClickEvent clickEvent) {
        getSelected().ifPresent(item -> {
            deleteItem(item);
            detailView.loadItem(null);
            SessionScopedEventBus.post(new BusEvent.RefreshUserGridEvent());
        });
    }

    protected abstract void deleteItem(T item);

    @Override
    public void localize() {
        createButton.setCaption(Localization.get("general.masterdetail.button.create"));
        editButton.setCaption(Localization.get("general.masterdetail.button.edit"));
        deleteButton.setCaption(Localization.get("general.masterdetail.button.delete"));
    }
}
