package com.iwaneez.stuffer.ui.component.masterdetail;

import com.iwaneez.stuffer.ui.component.Localizable;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Grid;

import java.util.Optional;

public abstract class MasterComponent<T> extends CustomComponent implements MasterItemCollection<T>, ItemSaveListener<T>, Localizable {

    private Grid<T> grid;


    public MasterComponent() {
        grid = createGrid();

        setCompositionRoot(grid);
        setSizeFull();
        localize();
    }

    protected abstract Grid<T> createGrid();

    @Override
    public Optional<T> getSelected() {
        return grid.getSelectionModel().getFirstSelectedItem();
    }

    @Override
    public void reload() {
        grid.getDataProvider().refreshAll();
    }

    @Override
    public void onItemSave(T item) {
        reload();
    }
}
