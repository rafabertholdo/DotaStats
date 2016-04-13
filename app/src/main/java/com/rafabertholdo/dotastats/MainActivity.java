package com.rafabertholdo.dotastats;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;

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

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private HeroAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private SearchView mSearchView;
    private SharedPreferences mPrefs;
    public static JSONObject abilityMap;


    public static Map<Integer, Hero> heroes;
    private ArrayList<Hero> heroList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        mSearchView = (SearchView) findViewById(R.id.mySearch);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mAdapter.getFilter().filter(newText);
                return false;
            }
        });

        //Creating a shared preference
        /*
        mPrefs = getPreferences(MODE_PRIVATE);
        heroes = new HashMap<Integer, Hero>();
        Gson gson = new Gson();
        String json = mPrefs.getString("heroes", "");
        if(json.equals("")){
        */

        heroes = new HashMap<Integer,Hero>();

        try {
            String jsonAbilities = LoadData("abilitydata.json");
            JSONObject abiltyReader = new JSONObject(jsonAbilities);
            abilityMap = abiltyReader.getJSONObject("abilitydata");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        loadHeores();
        AcessaHerois();
        /*
        }else
        {
            heroes = gson.fromJson(json, heroes.getClass());
            setAdapter();
        }
        */
    }

    public String LoadData(String inFile) {
        StringBuilder tContents = new StringBuilder();
        /*
        try {
            InputStream stream = getAssets().open(inFile);

            int size = stream.available();
            byte[] buffer = new byte[size];
            stream.read(buffer);
            stream.close();
            tContents = new String(buffer);
        } catch (IOException e) {
            // Handle exceptions here
        }
        */

        BufferedReader reader = null;
        try {
            reader = new BufferedReader(
                    new InputStreamReader(getAssets().open(inFile)));

            // do reading, usually loop until end of file reading
            String mLine;
            while ((mLine = reader.readLine()) != null) {
                tContents.append(mLine);
            }
        } catch (IOException e) {
            //log the exception
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    //log the exception
                }
            }
        }
        return tContents.toString();

    }

    private void loadHeores() {


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
                if (trimmedLine.startsWith("//"))
                    continue;

                Matcher heroMatcher = heroPattern.matcher(line);
                if (hero == null && heroMatcher.find() &&
                        heroMatcher.group(1).startsWith("npc_dota_hero_") &&
                        !heroMatcher.group(1).equals("npc_dota_hero_base")) {
                    hero = new Hero();
                    hero.setName(heroMatcher.group(1));
                    continue;
                }

                if (hero != null && trimmedLine.startsWith("{")) {
                    chave++;
                    continue;
                }

                if (hero != null && trimmedLine.startsWith("}")) {
                    if (chave == 1) {
                        heroes.put(hero.getHeroID(), hero);
                        hero = null;
                    }
                    chave--;
                    continue;
                }

                Matcher matcher = pattern.matcher(line);
                if (hero != null && matcher.find()) {
                    try {
                        String fieldName = matcher.group(1);
                        String fieldValue = matcher.group(2);
                        if(fieldName.startsWith("Ability"))
                        {
                            char c = fieldName.charAt(7);
                            if ((c >= '0' && c <= '9') && (!fieldValue.equals("attribute_bonus"))) {
                                hero.getAbilities().add(fieldValue);
                            }
                        }else {
                            Field field = Hero.class.getDeclaredField(fieldName.substring(0, 1).toLowerCase() + fieldName.substring(1));
                            field.setAccessible(true);
                            if (field.getType().equals(float.class)) {
                                field.set(hero, Float.parseFloat(fieldValue));
                            } else if (field.getType().equals(int.class)) {
                                field.set(hero, Integer.parseInt(fieldValue));
                            } else if (Collection.class.isAssignableFrom(field.getType())) {
                                ParameterizedType listType = (ParameterizedType) field.getGenericType();
                                Class<?> listClass = (Class<?>) listType.getActualTypeArguments()[0];

                                if (listClass.equals(Integer.class)) {
                                    List<Integer> intList = new ArrayList<Integer>();
                                    for (String s : fieldValue.split(","))
                                        intList.add(Integer.valueOf(s));
                                    field.set(hero, intList);
                                } else {
                                    field.set(hero, Arrays.asList(fieldValue.split(",")));
                                }
                            } else {
                                field.set(hero, fieldValue);
                            }
                        }
                    } catch (Exception e) {

                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setAdapter(){
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

        // specify an adapter (see also next example)
        mAdapter = new HeroAdapter(heroList, new HeroAdapter.OnListFragmentInteractionListener() {
            @Override
            public void onListFragmentInteraction(Hero item) {
                Intent push = new Intent(getApplicationContext(), HeroActivity.class);
                push.putExtra("hero", item);
                startActivity(push);
            }
        });
        mRecyclerView.setAdapter(mAdapter);
    }
    public void AcessaHerois(){

        RequestTask task = new RequestTask();
        task.execute("https://api.steampowered.com/IEconDOTA2_570/GetHeroes/v0001/?key=D694213C6F6E32058C2E37326AAF31FC&language=pt_br");
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
                        Hero hero = heroes.get(jsonObject.getInt("id"));
                        if(hero.getName().equals(jsonObject.getString("name"))){
                            hero.setLocalizedName(jsonObject.getString("localized_name"));
                        }
                    }

                    /*
                    SharedPreferences.Editor prefsEditor = mPrefs.edit();
                    Gson gson = new Gson();
                    String json = gson.toJson(heroes);
                    prefsEditor.putString("heroes", json);
                    prefsEditor.commit();
                    */
                    setAdapter();
                }catch (JSONException e){
                    Log.e("Json", e.getMessage());
                }
            }
        };
    }
}
