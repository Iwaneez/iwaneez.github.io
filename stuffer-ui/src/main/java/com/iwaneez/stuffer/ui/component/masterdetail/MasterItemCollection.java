package com.iwaneez.stuffer.ui.component.masterdetail;

import java.util.Optional;

public interface MasterItemCollection<T> {

    Optional<T> getSelected();

    void reload();
}
