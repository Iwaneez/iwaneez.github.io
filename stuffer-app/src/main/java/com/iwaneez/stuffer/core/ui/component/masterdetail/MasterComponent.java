package com.iwaneez.stuffer.core.ui.component.masterdetail;

import com.iwaneez.stuffer.core.ui.component.Localizable;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Grid;
import com.vaadin.ui.VerticalLayout;

import java.util.Optional;

public abstract class MasterComponent<T> extends CustomComponent implements MasterItemCollection<T>, ItemSaveListener<T>, Localizable {

    public static final String STYLE_NAME = "md-master";

    private final Grid<T> grid;

    public MasterComponent() {
        addStyleNames(STYLE_NAME);

        grid = createGrid();

        VerticalLayout content = new VerticalLayout(grid);
        content.setMargin(false);

        setCompositionRoot(content);
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
