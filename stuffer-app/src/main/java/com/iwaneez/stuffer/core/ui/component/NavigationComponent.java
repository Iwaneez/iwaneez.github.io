package com.iwaneez.stuffer.core.ui.component;

import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import org.springframework.data.domain.Page;

import java.util.Arrays;
import java.util.List;

public class NavigationComponent extends HorizontalLayout {

    private Page page = null;
    private int pageNumber = 1;
    private int totalPages = 1;

    private final List<Integer> pageSizes = Arrays.asList(10, 20, 30, 50);
    private int pageSize = pageSizes.get(2);
    private PageLoader pageLoader;

    private final Label pagingInfo = new Label(pageNumber + "/" + totalPages);

    public NavigationComponent(PageLoader pageLoader) {
        super();
        this.pageLoader = pageLoader;

        Button first = new Button("<<", event -> {
            pageNumber = 1;
            reloadPage();
        });

        Button prev = new Button("<", event -> {
            pageNumber = (pageNumber <= 1) ? 1 : pageNumber - 1;
            reloadPage();
        });

        Button next = new Button(">", event -> {
            pageNumber = (pageNumber >= totalPages) ? totalPages : pageNumber + 1;
            reloadPage();
        });

        Button last = new Button(">>", event -> {
            pageNumber = totalPages;
            reloadPage();
        });

        ComboBox<Integer> pageSizeBox = createPageSizeBox();

        addComponents(first, prev, pagingInfo, next, last, pageSizeBox);
    }

    private ComboBox<Integer> createPageSizeBox() {
        ComboBox<Integer> comboBox = new ComboBox<>();
        comboBox.setItems(pageSizes);
        comboBox.setSelectedItem(pageSize);
        comboBox.addValueChangeListener(event -> {
            pageSize = event.getValue();
            pageNumber = 1;
            reloadPage();
        });
        comboBox.setEmptySelectionAllowed(false);
        comboBox.setTextInputAllowed(false);
        comboBox.setWidth(6, Unit.REM);

        return comboBox;
    }

    public Page reloadPage() {
        Page page = pageLoader.loadPage(pageNumber, pageSize);
//        if (page.getContent().size() < 1) {
//            currentPage = 0;
//        }
        totalPages = (page == null || page.getTotalPages() < 1) ? 1 : page.getTotalPages();
        pagingInfo.setValue(pageNumber + "/" + totalPages);

        return page;
    }

    public Page loadFirstPage() {
        pageNumber = 1;
        return reloadPage();
    }

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    public Integer getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public PageLoader getPageLoader() {
        return pageLoader;
    }

    public void setPageLoader(PageLoader pageLoader) {
        this.pageLoader = pageLoader;
    }

    public interface PageLoader {

        Page loadPage(int pageNumber, int pageSize);

    }
}
