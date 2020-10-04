package com.example.spirit;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.spirit.objects.Propulsion;

import java.io.File;
import java.util.List;

public class CustomAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater layoutInflater;
    private List<Propulsion> propulsionList;

    public CustomAdapter(Context context, List<Propulsion> elementLabels) {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        this.propulsionList = elementLabels;
    }

    @Override
    public int getCount() {
        return propulsionList.size();
    }

    @Override
    public Object getItem(int position) {
        return propulsionList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.list_item, parent, false);
            Propulsion propulsion = propulsionList.get(position);

            TextView label = convertView.findViewById(R.id.textview_listitem);
            label.setText(propulsion.getName());
            ImageView imageView = convertView.findViewById(R.id.imageview_listitem);
            imageView.setImageResource(context.getResources().getIdentifier(propulsion.getPicture(), "drawable", context.getPackageName()));

        }

        convertView.setOnClickListener(v -> {
            Intent intent = new Intent(context, PropulsionActivity.class);
            intent.putExtra("propulsion", propulsionList.get(position));
            context.startActivity(intent);
        });

        return convertView;
    }

}
