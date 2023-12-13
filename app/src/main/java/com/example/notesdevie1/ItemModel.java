package com.example.notesdevie1;

public class ItemModel {
    private final String text;
    private final int iconResource;

    public ItemModel(String text, int iconResource) {
        this.text = text;
        this.iconResource = iconResource;
    }

    public String getText() {
        return text;
    }

    public int getIconResource() {
        return iconResource;
    }
}
