package com.etcmobileapps.nobetcieczaneler.Api;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.etcmobileapps.nobetcieczaneler.R;

import java.util.ArrayList;

public class CustomListViewAdapter extends ArrayAdapter<Repo> {

    private final LayoutInflater inflater;
    private final Context context;
    private ViewHolder holder;
    private final ArrayList<Repo> eczaneListesi;



    public CustomListViewAdapter(Context context, ArrayList<Repo> eczaneListesi) {
        super(context,0, eczaneListesi);
        this.context = context;
        this.eczaneListesi = eczaneListesi;
        inflater = LayoutInflater.from(context);


    }

    @Override
    public int getCount() {
        return eczaneListesi.size();
    }

    @Override
    public Repo getItem(int position) {
        return eczaneListesi.get(position);
    }

    @Override
    public long getItemId(int position) {
        return eczaneListesi.get(position).hashCode();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {

            convertView = inflater.inflate(R.layout.row_layout, null);

            holder = new ViewHolder();

            holder.nameData = (TextView) convertView.findViewById(R.id.nameData);
            holder.cityData =  (TextView) convertView.findViewById(R.id.cityData);
            holder.townData = (TextView) convertView.findViewById(R.id.townData);
            holder.adressData = (TextView) convertView.findViewById(R.id.adressData);
            holder.telephoneData = (TextView) convertView.findViewById(R.id.telephoneData);

            convertView.setTag(holder);

        }
        else{

            holder = (ViewHolder)convertView.getTag();
        }

        Repo value = eczaneListesi.get(position);
        if(value != null){


            holder.nameData.setText(value.getName());
            holder.cityData.setText(value.getCity());
            holder.townData.setText(value.getTown());
            holder.adressData.setText(value.getAddress());
            holder.telephoneData.setText(value.getPhone());



        }
        return convertView;
    }

    //View Holder Pattern for better performance
    private static class ViewHolder {
        TextView nameData;
        TextView cityData;
        TextView townData;
        TextView adressData;
        TextView telephoneData;

    }


}