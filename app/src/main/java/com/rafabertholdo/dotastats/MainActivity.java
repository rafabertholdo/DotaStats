package com.rafabertholdo.dotastats;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        AcessaHerois();
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
                    List<Hero> heroes = new ArrayList<Hero>();
                    //Iterate the jsonArray and print the info of JSONObjects
                    for(int i=0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        Hero hero = new Hero();
                        hero.setName(jsonObject.getString("name"));
                        hero.setId(jsonObject.getInt("id"));
                        hero.setLocalizedName(jsonObject.getString("localized_name"));
                        heroes.add(hero);
                    }
                    // specify an adapter (see also next example)
                    mAdapter = new ContactAdapter(heroes, new ContactAdapter.OnListFragmentInteractionListener() {
                        @Override
                        public void onListFragmentInteraction(Hero item) {

                        }
                    });
                    mRecyclerView.setAdapter(mAdapter);
                }catch (JSONException e){
                    Log.e("Json", e.getMessage());
                }
            }
        };
    }
}
