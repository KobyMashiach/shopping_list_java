package com.example.mylist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class CustomAdapter extends ArrayAdapter<String> {

    CustomAdapter(@NonNull Context context, ArrayList databaseContent) {
        super(context, R.layout.custom_row, databaseContent);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater li = LayoutInflater.from(getContext());
        View customView = li.inflate(R.layout.custom_row, parent, false);

        String singleItem = getItem(position);
        TextView tvText = (TextView)customView.findViewById(R.id.tvText);
        tvText.setText(singleItem);

        return customView;
    }
}
