package com.rafabertholdo.dotastats;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Bertholdo on 13/04/2016.
 */
public class AbilityFragment extends Fragment {
    private ImageView mAbilityImage;
    private TextView mAbilityName;
    private TextView mAbilityDescription;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View result = inflater.inflate(R.layout.fragment_ability, container, false);
        mAbilityImage = (ImageView) result.findViewById(R.id.abilityImage);
        mAbilityName = (TextView) result.findViewById(R.id.abilityName);
        mAbilityDescription = (TextView) result.findViewById(R.id.abilityDescription);
        return result;
    }

    public ImageView getAbilityImage() {
        return mAbilityImage;
    }

    public void setAbilityImage(ImageView mAbilityImage) {
        this.mAbilityImage = mAbilityImage;
    }

    public TextView getAbilityName() {
        return mAbilityName;
    }

    public void setAbilityName(TextView mAbilityName) {
        this.mAbilityName = mAbilityName;
    }

    public TextView getAbilityDescription() {
        return mAbilityDescription;
    }

    public void setAbilityDescription(TextView mAbilityDescription) {
        this.mAbilityDescription = mAbilityDescription;
    }
}
