package com.example.proyecto_traductor2;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class AdapterPalabra extends BaseAdapter {

    protected Activity activity;
    protected ArrayList<Palabra> items;

    public AdapterPalabra(Activity activity, ArrayList<Palabra> items) {
        this.activity = activity;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }
    public void clear() {
        items.clear();
    }
    public void addAll(ArrayList<Palabra> category) {
        for (int i = 0; i < category.size(); i++) {
            items.add(category.get(i));
        }
    }
    @Override
    public Object getItem(int arg0) {
        return items.get(arg0);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (convertView == null) {
            LayoutInflater inf = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inf.inflate(R.layout.item_category, null);
        }

        Palabra palabra = items.get(position);
        TextView txtPalabraESP = v.findViewById(R.id.txtPalabraOriginal);
        txtPalabraESP.setText(palabra.getPalabraSP());

        TextView txtPalabraENG = v.findViewById(R.id.txtPalabraOriginal2);
        txtPalabraENG.setText(palabra.getPalabraEN());

        return v;
    }
}
