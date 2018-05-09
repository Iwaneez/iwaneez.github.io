package com.iwaneez.stuffer.event;

import com.iwaneez.stuffer.ui.menu.MenuItem;

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

    public static class RefreshUserGridEvent {
    }
}
