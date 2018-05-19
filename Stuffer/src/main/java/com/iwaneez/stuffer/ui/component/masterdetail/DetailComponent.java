package com.iwaneez.stuffer.ui.component.masterdetail;

import com.iwaneez.stuffer.ui.component.Localizable;
import com.iwaneez.stuffer.util.Localization;
import com.vaadin.data.Binder;
import com.vaadin.data.ValidationResult;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public abstract class DetailComponent<T> extends CustomComponent implements ItemDetail<T>, Localizable {

    private final static Logger LOGGER = LoggerFactory.getLogger(DetailComponent.class);

    private static final String STYLE_NAME = "md-detail";
    private static final int BUTTON_DEFAULT_EM_WIDTH = 8;

    private Class<T> tClass;
    private T item;
    private Binder<T> binder;
    private List<ItemSaveListener<T>> itemSaveListeners;

    private Button cancelButton, saveButton;


    protected DetailComponent(Class<T> tClass) {
        this.tClass = tClass;
        this.item = createNewInstance(tClass);
        this.binder = new Binder<>(tClass);
        this.itemSaveListeners = new ArrayList<>();

        setStyleName(STYLE_NAME);
        VerticalLayout content = new VerticalLayout();

        Component itemDetail = createDetail(binder);
        Component buttonControls = createButtonControls();

        content.addComponents(itemDetail, buttonControls);
        content.setExpandRatio(buttonControls, 1);
        content.setSizeFull();

        setCompositionRoot(content);
        setSizeFull();
        localize();
    }

    /**
     * Creates item detail representation component
     *
     * @param binder binder for binding fields and validators
     * @return detail component
     */
    protected abstract Component createDetail(Binder<T> binder);

    private Component createButtonControls() {
        cancelButton = new Button(VaadinIcons.CLOSE_CIRCLE, this::onCancel);
        cancelButton.addStyleNames(ValoTheme.BUTTON_SMALL);
        cancelButton.setWidth(BUTTON_DEFAULT_EM_WIDTH, Unit.EM);

        saveButton = new Button(VaadinIcons.CHECK_CIRCLE, this::onSave);
        saveButton.addStyleNames(ValoTheme.BUTTON_SMALL, ValoTheme.BUTTON_FRIENDLY);
        saveButton.setWidth(BUTTON_DEFAULT_EM_WIDTH, Unit.EM);

        HorizontalLayout horizontalLayout = new HorizontalLayout(cancelButton, saveButton);

        return horizontalLayout;
    }

    private void onCancel(Button.ClickEvent event) {
        loadItem(item);
    }

    private void onSave(Button.ClickEvent event) {
        if (binder.writeBeanIfValid(item)) {
            try {
                item = save(item);
                itemSaveListeners.forEach(listener -> listener.onItemSave(item));
                Notification.show(Localization.get("messages.general.saved"), Notification.Type.HUMANIZED_MESSAGE);
            } catch (Exception e) {
                LOGGER.error("Error occurred during saving entity", e);
                Notification.show(Localization.get("messages.general.error_occurred", e.getMessage()), Notification.Type.ERROR_MESSAGE);
            }
        }
    }

    protected abstract void getFocus();

    public void addItemSaveListener(ItemSaveListener<T> listener) {
        itemSaveListeners.add(listener);
    }

    @Override
    public T getItem() {
        return item;
    }

    @Override
    public void setItem(T tItem) {
        item = (tItem == null) ? createNewInstance(tClass) : tItem;
        loadItem(item);
    }

    protected void loadItem(T item) {
        binder.readBean(item);
    }

    protected Binder<T> getBinder() {
        return binder;
    }

    private void showValidationErrorMessage(List<ValidationResult> validationResults) {
        StringBuilder sb = new StringBuilder("<ul>");
        validationResults.forEach(vr -> sb.append("<li>").append(vr.getErrorMessage()).append("</li>"));
        sb.append("</ul>");

        Notification notification = new Notification(Localization.get("messages.general.validation_errors"), sb.toString(), Notification.Type.ERROR_MESSAGE, true);
        notification.show(UI.getCurrent().getPage());
    }

    @Override
    public void localize() {
        setCaption(Localization.get("general.masterdetail.detail.caption"));
        cancelButton.setCaption(Localization.get("general.button.cancel"));
        saveButton.setCaption(Localization.get("general.button.save"));
    }

    private T createNewInstance(Class<T> tClass) {
        try {
            return tClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }
}
