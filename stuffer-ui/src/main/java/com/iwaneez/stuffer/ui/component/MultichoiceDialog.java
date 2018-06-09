package com.iwaneez.stuffer.ui.component;

import com.vaadin.ui.*;

public class MultichoiceDialog extends Window {

    private final HorizontalLayout mainLayout;


    public MultichoiceDialog(DialogType dialogType, String text, Option... options) {
        super(dialogType.getValue().toUpperCase());
        setModal(true);
        setResizable(false);
        setDraggable(false);
        setStyleName(dialogType.getValue());

        Label dialogText = new Label(text);

        mainLayout = new HorizontalLayout();
        for (Option option : options) {
            addOption(option);
        }

        VerticalLayout content = new VerticalLayout(dialogText, mainLayout);
        content.setExpandRatio(dialogText, 1);
        content.setComponentAlignment(mainLayout, Alignment.MIDDLE_CENTER);

        setContent(content);
    }

    private void addOption(Option option) {
        Button button = new Button(option.getCaption());
        button.addClickListener(event -> {
            if (option.getAction() != null) {
                new Thread(option.getAction()).start();
            }
            close();
        });
        mainLayout.addComponent(button);
    }

    public void addOption(String caption, Runnable action) {
        addOption(new Option(caption, action));
    }

    private static class Option {
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

}
