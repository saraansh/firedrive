package com.example.saraansh.firedrive;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hopeless on 03-Nov-17.
 */

public class DisplayAdapter extends ArrayAdapter<BookInformation> {

    private Activity context;
    private List<BookInformation> bookList;

    public DisplayAdapter(@NonNull Activity context, List<BookInformation> bookList) {
        super(context, R.layout.display_adapter_format, bookList);
        this.context = context;
        this.bookList = bookList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.display_adapter_format, null, true);
        TextView bname = (TextView) listViewItem.findViewById(R.id.textviewbook);
        TextView bgenre = (TextView) listViewItem.findViewById(R.id.textviewgenre);
        TextView brefer = (TextView) listViewItem.findViewById(R.id.textviewreferences);

        BookInformation bookInformation = bookList.get(position);

        bname.setText(bookInformation.getBname());
        bgenre.setText(bookInformation.getBgenre());
        brefer.setText(bookInformation.getBrefer());

        return listViewItem;
    }
}


