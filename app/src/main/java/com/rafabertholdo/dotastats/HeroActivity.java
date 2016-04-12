package com.rafabertholdo.dotastats;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.squareup.picasso.Picasso;

public class HeroActivity extends AppCompatActivity {

    private ImageView _imgHeroLarge;
    private LinearLayout _layout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hero);
        _imgHeroLarge = (ImageView)findViewById(R.id.imgHeroLarge);
        _layout = (LinearLayout) findViewById(R.id.abilitiesLayout);

        Intent intent = getIntent();
        Hero hero = (Hero) intent.getSerializableExtra("hero");

        Context context = _imgHeroLarge.getContext();
        String heroName = hero.getName().replace("npc_dota_hero_","");
        Picasso.with(context).load(String.format("http://cdn.dota2.com/apps/dota2/images/heroes/%s_vert.jpg",heroName)).into(_imgHeroLarge);


        for(int i=0;i<hero.getAbilities().size();i++){
            String ability = hero.getAbilities().get(i);
            //ImageView Setup
            ImageView imageView = new ImageView(this);
            //setting image resource
            //imageView.setImageResource(R.drawable.play);
            Picasso.with(context).load(String.format("http://media.steampowered.com/apps/dota2/images/abilities/%s_hp1.png",ability)).into(imageView);

            //setting image position
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            params.weight =1.0f;
            imageView.setLayoutParams(params);



            imageView.setPadding(i == 0 ? 0 : 10,10,10,10);

            imageView.setMinimumWidth(180);
            imageView.setMinimumHeight(180);
            //adding view to layout
            _layout.addView(imageView);
        }
        for (String ability: hero.getAbilities()) {

        }

    }
}
