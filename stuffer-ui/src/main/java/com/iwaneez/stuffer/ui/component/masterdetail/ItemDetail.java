package com.iwaneez.stuffer.ui.component.masterdetail;

public interface ItemDetail<T> {

    T getItem();

    void setItem(T item);

    T save(T item);

}
