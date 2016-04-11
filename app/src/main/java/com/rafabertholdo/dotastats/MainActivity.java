package com.rafabertholdo.dotastats;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import it.moondroid.coverflow.components.ui.containers.FeatureCoverFlow;

public class MainActivity extends AppCompatActivity {

//    private RecyclerView mRecyclerView;
    private HeroCoverFlowAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private FeatureCoverFlow mCoverFlow;
    public static Map<Integer,Hero> heroes;
    private TextSwitcher mTitle;
    private ArrayList<Hero> heroList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTitle = (TextSwitcher) findViewById(R.id.title);

        mTitle.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                LayoutInflater inflater = LayoutInflater.from(MainActivity.this);
                TextView textView = (TextView) inflater.inflate(R.layout.item_title, null);
                return textView;
            }
        });
        Animation in = AnimationUtils.loadAnimation(this, R.anim.slide_in_top);
        Animation out = AnimationUtils.loadAnimation(this, R.anim.slide_out_bottom);
        mTitle.setInAnimation(in);
        mTitle.setOutAnimation(out);

        //mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        mCoverFlow = (FeatureCoverFlow) findViewById(R.id.coverflow);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        //mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        //mRecyclerView.setLayoutManager(mLayoutManager);

        heroes = new HashMap<Integer, Hero>();

        try {

            // check sizeAssetManager am = getAssets();
            InputStream inputStream = getAssets().open("npc_heroes.txt");
            InputStreamReader isr = new InputStreamReader(inputStream);
            BufferedReader br = new BufferedReader(isr);
            String line = "";
            Hero hero = null;
            int chave = 0;
            Pattern pattern = Pattern.compile(".*\\\"(.*)\\\".*\\\"(.*)\\\".*");
            Pattern heroPattern = Pattern.compile(".*\\\"(.*)\\\".*");

            while ((line = br.readLine()) != null) {
                String trimmedLine = line.trim();
                if(trimmedLine.startsWith("//"))
                    continue;

                Matcher heroMatcher = heroPattern.matcher(line);
                if(hero == null && heroMatcher.find() &&
                        heroMatcher.group(1).startsWith("npc_dota_hero_") &&
                        !heroMatcher.group(1).equals("npc_dota_hero_base")){
                    hero = new Hero();
                    hero.setName(heroMatcher.group(1));
                    continue;
                }

                if(hero != null && trimmedLine.startsWith("{")){
                    chave ++;
                    continue;
                }

                if(hero != null && trimmedLine.startsWith("}")){
                    if(chave == 1){
                        heroes.put(hero.getHeroID(), hero);
                        hero = null;
                    }
                    chave--;
                    continue;
                }

                Matcher matcher = pattern.matcher(line);
                if(hero != null && matcher.find()) {
                    try {
                        String fieldName = matcher.group(1);
                        String fieldValue = matcher.group(2);
                        Field field = Hero.class.getDeclaredField(fieldName.substring(0,1).toLowerCase() + fieldName.substring(1));
                        field.setAccessible(true);
                        if(field.getType().equals(int.class)){
                            field.set(hero, Integer.parseInt(fieldValue));
                        }else if(Collection.class.isAssignableFrom(field.getType())) {
                            ParameterizedType listType = (ParameterizedType) field.getGenericType();
                            Class<?> listClass = (Class<?>) listType.getActualTypeArguments()[0];

                            if(listClass.equals(Integer.class)){
                                List<Integer> intList = new ArrayList<Integer>();
                                for(String s : fieldValue.split(",")) intList.add(Integer.valueOf(s));
                                field.set(hero, intList);
                            }else {
                                field.set(hero, Arrays.asList(fieldValue.split(",")));
                            }
                        }else{
                            field.set(hero, fieldValue);
                        }
                    } catch (Exception e) {

                    }
                }
            }
            mAdapter = new HeroCoverFlowAdapter(this);
            heroList = new ArrayList<Hero>(heroes.values());
            // specify an adapter (see also next example)
            Collections.sort(heroList, new Comparator<Hero>() {
                @Override public int compare(final Hero o1, final Hero o2) {
                    if(o1.getLocalizedName() == null){
                        o1.setLocalizedName("");
                    }
                    if(o2.getLocalizedName() == null){
                        o2.setLocalizedName("");
                    }
                    return o1.getLocalizedName().compareTo(o2.getLocalizedName());
                }
            });

            mAdapter.setData(heroList);
            mCoverFlow.setAdapter(mAdapter);
            mCoverFlow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    Intent push = new Intent(getApplicationContext(), HeroActivity.class);
                    push.putExtra("hero", heroList.get(position));
                    startActivity(push);

                    /*
                    Toast.makeText(MainActivity.this,
                            heroList.get(position).getLocalizedName(),
                            Toast.LENGTH_SHORT).show();
                     */
                }
            });

            mCoverFlow.setOnScrollPositionListener(new FeatureCoverFlow.OnScrollPositionListener() {
                @Override
                public void onScrolledToPosition(int position) {
                    mTitle.setText(heroList.get(position).getName());
                }

                @Override
                public void onScrolling() {
                    mTitle.setText("");
                }
            });
            AcessaHerois();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void AcessaHerois(){

        RequestTask task = new RequestTask();
        task.delegate = new IApiAccessResponse() {
            @Override
            public void postResult(String asyncresult) {
                try
                {
                    JSONObject reader = new JSONObject(asyncresult);
                    //Get the instance of JSONArray that contains JSONObjects
                    JSONArray jsonArray = reader.getJSONObject("result").optJSONArray("heroes");

                    //Iterate the jsonArray and print the info of JSONObjects
                    for(int i=0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        Hero hero = MainActivity.heroes.get(jsonObject.getInt("id"));
                        if(hero != null) {
                            if (hero.getName().equals(jsonObject.getString("name"))) {
                                hero.setLocalizedName(jsonObject.getString("localized_name"));
                            }
                        }
                    }




                    /*
                    mAdapter = new HeroAdapter(heroList, new HeroAdapter.OnListFragmentInteractionListener() {
                        @Override
                        public void onListFragmentInteraction(Hero item) {
                            Intent push = new Intent(getApplicationContext(), HeroActivity.class);
                            push.putExtra("hero", item);
                            startActivity(push);
                        }
                    });
                    */
                    //mRecyclerView.setAdapter(mAdapter);

                }catch (JSONException e){
                    Log.e("Json", e.getMessage());
                }
            }
        };
        task.execute("https://api.steampowered.com/IEconDOTA2_570/GetHeroes/v0001/?key=D694213C6F6E32058C2E37326AAF31FC&language=pt_br");
    }
/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_coverflow_activity, menu);
        return true;
    }
*/
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        /*
        if (id == R.id.action_settings) {
            return true;
        }
*/
        return super.onOptionsItemSelected(item);
    }
}
