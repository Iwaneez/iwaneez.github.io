package com.iwaneez.stuffer.core.ui.component;

public enum DialogType {

    INFO("info"), WARNING("warning"), ERROR("error");

    private String value;

    DialogType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
