package com.iwaneez.stuffer.core.event;

import com.iwaneez.stuffer.core.ui.menu.MenuItem;

public class BusEvent {

    public static class ViewChangedEvent {

        private final MenuItem menuItem;

        public ViewChangedEvent(MenuItem menuItem) {
            this.menuItem = menuItem;
        }

        public MenuItem getMenuItem() {
            return menuItem;
        }
    }

    public static class RefreshExchangeDataEvent {
    }
}
