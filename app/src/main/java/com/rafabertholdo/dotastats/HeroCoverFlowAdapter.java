package com.rafabertholdo.dotastats;

import android.content.Context;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import it.moondroid.coverflow.components.ui.containers.contentbands.BasicContentBand;

/**
 * Created by rafaelgb on 07/04/2016.
 */
public class HeroCoverFlowAdapter extends BaseAdapter {

    private ArrayList<Hero> mData = new ArrayList<>(0);
    private Context mContext;

    public HeroCoverFlowAdapter(Context context) {
        mContext = context;
    }

    public void setData(ArrayList<Hero> data) {
        mData = data;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int pos) {
        return mData.get(pos);
    }

    @Override
    public long getItemId(int pos) {
        return pos;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View rowView = convertView;

        if (rowView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = inflater.inflate(R.layout.item_coverflow, null);

            ViewHolder viewHolder = new ViewHolder();
            viewHolder.text = (TextView) rowView.findViewById(R.id.label);
            viewHolder.image = (ImageView) rowView
                    .findViewById(R.id.image);
            rowView.setTag(viewHolder);
        }

        ViewHolder holder = (ViewHolder) rowView.getTag();

        //holder.image.setImageResource(mData.get(position).imageResId);

        Context context = holder.image.getContext();
        String heroName = mData.get(position).getName().replace("npc_dota_hero_","");
        //Picasso.with(context).load(String.format("http://cdn.dota2.com/apps/dota2/images/heroes/%s_sb.png",heroName)).into(holder.mImageView);
        Picasso.with(context).load(String.format("http://cdn.dota2.com/apps/dota2/images/heroes/%s_vert.jpg",heroName)).into(holder.image);

        holder.text.setText(mValues.get(position).getLocalizedName());

        return rowView;
    }


    static class ViewHolder {
        public TextView text;
        public ImageView image;
    }
}