package com.iwaneez.stuffer.ui.menu;

import com.google.common.eventbus.Subscribe;
import com.iwaneez.stuffer.event.ViewChangedEvent;
import com.iwaneez.stuffer.util.SessionScopedEventBus;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

public class SidebarMenu extends CustomComponent {

    private static final String STYLE_VISIBLE = "valo-menu-visible";


    public SidebarMenu() {
        SessionScopedEventBus.register(this);
        setPrimaryStyleName(ValoTheme.MENU_ROOT);
        setSizeUndefined();

        setCompositionRoot(buildContent());
    }

    private Component buildContent() {
        final CssLayout menuContent = new CssLayout();
        menuContent.addStyleName(ValoTheme.MENU_PART);
        menuContent.addStyleNames("sidebar", "no-vertical-drag-hints", "no-horizontal-drag-hints");

        menuContent.addComponent(buildTitle());
        menuContent.addComponent(buildMenuItems());
        menuContent.addComponent(buildToggleButton());

        Button logoutButton = new Button("Logout", event -> {
            getUI().getPage().reload();
            getSession().close();
        });
//        menuContent.addComponent(logoutButton);

        return menuContent;
    }

    private Component buildTitle() {
        Label logo = new Label("App <strong>Stuffer Bot</strong>", ContentMode.HTML);
        logo.setSizeUndefined();

        HorizontalLayout logoWrapper = new HorizontalLayout(logo);
        logoWrapper.setComponentAlignment(logo, Alignment.MIDDLE_CENTER);
        logoWrapper.addStyleName(ValoTheme.MENU_TITLE);
        logoWrapper.setSpacing(false);

        return logoWrapper;
    }

    private Component buildMenuItems() {
        CssLayout menuItemsLayout = new CssLayout();
        menuItemsLayout.addStyleName("valo-menuitems");

        for (MenuItem menuItem : MenuItem.values()) {
            Component menuItemComponent = new MenuItemButton(menuItem);
            menuItemsLayout.addComponent(menuItemComponent);
        }

        return menuItemsLayout;
    }

    private Component buildToggleButton() {
        Button valoMenuToggleButton = new Button("Menu", event -> {
            if (getCompositionRoot().getStyleName().contains(STYLE_VISIBLE)) {
                getCompositionRoot().removeStyleName(STYLE_VISIBLE);
            } else {
                getCompositionRoot().addStyleName(STYLE_VISIBLE);
            }
        });
        valoMenuToggleButton.setIcon(VaadinIcons.LIST);
        valoMenuToggleButton.addStyleName("valo-menu-toggle");
        valoMenuToggleButton.addStyleName(ValoTheme.BUTTON_SMALL);
        valoMenuToggleButton.addStyleName(ValoTheme.BUTTON_PRIMARY);

        return valoMenuToggleButton;
    }

    public final class MenuItemButton extends Button {

        private static final String STYLE_SELECTED = "selected";

        private final MenuItem menuItem;

        public MenuItemButton(MenuItem menuItem) {
            this.menuItem = menuItem;
            SessionScopedEventBus.register(this);

            setPrimaryStyleName(ValoTheme.MENU_ITEM);
            setIcon(menuItem.getIcon());
            setCaption(menuItem.getViewName().substring(0, 1).toUpperCase() + menuItem.getViewName().substring(1));
            addClickListener(event -> UI.getCurrent().getNavigator().navigateTo(menuItem.getViewName()));
        }

        @Subscribe
        public void postViewChange(ViewChangedEvent viewChangedEvent) {
            removeStyleName(STYLE_SELECTED);
            if (viewChangedEvent.getMenuItem() == menuItem) {
                addStyleName(STYLE_SELECTED);
            }
        }
    }
}
