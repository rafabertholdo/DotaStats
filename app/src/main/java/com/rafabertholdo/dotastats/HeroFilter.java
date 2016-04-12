package com.rafabertholdo.dotastats;

import android.widget.Filter;

import java.util.ArrayList;

/**
 * Created by Bertholdo on 11/04/2016.
 */
public class HeroFilter extends Filter {

    HeroAdapter mAdapter;
    ArrayList<Hero> mHeroes;
    HeroFilter mfilter;

    public HeroFilter(ArrayList<Hero> heroes, HeroAdapter adapter){
        this.mAdapter = adapter;
        this.mHeroes = heroes;
    }

    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        FilterResults results = new FilterResults();

        if(constraint != null && constraint.length() > 0){
            constraint = constraint.toString().toUpperCase();
            ArrayList<Hero> filteredHeroes = new ArrayList<Hero>();
            for(int i=0;i<mHeroes.size();i++){
                Hero hero = mHeroes.get(i);
                if(hero.getLocalizedName().toUpperCase().contains(constraint))
                {
                    filteredHeroes.add(hero);
                }
            }
            results.count = filteredHeroes.size();
            results.values = filteredHeroes;
        }
        else
        {
            results.count = mHeroes.size();
            results.values = mHeroes;
        }

        return results;
    }

    @Override
    protected void publishResults(CharSequence constraint, FilterResults results) {
        mAdapter.setHeroes((ArrayList<Hero>)results.values);
        mAdapter.notifyDataSetChanged();
    }
}
