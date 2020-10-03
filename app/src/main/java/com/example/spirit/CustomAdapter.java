package com.example.spirit;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class CustomAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater layoutInflater;
    private List<String> elementLabels;

    public CustomAdapter(Context context, List<String> elementLabels){
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        this.elementLabels = elementLabels;
    }

    @Override
    public int getCount() {
        return elementLabels.size();
    }

    @Override
    public Object getItem(int position) {
        return elementLabels.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = layoutInflater.inflate(R.layout.list_item, parent, false);

            TextView label = convertView.findViewById(R.id.textview_listitem);
            label.setText(elementLabels.get(position));
        }

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PropulsionActivity.class);
                intent.putExtra("prop_name", elementLabels.get(position));
                context.startActivity(intent);
            }
        });

        return convertView;
    }
}
