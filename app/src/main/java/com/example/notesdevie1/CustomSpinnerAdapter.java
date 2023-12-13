package com.example.notesdevie1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;

public class CustomSpinnerAdapter extends ArrayAdapter<String> {
    private final LayoutInflater inflater;
    private final String[] opciones;
    private final Spinner spinner;
    private final boolean isSpinnerOpen = false;

    public CustomSpinnerAdapter(Context context, int resource, String[] objects, Spinner spinner) {
        super(context, resource, objects);
        inflater = LayoutInflater.from(context);
        opciones = objects;
        this.spinner = spinner;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = inflater.inflate(R.layout.activity_spinner_item, parent, false);
        }

        TextView textView = view.findViewById(R.id.spinner_text);
        Button button = view.findViewById(R.id.spinner_button);

        textView.setText(opciones[position]);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isSpinnerOpen) {
                    spinner.performClick();
                } else {
                    spinner.performClick();
                }
            }
        });

        return view;
    }
}
