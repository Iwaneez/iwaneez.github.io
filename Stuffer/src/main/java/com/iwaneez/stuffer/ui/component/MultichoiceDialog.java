package com.iwaneez.stuffer.ui.component;

import com.vaadin.ui.*;

public class MultichoiceDialog extends Window {

    public MultichoiceDialog(DialogType dialogType, String text, Option... options) {
        super(dialogType.getValue().toUpperCase());
        setModal(true);
        setResizable(false);
        setDraggable(false);
        setStyleName(dialogType.getValue());

        Label dialogText = new Label(text);

        HorizontalLayout mainLayout = new HorizontalLayout();
        for (Option option : options) {
            Button button = new Button(option.getCaption());
            button.addClickListener(event -> {
                if (option.getAction() != null) {
                    new Thread(option.getAction()).start();
                }
                close();
            });
            mainLayout.addComponent(button);
        }

        VerticalLayout content = new VerticalLayout(dialogText, mainLayout);
        content.setExpandRatio(dialogText, 1);
        content.setComponentAlignment(mainLayout, Alignment.MIDDLE_CENTER);

        setContent(content);
    }

    static class Option {
        private final String caption;
        private final Runnable action;

        Option(String caption, Runnable action) {
            this.caption = caption;
            this.action = action;
        }

        public String getCaption() {
            return caption;
        }

        public Runnable getAction() {
            return action;
        }
    }

    public static Option createOption(String caption, Runnable action) {
        return new Option(caption, action);
    }

}
