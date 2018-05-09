package com.iwaneez.stuffer.ui.component.masterdetail;

import com.google.common.eventbus.Subscribe;
import com.iwaneez.stuffer.event.BusEvent;
import com.iwaneez.stuffer.ui.component.Localizable;
import com.iwaneez.stuffer.util.SessionScopedEventBus;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Grid;

import java.util.Optional;

public abstract class MasterComponent<T> extends CustomComponent implements Localizable {

    private Grid<T> grid;

    public MasterComponent() {
        SessionScopedEventBus.register(this);
        this.grid = createGrid();

        setCompositionRoot(grid);
        setSizeFull();
        localize();
    }

    protected abstract Grid<T> createGrid();

    protected Optional<T> getSelected() {
        return grid.getSelectionModel().getFirstSelectedItem();
    }

    @Subscribe
    protected void reload(BusEvent.RefreshUserGridEvent event) {
        grid.getDataProvider().refreshAll();
    }

}
