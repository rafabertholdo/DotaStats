package com.rafabertholdo.dotastats;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rafaelgb on 07/04/2016.
 */
public class HeroAdapter extends RecyclerView.Adapter<HeroAdapter.ViewHolder> implements Filterable{

    private List<Hero> mHeroes;
    private List<Hero> mFilteredHeroes;
    private final OnListFragmentInteractionListener mListener;
    private HeroFilter mFilter;

    public HeroAdapter(List<Hero> items, OnListFragmentInteractionListener listener) {
        mHeroes = items;
        mListener = listener;
        mFilteredHeroes = items;
    }


    @Override
    public Filter getFilter() {
        if(mFilter == null){
            mFilter = new HeroFilter((ArrayList<Hero>) mFilteredHeroes, this);
        }
        return mFilter;
    }

    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(Hero item);
    }

    public List<Hero> getHeroes() {
        return mHeroes;
    }

    public void setHeroes(List<Hero> value) {
        this.mHeroes = value;
    }



    public List<Hero> getFilteredHeroes() {
        return mFilteredHeroes;
    }
    public void setFilteredHeroes(List<Hero> value) {
        this.mFilteredHeroes = value;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_hero, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mHeroes.get(position);

        Context context = holder.mImageView.getContext();
        String heroName = mHeroes.get(position).getName().replace("npc_dota_hero_","");
        Picasso.with(context).load(String.format("http://cdn.dota2.com/apps/dota2/images/heroes/%s_sb.png",heroName)).into(holder.mImageView);
        holder.mLocalizedNameView.setText(mHeroes.get(position).getLocalizedName());

        holder.mAttributeView.setImageResource(context.getResources().getIdentifier(holder.mItem.getAttributePrimary().toLowerCase(),"drawable",context.getPackageName()));

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                    holder.mView.setSelected(true);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mHeroes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final ImageView mImageView;
        public final TextView mLocalizedNameView;
        public final ImageView mAttributeView;
        public Hero mItem;

        public ViewHolder(View view) {
            super(view);
            view.setClickable(true);
            mView = view;
            mImageView = (ImageView) view.findViewById(R.id.HeroImage);
            mLocalizedNameView = (TextView) view.findViewById(R.id.LocalizedName);
            mAttributeView= (ImageView) view.findViewById(R.id.heroMainAttribute);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mLocalizedNameView.getText() + "'";
        }
    }
}
