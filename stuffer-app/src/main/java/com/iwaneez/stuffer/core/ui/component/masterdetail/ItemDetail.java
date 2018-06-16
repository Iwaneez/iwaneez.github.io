package com.iwaneez.stuffer.core.ui.component.masterdetail;

public interface ItemDetail<T> {

    T getItem();

    void setItem(T item);

    T save(T item);

}
