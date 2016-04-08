package com.rafabertholdo.dotastats;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import android.support.v7.widget.RecyclerView;
import java.util.List;

/**
 * Created by rafaelgb on 07/04/2016.
 */
public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ViewHolder> {

    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(Hero item);
    }

    private final List<Hero> mValues;
    private final OnListFragmentInteractionListener mListener;

    public ContactAdapter(List<Hero> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_hero, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mNameView.setText(mValues.get(position).getName());
        holder.mLocalizedNameView.setText(mValues.get(position).getLocalizedName());

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
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mNameView;
        public final TextView mLocalizedNameView;
        public Hero mItem;

        public ViewHolder(View view) {
            super(view);
            view.setClickable(true);
            mView = view;
            mNameView = (TextView) view.findViewById(R.id.Name);
            mLocalizedNameView = (TextView) view.findViewById(R.id.LocalizedName);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mLocalizedNameView.getText() + "'";
        }
    }
}
