package com.example.notesdevie1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomItemAdapter extends ArrayAdapter<ItemModel> {
    private Context context;
    private int resourceId;

    public CustomItemAdapter(Context context, int resourceId, ItemModel[] items) {
        super(context, resourceId, items);
        this.context = context;
        this.resourceId = resourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(resourceId, parent, false);
        }

        ItemModel item = getItem(position);

        if (item != null) {
            TextView textView = convertView.findViewById(R.id.text);
            ImageView imageView = convertView.findViewById(R.id.icon);

            textView.setText(item.getText());
            imageView.setImageResource(item.getIconResource());
        }

        return convertView;
    }
}
