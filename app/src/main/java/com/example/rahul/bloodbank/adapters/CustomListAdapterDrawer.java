package com.example.rahul.bloodbank.adapters;

/**
 * Created by Rahul on 11/25/2016.
 */

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rahul.bloodbank.R;
import com.example.rahul.bloodbank.pojo.DrawerItemPojo;

import java.util.ArrayList;

public class CustomListAdapterDrawer extends BaseAdapter {

    private Activity activity;
    private ArrayList<DrawerItemPojo> listDrawer;
    private static LayoutInflater inflater = null;

    public CustomListAdapterDrawer(Activity activity, ArrayList<DrawerItemPojo> listDrawer) {

        this.activity = activity;
        this.listDrawer = listDrawer;

        inflater = (LayoutInflater) activity.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    public int getCount() {

        if (listDrawer.size() <= 0)
            return 1;
        return listDrawer.size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        ViewHolder holder;

        if (convertView == null) {

            vi = inflater.inflate(R.layout.drawer_item, null);

            holder = new ViewHolder();
            holder.drawerText = (TextView) vi.findViewById(R.id.tv_NavTitle);
            holder.drawerImage = (ImageView) vi.findViewById(R.id.iv_NavIcon);

            vi.setTag(holder);
        } else
            holder = (ViewHolder) vi.getTag();

        if (listDrawer.size() <= 0) {
            holder.drawerText.setText("No Data");

        } else {
            DrawerItemPojo drawerItemPojo = listDrawer.get(position);
            holder.drawerText.setText(drawerItemPojo.getTitle());
            holder.drawerImage.setImageDrawable(activity.getResources().getDrawable(drawerItemPojo.getImageId()));

        }
        return vi;
    }

    public static class ViewHolder {

        public TextView drawerText;
        public ImageView drawerImage;

    }


}
