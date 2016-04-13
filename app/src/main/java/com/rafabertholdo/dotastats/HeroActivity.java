package com.rafabertholdo.dotastats;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

public class HeroActivity extends AppCompatActivity {

    private ImageView _imgHeroLarge;
    private LinearLayout _layout;
    private TextView _heroName;
    private TextView _heroRoles;
    private TextView _heroAttackType;

    private TextView _strengthAttribute;
    private TextView _agilityAttribute;
    private TextView _inteligenceAttribute;

    private int _level = 1;

    private Button _btnLevelDown;
    private Button _btnLevelUp;
    private TextView _txtLevel;

    private Hero hero;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hero);
        _imgHeroLarge = (ImageView)findViewById(R.id.imgHeroLarge);
        _layout = (LinearLayout) findViewById(R.id.abilitiesLayout);
        _heroName = (TextView) findViewById(R.id.txtHeroName);
        _heroRoles = (TextView) findViewById(R.id.txtHeroRolesValues);
        _heroAttackType = (TextView) findViewById(R.id.txtHeroAttackTypeValue);
        _strengthAttribute = (TextView) findViewById(R.id.attributeStrengthValue);
        _agilityAttribute = (TextView) findViewById(R.id.attributeAgilityValue);
        _inteligenceAttribute = (TextView) findViewById(R.id.attributeInteligenceValue);

        _txtLevel = (TextView) findViewById(R.id.level);
        _btnLevelDown = (Button) findViewById(R.id.levelDown);
        _btnLevelDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(_level > 1){
                    _level--;
                    updateLevel();
                }
            }
        });
        _btnLevelUp = (Button) findViewById(R.id.levelUp);
        _btnLevelUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(_level < 25){
                    _level++;
                    updateLevel();
                }
            }
        });

        Intent intent = getIntent();
        hero = (Hero) intent.getSerializableExtra("hero");

        Context context = _imgHeroLarge.getContext();
        String heroName = hero.getName().replace("npc_dota_hero_","");
        Picasso.with(context).load(String.format("http://cdn.dota2.com/apps/dota2/images/heroes/%s_vert.jpg",heroName)).into(_imgHeroLarge);
        _heroName.setText(hero.getLocalizedName());
        _heroRoles.setText(TextUtils.join(", ", hero.getRole()));
        _heroAttackType.setText(hero.getAttackCapability());

        updateLevel();

        //android.app.FragmentManager manager = getFragmentManager();
        for(int i=0;i<hero.getAbilities().size();i++){
            String ability = hero.getAbilities().get(i);

            View abilityView = LayoutInflater.from(context).inflate(R.layout.fragment_ability, _layout, false);
            ImageView abilityImage = (ImageView)abilityView.findViewById(R.id.abilityImage);
            TextView abilityName = (TextView)abilityView.findViewById(R.id.abilityName);
            TextView abilityDescription = (TextView)abilityView.findViewById(R.id.abilityDescription);

            /*
            android.app.FragmentTransaction transaction = manager.beginTransaction();
            AbilityFragment fragment = new AbilityFragment();
            transaction.add(R.id.abilitiesLayout, fragment);
            transaction.commit();
            */

            try {
                JSONObject abilityMap = (JSONObject) MainActivity.abilityMap.get(ability);
                abilityName.setText(abilityMap.getString("dname"));
                abilityDescription.setText(abilityMap.getString("desc"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Picasso.with(context).load(String.format("http://media.steampowered.com/apps/dota2/images/abilities/%s_hp1.png",ability)).into(abilityImage);
            _layout.addView(abilityView);
        }
    }

    private void updateLevel(){
        _strengthAttribute.setText(hero.getBaseStrength(_level));
        _agilityAttribute.setText(hero.getBaseAgility(_level));
        _inteligenceAttribute.setText(hero.getBaseInteligence(_level));
        _txtLevel.setText(String.valueOf(_level));
    }

    public static float convertPixelsToDp(int px, Context context){
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float dp = (float)px / ((float)metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return dp;
    }
}
