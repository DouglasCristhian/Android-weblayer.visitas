package com.weblayer.visitas.VIEW;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.weblayer.visitas.DTO.resultadoDTO;

public class resultadoSpinAdapter extends ArrayAdapter<resultadoDTO> {

    private Context context;
    private resultadoDTO[] values;

    public resultadoSpinAdapter(Context context, int textViewResourceId, resultadoDTO[] values) {
        super(context, textViewResourceId, values);
        this.context = context;
        this.values = values;
    }

    public int getCount(){
        return values.length;
    }

    public resultadoDTO getItem(int position){
        return values[position];
    }

    public long getItemId(int position){
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView label = new TextView(context);
        //label.setTextColor(Color.BLACK);
        label.setText(values[position].getds_descricao());
        return label;
    }

    @Override
    public View getDropDownView(int position, View convertView,
                                ViewGroup parent) {
        TextView label = new TextView(context);
        label.setText(values[position].getds_descricao());
        return label;
    }

}
