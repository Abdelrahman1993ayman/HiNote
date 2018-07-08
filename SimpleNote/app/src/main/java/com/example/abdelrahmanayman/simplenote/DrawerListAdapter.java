package com.example.abdelrahmanayman.simplenote;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

class DrawerListAdapter  extends BaseAdapter {

    Context mContext ;
    ArrayList<ListViewItems> listViewItems ;

    public DrawerListAdapter(Context mContext, ArrayList<ListViewItems> listViewItems) {
        this.mContext = mContext;
        this.listViewItems = listViewItems;
    }

    @Override
    public int getCount() {
        return listViewItems.size();
    }


    @Override
    public Object getItem(int position) {
        return listViewItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0 ;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        if (convertView == null)
        {
            LayoutInflater inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view=inflater.inflate(R.layout.drawer_item , null);
        }else {
            view= convertView ;
        }
        TextView textView = view.findViewById(R.id.tvTitle);
        ImageView imageView = view.findViewById(R.id.ivIcon);

        textView.setText(listViewItems.get(position).Title);
        imageView.setImageResource(listViewItems.get(position).Icon);
        return  view;
    }
}