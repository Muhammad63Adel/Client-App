package com.example.clientapp;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;


public class custom_adapter extends ArrayAdapter<order> {

    private static final String LOG_TAG = custom_adapter.class.getSimpleName();

    public custom_adapter(Activity context, ArrayList<order> order){

        super(context, 0, order);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if the existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = ((Activity) getContext()).getLayoutInflater().inflate(R.layout.list, parent, false);
        }

        TextView redTextView = (TextView) convertView.findViewById(R.id.red);
        TextView greenTextView = (TextView) convertView.findViewById(R.id.green);
        TextView yellowTextView = (TextView) convertView.findViewById(R.id.yellow);

        order current_order = getItem(position);

        assert current_order != null;
        redTextView.setText(current_order.getRed());
        greenTextView.setText(current_order.getGreen());
        yellowTextView.setText(current_order.getYellow());

        return convertView;


    }
}
