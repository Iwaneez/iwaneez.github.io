package com.iwaneez.stuffer.core.ui.component.masterdetail;

import java.util.Optional;

public interface MasterItemCollection<T> {

    Optional<T> getSelected();

    void reload();
}
