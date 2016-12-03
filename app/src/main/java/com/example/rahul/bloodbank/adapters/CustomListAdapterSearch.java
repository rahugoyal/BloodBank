package com.example.rahul.bloodbank.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rahul.bloodbank.R;
import com.example.rahul.bloodbank.constants.Constant;
import com.example.rahul.bloodbank.pojo.DrawerItemPojo;
import com.example.rahul.bloodbank.pojo.RegistrationPojo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rahul on 11/27/2016.
 */

public class CustomListAdapterSearch extends BaseAdapter {

    private Activity activity;
    private List<RegistrationPojo> registrationPojoArrayList;
    private static LayoutInflater inflater = null;

    public CustomListAdapterSearch(Activity activity, ArrayList<RegistrationPojo> registrationPojoArrayList) {

        this.activity = activity;
        this.registrationPojoArrayList = registrationPojoArrayList;

        inflater = (LayoutInflater) activity.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    public int getCount() {

        if (registrationPojoArrayList.size() <= 0)
            return 1;
        return registrationPojoArrayList.size();
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

            vi = inflater.inflate(R.layout.list_item_search, null);

            holder = new ViewHolder();
            holder.nameText = (TextView) vi.findViewById(R.id.tv_name_list_search);
            holder.bgText = (TextView) vi.findViewById(R.id.tv_bg_list_search);
            holder.phoneText = (TextView) vi.findViewById(R.id.tv_phone_list_search);
            holder.mIvOnline = (ImageView) vi.findViewById(R.id.iv_online_search);

            vi.setTag(holder);
        } else
            holder = (ViewHolder) vi.getTag();

        if (registrationPojoArrayList.size() <= 0) {
            holder.nameText.setText("No Data");

        } else {
            RegistrationPojo registrationPojo = registrationPojoArrayList.get(position);
            holder.nameText.setText(registrationPojo.getName());
            holder.bgText.setText(registrationPojo.getBgType());
            holder.phoneText.setText(registrationPojo.getPhone());
            if (registrationPojo.isDonorStatus() || registrationPojo.isAcceptorStatus()) {
                holder.mIvOnline.setVisibility(View.VISIBLE);
            }

        }
        return vi;
    }

    public static class ViewHolder {

        public TextView nameText, bgText, phoneText;
        public ImageView mIvOnline;

    }


}
