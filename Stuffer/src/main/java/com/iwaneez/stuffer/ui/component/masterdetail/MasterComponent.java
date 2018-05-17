package com.iwaneez.stuffer.ui.component.masterdetail;

import com.iwaneez.stuffer.ui.component.Localizable;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Grid;
import com.vaadin.ui.VerticalLayout;

import java.util.Optional;

public abstract class MasterComponent<T> extends CustomComponent implements MasterItemCollection<T>, ItemSaveListener<T>, Localizable {

    public static final String STYLE_NAME = "md-master";

    private Grid<T> grid;


    public MasterComponent() {
        setStyleName(STYLE_NAME);

        VerticalLayout content = new VerticalLayout();
        content.setMargin(false);
        content.setSizeFull();

        grid = createGrid();
        content.addComponent(grid);
        content.setExpandRatio(grid, 1);

        setCompositionRoot(content);
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
