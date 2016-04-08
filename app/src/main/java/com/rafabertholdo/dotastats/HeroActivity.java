package com.rafabertholdo.dotastats;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class HeroActivity extends AppCompatActivity {

    private ImageView _imgHeroLarge;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hero);
        _imgHeroLarge = (ImageView)findViewById(R.id.imgHeroLarge);
        Intent intent = getIntent();
        Hero hero = (Hero) intent.getSerializableExtra("hero");

        Context context = _imgHeroLarge.getContext();
        String heroName = hero.getName().replace("npc_dota_hero_","");
        Picasso.with(context).load(String.format("http://cdn.dota2.com/apps/dota2/images/heroes/%s_vert.jpg",heroName)).into(_imgHeroLarge);
    }
}
